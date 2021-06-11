package org.serratec.ecommerce.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.ecommerce.dto.PedidoDTO;
import org.serratec.ecommerce.dto.PedidoDTOAll;
import org.serratec.ecommerce.dto.PedidoDTOComp;
import org.serratec.ecommerce.dto.ProdutosPedidosDTO;
import org.serratec.ecommerce.entities.ClienteEntity;
import org.serratec.ecommerce.entities.PedidoEntity;
import org.serratec.ecommerce.entities.ProdutosPedidosEntity;
import org.serratec.ecommerce.enums.StatusEnum;
import org.serratec.ecommerce.exceptions.ClienteNotFoundException;
import org.serratec.ecommerce.exceptions.EnderecoNotFoundException;
import org.serratec.ecommerce.exceptions.EstoqueInsuficienteException;
import org.serratec.ecommerce.exceptions.PedidoFinalizadoException;
import org.serratec.ecommerce.exceptions.PedidoNotFoundException;
import org.serratec.ecommerce.exceptions.ProdutoNotFoundException;
import org.serratec.ecommerce.exceptions.StatusUnacceptableException;
import org.serratec.ecommerce.mapper.PedidoMapper;
import org.serratec.ecommerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PedidoService {
	
	@Autowired
	PedidoRepository repository;
	
	@Autowired
	PedidoMapper mapper;
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	ProdutosPedidosService produtosPedidosService;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	EmailSenderService emailSender;
	
	@Autowired
	EnderecoService endService;
	
	public List<PedidoDTOAll> getAll() {
		return repository.findAll(Sort.by("dataDoPedido")).stream().map(mapper::entityToAll).collect(Collectors.toList());
	}
	
	public PedidoDTOComp getByNumeroDTO(Long numeroDoPedido) throws PedidoNotFoundException {
		Optional<PedidoEntity> pedido = repository.findByNumeroDoPedido(numeroDoPedido);
		if (pedido.isEmpty()) {
			throw new PedidoNotFoundException("Não existe pedido com esse numero.");
		}
		PedidoDTOComp comp = mapper.entityToDTOComp(pedido.get());
		
		ArrayList<ProdutosPedidosDTO> listaProduto = new ArrayList<>();
		
		List<ProdutosPedidosEntity> produtosPedidos = produtosPedidosService.findByPedido(pedido.get());
		for (ProdutosPedidosEntity produtosPedidosEntity : produtosPedidos) {
			var produtosPedidosDTO = new ProdutosPedidosDTO();
			produtosPedidosDTO.setNome(produtosPedidosEntity.getProduto().getNome());
			produtosPedidosDTO.setValor(produtosPedidosEntity.getPreco());
			produtosPedidosDTO.setQuantidade(produtosPedidosEntity.getQuantidade());
			
			listaProduto.add(produtosPedidosDTO);
		}
		comp.setProduto(listaProduto);
		return comp;
	}
	
	public PedidoEntity getByNumero(Long numeroDoPedido) throws PedidoNotFoundException {
		Optional<PedidoEntity> pedido = repository.findByNumeroDoPedido(numeroDoPedido);
		if (pedido.isEmpty()) {
			throw new PedidoNotFoundException("Não existe pedido com esse numero.");
		}
		return pedido.get();
	}
	
	public String create(PedidoDTO pedidoNovo) throws ProdutoNotFoundException, ClienteNotFoundException, EnderecoNotFoundException {
		PedidoEntity pedido = mapper.toEntity(pedidoNovo);
		ClienteEntity cliente = clienteService.findByUserNameOrEmail(pedidoNovo.getCliente());
		pedido.setCliente(cliente);
		pedido.setDataDoPedido(LocalDate.now());
		pedido.setStatus(StatusEnum.RECEBIDO);
		pedido = repository.save(pedido);
		var produtosPedidos = produtosPedidosService.create(pedido, pedidoNovo);
		pedido.setTotalProdutos(produtosPedidos.getPreco() * produtosPedidos.getQuantidade());
		repository.save(this.sedex(pedido));
		return "Criado com sucesso";
	}
	
	public String update(PedidoDTO pedido) throws PedidoNotFoundException, ProdutoNotFoundException, EstoqueInsuficienteException, StatusUnacceptableException, EnderecoNotFoundException {
		var pedidoEntity = getByNumero(pedido.getNumeroDoPedido());
		if (pedidoEntity.getStatus() == StatusEnum.RECEBIDO) {
			if (pedido.getProduto() != null) {
				var produtoEntity = produtoService.findByNome(pedido.getProduto());
				Optional<ProdutosPedidosEntity> produtosPedidos = Optional.ofNullable(produtosPedidosService.findByPedidoAndProduto(pedidoEntity, produtoEntity));
				if (produtosPedidos.isPresent()) {
					if (pedido.getQuantidade() <= produtoEntity.getQuantEstoque()) {
						Integer quantidade = produtosPedidos.get().getQuantidade();
						produtosPedidosService.update(produtosPedidos.get(), pedido.getQuantidade());
						pedidoEntity.setTotalProdutos(pedidoEntity.getTotalProdutos() + ((produtosPedidos.get().getQuantidade() - quantidade) * produtosPedidos.get().getPreco()));
						repository.save(pedidoEntity);
						return "Atualizado com sucesso!";
					} else throw new EstoqueInsuficienteException("Estoque insuficiente!");
				} else if (pedido.getQuantidade() <= produtoEntity.getQuantEstoque()) {
					var produtosPedidosNovo = produtosPedidosService.create(pedidoEntity, pedido);
					pedidoEntity.setTotalProdutos(pedidoEntity.getTotalProdutos() + produtosPedidosNovo.getPreco() * produtosPedidosNovo.getQuantidade());
				}
			}
			if (pedido.getEndEntrega() != null) {
				pedidoEntity.setEndEntrega(pedido.getEndEntrega());
				pedidoEntity.setValorTotalDoPedido(pedidoEntity.getValorTotalDoPedido() - pedidoEntity.getFrete());
				pedidoEntity = this.sedex(pedidoEntity);
			}
			repository.save(pedidoEntity);
			return "Atualizado com sucesso!";
		}
		throw new StatusUnacceptableException("Apenas pedidos recebidos podem ser editados.");
	}
	
	public String fechar(long numeroPedido) throws PedidoNotFoundException, EstoqueInsuficienteException, StatusUnacceptableException {
		PedidoEntity pedido = this.getByNumero(numeroPedido);
		if (pedido.getStatus() == StatusEnum.RECEBIDO) {
			List<ProdutosPedidosEntity> listaProdutosPedidos = produtosPedidosService.findByPedido(pedido);
			for (ProdutosPedidosEntity produtosPedidosEntity : listaProdutosPedidos) {
				produtoService.vender(produtosPedidosEntity.getProduto(), produtosPedidosEntity.getQuantidade());
			}
			pedido.setStatus(StatusEnum.FECHADO);
			repository.save(pedido);
			emailSender.sendSimpleMessage(pedido.getCliente().getEmail(), "Pedido " + pedido.getNumeroDoPedido() + " fechado com sucesso", "Testando email de fechamento de pedido");
			return "Fechado com sucesso!";
		}
		throw new StatusUnacceptableException("Apenas pedidos recebidos podem ser fechados.");
	}
	
	public String pagar(Long numeroDoPedido) throws PedidoNotFoundException, StatusUnacceptableException, EnderecoNotFoundException {
		PedidoEntity pedido = this.getByNumero(numeroDoPedido);
		if (pedido.getStatus() == StatusEnum.FECHADO) {
			pedido.setDataDoPedido(LocalDate.now());
			pedido.setStatus(StatusEnum.PAGO);
			repository.save(this.sedexPrazo(pedido));
			emailSender.sendSimpleMessage(pedido.getCliente().getEmail(), "Recebemos seu pagamento", "Recebemos seu pagamento, em breve seus produtos serão enviados à transportadora.");
			return "Pagamento Recebido!";
		}
		throw new StatusUnacceptableException("Favor fechar o pedido antes de efetuar pagamento!"); 
	}
	
	public String transportar(Long numeroDoPedido) throws PedidoNotFoundException, StatusUnacceptableException {
		PedidoEntity pedido = this.getByNumero(numeroDoPedido);
		if (pedido.getStatus() == StatusEnum.PAGO) {
			pedido.setStatus(StatusEnum.TRANSPORTE);
			repository.save(pedido);
			emailSender.sendSimpleMessage(pedido.getCliente().getEmail(), "Pedido " + pedido.getNumeroDoPedido() + " está a caminho", "Temos uma ótima notícia, seu produtos já estão em transporte!");
			return "Pedido em transporte!";
		}
		throw new StatusUnacceptableException("Apenas pedidos pagos podem ser enviados para transporte.");
	}
	
	public String entregar(Long numeroDoPedido) throws PedidoNotFoundException, StatusUnacceptableException {
		PedidoEntity pedido = this.getByNumero(numeroDoPedido);
		if (pedido.getStatus() == StatusEnum.TRANSPORTE) {
			pedido.setStatus(StatusEnum.ENTREGUE);
			repository.save(pedido);
			emailSender.sendSimpleMessage(pedido.getCliente().getEmail(), "Pedido " + pedido.getNumeroDoPedido() + " entregue com sucesso", "Recebemos a confirmação de que seus produtos foram entregues. Agradecemos a preferência!");
			return "Pedido entregue!";
		}
		throw new StatusUnacceptableException("Apenas pedidos em transporte podem ser entregues.");
	}
	
	public String delete(Long numeroDoPedido) throws PedidoNotFoundException, PedidoFinalizadoException, EstoqueInsuficienteException {
		PedidoEntity pedido = this.getByNumero(numeroDoPedido);
		if(pedido.getStatus() == StatusEnum.ENTREGUE) throw new PedidoFinalizadoException("Pedidos finalizados nao podem ser deletados");
		if (pedido.getStatus() == StatusEnum.FECHADO) {
			List<ProdutosPedidosEntity> listaPedProd = produtosPedidosService.findByPedido(pedido);
			for (ProdutosPedidosEntity produtosPedidosEntity : listaPedProd) {
				produtoService.retornaEstoque(produtosPedidosEntity.getProduto(), produtosPedidosEntity.getQuantidade());
				produtosPedidosService.delete(produtosPedidosEntity);
			}
			repository.delete(pedido);
			return "Pedido deletado com sucesso!";
		}
		if (pedido.getStatus() == StatusEnum.RECEBIDO) {
			List<ProdutosPedidosEntity> listaPedProd = produtosPedidosService.findByPedido(pedido);
			for (ProdutosPedidosEntity produtosPedidosEntity : listaPedProd) {
				produtosPedidosService.delete(produtosPedidosEntity);
			}
			repository.delete(pedido);
			return "Pedido deletado com sucesso!";
		}
		if (pedido.getStatus() != StatusEnum.CANCELADO) {
			List<ProdutosPedidosEntity> listaPedProd = produtosPedidosService.findByPedido(pedido);
			for (ProdutosPedidosEntity produtosPedidosEntity : listaPedProd) {
				produtoService.retornaEstoque(produtosPedidosEntity.getProduto(), produtosPedidosEntity.getQuantidade());
			}
			pedido.setStatus(StatusEnum.CANCELADO);
			repository.save(pedido);
			return "Pedido cancelado com sucesso!";
		}
		return "Esse pedido esta cancelado e nao pode ser alterado!";
	}
	
	public PedidoEntity sedex(PedidoEntity pedido) throws EnderecoNotFoundException {
		var restTemplate = new RestTemplate();
		String cepDestino = endService.findByNomeAndCliente(pedido.getEndEntrega(), pedido.getCliente()).getCep();
		String sedex = restTemplate.getForObject("http://ws.correios.com.br/calculador/CalcPrecoPrazo.aspx?nCdEmpresa=08082650&sDsSenha=564321&sCepOrigem=25955050&sCepDestino="+cepDestino+"&nVlPeso=1&nCdFormato=1&nVlComprimento=20&nVlAltura=20&nVlLargura=20&sCdMaoPropria=n&nVlValorDeclarado=0&sCdAvisoRecebimento=n&nCdServico=40010&nVlDiametro=0&StrRetorno=xml&nIndicaCalculo=3", String.class);
		int valorInicio = sedex.indexOf("Valor>") + 6;
		int valorFim = sedex.indexOf("</Valor");
		pedido.setFrete(Double.parseDouble(sedex.substring(valorInicio, valorFim).replace(',', '.')));
		pedido.setValorTotalDoPedido(pedido.getTotalProdutos() + pedido.getFrete());
		int prazoInicio = sedex.indexOf("PrazoEntrega>") + 13;
		int prazoFim = sedex.indexOf("</PrazoEntrega>");
		pedido.setDataEntrega(pedido.getDataDoPedido().plusDays(Long.parseLong(sedex.substring(prazoInicio, prazoFim)) + 1));
		return pedido;
	}
	
	public PedidoEntity sedexPrazo(PedidoEntity pedido) throws EnderecoNotFoundException {
		var restTemplate = new RestTemplate();
		String cepDestino = endService.findByNomeAndCliente(pedido.getEndEntrega(), pedido.getCliente()).getCep();
		String sedex = restTemplate.getForObject("http://ws.correios.com.br/calculador/CalcPrecoPrazo.aspx?nCdEmpresa=08082650&sDsSenha=564321&sCepOrigem=25955050&sCepDestino="+cepDestino+"&nVlPeso=1&nCdFormato=1&nVlComprimento=20&nVlAltura=20&nVlLargura=20&sCdMaoPropria=n&nVlValorDeclarado=0&sCdAvisoRecebimento=n&nCdServico=40010&nVlDiametro=0&StrRetorno=xml&nIndicaCalculo=3", String.class);
		int prazoInicio = sedex.indexOf("PrazoEntrega>") + 13;
		int prazoFim = sedex.indexOf("</PrazoEntrega>");
		pedido.setDataEntrega(pedido.getDataDoPedido().plusDays(Long.parseLong(sedex.substring(prazoInicio, prazoFim)) + 1));
		return pedido;
	}
}
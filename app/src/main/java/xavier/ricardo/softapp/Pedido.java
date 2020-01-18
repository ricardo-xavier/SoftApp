package xavier.ricardo.softapp;

import java.util.List;

public class Pedido {
	
	private String fornecedor;
	private String data;
	private int codOrcamento;
	private int codPedido;
	private String vendedor;
	private String observacao;
	private Cliente cliente;
	
	private List<Area> areas;

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getCodOrcamento() {
		return codOrcamento;
	}

	public void setCodOrcamento(int codOrcamento) {
		this.codOrcamento = codOrcamento;
	}

	public int getCodPedido() {
		return codPedido;
	}

	public void setCodPedido(int codPedido) {
		this.codPedido = codPedido;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}

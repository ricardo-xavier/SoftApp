package xavier.ricardo.softapp;

import java.util.List;

public class Area {

	private String codigo;
	private List<Item> itens;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
}

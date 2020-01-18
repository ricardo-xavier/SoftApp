package xavier.ricardo.softapp;

import java.util.List;

public class Compromisso {
	
	private String usuario;
	private String data;
	private String hora;
	private String natureza;
	private String parceiro;
	private String pendencia;
	private String contato;
	private String papel;
	private String fone1;
	private String fone2;
	private String celular;
	private String rua;
	private String nro;
	private String complemento;
	private String bairro;
	private String cidade;
	private String pedido;
	private String orcamento;
	private String codFornecedor;
	private String datOrcamento;
	private int codOrcamento;
	private int nroPedido;	
	private List<Anexo> anexos;
	private String encerramento;
	private String nome;
	private String documento;
	private String email;
	private String json;
	
	public String getNatureza() {
		return natureza != null ? natureza.trim() : null;
	}
	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}
	public String getParceiro() {
		return parceiro != null ? parceiro.trim() : null;
	}
	public void setParceiro(String parceiro) {
		this.parceiro = parceiro;
	}
	public String getHora() {
		return hora != null ? hora.trim() : null;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getPendencia() {
		return pendencia != null ? pendencia.trim() : null;
	}
	public void setPendencia(String pendencia) {
		this.pendencia = pendencia;
	}
	public String getContato() {
		return contato != null ? contato.trim() : null;
	}
	public void setContato(String contato) {
		this.contato = contato;
	}
	public String getRua() {
		return rua != null ? rua.trim() : null;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getNro() {
		return nro != null ? nro.trim() : null;
	}
	public void setNro(String nro) {
		this.nro = nro;
	}
	public String getComplemento() {
		return complemento != null ? complemento.trim() : null;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro != null ? bairro.trim() : null;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade != null ? cidade.trim() : null;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getFone1() {
		return fone1 != null ? fone1.trim() : null;
	}
	public void setFone1(String fone1) {
		this.fone1 = fone1;
	}
	public String getFone2() {
		return fone2 != null ? fone2.trim() : null;
	}
	public void setFone2(String fone2) {
		this.fone2 = fone2;
	}
	public String getCelular() {
		return celular != null ? celular.trim() : null;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getPapel() {
		return papel != null ? papel.trim() : null;
	}
	public void setPapel(String papel) {
		this.papel = papel;
	}
	public String getPedido() {
		return pedido;
	}
	public void setPedido(String pedido) {
		this.pedido = pedido;
	}
	public String getOrcamento() {
		return orcamento;
	}
	public void setOrcamento(String orcamento) {
		this.orcamento = orcamento;
	}
	public String getCodFornecedor() {
		return codFornecedor;
	}
	public void setCodFornecedor(String codFornecedor) {
		this.codFornecedor = codFornecedor;
	}
	public String getDatOrcamento() {
		return datOrcamento;
	}
	public void setDatOrcamento(String datOrcamento) {
		this.datOrcamento = datOrcamento;
	}
	public int getCodOrcamento() {
		return codOrcamento;
	}
	public void setCodOrcamento(int codOrcamento) {
		this.codOrcamento = codOrcamento;
	}
	public int getNroPedido() {
		return nroPedido;
	}
	public void setNroPedido(int nroPedido) {
		this.nroPedido = nroPedido;
	}
	public List<Anexo> getAnexos() {
		return anexos;
	}
	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getEncerramento() {
		return encerramento;
	}
	public void setEncerramento(String encerramento) {
		this.encerramento = encerramento;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}

}

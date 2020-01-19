package xavier.ricardo.softapp;

public class Imagem {
    private String fornecedor;
    private String data;
    private int orcamento;
    private String image64;
    private String id;

    public String getImage64() {
        return image64;
    }
    public void setImage64(String image64) {
        this.image64 = image64;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getFornecedor() {
        return fornecedor;
    }
    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }
    public int getOrcamento() {
        return orcamento;
    }
    public void setOrcamento(int orcamento) {
        this.orcamento = orcamento;
    }
}

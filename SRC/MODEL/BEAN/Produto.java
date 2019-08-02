package model.bean;

public class Produto {
    private Integer procod;
    private String pronome;
    private Double procusto;
    private Double propreco;
    private GrupoProduto grupoProduto;
    private String nomegrupo;
    private Integer prosaldo;
    private Integer proEstoqueMin;
    private String proStatus;
    private String procurar;
    
    public Produto() {
    }
    
    public Integer getProcod() {
        return procod;
    }

    public void setProcod(Integer procod) {
        this.procod = procod;
    }

    public String getPronome() {
        return pronome;
    }

    public void setPronome(String pronome) {
        this.pronome = pronome;
    }

    public Double getProcusto() {
        return procusto;
    }

    public void setProcusto(Double procusto) {
        this.procusto = procusto;
    }

    public Double getPropreco() {
        return propreco;
    }

    public void setPropreco(Double propreco) {
        this.propreco = propreco;
    }

    public GrupoProduto getGrupoProduto() {
        return grupoProduto;
    }

    public void setGrupoProduto(GrupoProduto grupoProduto) {
        this.grupoProduto = grupoProduto;
    }

    public int getProsaldo() {
        return prosaldo;
    }

    public void setProsaldo(int prosaldo) {
        this.prosaldo = prosaldo;
    }  

    public String getProcurar() {
        return procurar;
    }

    public void setProcurar(String procurar) {
        this.procurar = procurar;
    }

    public String getNomegrupo() {
        return nomegrupo;
    }

    public void setNomegrupo(String nomegrupo) {
        this.nomegrupo = nomegrupo;
    }

    public int getProEstoqueMin() {
        return proEstoqueMin;
    }

    public void setProEstoqueMin(int proEstoqueMin) {
        this.proEstoqueMin = proEstoqueMin;
    }

    public String getProStatus() {
        return proStatus;
    }

    public void setProStatus(String proStatus) {
        this.proStatus = proStatus;
    }
    
}
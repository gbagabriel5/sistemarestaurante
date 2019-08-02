package MODEL.BEAN;

import model.bean.Fornecedor;

public class Item {
    private Integer itcod;
    private String itnome;
    private String itdesc;
    private Double itpreco;
    private Double itqtde;
    private String itmedida;
    private Integer itvalidade;
    private Fornecedor fornecedor;
    private String nomefor;
    private String procurar;

    public Item() {
    }

    public Integer getItcod() {
        return itcod;
    }

    public void setItcod(Integer itcod) {
        this.itcod = itcod;
    }

    public String getItnome() {
        return itnome;
    }

    public void setItnome(String itnome) {
        this.itnome = itnome;
    }

    public String getItdesc() {
        return itdesc;
    }

    public void setItdesc(String itdesc) {
        this.itdesc = itdesc;
    }

    public Double getItpreco() {
        return itpreco;
    }

    public void setItpreco(Double itpreco) {
        this.itpreco = itpreco;
    }

    public Double getItqtde() {
        return itqtde;
    }

    public void setItqtde(Double itqtde) {
        this.itqtde = itqtde;
    }

    public String getItmedida() {
        return itmedida;
    }

    public void setItmedida(String itmedida) {
        this.itmedida = itmedida;
    }

    public Integer getItvalidade() {
        return itvalidade;
    }

    public void setItvalidade(Integer itvalidade) {
        this.itvalidade = itvalidade;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getNomefor() {
        return nomefor;
    }

    public void setNomefor(String nomefor) {
        this.nomefor = nomefor;
    }

    public String getProcurar() {
        return procurar;
    }

    public void setProcurar(String procurar) {
        this.procurar = procurar;
    }
}
package model.bean;

public class Venda {
    private Integer vencod;
    private String cxvencod;
    private String venmcod;
    private String vendata;       
    private String venhora;    
    private String nomeProduto;
    private Integer qtdeItem;
    private Double ventotal;
    private String pagamento;
    private Double valorRecebido;
    private Double troco;
    private Double saida;
    private String desc;
    private String venmstatus;
    
    public Venda() {
    }

    public Integer getVencod() {
        return vencod;
    }

    public void setVencod(Integer vencod) {
        this.vencod = vencod;
    }

    public String getCxvencod() {
        return cxvencod;
    }

    public void setCxvencod(String cxvencod) {
        this.cxvencod = cxvencod;
    }

    public String getVenmcod() {
        return venmcod;
    }

    public void setVenmcod(String venmcod) {
        this.venmcod = venmcod;
    }

    public String getVendata() {
        return vendata;
    }

    public void setVendata(String vendata) {
        this.vendata = vendata;
    }
  
    public Double getVentotal() {
        return ventotal;
    }

    public void setVentotal(Double ventotal) {
        this.ventotal = ventotal;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Integer getQtdeItem() {
        return qtdeItem;
    }

    public void setQtdeItem(Integer qtdeItem) {
        this.qtdeItem = qtdeItem;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public String getVenhora() {
        return venhora;
    }

    public void setVenhora(String venhora) {
        this.venhora = venhora;
    }

    public Double getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(Double valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    public Double getTroco() {
        return troco;
    }

    public void setTroco(Double troco) {
        this.troco = troco;
    }

    public Double getSaida() {
        return saida;
    }

    public void setSaida(Double saida) {
        this.saida = saida;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVenmstatus() {
        return venmstatus;
    }

    public void setVenmstatus(String venmstatus) {
        this.venmstatus = venmstatus;
    }
    
}
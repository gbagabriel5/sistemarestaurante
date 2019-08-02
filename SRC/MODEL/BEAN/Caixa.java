package model.bean;


public class Caixa {
 
    private Integer cxcod;
    private String cxdthabre;
    private String cxdthfecha;
    private Double cxsinicial;    
    private Double cxsfinal;
    private String cxobs;   
    private String cxstatus;
    
    public Caixa() {
    }   
    
    public Integer getCxcod() {
        return cxcod;
    }

    public void setCxcod(Integer cxcod) {
        this.cxcod = cxcod;
    }

    public String getCxdthabre() {
        return cxdthabre;
    }

    public void setCxdthabre(String cxdthabre) {
        this.cxdthabre = cxdthabre;
    }

    public String getCxdthfecha() {
        return cxdthfecha;
    }

    public void setCxdthfecha(String cxdthfecha) {
        this.cxdthfecha = cxdthfecha;
    }

    public Double getCxsinicial() {
        return cxsinicial;
    }

    public void setCxsinicial(Double cxsinicial) {
        this.cxsinicial = cxsinicial;
    }

    public Double getCxsfinal() {
        return cxsfinal;
    }

    public void setCxsfinal(Double cxsfinal) {
        this.cxsfinal = cxsfinal;
    }

    public String getCxobs() {
        return cxobs;
    }

    public void setCxobs(String cxobs) {
        this.cxobs = cxobs;
    }

    public String getCxstatus() {
        return cxstatus;
    }

    public void setCxstatus(String cxstatus) {
        this.cxstatus = cxstatus;
    }
    
}
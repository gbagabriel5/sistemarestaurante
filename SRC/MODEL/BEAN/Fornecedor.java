package model.bean;

/**
 *
 * @author Gabriel Brandao
 */
public class Fornecedor {
    private Integer forcod;
    private String fornome;
    private String forcnpj;
    private String forcep;
    private String forend;
    private String forfone;
    private String procurar;
    
    public Fornecedor() {
    }

    public Integer getForcod() {
        return forcod;
    }

    public void setForcod(Integer forcod) {
        this.forcod = forcod;
    }

    public String getFornome() {
        return fornome;
    }

    public void setFornome(String fornome) {
        this.fornome = fornome;
    }

    public String getForcnpj() {
        return forcnpj;
    }

    public void setForcnpj(String forcnpj) {
        this.forcnpj = forcnpj;
    }

    public String getForcep() {
        return forcep;
    }

    public void setForcep(String forcep) {
        this.forcep = forcep;
    }

    public String getForend() {
        return forend;
    }

    public void setForend(String forend) {
        this.forend = forend;
    }

    public String getForfone() {
        return forfone;
    }

    public void setForfone(String forfone) {
        this.forfone = forfone;
    }

    public String getProcurar() {
        return procurar;
    }

    public void setProcurar(String procurar) {
        this.procurar = procurar;
    }

    @Override
    public String toString() {
        return fornome;
    }
        
}

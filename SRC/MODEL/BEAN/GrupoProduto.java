package model.bean;

public class GrupoProduto {
    private Integer grpcod;
    private String grpdesc;
    private String procurar;
    
    public GrupoProduto() {
    }

    public Integer getGrpcod() {
        return grpcod;
    }

    public void setGrpcod(Integer grpcod) {
        this.grpcod = grpcod;
    }

    public String getGrpdesc() {
        return grpdesc;
    }

    public void setGrpdesc(String grpdesc) {
        this.grpdesc = grpdesc;
    }

    public String getProcurar() {
        return procurar;
    }

    public void setProcurar(String procurar) {
        this.procurar = procurar;
    }

    @Override
    public String toString() {
        return grpcod+"-"+grpdesc;
    }
    
}
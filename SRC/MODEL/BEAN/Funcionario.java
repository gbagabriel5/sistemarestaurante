package model.bean;

public class Funcionario {
    private Integer funcod;
    private String funnome;
    private String funrg;
    private String funcpf;
    private String fundtnasc;
    private String funend;
    private String funfone;
    private Double funsalario;
    private Departamento departamento;
    private String nomeDep;
    private String procurar;
    
    public Funcionario() {
    }
    
    public Integer getFuncod() {
        return funcod;
    }

    public void setFuncod(Integer funcod) {
        this.funcod = funcod;
    }

    public String getFunnome() {
        return funnome;
    }

    public void setFunnome(String funnome) {
        this.funnome = funnome;
    }

    public String getFunrg() {
        return funrg;
    }

    public void setFunrg(String funrg) {
        this.funrg = funrg;
    }

    public String getFuncpf() {
        return funcpf;
    }

    public void setFuncpf(String funcpf) {
        this.funcpf = funcpf;
    }

    public String getFundtnasc() {
        return fundtnasc;
    }

    public void setFundtnasc(String fundtnasc) {
        this.fundtnasc = fundtnasc;
    }

    public String getFunend() {
        return funend;
    }

    public void setFunend(String funend) {
        this.funend = funend;
    }

    public String getFunfone() {
        return funfone;
    }

    public void setFunfone(String funfone) {
        this.funfone = funfone;
    }

    public Double getFunsalario() {
        return funsalario;
    }

    public void setFunsalario(Double funsalario) {
        this.funsalario = funsalario;
    }
       
    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public String getProcurar() {
        return procurar;
    }

    public void setProcurar(String procurar) {
        this.procurar = procurar;
    }

    public String getNomeDep() {
        return nomeDep;
    }

    public void setNomeDep(String nomeDep) {
        this.nomeDep = nomeDep;
    }
        
}
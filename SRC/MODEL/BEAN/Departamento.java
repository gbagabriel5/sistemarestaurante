package model.bean;

public class Departamento {
    private Integer depcod;
    private String depdesc;

    public Departamento() {
    }

    public Integer getDepcod() {
        return depcod;
    }

    public void setDepcod(Integer depcod) {
        this.depcod = depcod;
    }

    public String getDepdesc() {
        return depdesc;
    }

    public void setDepdesc(String depdesc) {
        this.depdesc = depdesc;
    }

    @Override
    public String toString() {
        return depdesc;
    }        
}

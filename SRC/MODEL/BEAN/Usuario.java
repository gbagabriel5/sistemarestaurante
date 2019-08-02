package model.bean;


public class Usuario {
    
    private Integer usuCodigo;
    private String usuUsuario;
    private String usuTipo;
    private String usuSenha;
    private String usutatus;
    public String getUsuProcurar() {
        return usuProcurar;
    }

    public void setUsuProcurar(String usuProcurar) {
        this.usuProcurar = usuProcurar;
    }
    private String usuProcurar;

    public Integer getUsuCodigo() {
        return usuCodigo;
    }

    public void setUsuCodigo(Integer usuCodigo) {
        this.usuCodigo = usuCodigo;
    }

    public String getUsuUsuario() {
        return usuUsuario;
    }

    public void setUsuUsuario(String usuUsuario) {
        this.usuUsuario = usuUsuario;
    }

    public String getUsuTipo() {
        return usuTipo;
    }

    public void setUsuTipo(String usuTipo) {
        this.usuTipo = usuTipo;
    }

    public String getUsuSenha() {
        return usuSenha;
    }

    public void setUsuSenha(String usuSenha) {
        this.usuSenha = usuSenha;
    }

    public String getUsutatus() {
        return usutatus;
    }

    public void setUsutatus(String usutatus) {
        this.usutatus = usutatus;
    }
    
    
}

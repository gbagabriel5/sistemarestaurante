package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ExecutaSql {
    public Statement stm;
    public ResultSet rs;
    public Connection conn;
    private String driver="com.mysql.jdbc.Driver";
    private String caminho="jdbc:mysql://localhost:3306/sistemarestaurante";
    private String usuario="root";
    private String senha="123";
    
    public void conexao(){
        try{
            System.setProperty("jdbc.Drivers", driver);
            conn=DriverManager.getConnection(caminho, usuario, senha);            
        }catch(Exception erro){
             JOptionPane.showMessageDialog(null, "erro de BD2 "+erro.getMessage());            
        }
    }
    
    public void executaSQL(String sql){
        try {
            stm=conn.createStatement(rs.TYPE_SCROLL_INSENSITIVE,rs.CONCUR_READ_ONLY);
            rs=stm.executeQuery(sql);
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "erro no executa Sql "+ex.getMessage());
        }        
    }
    
    public void desconecta(){
        try{
            conn.close();            
        }catch(Exception erro){            
        }
    }
}

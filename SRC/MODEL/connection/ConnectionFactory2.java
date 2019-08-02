package model.connection;

import java.sql.*;
import javax.swing.JOptionPane;


public class ConnectionFactory2 { 
    
    
   public Statement pstm;
   public ResultSet rs;
   
   private String driver = "com.mysql.jdbc.Driver";
   private String url = "jdbc:mysql://localhost:3306/sistemarestaurante";
   private String login = "root";
   private String senha="123";
   public Connection con;

  
   public void conexao(){
       
       try{
       System.setProperty("jdbc.Drivers",driver);
       con=DriverManager.getConnection(url, login, senha);
//       JOptionPane.showMessageDialog(null, "Conectado com o BD");
   }catch (SQLException ex){
       JOptionPane.showMessageDialog(null,"Erro de BD:\n" +ex.getMessage());
       
   }   
    
  }
   
  public void executaSql(String sql){ 
      try{                              //minusculo e M              //inicio e fim, e fim e inicio
           pstm = con.createStatement(rs.TYPE_SCROLL_INSENSITIVE,rs.CONCUR_READ_ONLY);
           rs = pstm.executeQuery(sql);
      }catch (SQLException ex){
           JOptionPane.showMessageDialog(null,"Erro de SQL:\n" +ex.getMessage());
      }
               
   }
   
   public void desconecta(){
       try{
           con.close();
          // JOptionPane.showMessageDialog(null, "BD deconectado com sucesso");
           
       }catch (SQLException ex){
           JOptionPane.showMessageDialog(null, "Erro de fechar DB\n" +ex.getMessage());
           
       }
   }

    public PreparedStatement prepareStatement(String sql) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
}
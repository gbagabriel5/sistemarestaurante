package model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ConnectionFactory {
   
    public static Connection getConnection(){
        Connection con=null;
        Statement stm;
        ResultSet rs;
        
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url="jdbc:mysql://localhost:3306/sistemarestaurante";
            String login="root";
            String senha="123";
            con=DriverManager.getConnection(url,login,senha);
            
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "erro de BD "+erro.getMessage());            
        }       
        return con;
    }
}

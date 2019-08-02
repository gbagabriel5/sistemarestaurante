package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import model.bean.Cliente;
import model.connection.ConnectionFactory;
import model.connection.ConnectionFactory2;

public class ClienteDao {
    Connection con=null;
    Cliente cliente = new Cliente();
    ConnectionFactory2 conex = new ConnectionFactory2();
    String sql;
    PreparedStatement pstm;
    ResultSet rs;
    
    public void salvarCliente(Cliente cliente){
        
         con=ConnectionFactory.getConnection();
        
        
        try{
            sql="INSERT INTO cliente(clinome,clirg,clicpf,cliend,clifone)VALUES(?,?,?,?,?);";
            
        pstm=con.prepareStatement(sql);
        pstm.setString(1, cliente.getClinome());
        pstm.setString(2, cliente.getClirg());
        pstm.setString(3, cliente.getClicpf());        
        pstm.setString(4, cliente.getCliend());
        pstm.setString(5, cliente.getClifone());
        
        
        pstm.execute();
            JOptionPane.showMessageDialog(null,cliente.getClinome()+ " Foi Salvo com Sucesso!");
            
        }catch(Exception erro){
                JOptionPane.showMessageDialog(null, "ERRO PSTM "+erro.getMessage());
        }
        
    }
    public Cliente BuscarCliente(String nome){
       
        con=ConnectionFactory.getConnection();
      
      try{
          //sql="select * from cliente where clinome ='" +nome+ "'";
          sql="select * from cliente where clinome like'%" +nome+ "%'";
        
          pstm=con.prepareStatement(sql);
          
          pstm.setString(1, nome);
          rs=pstm.executeQuery();
          if(rs.first()){
              cliente.setClicod(rs.getInt("clicod"));
              cliente.setClinome(rs.getString("clinome"));
              cliente.setClirg(rs.getString("clirg"));
              cliente.setClicpf(rs.getString("clicpf"));              
              cliente.setCliend(rs.getString("cliend"));
              cliente.setClifone(rs.getString("clifone"));                                        
              
           }else{
                JOptionPane.showMessageDialog(null, "Cliente não Encontrado!");
            }          
      }catch(Exception erro){
          //JOptionPane.showMessageDialog(null, "erro de rs "+erro.getMessage(),null,JOptionPane.ERROR_MESSAGE);          
      }      
      return cliente;        
    }
    
    public void excluirCliente(Cliente cliente){
        con=ConnectionFactory.getConnection();
        try{
            sql="delete from cliente where clicod=?";
            
            pstm=con.prepareStatement(sql);
            pstm.setInt(1, cliente.getClicod());
            pstm.execute();
             JOptionPane.showMessageDialog(null," Excluido com Sucesso!");            
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "ERRO PSTM "+erro.getMessage());            
        }        
    }
    
       public void updateCliente(Cliente cliente){
        con=ConnectionFactory.getConnection();
        try{
            sql="update cliente set clinome=? ,clirg=? ,clicpf=?,cliend=?,clifone=? where clicod=?";
       pstm=con.prepareStatement(sql);
        pstm.setString(1, cliente.getClinome());
        pstm.setString(2, cliente.getClirg());
        pstm.setString(3, cliente.getClicpf());        
        pstm.setString(4,cliente.getCliend());        
        pstm.setString(5, cliente.getClifone());                     
        pstm.setInt(6, cliente.getClicod());
        pstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Dados Alterados com Sucesso!");            
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "erro de pstm "+erro.getMessage(),null,JOptionPane.ERROR_MESSAGE);            
        }
    }        
           public Cliente ProcurarCliente(Cliente c){
            conex.conexao();
            conex.executaSql("SELECT *FROM cliente WHERE clinome LIKE'%"+c.getProcurar()+"%'");
        try{
           conex.rs.first();
             c.setClicod(conex.rs.getInt("clicod"));
             c.setClinome(conex.rs.getString("clinome"));
             c.setClirg(conex.rs.getString("clirg"));
             c.setClicpf(conex.rs.getString("clicpf"));             
             c.setCliend(conex.rs.getString("cliend"));
             c.setClifone(conex.rs.getString("clifone"));
             
        }catch(Exception ex){
//             JOptionPane.showMessageDialog(null,"Cliente não cadastrado.");
        }
        conex.desconecta();
        return c; 
   }
           public Cliente ProcurarCliente1(Cliente c){
            conex.conexao();
            conex.executaSql("SELECT *FROM cliente WHERE clicod="+c.getProcurar());
        try{
           conex.rs.first();
             c.setClicod(conex.rs.getInt("clicod"));
             c.setClinome(conex.rs.getString("clinome"));
             c.setClirg(conex.rs.getString("clirg"));
             c.setClicpf(conex.rs.getString("clicpf"));             
             c.setCliend(conex.rs.getString("cliend"));
             c.setClifone(conex.rs.getString("clifone"));
             
        }catch(Exception ex){
//             JOptionPane.showMessageDialog(null,"Cliente não cadastrado.");
        }
        conex.desconecta();
        return c; 
   }

}
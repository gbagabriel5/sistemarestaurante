package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.bean.Fornecedor;
import model.connection.ConnectionFactory;
import model.connection.ConnectionFactory2;

public class FornecedorDao {
    
    ConnectionFactory2 conex = new ConnectionFactory2();
    ConnectionFactory2 conexao = new ConnectionFactory2();
    Connection con=null;
    ResultSet rs;    
    String sql,nomeDep;    
    int cod;
    PreparedStatement pstm;    
    
    public void salvarFornecedor(Fornecedor forn){        
        con=ConnectionFactory.getConnection();
        
        try{
            sql="INSERT INTO fornecedor(fornome,forcnpj,forcep,forend,forfone)VALUES(?,?,?,?,?);";
            
        pstm=con.prepareStatement(sql);
                 
        pstm.setString(1, forn.getFornome());
        pstm.setString(2, forn.getForcnpj());
        pstm.setString(3, forn.getForcep());
        pstm.setString(4, forn.getForend());
        pstm.setString(5, forn.getForfone());                
        pstm.execute();
            JOptionPane.showMessageDialog(null,forn.getFornome()+ " Foi Salvo com Sucesso!");            
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "ERRO PSTM "+erro.getMessage());
        }        
    }    
    public Fornecedor buscarFor(String nome){
        Fornecedor f=new Fornecedor();
        con=ConnectionFactory.getConnection();
        try{
           //sql="SELECT *FROM FUNCIONARIO WHERE FUNNOME LIKE'%" +nome+ "%'";
          sql="select * from fornecedor where fornome like'%" +nome+ "%'";
           pstm=con.prepareStatement(sql);
           //pstm.setInt(1, cod);
          rs=pstm.executeQuery();
          if(rs.first()){
              f.setForcod(rs.getInt("forcod"));
              f.setFornome(rs.getString("fornome"));
              f.setForcnpj(rs.getString("forcnpj"));
              f.setForcep(rs.getString("forcep"));                            
              f.setForend(rs.getString("forend"));              
              f.setForfone(rs.getString("forfone"));
              
          }else{
              JOptionPane.showMessageDialog(null, "FORNECEDOR NÃO ENCONTRADO");
          }
        }catch(Exception erro){
          //JOptionPane.showMessageDialog(null, "ERRO DE PSTM "+erro.getMessage());  
        }
      return f;  
    }
    
    public void updateFor(Fornecedor f){        
        con=ConnectionFactory.getConnection();
        try{
            sql="update fornecedor set fornome=?,forcnpj=?,forcep=?,forend=?,forfone=? where forcod=?";
            pstm=con.prepareStatement(sql);            
            pstm.setString(1, f.getFornome());
            pstm.setString(2, f.getForcnpj());
            pstm.setString(3, f.getForcep());            
            pstm.setString(4, f.getForend());
            pstm.setString(5, f.getForfone());                        
            pstm.setInt(6, f.getForcod());
            pstm.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Alterado Com Sucesso!");            
        }catch(Exception erro){
         JOptionPane.showMessageDialog(null, "ERRO DE PSTM "+erro.getMessage());
        }        
    }
    
    public void excluirForn(Fornecedor f){
        con=ConnectionFactory.getConnection();
        try{
         sql="delete from fornecedor where forcod=?";
         pstm=con.prepareStatement(sql);
         pstm.setInt(1, f.getForcod());
         pstm.execute();
         JOptionPane.showMessageDialog(null, "Excluido com Sucesso!");
        }catch(Exception erro){
         JOptionPane.showMessageDialog(null, "ERRO DE PSTM "+erro.getMessage());   
        }        
    }             
    public Fornecedor ProcurarFornecedor(Fornecedor f){
            conex.conexao();
            conex.executaSql("SELECT *FROM fornecedor WHERE fornome LIKE'%"+f.getProcurar()+"%'");
        try{
            conex.rs.first();            
            f.setForcod(conex.rs.getInt("forcod"));
            f.setFornome(conex.rs.getString("fornome"));
            f.setForcnpj(conex.rs.getString("forcnpj"));           
            f.setForcep(conex.rs.getString("forcep"));           
            f.setForend(conex.rs.getString("forend"));
            f.setForfone(conex.rs.getString("forfone"));                        
        }catch(Exception ex){
//             JOptionPane.showMessageDialog(null,"Funcionario não cadastrado.");
        }
        conex.desconecta();
        return f; 
   }
    public Fornecedor ProcurarForn1(Fornecedor f){
            conex.conexao();
            conex.executaSql("SELECT *FROM fornecedor WHERE forcod="+f.getProcurar());
        try{
            conex.rs.first();            
            f.setForcod(conex.rs.getInt("forcod"));
            f.setFornome(conex.rs.getString("fornome"));
            f.setForcnpj(conex.rs.getString("forcnpj"));
            f.setForcep(conex.rs.getString("forcep"));            
            f.setForend(conex.rs.getString("forend"));
            f.setForfone(conex.rs.getString("forfone"));            
        }catch(Exception ex){
//             JOptionPane.showMessageDialog(null,"Funcionario não cadastrado.");
        }
        conex.desconecta();
        return f; 
   }
//    public double porcentagemFuncionario(double valor){
//        double total;
//       
//        total=valor*40/100;
//       
//       return total;                     
//    }        
}

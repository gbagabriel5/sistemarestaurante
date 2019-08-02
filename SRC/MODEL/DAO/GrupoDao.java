package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.bean.GrupoProduto;
import model.connection.ConnectionFactory;
import model.connection.ConnectionFactory2;

public class GrupoDao {
    Connection con=null;
    ResultSet rs;    
    String sql;        
    PreparedStatement pstm;
    ArrayList<GrupoProduto>lista=new ArrayList<>();
    ConnectionFactory2 conex = new ConnectionFactory2();
    ConnectionFactory2 conexao = new ConnectionFactory2();
    
    public List<GrupoProduto> ListaTipoPro(){
        try{
            con=ConnectionFactory.getConnection();
            sql="select grpcod,grpdesc from grupoproduto order by grpdesc;";
            pstm=con.prepareStatement(sql);
            rs=pstm.executeQuery(sql);
            while(rs.next()){
                GrupoProduto g=new GrupoProduto();
                g.setGrpcod(rs.getInt(1));
                g.setGrpdesc(rs.getString(2));
                lista.add(g);
            }
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "ERRO DE RS"+erro.getMessage());                        
        }
        return lista;
    }
     
    
    public void salvarTipo(GrupoProduto pro){        
        con=ConnectionFactory.getConnection();                
        try{
            sql="INSERT INTO grupoproduto(grpdesc)VALUES(?);";
            
        pstm=con.prepareStatement(sql);
                         
        pstm.setString(1, pro.getGrpdesc());
        

        pstm.execute();
            JOptionPane.showMessageDialog(null,pro.getGrpdesc()+ " foi Salvo com sucesso!");            
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "ERRO PSTM "+erro.getMessage());
        }        
    }    
    
    public void updateTipo(GrupoProduto pro){        
        con=ConnectionFactory.getConnection();        
        try{
            sql="update grupoproduto set grpdesc=? where grpcod=?";
            pstm=con.prepareStatement(sql);
            pstm.setString(1,pro.getGrpdesc());
            pstm.setInt(2, pro.getGrpcod());
            pstm.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Alterado com sucesso!");            
        }catch(Exception erro){
         JOptionPane.showMessageDialog(null, "ERRO DE PSTM "+erro.getMessage());
        }        
    }
    
    public void excluirTipo(GrupoProduto pro){
        con=ConnectionFactory.getConnection();
        try{
         sql="delete from grupoproduto where grpcod=?";
         pstm=con.prepareStatement(sql);
         pstm.setInt(1, pro.getGrpcod());
         pstm.execute();
         JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
        }catch(Exception erro){
         JOptionPane.showMessageDialog(null, "ERRO DE PSTM "+erro.getMessage());   
        }        
    }
    
    public GrupoProduto ProcurarTipo(GrupoProduto p){
            conex.conexao();
            conex.executaSql("SELECT *FROM grupoproduto WHERE grpcod="+p.getProcurar());
        try{
            conex.rs.first();            
//            p.setGrpcod(conex.rs.getInt("grpcod"));
            p.setGrpdesc(conex.rs.getString("grpdesc"));                                      
        }catch(Exception ex){
        }
        conex.desconecta();
        return p; 
   }    
}
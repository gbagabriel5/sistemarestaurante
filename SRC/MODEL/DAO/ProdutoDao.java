package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.bean.Fornecedor;
import model.bean.GrupoProduto;
import model.bean.Produto;
import model.connection.ConnectionFactory;
import model.connection.ConnectionFactory2;

public class ProdutoDao {
    Connection con=null;
    ResultSet rs;    
    String sql,nomeGrupo,nomefor;    
    int codGrupo,codFor;
    PreparedStatement pstm;
    ArrayList<Produto>lista=new ArrayList<>();
    ConnectionFactory2 conex = new ConnectionFactory2();
    ConnectionFactory2 conexao = new ConnectionFactory2();

    public List<Produto> ListaPro(){
        try{
            con=ConnectionFactory.getConnection();
            sql="select procod,pronome from produto order by pronome;";
            pstm=con.prepareStatement(sql);
            rs=pstm.executeQuery(sql);
            while(rs.next()){
                Produto pro=new Produto();
                pro.setProcod(rs.getInt(1));
                pro.setPronome(rs.getString(2));
               
                lista.add(pro);
            }
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "ERRO DE RS"+erro.getMessage());                        
        }
        return lista;
    }
    
    public void salvarProduto(Produto pro){
        buscaCodigo(pro.getNomegrupo());
        buscaCodigoFornecedor(pro.getNomefor());
        con=ConnectionFactory.getConnection();                
        try{
            sql="INSERT INTO produto(pronome,propreco,procusto,proestoque,proestm,progrpcod,proforcod,prostatus)VALUES(?,?,?,?,?,?,?,?);";
            
        pstm=con.prepareStatement(sql);
                 
        pstm.setString(1, pro.getPronome());
        pstm.setDouble(2, pro.getPropreco());
        pstm.setDouble(3, pro.getProcusto());        
        pstm.setInt(4, pro.getProsaldo());
        pstm.setInt(5, pro.getProEstoqueMin());
        pstm.setInt(6, codGrupo);
        pstm.setInt(7, codFor);
        pstm.setString(8, pro.getProStatus());        
        pstm.execute();
            JOptionPane.showMessageDialog(null,pro.getPronome()+ " foi Salvo com sucesso!");            
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "ERRO PSTM "+erro.getMessage());
        }        
    }
    public void buscaCodigo(String nome){
        conex.conexao();
        conex.executaSql("select *from grupoproduto where grpdesc='"+nome+"'");
        try {
            conex.rs.first();
            codGrupo= conex.rs.getInt("grpcod");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar codigo do grupoproduto.");
        }
    }
    public void buscaCodigoFornecedor(String nome){
        conex.conexao();
        conex.executaSql("select *from fornecedor where fornome='"+nome+"'");
        try {
            conex.rs.first();
            codFor= conex.rs.getInt("forcod");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar codigo do fornecedor.");
        }
    }
    public Produto buscarProduto(String nome){
        Produto pro=new Produto();
        con=ConnectionFactory.getConnection();
        try{           
          sql="select * from produto where pronome like'%" +nome+ "%'";
           pstm=con.prepareStatement(sql);
           //pstm.setInt(1, cod);
          rs=pstm.executeQuery();
          if(rs.first()){
              pro.setProcod(rs.getInt("procod"));
              pro.setPronome(rs.getString("pronome"));
              pro.setPropreco(rs.getDouble("propreco"));
              pro.setProcusto(rs.getDouble("procusto"));
              pro.setProsaldo(rs.getInt("proestoque"));
              pro.setProEstoqueMin(rs.getInt("proestm"));
              pro.setGrupoProduto((GrupoProduto) rs.getObject("progrpcod"));
              pro.setFornecedor((Fornecedor) rs.getObject("proforcod"));
              pro.setProStatus(rs.getString("prostatus"));
              
          }else{
              JOptionPane.showMessageDialog(null, "PRODUTO NÃO ENCONTRADO");
          }
        }catch(Exception erro){
          //JOptionPane.showMessageDialog(null, "ERRO DE PSTM "+erro.getMessage());  
        }
      return pro;  
    }
    
    public void updateProd(Produto pro){
        buscaCodigo(pro.getNomegrupo());
        buscaCodigoFornecedor(pro.getNomefor());
        con=ConnectionFactory.getConnection();        
        try{
            sql="update produto set pronome=?,propreco=?,procusto=?,proestoque=?,proestm=?,progrpcod=?,proforcod=?,prostatus=? where procod=?";
            pstm=con.prepareStatement(sql);
            pstm.setString(1, pro.getPronome());
            pstm.setDouble(2, pro.getPropreco());
            pstm.setDouble(3, pro.getProcusto());
            pstm.setInt(4, pro.getProsaldo());
            pstm.setInt(5, pro.getProEstoqueMin());
            pstm.setInt(6, codGrupo);
            pstm.setInt(7, codFor);
            pstm.setString(8, pro.getProStatus());
            pstm.setInt(9, pro.getProcod());
            pstm.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Alterado com sucesso!");            
        }catch(Exception erro){
         JOptionPane.showMessageDialog(null, "ERRO DE PSTM "+erro.getMessage());
        }        
    }
    
    public void excluirProduto(Produto pro){
        con=ConnectionFactory.getConnection();
        try{
         sql="delete from produto where procod=?";
         pstm=con.prepareStatement(sql);
         pstm.setInt(1, pro.getProcod());
         pstm.execute();
         JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
        }catch(Exception erro){
         JOptionPane.showMessageDialog(null, "ERRO DE PSTM "+erro.getMessage());   
        }        
    }
    
    public Produto ProcurarProduto(Produto p){
            conex.conexao();
            conex.executaSql("SELECT *FROM produto inner join grupoproduto on progrpcod=grpcod inner join fornecedor on proforcod=forcod WHERE pronome LIKE'%"+p.getProcurar()+"%'");
        try{
            conex.rs.first();
            buscaNomeGrupo(conex.rs.getInt("progrpcod"));
            buscaNomeFor(conex.rs.getInt("proforcod"));
            p.setProcod(conex.rs.getInt("procod"));
            p.setPronome(conex.rs.getString("pronome"));
            p.setPropreco(conex.rs.getDouble("propreco"));
            p.setProcusto(conex.rs.getDouble("procusto"));
            p.setProsaldo(conex.rs.getInt("proestoque"));
            p.setProEstoqueMin(conex.rs.getInt("proestm"));
            p.setNomegrupo(nomeGrupo);
            p.setNomefor(nomefor);
            p.setProStatus(conex.rs.getString("prostatus"));               
        }catch(Exception ex){
        }
        conex.desconecta();
        return p; 
   }
    public Produto ProcurarProduto1(Produto p){
            conex.conexao();
            conex.executaSql("SELECT *FROM produto inner join grupoproduto on progrpcod=grpcod inner join fornecedor on proforcod=forcod WHERE procod="+p.getProcurar());
        try{
            conex.rs.first();
            buscaNomeGrupo(conex.rs.getInt("progrpcod"));
            buscaNomeFor(conex.rs.getInt("proforcod"));
            p.setProcod(conex.rs.getInt("procod"));
            p.setPronome(conex.rs.getString("pronome"));
            p.setPropreco(conex.rs.getDouble("propreco"));
            p.setProcusto(conex.rs.getDouble("procusto"));
            p.setProsaldo(conex.rs.getInt("proestoque"));
            p.setProEstoqueMin(conex.rs.getInt("proestm"));
            p.setNomegrupo(nomeGrupo);
            p.setNomefor(nomefor);
            p.setProStatus(conex.rs.getString("prostatus"));               
        }catch(Exception ex){
        }
        conex.desconecta();
        return p; 
   }
    public void buscaNomeGrupo(int cod){
        conexao.conexao();
        conexao.executaSql("select *from grupoproduto where grpcod="+cod);
        try {
            conexao.rs.first();
            nomeGrupo= conexao.rs.getString("grpdesc");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar codigo do grupoproduto.");
        }
        conexao.desconecta();
    }
    public void buscaNomeFor(int cod){
        conexao.conexao();
        conexao.executaSql("select *from fornecedor where forcod="+cod);
        try {
            conexao.rs.first();
            nomefor= conexao.rs.getString("fornome");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar codigo do fornecedor.");
        }
        conexao.desconecta();
    }

    public void atualizarStatus(){
        conexao.conexao();
        conexao.executaSql("select *from produto");
        try {
            int est=conexao.rs.getInt("proestoque");
            int estm=conexao.rs.getInt("proestm");
            if(est<estm){
                sql="update produto set prostatus='baixo' where procod=?";
                pstm=con.prepareStatement(sql);
//                pstm.setString(1, pro.getPronome());            
                pstm.executeUpdate();
            }
        } catch (SQLException ex) {
            
        }
}
}
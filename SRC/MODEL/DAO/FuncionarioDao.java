package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.bean.Departamento;
import model.bean.Funcionario;
import model.connection.ConnectionFactory;
import model.connection.ConnectionFactory2;

public class FuncionarioDao {
    
    ConnectionFactory2 conex = new ConnectionFactory2();
    ConnectionFactory2 conexao = new ConnectionFactory2();
    Connection con=null;
    ResultSet rs;    
    String sql,nomeDep;    
    int cod;
    PreparedStatement pstm;
    ArrayList<Funcionario>lista=new ArrayList<>();
    
    public List<Funcionario> ListaFunc(){
        try{
            con=ConnectionFactory.getConnection();
            sql="select funcod,funnome from funcionario order by funnome;";
            pstm=con.prepareStatement(sql);
            rs=pstm.executeQuery(sql);
            while(rs.next()){
                Funcionario func=new Funcionario();
                func.setFuncod(rs.getInt(1));
                func.setFunnome(rs.getString(2));
               
                lista.add(func);
            }
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "ERRO DE RS"+erro.getMessage());                        
        }
        return lista;
    }
    
    public void salvarFunc(Funcionario func){
        buscaCodigo(func.getNomeDep());
        con=ConnectionFactory.getConnection();
        
        try{
            sql="INSERT INTO funcionario(funnome,funrg,funcpf,fundtnasc,funend,funfone,funsalario,fundepcod)VALUES(?,?,?,?,?,?,?,?);";
            
        pstm=con.prepareStatement(sql);
                 
        pstm.setString(1, func.getFunnome());
        pstm.setString(2, func.getFunrg());
        pstm.setString(3, func.getFuncpf());
        pstm.setString(4, func.getFundtnasc());
        pstm.setString(5, func.getFunend());
        pstm.setString(6, func.getFunfone());        
        pstm.setDouble(7, func.getFunsalario());
        pstm.setInt(8,cod);
        pstm.execute();
            JOptionPane.showMessageDialog(null,func.getFunnome()+ " Foi Salvo com Sucesso!");            
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "ERRO PSTM "+erro.getMessage());
        }        
    }
    public void buscaCodigo(String nome){
        conex.conexao();
        conex.executaSql("select *from departamento where depdesc='"+nome+"'");
        try {
            conex.rs.first();
            cod= conex.rs.getInt("depcod");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar codigo do DEPARTAMENTO.");
        }
    }
    public Funcionario buscarFunc(String nome){
        Funcionario func=new Funcionario();
        con=ConnectionFactory.getConnection();
        try{
           //sql="SELECT *FROM FUNCIONARIO WHERE FUNNOME LIKE'%" +nome+ "%'";
          sql="select * from funcionario where funnome like'%" +nome+ "%'";
           pstm=con.prepareStatement(sql);
           //pstm.setInt(1, cod);
          rs=pstm.executeQuery();
          if(rs.first()){
              func.setFuncod(rs.getInt("funcod"));
              func.setFunnome(rs.getString("funnome"));
              func.setFunrg(rs.getString("funrg"));              
              func.setFuncpf(rs.getString("funcpf"));
              func.setFundtnasc(rs.getString("fundtnasc"));
              func.setFunend(rs.getString("funend"));              
              func.setFunfone(rs.getString("funfone"));
              func.setFunsalario(rs.getDouble("funsalario"));
              func.setDepartamento((Departamento) rs.getObject("fundepcod"));                                                        
          }else{
              JOptionPane.showMessageDialog(null, "FUNCIONÁRIO NÃO ENCONTRADO");
          }
        }catch(Exception erro){
          //JOptionPane.showMessageDialog(null, "ERRO DE PSTM "+erro.getMessage());  
        }
      return func;  
    }
    
    public void updateFunc(Funcionario func){
        buscaCodigo(func.getNomeDep());
        con=ConnectionFactory.getConnection();
        try{
            sql="update funcionario set funnome=?,funrg=?,funcpf=?,fundtnasc=?,funend=?,funfone=?,funsalario=?,fundepcod=? where funcod=?";
            pstm=con.prepareStatement(sql);            
            pstm.setString(1, func.getFunnome());
            pstm.setString(2, func.getFunrg());
            pstm.setString(3, func.getFuncpf());
            pstm.setString(4, func.getFundtnasc());
            pstm.setString(5, func.getFunend());
            pstm.setString(6, func.getFunfone());
            pstm.setDouble(7, func.getFunsalario());
            pstm.setInt(8,cod);
            pstm.setInt(9, func.getFuncod());
            pstm.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Alterado Com Sucesso!");            
        }catch(Exception erro){
         JOptionPane.showMessageDialog(null, "ERRO DE PSTM "+erro.getMessage());
        }        
    }
    
    public void excluirFunc(Funcionario func){
        con=ConnectionFactory.getConnection();
        try{
         sql="delete from funcionario where funcod=?";
         pstm=con.prepareStatement(sql);
         pstm.setInt(1, func.getFuncod());
         pstm.execute();
         JOptionPane.showMessageDialog(null, "Excluido com Sucesso!");
        }catch(Exception erro){
         JOptionPane.showMessageDialog(null, "ERRO DE PSTM "+erro.getMessage());   
        }        
    }             
    public void buscaNomeDep(int cod){
        conexao.conexao();
        conexao.executaSql("select *from departamento where depcod='"+cod+"'");
        try {
            conexao.rs.first();
            nomeDep= conexao.rs.getString("depdesc");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar codigo do Departamento.");
        }
        conexao.desconecta();
    }
    public Funcionario ProcurarFun(Funcionario func){
            conex.conexao();
            conex.executaSql("SELECT *FROM funcionario WHERE funnome LIKE'%"+func.getProcurar()+"%'");
        try{
            conex.rs.first();
            buscaNomeDep(conex.rs.getInt("fundepcod"));
            func.setFuncod(conex.rs.getInt("funcod"));
            func.setFunnome(conex.rs.getString("funnome"));
            func.setFunrg(conex.rs.getString("funrg"));
            func.setFuncpf(conex.rs.getString("funcpf"));
            func.setFundtnasc(conex.rs.getString("fundtnasc"));
            func.setFunend(conex.rs.getString("funend"));
            func.setFunfone(conex.rs.getString("funfone"));
            func.setFunsalario(conex.rs.getDouble("funsalario"));
            func.setNomeDep(nomeDep);
        }catch(Exception ex){
//             JOptionPane.showMessageDialog(null,"Funcionario não cadastrado.");
        }
        conex.desconecta();
        return func; 
   }
    public Funcionario ProcurarFun1(Funcionario func){
            conex.conexao();
            conex.executaSql("SELECT *FROM funcionario WHERE funcod="+func.getProcurar());
        try{
            conex.rs.first();
            buscaNomeDep(conex.rs.getInt("fundepcod"));
            func.setFuncod(conex.rs.getInt("funcod"));
            func.setFunnome(conex.rs.getString("funnome"));
            func.setFunrg(conex.rs.getString("funrg"));
            func.setFuncpf(conex.rs.getString("funcpf"));
            func.setFundtnasc(conex.rs.getString("fundtnasc"));
            func.setFunend(conex.rs.getString("funend"));
            func.setFunfone(conex.rs.getString("funfone"));
            func.setFunsalario(conex.rs.getDouble("funsalario"));
            func.setNomeDep(nomeDep);
        }catch(Exception ex){
//             JOptionPane.showMessageDialog(null,"Funcionario não cadastrado.");
        }
        conex.desconecta();
        return func; 
   }
//    public double porcentagemFuncionario(double valor){
//        double total;
//       
//        total=valor*40/100;
//       
//       return total;                     
//    }        
}

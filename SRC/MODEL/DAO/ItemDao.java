package MODEL.DAO;

import MODEL.BEAN.Item;
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

public class ItemDao {
    Connection con=null;
    ResultSet rs;    
    String sql,nomefor;    
    int codFor;
    PreparedStatement pstm;
    ArrayList<Item>lista=new ArrayList<>();
    ConnectionFactory2 conex = new ConnectionFactory2();
    ConnectionFactory2 conexao = new ConnectionFactory2();

    public List<Item> ListaItem(){
        try{
            con=ConnectionFactory.getConnection();
            sql="select itcod, itnome from item order by itnome;";
            pstm=con.prepareStatement(sql);
            rs=pstm.executeQuery(sql);
            while(rs.next()){
                Item item=new Item();
                item.setItcod(rs.getInt(1));
                item.setItnome(rs.getString(2));
                lista.add(item);
            }
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "ERRO DE RS"+erro.getMessage());                        
        }
        return lista;
    }
    
    public void salvarItem(Item item){
        buscaCodigoFornecedor(item.getNomefor());
        con=ConnectionFactory.getConnection();                
        try{
            sql="INSERT INTO item(itcod, itnome, itdesc, itpreco, itqtde, itmedida, itvalidade, itforcod)VALUES(?,?,?,?,?,?,?,?);";
            pstm=con.prepareStatement(sql);
            
            pstm.setString(1, item.getItnome());
            pstm.setString(2, item.getItdesc());
            pstm.setDouble(3, item.getItpreco());
            pstm.setDouble(4, item.getItqtde());        
            pstm.setString(5, item.getItmedida());
            pstm.setInt(6, item.getItvalidade());       
            pstm.setInt(7, codFor);       
            pstm.execute();
            JOptionPane.showMessageDialog(null,item.getItnome()+ " foi Salvo com sucesso!");            
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "ERRO PSTM "+erro.getMessage());
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
    public Item buscarItem(String nome){
        Item item=new Item();
        con=ConnectionFactory.getConnection();
        try{           
            sql="select * from item where itemnome like'%" +nome+ "%'";
            pstm=con.prepareStatement(sql);
            rs=pstm.executeQuery();
            if(rs.first()){
                item.setItcod(rs.getInt("itcod"));
                item.setItnome(rs.getString("itnome"));
                item.setItdesc(rs.getString("itdesc"));
                item.setItpreco(rs.getDouble("itpreco"));
                item.setItqtde(rs.getDouble("itqtde"));
                item.setItmedida(rs.getString("itmedida"));
                item.setItvalidade(rs.getInt("itvalidade"));
                item.setFornecedor((Fornecedor) rs.getObject("itemforcod"));
            }else{
              JOptionPane.showMessageDialog(null, "ITEM NÃO ENCONTRADO");
            }
        }catch(Exception erro){
        }
        return item;  
    }
    
    public void updateItem(Item item){
        buscaCodigoFornecedor(item.getNomefor());
        con=ConnectionFactory.getConnection();        
        try{
            sql="update item set itnome=?,itdesc=?,itpreco=?,itqtde=?,itmedida=?,itvalidade=?,itforcod=? where itcod=?";
            pstm=con.prepareStatement(sql);
            pstm.setString(1, item.getItnome());
            pstm.setString(2, item.getItdesc());
            pstm.setDouble(3, item.getItpreco());
            pstm.setDouble(4, item.getItqtde());        
            pstm.setString(5, item.getItmedida());
            pstm.setInt(6, item.getItvalidade());       
            pstm.setInt(7, codFor);       
            pstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Alterado com sucesso!");            
        }catch(Exception erro){
         JOptionPane.showMessageDialog(null, "ERRO DE PSTM "+erro.getMessage());
        }        
    }
    
    public void excluirItem(Item item){
        con=ConnectionFactory.getConnection();
        try{
            sql="delete from item where itcod=?";
            pstm=con.prepareStatement(sql);
            pstm.setInt(1, item.getItcod());
            pstm.execute();
            JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "ERRO DE PSTM "+erro.getMessage());   
        }        
    }
    
    public Item ProcurarItem(Item i){
        conex.conexao();
        conex.executaSql("SELECT *FROM item inner join fornecedor on itemforcod=forcod WHERE itnome LIKE'%"+i.getProcurar()+"%'");
        try{
            conex.rs.first();
            buscaNomeFor(conex.rs.getInt("itforcod"));
            i.setItcod(conex.rs.getInt("itcod"));
            i.setItnome(conex.rs.getString("itnome"));
            i.setItdesc(conex.rs.getString("itdesc"));
            i.setItpreco(conex.rs.getDouble("itpreco"));
            i.setItqtde(conex.rs.getDouble("itqtde"));
            i.setItmedida(conex.rs.getString("itmedida"));
            i.setItvalidade(conex.rs.getInt("itvalidade"));               
            i.setNomefor(nomefor);
        }catch(Exception ex){
        }
        conex.desconecta();
        return i; 
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
}
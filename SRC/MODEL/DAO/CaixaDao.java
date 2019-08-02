package model.dao;

import java.awt.Desktop;
import java.io.File;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.bean.Caixa;
import model.bean.ItemVenda;
import model.bean.Produto;
import model.bean.Venda;
import model.connection.ConnectionFactory2;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class CaixaDao {

    ExecutaSql conexao= new ExecutaSql();
    int codProd,codProd1,codCli,codFun,codItv,cod,codv;    
    ConnectionFactory2 conex = new ConnectionFactory2();
    public ResultSet jrRS;
    public void buscaCodigo(String nome){
        conex.conexao();
        conex.executaSql("select *from itemvenda inner join produto on itvprocod=procod where nome='"+nome+"'");
        try {
            conex.rs.first();
            cod= conex.rs.getInt("procod");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar codigo do Aluno.");
        }
    }
    
    public void EstornoItem(Venda ven){    
//        Venda ven= new Venda();
        AchaCodProduto(ven.getNomeProduto());
        conexao.conexao();
        PreparedStatement pstm;
        try {
            pstm=conexao.conn.prepareStatement("update produto inner join itemvenda on procod=itvprocod set proestoque=(proestoque+?) where itvprocod=?");
            pstm.setInt(1, ven.getQtdeItem());
            pstm.setInt(2, codProd);
            pstm.execute();
            JOptionPane.showMessageDialog(null, "Produto de volta ao estoque");
            conexao.desconecta();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro no estorno de produtos\n Erro:"+ ex);
            conexao.desconecta();
        }
    


    }
    public void AdcionaItem(Venda ven){
        AchaCodProduto(ven.getNomeProduto());
        conexao.conexao();
        try {
            PreparedStatement pstm = conexao.conn.prepareStatement("insert into itemvenda (itvvencod,itvprocod,itvqtde)values(?,?,?)");
            pstm.setInt(1, ven.getVencod());
            pstm.setInt(2, codProd);
            pstm.setInt(3, ven.getQtdeItem());
            
            pstm.execute();
            
            //Baixa de estoque
            int quantidade=0,resul=0;
            conexao.executaSQL("select *from produto where pronome='"+ven.getNomeProduto()+"'");
            conexao.rs.first();            
            quantidade=conexao.rs.getInt("proestoque");
            resul= quantidade-ven.getQtdeItem();
            pstm=conexao.conn.prepareStatement("update produto set proestoque=? where pronome=?");
            pstm.setInt(1, resul);
            pstm.setString(2, ven.getNomeProduto());
            pstm.execute();
//            JOptionPane.showMessageDialog(null,"Produto Adcionado!");
            conexao.desconecta();
        } catch (SQLException ex) {
            conexao.desconecta();
            JOptionPane.showMessageDialog(null,"Erro ao realizar venda: "+ ex);
        }
    }
    public void RemoveItem(int cod){
                
        conexao.conexao();
        try {
            PreparedStatement pstm;                       
            
            pstm = conexao.conn.prepareStatement("delete from venda where vencod=?");            
            pstm.setInt(1, cod);
            pstm.execute();                                                
            
            JOptionPane.showMessageDialog(null,"Excluido!");
            conexao.desconecta();
        } catch (SQLException ex) {
            conexao.desconecta();
            JOptionPane.showMessageDialog(null,"Erro ao excluir: "+ ex);
        }
    }
    public void AchaCodProduto(String nome){
        conexao.conexao();
        conexao.executaSQL("select *from produto where pronome='"+nome+"'");
        try {
            conexao.rs.first();
            codProd=conexao.rs.getInt("procod");
            conexao.desconecta();
        } catch (SQLException ex) {
            conexao.desconecta();
            JOptionPane.showMessageDialog(null,"Erro ao achar codigo: "+ ex);
        }
    }
     public void AchaCodProduto1(int cod){
        conexao.conexao();
        conexao.executaSQL("select *from itemvenda inner join produto on itvprocod=procod where procod="+cod);
        try {
            conexao.rs.first();
            codProd1=conexao.rs.getInt("procod");
            conexao.desconecta();
        } catch (SQLException ex) {
            conexao.desconecta();
            JOptionPane.showMessageDialog(null,"Erro ao achar codigo: "+ ex);
        }
    }
    
    public void AchaCodVenda(int cod){
        int codVenda;
        conexao.conexao();
        try {            
            conexao.executaSQL("select *from venda where ventotal=0");
            conexao.rs.last();
            codVenda=conexao.rs.getInt("vencod");            
//            conexao.desconecta();
        } catch (SQLException ex) {
//            conexao.desconecta();
            JOptionPane.showMessageDialog(null, "Erro:"+ex);
        }        
    }
    
    public void FecharCaixa(Caixa cx){               
        conexao.conexao();
        try {
            PreparedStatement pstm=conexao.conn.prepareStatement("update caixa set cxdthfecha=?,cxsfinal=?,cxobs=?,cxstatus=? where cxcod=?");
            pstm.setString(1, cx.getCxdthfecha());            
            pstm.setDouble(2, cx.getCxsfinal());       
            pstm.setString(3, cx.getCxobs());
            pstm.setString(4, cx.getCxstatus());
            pstm.setInt(5, cx.getCxcod());
            JOptionPane.showMessageDialog(null,"Caixa Fechado!");
            pstm.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao fechar Caixa\n Erro: "+ ex);
        }
        conexao.desconecta();
    }
    public void AdcionarEntrada(Caixa cx){               
        conexao.conexao();
        Venda ven= new Venda();
        try {
            PreparedStatement pstm=conexao.conn.prepareStatement("update venda set saida=?,desc=? where vencxcod=?");
            pstm.setDouble(1, ven.getSaida());
            pstm.setString(2, ven.getDesc());
            pstm.setInt(3, cx.getCxcod());
            JOptionPane.showMessageDialog(null,"Adcionado!");
            pstm.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao Salvar \n Erro: "+ ex);
        }
        conexao.desconecta();
    }
      public void CancelaVenda(Venda ven){        
            conexao.conexao();
            PreparedStatement pstm;                                    
            AchaCodVenda(ven.getVencod());
            conexao.executaSQL("select vencod,vendata,ventotal,pagamento,vrecebido,troco,itvcod,itvvencod,itvprocod,sum(itvqtde),procod,pronome, propreco, proestoque, progrpcod from venda inner join itemvenda on vencod=itvvencod inner join produto on procod=itvprocod where ventotal=0 and vencod="+ven.getVencod()+" group by procod");
//              conexao.executaSQL("select vencod,vendata,ventotal,itvcod,itvvencod,itvprocod,itvqtde,procod,pronome, propreco, proestoque, progrpcod from venda inner join itemvenda on vencod=itvvencod inner join produto on procod=itvprocod where ventotal=0");
        try {
            conexao.rs.first();
            do{                                
                int quantidade=0;
                quantidade=conexao.rs.getInt("proestoque");
                
                int quantidadeVend=0;
//                quantidadeVend=conexao.rs.getInt("itvqtde");
                quantidadeVend=conexao.rs.getInt("sum(itvqtde)");
                
                int soma=0;
                soma= quantidade+quantidadeVend;
                pstm= conexao.conn.prepareStatement("update produto set proestoque=? where procod=?");
                pstm.setInt(1, soma);
                pstm.setInt(2, conexao.rs.getInt("procod"));
                pstm.execute();
                
                pstm=conexao.conn.prepareStatement("delete from itemvenda where itvvencod=?");
                pstm.setInt(1, conexao.rs.getInt("itvvencod"));
                pstm.execute();                                
            }while(conexao.rs.next());
            pstm=conexao.conn.prepareStatement("delete from venda where ventotal=?");
            pstm.setInt(1,0);
            pstm.execute();            
        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null,"Erro ao cancelar venda!\n Erro: "+ ex);
        }
        conexao.desconecta();
    }
      public void estornoVenda(Venda ven){        
            conexao.conexao();
            PreparedStatement pstm;                                    
            AchaCodVenda(ven.getVencod());
            conexao.executaSQL("select vencod,vendata,ventotal,pagamento,vrecebido,troco,itvcod,itvvencod,itvprocod,sum(itvqtde),procod,pronome, propreco, proestoque, progrpcod from venda inner join itemvenda on vencod=itvvencod inner join produto on procod=itvprocod where ventotal=0 and vencod="+ven.getVencod()+" group by procod");
//              conexao.executaSQL("select vencod,vendata,ventotal,itvcod,itvvencod,itvprocod,itvqtde,procod,pronome, propreco, proestoque, progrpcod from venda inner join itemvenda on vencod=itvvencod inner join produto on procod=itvprocod where ventotal=0");
        try {
            conexao.rs.first();
            do{                                
                int quantidade=0;
                quantidade=conexao.rs.getInt("proestoque");
                
                int quantidadeVend=0;
//                quantidadeVend=conexao.rs.getInt("itvqtde");
                quantidadeVend=conexao.rs.getInt("sum(itvqtde)");
                
                int soma=0;
                soma= quantidade + quantidadeVend;
                pstm= conexao.conn.prepareStatement("update produto set proestoque=? where procod=?");
                pstm.setInt(1, soma);
                pstm.setInt(2, conexao.rs.getInt("procod"));
                pstm.execute();
                
                pstm=conexao.conn.prepareStatement("delete from itemvenda where itvvencod=?");
                pstm.setInt(1, conexao.rs.getInt("itvvencod"));
                pstm.execute();                                
            }while(conexao.rs.next());
            pstm=conexao.conn.prepareStatement("delete from venda where ventotal=?");
            pstm.setInt(1,0);
            pstm.execute();            
        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null,"Erro ao cancelar venda!\n Erro: "+ ex);
        }
        conexao.desconecta();
    }
      public void DeletaVendaNula(){
          conexao.conexao();
            PreparedStatement pstm;                                                
            
        try {
                pstm=conexao.conn.prepareStatement("delete from itemvenda where itvvencod=?");
                pstm.setInt(1, conexao.rs.getInt("itvvencod"));
                pstm.execute();                                
                
        } catch (SQLException ex) {            
        }            
      }
    public void DeletaItemVenda(ItemVenda itv){
        conexao.conexao();
        AchaCodItemVenda(itv.getItvcod());
        PreparedStatement pstm;                                    
        try {
            pstm=conexao.conn.prepareStatement("delete from itemvenda where itvcod=?");
            pstm.setInt(1, conexao.rs.getInt("itvcod"));
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(VendaDao.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    public void excluirFunc(int cod){
        conexao.conexao();
        PreparedStatement pstm;
        String sql;
        ItemVenda itv=new ItemVenda();
        try{         
         pstm=conexao.conn.prepareStatement("delete from itemvenda where itvcod=?");         
         pstm.setInt(1, itv.getItvcod());
         pstm.execute();
         JOptionPane.showMessageDialog(null, "Item removido!");
        }catch(Exception erro){
         JOptionPane.showMessageDialog(null, "ERRO DE PSTM "+erro.getMessage());   
        }        
    }                  
    public void AchaCodItemVenda(int cod){
        int codItemVenda;
        conexao.conexao();
        PreparedStatement pstm;
        try {            
            ItemVenda itv = new ItemVenda();
                    
            conexao.executaSQL("select *from itemvenda where itvcod="+itv.getItvcod());            
            conexao.rs.last();             
            pstm=conexao.conn.prepareStatement("delete from itemvenda where itvcod=?");         
            pstm.setInt(0, itv.getItvcod());
            pstm.execute();
            codItemVenda=conexao.rs.getInt("itvcod");            
//            conexao.desconecta();
        } catch (SQLException ex) {
//            conexao.desconecta();
            JOptionPane.showMessageDialog(null, "Erro:"+ex);
        }        
    }
    public String PegarData(){
       //pega data para converter em horas
       Date data = new Date();
       //cria o formatador
       SimpleDateFormat dformatador = new SimpleDateFormat("dd/MM/yyyy HH:mm");
       // cria a string
       String sData = dformatador.format(data);
       return sData;
    }
    public String PegarData1(){
       //pega data para converter em horas
       Date data = new Date();
       //cria o formatador
       SimpleDateFormat dformatador = new SimpleDateFormat("dd/MM/yyyy");
       // cria a string
       String sData = dformatador.format(data);
       return sData;
    }
    public String MostraHora(){
       //pega data para converter em horas
       Date data = new Date();
       //cria o formatador
       SimpleDateFormat hformatador = new SimpleDateFormat("HH:mm:ss");
       // cria a string
       String sHora = hformatador.format(data);
       return sHora;
    }
   public boolean gerarRelatorioVenda(int codigo){              
       Venda ven=new Venda();
       try {           
           conexao.conexao();           
           conexao.executaSQL("select itvvencod,vendata,hora,ventotal,pagamento,vrecebido,troco,(propreco*itvqtde),pronome,sum(itvqtde) from venda "
                   + "inner join itemvenda on vencod=itvvencod "
                   + "inner join produto on itvprocod=procod WHERE itvvencod='"+codigo+"' group by procod");
           
        JRResultSetDataSource jrRS= new JRResultSetDataSource(conexao.rs);
        InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("Relatorios/RelatorioDeVendas.jasper");
        java.util.HashMap filtro = new java.util.HashMap();
        JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, filtro, jrRS);
        JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/Users/Meu Computador/Desktop/Arquivos Dev/Sistema Relatorio/SistemaFesta/ireport/relatorioVenda.pdf");
        File file= new File("C:/Users/Meu Computador/Desktop/Arquivos Dev/Sistema Relatorio/SistemaFesta/ireport/relatorioVenda.pdf");
           try {
               Desktop.getDesktop().open(file);
           } catch (Exception e) {
               JOptionPane.showConfirmDialog(null, e);
           }
           file.deleteOnExit();
        return true;        
       } catch (Exception e) { 
        e.printStackTrace();
        return false;       
       }finally{
        conexao.desconecta();
       }       
    }            

}
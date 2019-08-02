package model.dao;

import java.awt.Desktop;
import java.io.File;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.bean.ItemVenda;
import model.bean.Produto;
import model.bean.Venda;
import model.connection.ConnectionFactory2;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;


public class VendaDao {
    ExecutaSql conexao= new ExecutaSql();
    int codProd,codProd1,codCli,codFun,codItv,cod,codv,codVenda;;    
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
    
    public void iniciaVenda(String mesa, String data){
        conexao.conexao();
        try {
            PreparedStatement pstm= conexao.conn.prepareStatement("insert into venda (venmcod,vendata,ventotal,venmstatus)values(?,?,?,?)");
            pstm.setString(1,mesa);
            pstm.setString(2,data);
            pstm.setDouble(3,0);
            pstm.setString(4,"Ocupado");
            pstm.execute();            
            
//            conexao.executaSQL("select *from venda where venmcod="+mesa);
//            conexao.rs.last();
//            codVenda=conexao.rs.getInt("vencod");                                       
            conexao.desconecta();
        } catch (SQLException ex) {            
        }
    }
    
    public void estornoItem(Venda ven){    
//        Venda ven= new Venda();
        achaCodProduto(ven.getNomeProduto());
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
    
    public void adcionaItem(Venda ven){
        achaCodProduto(ven.getNomeProduto());
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
    public void inserirTotal(Venda ven){        
        conexao.conexao();
        try {
            PreparedStatement pstm = conexao.conn.prepareStatement("update venda set ventotal=? where vencod=?");
            pstm.setDouble(1, ven.getVentotal());
            pstm.setInt(2, ven.getVencod());
            
            pstm.execute();
            JOptionPane.showMessageDialog(null,"Total inserido!");
            conexao.desconecta();
        } catch (SQLException ex) {
            conexao.desconecta();
            JOptionPane.showMessageDialog(null,"Erro: "+ ex);
        }
    }
    public void removeItem(ItemVenda itv){
        Venda ven= new Venda();
        Produto pro = new Produto();        
        conexao.conexao();
        try {
            PreparedStatement pstm;                       
            
            pstm = conexao.conn.prepareStatement("delete from itemvenda where itvcod=?");            
            pstm.setInt(1, itv.getItvcod());                        
            pstm.execute();                                                
            
            JOptionPane.showMessageDialog(null,"Produto Removido!");
            conexao.desconecta();
        } catch (SQLException ex) {
            conexao.desconecta();
            JOptionPane.showMessageDialog(null,"Erro ao remover item: "+ ex);
        }
    }
    public void achaCodProduto(String nome){
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
     public void achaCodProduto1(int cod){
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
    
    public void achaCodVenda(int cod){
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
    public void trocarmesa(Venda ven){
//        Venda ven=new Venda();
        conexao.conexao();
        try {
            PreparedStatement pstm=conexao.conn.prepareStatement("update venda set venmcod=? where vencod=?");
            pstm.setString(1, ven.getVenmcod());
            pstm.setInt(2, ven.getVencod());
            JOptionPane.showMessageDialog(null,"A troca foi realizada com sucesso!");
            pstm.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao trocar mesa\n Erro: "+ e);
        }
        conexao.desconecta();
    }
    
    public void fechaVenda(Venda ven){               
        conexao.conexao();
        try {
            PreparedStatement pstm=conexao.conn.prepareStatement("update venda set vencxcod=?,hora=?,ventotal=?,pagamento=?,vrecebido=?,troco=?,venmstatus=? where vencod=?");            
            pstm.setString(1,ven.getCxvencod());            
            pstm.setString(2,ven.getVenhora());
            pstm.setDouble(3, ven.getVentotal());            
            pstm.setString(4, ven.getPagamento());
            pstm.setDouble(5, ven.getValorRecebido());            
            pstm.setDouble(6, ven.getTroco());            
            pstm.setString(7, ven.getVenmstatus());
            pstm.setInt(8, ven.getVencod());
            JOptionPane.showMessageDialog(null,"Venda Finalizada!");
            pstm.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao fechar venda\n Erro: "+ ex);
        }
        conexao.desconecta();
    }
      public void cancelaVenda(String mesa,int cod){                   
        conexao.conexao();
        PreparedStatement pstm;                                    
        try {
            conexao.executaSQL("select vencod,itvvencod,proestoque,sum(itvqtde),procod,ventotal from venda inner join itemvenda on vencod=itvvencod inner join produto on procod=itvprocod where venmcod="+mesa+" and venmstatus='Ocupado' group by procod");
            conexao.rs.first();
            do{                                
                int quantidade=0;
                quantidade=conexao.rs.getInt("proestoque");
                
                int quantidadeVend=0;
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
            conexao.executaSQL("select *from venda where venmcod="+mesa+" and venmstatus='Ocupado'");
            try {                              
                conexao.rs.first();
                pstm=conexao.conn.prepareStatement("delete from venda where vencod=?");
                pstm.setInt(1,cod);
                pstm.execute();            
            } catch (SQLException ex1) { 
                JOptionPane.showMessageDialog(null, ex1);
            }
        }
        conexao.desconecta();
    }
      public void estornoVenda(Venda ven){        
            conexao.conexao();
            PreparedStatement pstm;                                    
            achaCodVenda(ven.getVencod());
            conexao.executaSQL("select *from venda inner join itemvenda on vencod=itvvencod inner join produto on procod=itvprocod where ventotal=0 and vencod="+ven.getVencod()+" group by procod");
        try {
            conexao.rs.first();
            do{                                
                int quantidade=0;
                quantidade=conexao.rs.getInt("proestoque");
                
                int quantidadeVend=0;
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
      public void DeletaVendaNula(int cod){
        conexao.conexao();
        PreparedStatement pstm;                                                
        try {
            pstm=conexao.conn.prepareStatement("delete from itemvenda where itvvencod=?");
            pstm.setInt(1, cod);
            pstm.execute();                                
        } catch (SQLException ex) {            
        }            
      }
    public void deletaItemVenda(ItemVenda itv){
        conexao.conexao();
        achaCodItemVenda(itv.getItvcod());
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
    public void achaCodItemVenda(int cod){
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
    public String mostraHora(){
       //pega data para converter em horas
       Date data = new Date();
       //cria o formatador
       SimpleDateFormat hformatador = new SimpleDateFormat("HH:mm:ss");
       // cria a string
       String sHora = hformatador.format(data);
       return sHora;
    }
    public String pegarData(){
       //pega data para converter em horas
       Date data = new Date();
       //cria o formatador
       SimpleDateFormat dformatador = new SimpleDateFormat("dd/MM/yyyy");
       // cria a string
       String sData = dformatador.format(data);
       return sData;
    }
   public boolean gerarRelatorioVenda(int codigo){              
       Venda ven=new Venda();
       try {           
           conexao.conexao();           
           conexao.executaSQL("select itvvencod,vencxcod,venmcod,vendata,hora,ventotal,pagamento,vrecebido,troco,(propreco*itvqtde),pronome,sum(itvqtde) from venda inner join itemvenda on vencod=itvvencod inner join produto on itvprocod=procod where itvvencod='"+codigo+"' group by procod");
           
        JRResultSetDataSource jrRS= new JRResultSetDataSource(conexao.rs);
        InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("Relatorios/RelatorioDeVendas.jasper");
//        java.util.HashMap filtro = new java.util.HashMap();;
        JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);
        JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/Sistema Restaurante/Ireport/RelatorioVendaR.pdf");
        File file= new File("C:/Sistema Restaurante/Ireport/RelatorioVendaR.pdf");
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
   public boolean gerarRelatorioCaixa(int codigo){              
       Venda ven=new Venda();
       try {           
           conexao.conexao();           
           conexao.executaSQL("select vencxcod,vencod,venmcod,vendata,hora,ventotal,pagamento,cxsinicial,cxsfinal from venda inner join caixa on vencxcod=cxcod where vencxcod="+codigo+"");
           
        JRResultSetDataSource jrRS= new JRResultSetDataSource(conexao.rs);
        InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("Relatorios/RelatorioCaixa.jasper");
//        java.util.HashMap filtro = new java.util.HashMap();;
        JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);
        JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/Sistema Restaurante/Ireport/RelatorioCaixa.pdf");
        File file= new File("C:/Sistema Restaurante/Ireport/RelatorioCaixa.pdf");
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
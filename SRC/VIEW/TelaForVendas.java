package view;

import java.applet.Applet;
import java.awt.print.Printable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import model.bean.ItemVenda;
import model.bean.Produto;
import model.bean.Venda;
import model.dao.ExecutaSql;
import model.dao.ModeloTabela;
import model.dao.ProdutoDao;
import model.dao.VendaDao;
import java.awt.*;
import java.awt.print.*;
import java.net.URL;

public class TelaForVendas extends javax.swing.JFrame implements Printable{

    ExecutaSql conecta=new ExecutaSql();    
    Produto pro=new Produto();
    ProdutoDao prodao=new ProdutoDao();
    Venda ven= new Venda();
    VendaDao vendao= new VendaDao();
    int flag =1,codVenda,codItv;    
    String qtde,nome_produto,codCaixa,MesaN;
    double preco_produto,total=0;
    TelaForMesas tela = new TelaForMesas();
    
    public TelaForVendas(String mesa,int cod) {
        initComponents();
        transparenciabutton();
        setIcon();
        jLabelMesa.setText(mesa);        
        try{        
            if(cod==1){
                codVenda=0;
                conecta.conexao();
                conecta.executaSQL("select *from venda where venmcod="+mesa);
                conecta.rs.last();                     
                codVenda=conecta.rs.getInt("vencod");       
                txtdata.setText(conecta.rs.getString("vendata"));
                txtdata.setEditable(false);
                txthora.setText(vendao.mostraHora());
                txthora.setEditable(false);                
                txtproduto.setEnabled(true);       
                txtqtde.setEnabled(true);                              
                txtvalorItem.setEnabled(true);                              
                txtvalorTotal.setEnabled(true);
                btfinalizarVenda.setEnabled(true);
                btAbreVenda.setEnabled(false);
                btcancelarVenda.setEnabled(true);        
                btProcurarProduto.setEnabled(true);
                btadd.setEnabled(true);
                btexcluir.setEnabled(true);
                txtDinheiro.setEnabled(true);
                txtTroco.setEnabled(true);
                jRadioBtDinheiro.setEnabled(true);
                jRadioBtDebito.setEnabled(true);
                jRadioBtCredito.setEnabled(true);
                preencherTabelaProduto("select * from produto inner join grupoproduto on progrpcod=grpcod  where proestoque>0 order by pronome");
                preencherTabelaItensVenda("select *from produto inner join itemvenda on itvprocod=procod inner join venda on vencod=itvvencod where vencod="+codVenda);
                txtproduto.setText("");
                txtqtde.setText("");                                                    
                txtvalorItem.setText("");                                                    
                txtvalorTotal.setText("");
            }else{                    
                txtdata.setText(vendao.pegarData());
                txthora.setText(vendao.mostraHora());
                txtdata.setEditable(false);        
                txthora.setEditable(false);        
                txtproduto.setEnabled(false);       
                txtqtde.setEnabled(false);                              
                txtvalorItem.setEnabled(false);                              
                txtvalorTotal.setEnabled(false);
                btfinalizarVenda.setEnabled(false);
                btAbreVenda.setEnabled(true);
                btcancelarVenda.setEnabled(false);        
                btProcurarProduto.setEnabled(false);
                btadd.setEnabled(false);
                btexcluir.setEnabled(false);
                txtDinheiro.setEnabled(false);
                txtTroco.setEnabled(false);
                jRadioBtDinheiro.setEnabled(false);
                jRadioBtDebito.setEnabled(false);
                jRadioBtCredito.setEnabled(false);                
        }}catch(Exception ex){        
    }
}    

    private TelaForVendas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagens/venda.png")));
    }
    public void transparenciabutton(){
        btadd.setOpaque(false);
        btadd.setContentAreaFilled(false);
        btadd.setBorderPainted(false);        
        
        btProcurarProduto.setOpaque(false);
        btProcurarProduto.setContentAreaFilled(false);
        btProcurarProduto.setBorderPainted(false);
        
        btImprimir.setOpaque(false);
        btImprimir.setContentAreaFilled(false);
        btImprimir.setBorderPainted(false);
        
        btexcluir.setOpaque(false);
        btexcluir.setContentAreaFilled(false);
        btexcluir.setBorderPainted(false);
    }
    public void preencherTabelaItensVenda(String QSL){
        ArrayList dados=new ArrayList();
        String[] colunas=new String[]{"Cod","Descrição","Qtde.","Sub Total","Obs."};
         
        conecta.executaSQL(QSL);
        try{
            conecta.rs.first();
            do{
                double valorProduto=conecta.rs.getDouble("propreco");
                int qtdeVendida= conecta.rs.getInt("itvqtde");
                dados.add(new Object[]{conecta.rs.getInt("itvcod"),conecta.rs.getString("pronome"),conecta.rs.getInt("itvqtde"),valorProduto*qtdeVendida,conecta.rs.getString("itvobs")});
            }while(conecta.rs.next());                             
        }catch(Exception erro){
        }
           
        ModeloTabela modelo=new ModeloTabela(dados,colunas);
        tabelaItensVenda.setModel(modelo);
        
        tabelaItensVenda.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabelaItensVenda.getColumnModel().getColumn(0).setResizable(false);        
        
        tabelaItensVenda.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabelaItensVenda.getColumnModel().getColumn(1).setResizable(false);
        
        tabelaItensVenda.getColumnModel().getColumn(2).setPreferredWidth(40);
        tabelaItensVenda.getColumnModel().getColumn(2).setResizable(false);
        
        tabelaItensVenda.getColumnModel().getColumn(3).setPreferredWidth(80);
        tabelaItensVenda.getColumnModel().getColumn(3).setResizable(false);
        
        tabelaItensVenda.getColumnModel().getColumn(4).setPreferredWidth(200);
        tabelaItensVenda.getColumnModel().getColumn(4).setResizable(false);
        
        tabelaItensVenda.getTableHeader().setReorderingAllowed(false);
        tabelaItensVenda.setAutoResizeMode(tabelaItensVenda.AUTO_RESIZE_OFF);
        
        tabelaItensVenda.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        SomaProduto();
    }
    public void LimparTabelaItensVenda(){
        ArrayList dados=new ArrayList();
        String[] colunas=new String[]{"Cod","Descrição","Qtde.","Sub Total","Obs."};                                                                          
           
        ModeloTabela modelo=new ModeloTabela(dados,colunas);
        tabelaItensVenda.setModel(modelo);                
        tabelaItensVenda.getTableHeader().setReorderingAllowed(false);
        tabelaItensVenda.setAutoResizeMode(tabelaItensVenda.AUTO_RESIZE_OFF);
        tabelaItensVenda.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    public void LimparTabelaPesquisa(){
        ArrayList dados=new ArrayList();
        String[] colunas=new String[]{"Cod","Nome","Estoque","Tipo"};                                                                         
           
        ModeloTabela modelo=new ModeloTabela(dados,colunas);
        tabelapesquisa.setModel(modelo);
        tabelapesquisa.getTableHeader().setReorderingAllowed(false);
        tabelapesquisa.setAutoResizeMode(tabelapesquisa.AUTO_RESIZE_OFF);
        tabelapesquisa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    public void SomaProduto(){
        total=0;
        int qtde=0;
        double valor=0;
        conecta.executaSQL("select *from itemvenda inner join produto on itvprocod=procod where itvvencod="+codVenda);
        try {
            while(conecta.rs.next()){
                total=total+conecta.rs.getDouble("propreco")*conecta.rs.getInt("itvqtde");
            }
            txtvalorTotal.setText(String.valueOf(total));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro na soma do total!\nErro:"+ex);
        }
    }            
    public void preencherTabelaProduto(String QSL){
        ArrayList dados=new ArrayList();
        String[] colunas=new String[]{"Cod","Nome","Estoque","Tipo"};
           
        conecta.executaSQL(QSL);
        try{
            conecta.rs.first();
            do{
               dados.add(new Object[]{conecta.rs.getInt("procod"),conecta.rs.getString("pronome"),conecta.rs.getString("proestoque"),conecta.rs.getString("grpdesc")});
            }while(conecta.rs.next());               
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "Sem Produtos no Estoque!"+erro.getMessage());               
        }
           
        ModeloTabela modelo=new ModeloTabela(dados,colunas);
        tabelapesquisa.setModel(modelo);
        
        tabelapesquisa.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabelapesquisa.getColumnModel().getColumn(0).setResizable(false);
        
        tabelapesquisa.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabelapesquisa.getColumnModel().getColumn(1).setResizable(false);
        
        tabelapesquisa.getColumnModel().getColumn(2).setPreferredWidth(70);
        tabelapesquisa.getColumnModel().getColumn(2).setResizable(false);
        
        tabelapesquisa.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabelapesquisa.getColumnModel().getColumn(3).setResizable(false);
        
        tabelapesquisa.getTableHeader().setReorderingAllowed(false);
        tabelapesquisa.setAutoResizeMode(tabelapesquisa.AUTO_RESIZE_OFF);
        tabelapesquisa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtproduto = new javax.swing.JTextField();
        btProcurarProduto = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtqtde = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtvalorItem = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtvalorTotal = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelapesquisa = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelaItensVenda = new javax.swing.JTable();
        btfinalizarVenda = new javax.swing.JButton();
        btcancelarVenda = new javax.swing.JButton();
        btadd = new javax.swing.JButton();
        btAbreVenda = new javax.swing.JButton();
        jLabelMesa = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDinheiro = new javax.swing.JTextField();
        txtTroco = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jRadioBtDinheiro = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        jRadioBtDebito = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        jRadioBtCredito = new javax.swing.JRadioButton();
        btImprimir = new javax.swing.JButton();
        btexcluir = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txthora = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtdata = new javax.swing.JTextField();
        jLabelFundo = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Vendas");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Data:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(10, 20, 50, 30);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Produto:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(12, 67, 105, 29);

        txtproduto.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtproduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtprodutoActionPerformed(evt);
            }
        });
        txtproduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprodutoKeyPressed(evt);
            }
        });
        jPanel1.add(txtproduto);
        txtproduto.setBounds(12, 102, 275, 50);

        btProcurarProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/lupa.png"))); // NOI18N
        btProcurarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btProcurarProdutoActionPerformed(evt);
            }
        });
        jPanel1.add(btProcurarProduto);
        btProcurarProduto.setBounds(293, 96, 42, 41);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Quantidade:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(390, 100, 146, 29);

        txtqtde.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtqtde.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtqtdeMouseClicked(evt);
            }
        });
        txtqtde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtqtdeKeyPressed(evt);
            }
        });
        jPanel1.add(txtqtde);
        txtqtde.setBounds(540, 100, 49, 35);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Valor por item:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(590, 100, 179, 29);

        txtvalorItem.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtvalorItem.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtvalorItem.setEnabled(false);
        jPanel1.add(txtvalorItem);
        txtvalorItem.setBounds(770, 100, 80, 35);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Valor Total:");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(591, 362, 139, 29);

        txtvalorTotal.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtvalorTotal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtvalorTotalFocusGained(evt);
            }
        });
        txtvalorTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtvalorTotalActionPerformed(evt);
            }
        });
        jPanel1.add(txtvalorTotal);
        txtvalorTotal.setBounds(734, 359, 98, 35);

        tabelapesquisa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tabelapesquisa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabelapesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelapesquisaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelapesquisa);

        jPanel1.add(jScrollPane3);
        jScrollPane3.setBounds(12, 201, 416, 130);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Tabela de pesquisa");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(12, 166, 226, 29);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Itens da Venda");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(484, 166, 180, 29);

        tabelaItensVenda.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tabelaItensVenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabelaItensVenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaItensVendaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabelaItensVenda);

        jPanel1.add(jScrollPane4);
        jScrollPane4.setBounds(484, 201, 366, 130);

        btfinalizarVenda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btfinalizarVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/fechar.png"))); // NOI18N
        btfinalizarVenda.setText("Finalizar Venda");
        btfinalizarVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btfinalizarVendaActionPerformed(evt);
            }
        });
        jPanel1.add(btfinalizarVenda);
        btfinalizarVenda.setBounds(550, 550, 157, 40);

        btcancelarVenda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btcancelarVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/007-negative.png"))); // NOI18N
        btcancelarVenda.setText("Cancelar Venda");
        btcancelarVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcancelarVendaActionPerformed(evt);
            }
        });
        jPanel1.add(btcancelarVenda);
        btcancelarVenda.setBounds(370, 550, 163, 40);

        btadd.setBackground(new java.awt.Color(0, 0, 0));
        btadd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/add.png"))); // NOI18N
        btadd.setBorder(null);
        btadd.setOpaque(false);
        btadd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btaddFocusGained(evt);
            }
        });
        btadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btaddActionPerformed(evt);
            }
        });
        jPanel1.add(btadd);
        btadd.setBounds(434, 210, 44, 47);

        btAbreVenda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btAbreVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/abrir.png"))); // NOI18N
        btAbreVenda.setMnemonic('e');
        btAbreVenda.setText("Nova Venda");
        btAbreVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAbreVendaActionPerformed(evt);
            }
        });
        jPanel1.add(btAbreVenda);
        btAbreVenda.setBounds(200, 550, 150, 41);

        jLabelMesa.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelMesa.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(jLabelMesa);
        jLabelMesa.setBounds(730, 10, 50, 30);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Valor recebido:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(18, 444, 181, 29);

        txtDinheiro.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtDinheiro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDinheiroKeyPressed(evt);
            }
        });
        jPanel1.add(txtDinheiro);
        txtDinheiro.setBounds(203, 441, 132, 35);

        txtTroco.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtTroco.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTrocoFocusGained(evt);
            }
        });
        jPanel1.add(txtTroco);
        txtTroco.setBounds(203, 476, 132, 35);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Troco:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(122, 476, 77, 29);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Formas de Pagamento:");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(18, 362, 276, 29);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Dinheiro:");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(18, 408, 85, 22);

        jRadioBtDinheiro.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioBtDinheiro);
        jRadioBtDinheiro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioBtDinheiroMouseClicked(evt);
            }
        });
        jRadioBtDinheiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioBtDinheiroActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioBtDinheiro);
        jRadioBtDinheiro.setBounds(103, 408, 21, 21);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Debito:");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(124, 408, 66, 22);

        jRadioBtDebito.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioBtDebito);
        jRadioBtDebito.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioBtDebitoMouseClicked(evt);
            }
        });
        jRadioBtDebito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioBtDebitoActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioBtDebito);
        jRadioBtDebito.setBounds(190, 408, 21, 21);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Credito:");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(211, 408, 72, 22);

        jRadioBtCredito.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioBtCredito);
        jRadioBtCredito.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioBtCreditoMouseClicked(evt);
            }
        });
        jRadioBtCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioBtCreditoActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioBtCredito);
        jRadioBtCredito.setBounds(283, 408, 21, 21);

        btImprimir.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/print.png"))); // NOI18N
        btImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IMPRIME(evt);
            }
        });
        jPanel1.add(btImprimir);
        btImprimir.setBounds(405, 403, 95, 73);

        btexcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/x-button (1).png"))); // NOI18N
        btexcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btexcluirActionPerformed(evt);
            }
        });
        jPanel1.add(btexcluir);
        btexcluir.setBounds(434, 275, 42, 41);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Hora:");
        jPanel1.add(jLabel15);
        jLabel15.setBounds(140, 20, 51, 30);

        txthora.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txthora.setEnabled(false);
        jPanel1.add(txthora);
        txthora.setBounds(190, 20, 60, 30);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Tela de Vendas");
        jPanel1.add(jLabel17);
        jLabel17.setBounds(341, 13, 179, 29);

        txtdata.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtdata.setEnabled(false);
        jPanel1.add(txtdata);
        txtdata.setBounds(60, 20, 80, 30);

        jLabelFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/blue.jpg"))); // NOI18N
        jPanel1.add(jLabelFundo);
        jLabelFundo.setBounds(-560, 0, 1490, 770);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(874, 660));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btProcurarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btProcurarProdutoActionPerformed
        flag=1;
        preencherTabelaProduto("select * from produto inner join grupoproduto on progrpcod=grpcod WHERE pronome LIKE'%"+txtproduto.getText()+"%' and proestoque>0 order by pronome");
    }//GEN-LAST:event_btProcurarProdutoActionPerformed

    private void tabelapesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelapesquisaMouseClicked
    if(flag==1){
        conecta.conexao();
        try {
            nome_produto=""+tabelapesquisa.getValueAt(tabelapesquisa.getSelectedRow(),1);
            conecta.executaSQL("select *from produto where pronome='"+nome_produto+"'");
            conecta.rs.first();
            preco_produto=conecta.rs.getDouble("propreco");
            txtvalorItem.setText(String.valueOf(preco_produto));
            txtqtde.setText("1");
            txtqtde.requestFocus();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar preco");
        }
    }
    }//GEN-LAST:event_tabelapesquisaMouseClicked

    private void btaddFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btaddFocusGained
        double valorTotal;
        valorTotal=Double.parseDouble(txtvalorItem.getText())*Integer.parseInt(txtqtde.getText());
        txtvalorTotal.setText(String.valueOf(valorTotal));
    }//GEN-LAST:event_btaddFocusGained
    public void play(String nome){
        URL url = TelaForVendas.class.getResource(nome+".wav");
        java.applet.AudioClip audio= Applet.newAudioClip(url);
        audio.play();
    }
    private void btaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btaddActionPerformed
    try {
        int quantidade=0;
        conecta.conexao();
        conecta.executaSQL("select *from produto where pronome='"+nome_produto+"'");
        conecta.rs.first();
        quantidade=conecta.rs.getInt("proestoque");
        if(quantidade>=Integer.parseInt(txtqtde.getText())){                                                        
            int resul= quantidade-Integer.parseInt(txtqtde.getText());
            ven.setNomeProduto(nome_produto);
            ven.setQtdeItem(Integer.parseInt(txtqtde.getText()));
            ven.setVencod(codVenda);
            vendao.adcionaItem(ven);
            
            play("beep");
            preencherTabelaProduto("select * from produto inner join grupoproduto on progrpcod=grpcod  where proestoque>0 order by pronome");
            preencherTabelaItensVenda("select *from produto inner join itemvenda on itvprocod=procod inner join venda on vencod=itvvencod where vencod="+codVenda);
            }else{
                JOptionPane.showMessageDialog(rootPane, " A quantidade desejada não esta disponivel no estoque!");
            }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Selecione um produto!");
    }
    }//GEN-LAST:event_btaddActionPerformed

    private void txtvalorTotalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtvalorTotalFocusGained
        double valorTotal;
        valorTotal=Double.parseDouble(txtvalorItem.getText())*Integer.parseInt(txtqtde.getText());
        txtvalorTotal.setText(String.valueOf(total));                
    }//GEN-LAST:event_txtvalorTotalFocusGained

    private void btfinalizarVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btfinalizarVendaActionPerformed
        if(jRadioBtDinheiro.isSelected()==true){
            ven.setPagamento("Dinheiro");
        }else if(jRadioBtDebito.isSelected()==true){
            ven.setPagamento("Debito");            
        }else if(jRadioBtCredito.isSelected()==true){
            ven.setPagamento("Credito");            
        }else{            
            JOptionPane.showMessageDialog(null, "Selecionar forma de pagamento!","Aviso ",JOptionPane.WARNING_MESSAGE);            
            jRadioBtDinheiro.requestFocus();
            return;
        }
        if(jRadioBtDebito.isSelected()==true || jRadioBtCredito.isSelected()==true){
            txtDinheiro.setText("0");
            txtTroco.setText("0");
        }
        if(jRadioBtDinheiro.isSelected()==true && txtDinheiro.getText().equals("")){
            txtDinheiro.requestFocus();
            JOptionPane.showMessageDialog(null, "Campo Dinheiro Pago Obrigatorio ","Aviso ",JOptionPane.WARNING_MESSAGE);
            return;   
        }
        else if(jRadioBtDinheiro.isSelected()==true && txtTroco.getText().equals("")){
            txtTroco.requestFocus();
            JOptionPane.showMessageDialog(null, "Campo Troco Obrigatorio ","Aviso ",JOptionPane.WARNING_MESSAGE);
            return;   
        }
        
        txtproduto.setEnabled(false);               
        txtqtde.setEnabled(false);                                      
        txtvalorItem.setEnabled(false);                                      
        txtvalorTotal.setEnabled(false);
        btfinalizarVenda.setEnabled(false);
        btcancelarVenda.setEnabled(false);        
        btProcurarProduto.setEnabled(false);        
        btadd.setEnabled(false);
        btexcluir.setEnabled(false);
        btAbreVenda.setEnabled(true);
        txtTroco.setEnabled(false);
        txtDinheiro.setEnabled(false);
        
        double valorTotal;
        valorTotal=Double.parseDouble(txtvalorItem.getText())*Integer.parseInt(txtqtde.getText());
        txtvalorTotal.setText(String.valueOf(total));
        
        ven.setVencod(codVenda);
        conecta.conexao();
        try {            
            conecta.executaSQL("select *from caixa where cxstatus='ABERTO'");
            conecta.rs.last();
            codCaixa=conecta.rs.getString("cxcod");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "nao achou codigo do caixa");
        }
        ven.setCxvencod(codCaixa);                
        ven.setVenhora(txthora.getText());
        ven.setVentotal(Double.parseDouble(txtvalorTotal.getText()));        
        ven.setValorRecebido(Double.parseDouble(txtDinheiro.getText()));
        ven.setTroco(Double.parseDouble(txtTroco.getText()));        
        ven.setVenmstatus("Livre");
        vendao.fechaVenda(ven);                            
        
        int opcao=JOptionPane.showConfirmDialog(null, "Você deseja imprimir a venda?");
        switch(opcao){
            case 0:
                final TelaForAguarde carregando= new TelaForAguarde();
                carregando.setVisible(true);
                Thread t= new Thread(){
                    public void run(){
                    //metodo de imprimir
                        try {
                            vendao.gerarRelatorioVenda(codVenda);
                            carregando.dispose();                
                        } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Erro ao gerar relatorio/nErro:"+e);
                        }
                    }
                };
                t.start();
            break;
        }
        txtproduto.setText("");
        txtqtde.setText("");
        txtvalorItem.setText("");
        txtvalorTotal.setText("");
        txtTroco.setText("");
        txtDinheiro.setText("");
        jRadioBtDinheiro.setEnabled(false);
        jRadioBtCredito.setEnabled(false);
        jRadioBtDebito.setEnabled(false);
        jRadioBtDinheiro.setSelected(false);       
        jRadioBtDebito.setSelected(false);
        jRadioBtCredito.setSelected(false);
        LimparTabelaPesquisa();
        LimparTabelaItensVenda();   
        dispose();
    }//GEN-LAST:event_btfinalizarVendaActionPerformed
        
    private void btcancelarVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcancelarVendaActionPerformed
        conecta.conexao();
        try{
            conecta.executaSQL("select *from venda where venmcod="+jLabelMesa.getText());
            conecta.rs.last();
            codVenda=conecta.rs.getInt("vencod");                      
            ven.setVencod(codVenda);
            vendao.cancelaVenda(jLabelMesa.getText(),codVenda);                                                                               
            dispose();
            
            txtproduto.setEnabled(false);               
            txtqtde.setEnabled(false);                              
            txtvalorItem.setEnabled(false);                              
            txtvalorTotal.setEnabled(false);
            btfinalizarVenda.setEnabled(false);
            btcancelarVenda.setEnabled(false);        
            btProcurarProduto.setEnabled(false);
            tabelapesquisa.setEnabled(false);
            tabelaItensVenda.setEnabled(false);
            btadd.setEnabled(false);
            btexcluir.setEnabled(false);
            txtDinheiro.setEnabled(false);
            txtTroco.setEnabled(false);
            jRadioBtDinheiro.setEnabled(false);
            jRadioBtDebito.setEnabled(false);
            jRadioBtCredito.setEnabled(false);       
            btAbreVenda.setEnabled(true);
            txtproduto.setText("");
            txtqtde.setText("");
            txtvalorItem.setText("");
            txtvalorTotal.setText("");
            txtTroco.setText("");
            txtDinheiro.setText("");
            jRadioBtDinheiro.setEnabled(false);
            jRadioBtCredito.setEnabled(false);
            jRadioBtDebito.setEnabled(false);
            jRadioBtDinheiro.setSelected(false);       
            jRadioBtDebito.setSelected(false);
            jRadioBtCredito.setSelected(false);
            LimparTabelaPesquisa();
            LimparTabelaItensVenda();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(rootPane, "Erro ao cancelar venda \nErro:"+ex);
        }
    }//GEN-LAST:event_btcancelarVendaActionPerformed

    private void txtvalorTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtvalorTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtvalorTotalActionPerformed

    private void btAbreVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAbreVendaActionPerformed
        txthora.setText(vendao.mostraHora());
        txthora.setEditable(false);
        txtproduto.setEnabled(true);       
        txtqtde.setEnabled(true);                              
        txtvalorItem.setEnabled(true);                              
        txtvalorTotal.setEnabled(true);
        txtproduto.requestFocus();
        btfinalizarVenda.setEnabled(true);
        btcancelarVenda.setEnabled(true);        
        btAbreVenda.setEnabled(false);        
        btProcurarProduto.setEnabled(true);
        tabelapesquisa.setEnabled(true);
        tabelaItensVenda.setEnabled(true);
        btadd.setEnabled(true);
        jRadioBtDinheiro.setEnabled(true);        
        jRadioBtDinheiro.setSelected(true);
        jRadioBtDebito.setEnabled(true);
        jRadioBtCredito.setEnabled(true);
        txtDinheiro.setEnabled(true);
        txtTroco.setEnabled(true);
        btexcluir.setEnabled(true);        
        //Inicia a venda
        conecta.conexao();
        try {
            PreparedStatement pstm= conecta.conn.prepareStatement("insert into venda (venmcod,vendata,ventotal,venmstatus)values(?,?,?,?)");
            pstm.setString(1,jLabelMesa.getText());
            pstm.setString(2,txtdata.getText());
            pstm.setDouble(3,0);
            pstm.setString(4,"Ocupado");
            pstm.execute();            
            
            conecta.executaSQL("select *from venda where venmcod="+jLabelMesa.getText());
            conecta.rs.last();
            codVenda=conecta.rs.getInt("vencod");                                       
            
        } catch (SQLException ex) {            
        }
        preencherTabelaProduto("select * from produto inner join grupoproduto on progrpcod=grpcod  where proestoque>0 order by pronome");
        txtproduto.setText("");
        txtqtde.setText("");                                                    
        txtvalorItem.setText("");                                                    
        txtvalorTotal.setText("");                                                    
    }//GEN-LAST:event_btAbreVendaActionPerformed

    private void txtTrocoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTrocoFocusGained
        double troco;
        troco=Double.parseDouble(txtDinheiro.getText())-Double.parseDouble(txtvalorTotal.getText());
        txtTroco.setText(String.valueOf(troco));                
    }//GEN-LAST:event_txtTrocoFocusGained

    private void tabelaItensVendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaItensVendaMouseClicked
        conecta.conexao();            
        nome_produto=""+tabelaItensVenda.getValueAt(tabelaItensVenda.getSelectedRow(),1);                
        qtde=""+tabelaItensVenda.getValueAt(tabelaItensVenda.getSelectedRow(),2);
        String preco=""+tabelaItensVenda.getValueAt(tabelaItensVenda.getSelectedRow(),3);
        txtqtde.setText(qtde);
        txtvalorItem.setText(preco);
        ItemVenda itv= new ItemVenda();
        int cod=(int)tabelaItensVenda.getValueAt(tabelaItensVenda.getSelectedRow(),0);
        conecta.executaSQL("select *from itemvenda inner join produto on itvprocod=procod where itvcod="+cod);
    }//GEN-LAST:event_tabelaItensVendaMouseClicked

    private void txtprodutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtprodutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtprodutoActionPerformed

    private void jRadioBtDinheiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioBtDinheiroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioBtDinheiroActionPerformed

    private void jRadioBtDebitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioBtDebitoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioBtDebitoActionPerformed

    private void jRadioBtCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioBtCreditoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioBtCreditoActionPerformed

    private void IMPRIME(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IMPRIME
    final TelaForAguarde carregando= new TelaForAguarde();
    carregando.setVisible(true);
    Thread t= new Thread(){
        public void run(){
            //metodo de imprimir
            try {
                vendao.gerarRelatorioVenda(codVenda);
                carregando.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao gerar relatorio/nErro:"+e);
            }
        }
    };
    t.start();
    }//GEN-LAST:event_IMPRIME

    private void btexcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btexcluirActionPerformed
    int opcao=JOptionPane.showConfirmDialog(null, "Você deseja remover o item: "+nome_produto+"?");
        switch(opcao){
            case 0:
                try {
                    int quantidade=0;
                    conecta.conexao();
                    conecta.executaSQL("select *from produto where pronome='"+nome_produto+"'");
                    conecta.rs.first();
                    quantidade=conecta.rs.getInt("proestoque");
                    if(quantidade>=Integer.parseInt(txtqtde.getText())){                                                        
                        int cod=(int)tabelaItensVenda.getValueAt(tabelaItensVenda.getSelectedRow(),0);            
                        conecta.executaSQL("select *from itemvenda inner join produto where itvcod="+cod);
                        ItemVenda itv=new ItemVenda();      
                        ven.setNomeProduto(nome_produto);                
                        ven.setQtdeItem(Integer.parseInt(txtqtde.getText()));
                        itv.setItvcod(Integer.valueOf(cod));            
                        vendao.removeItem(itv); 
                        vendao.estornoItem(ven);
                        preencherTabelaItensVenda("select *from produto inner join itemvenda on itvprocod=procod inner join venda on vencod=itvvencod where vencod="+codVenda);
                        preencherTabelaProduto("select * from produto inner join grupoproduto on progrpcod=grpcod  where proestoque>0 order by pronome");
                    }else{
                        JOptionPane.showMessageDialog(rootPane, " A quantidade desejada não esta disponivel no estoque!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Selecione um produto!");
                }
            break;
        }        
    }//GEN-LAST:event_btexcluirActionPerformed

    private void jRadioBtDinheiroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioBtDinheiroMouseClicked
        txtDinheiro.setEnabled(true);
        txtTroco.setEnabled(true);
        txtDinheiro.requestFocus();
    }//GEN-LAST:event_jRadioBtDinheiroMouseClicked

    private void jRadioBtDebitoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioBtDebitoMouseClicked
        txtDinheiro.setEnabled(false);
        txtTroco.setEnabled(false);
        txtDinheiro.setText("");
        txtTroco.setText("");
    }//GEN-LAST:event_jRadioBtDebitoMouseClicked

    private void jRadioBtCreditoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioBtCreditoMouseClicked
        txtDinheiro.setEnabled(false);
        txtTroco.setEnabled(false);
        txtDinheiro.setText("");
        txtTroco.setText("");
    }//GEN-LAST:event_jRadioBtCreditoMouseClicked

    private void txtprodutoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprodutoKeyPressed
        if(evt.getKeyCode()==evt.VK_ENTER){
            flag=1;
            preencherTabelaProduto("select * from produto inner join grupoproduto on progrpcod=grpcod WHERE pronome LIKE'%"+txtproduto.getText()+"%' and proestoque>0 order by pronome");
        }
    }//GEN-LAST:event_txtprodutoKeyPressed

    private void txtDinheiroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDinheiroKeyPressed
        if(evt.getKeyCode()==evt.VK_ENTER){
            txtTroco.requestFocus();
        }
    }//GEN-LAST:event_txtDinheiroKeyPressed

    private void txtqtdeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtqtdeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtqtdeMouseClicked

    private void txtqtdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtqtdeKeyPressed
        if(evt.getKeyCode()==evt.VK_ENTER){
            try {
            int quantidade=0;
            conecta.conexao();
            conecta.executaSQL("select *from produto where pronome='"+nome_produto+"'");
            conecta.rs.first();
            quantidade=conecta.rs.getInt("proestoque");
            if(quantidade>=Integer.parseInt(txtqtde.getText())){                                                        
                ven.setNomeProduto(nome_produto);
                ven.setQtdeItem(Integer.parseInt(txtqtde.getText()));
                ven.setVencod(codVenda);
                vendao.adcionaItem(ven);
            
                play("beep");
                preencherTabelaProduto("select * from produto inner join grupoproduto on progrpcod=grpcod  where proestoque>0 order by pronome");
                preencherTabelaItensVenda("select *from produto inner join itemvenda on itvprocod=procod inner join venda on vencod=itvvencod where vencod="+codVenda);
            }else{
                JOptionPane.showMessageDialog(rootPane, " A quantidade desejada não esta disponivel no estoque!");
            }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Selecione um produto!");
        }
    }
    }//GEN-LAST:event_txtqtdeKeyPressed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaForVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaForVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaForVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaForVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaForVendas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAbreVenda;
    private javax.swing.JButton btImprimir;
    private javax.swing.JButton btProcurarProduto;
    private javax.swing.JButton btadd;
    private javax.swing.JButton btcancelarVenda;
    private javax.swing.JButton btexcluir;
    private javax.swing.JButton btfinalizarVenda;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelFundo;
    private javax.swing.JLabel jLabelMesa;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioBtCredito;
    private javax.swing.JRadioButton jRadioBtDebito;
    private javax.swing.JRadioButton jRadioBtDinheiro;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable tabelaItensVenda;
    private javax.swing.JTable tabelapesquisa;
    private javax.swing.JTextField txtDinheiro;
    private javax.swing.JTextField txtTroco;
    private javax.swing.JTextField txtdata;
    private javax.swing.JTextField txthora;
    private javax.swing.JTextField txtproduto;
    private javax.swing.JTextField txtqtde;
    private javax.swing.JTextField txtvalorItem;
    private javax.swing.JTextField txtvalorTotal;
    // End of variables declaration//GEN-END:variables

    @Override
    public int print(Graphics graf, PageFormat pagefor, int index) throws PrinterException {
        if(index>0){
            
            return NO_SUCH_PAGE;
        }
        Graphics2D hub=(Graphics2D) graf;        
        hub.translate(pagefor.getImageableX()-5, pagefor.getImageableY());
        hub.scale(1, 5);
        
        
        tabelaItensVenda.print(graf);
        
        return PAGE_EXISTS;
    }
}

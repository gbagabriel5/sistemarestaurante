package view;

import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import model.bean.Venda;
import model.connection.ConnectionFactory;
import model.dao.ExecutaSql;
import model.dao.ModeloTabela;
import model.dao.VendaDao;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class TelaPesquisarVenda extends javax.swing.JFrame {
    ExecutaSql conecta=new ExecutaSql();
    Venda ven= new Venda();
    VendaDao vendao= new VendaDao();
    int codigo,codi,cod;
    
    public TelaPesquisarVenda() {
        initComponents();
        transparenciabutton();
        setIcon();
        conecta.conexao();
        preencherTabela1("select *from caixa order by cxcod DESC");
        Timer timer = new Timer();
            final long SEGUNDOS = (1000*60);
            TimerTask tarefa = new TimerTask() {
            @Override
            public void run() {
                Atualizar();
            }
        };
            timer.scheduleAtFixedRate(tarefa,0, SEGUNDOS);
        
    }
    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagens/clock.png")));
    }
    public void transparenciabutton(){        
        btImprimir.setOpaque(false);
        btImprimir.setContentAreaFilled(false);
        btImprimir.setBorderPainted(false);   
        
        btImprimir1.setOpaque(false);
        btImprimir1.setContentAreaFilled(false);
        btImprimir1.setBorderPainted(false);   
        
        btAtualiza.setOpaque(false);
        btAtualiza.setContentAreaFilled(false);
        btAtualiza.setBorderPainted(false);   
        
        btprocurar.setOpaque(false);
        btprocurar.setContentAreaFilled(false);
        btprocurar.setBorderPainted(false);           
    }
    
    public void preencherTabela(String QSL){
           
           ArrayList dados=new ArrayList();
           String[] colunas=new String[]{"Codigo","Data","Hora","V.Total","Pagamento","V.Recebido","Troco"};
           
           conecta.executaSQL(QSL);
           try{
               conecta.rs.first();
              do{
                dados.add(new Object[]{conecta.rs.getInt("vencod"),conecta.rs.getString("vendata"),conecta.rs.getString("hora"),conecta.rs.getDouble("ventotal"),conecta.rs.getString("pagamento"),"R$"+conecta.rs.getDouble("vrecebido"),"R$"+conecta.rs.getDouble("troco")});
                }while(conecta.rs.next());               
           }catch(Exception erro){
//               JOptionPane.showMessageDialog(null, "Produto não cadastrado!"+erro.getMessage());               
           }
           
        ModeloTabela modelo=new ModeloTabela(dados,colunas);
        tabela.setModel(modelo);
        
        tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(0).setResizable(false);
        
        tabela.getColumnModel().getColumn(1).setPreferredWidth(80);
        tabela.getColumnModel().getColumn(1).setResizable(false);
        
        tabela.getColumnModel().getColumn(2).setPreferredWidth(70);
        tabela.getColumnModel().getColumn(2).setResizable(false);
        
        tabela.getColumnModel().getColumn(3).setPreferredWidth(60);
        tabela.getColumnModel().getColumn(3).setResizable(false);                
        
        tabela.getColumnModel().getColumn(4).setPreferredWidth(74);
        tabela.getColumnModel().getColumn(4).setResizable(false);        
        
        tabela.getColumnModel().getColumn(5).setPreferredWidth(80);
        tabela.getColumnModel().getColumn(5).setResizable(false);                
        
        tabela.getColumnModel().getColumn(6).setPreferredWidth(60);
        tabela.getColumnModel().getColumn(6).setResizable(false);                
        
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setAutoResizeMode(tabela.AUTO_RESIZE_OFF);
        
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
    }
    public void preencherTabela1(String QSL){
           
           ArrayList dados=new ArrayList();
           String[] colunas=new String[]{"Cod.","Data","Aberto em","Fechado em","Saldo Inicial","Saldo Final","Status do Caixa","Observação"};
           
           conecta.executaSQL(QSL);
           try{
               conecta.rs.first();
              do{
                dados.add(new Object[]{conecta.rs.getInt("cxcod"),conecta.rs.getString("cxdata"),conecta.rs.getString("cxdthabre"),conecta.rs.getString("cxdthfecha"),"R$"+conecta.rs.getDouble("cxsinicial"),"R$"+conecta.rs.getDouble("cxsfinal"),conecta.rs.getString("cxstatus"),conecta.rs.getString("cxobs")});
                }while(conecta.rs.next());               
           }catch(Exception erro){
//               JOptionPane.showMessageDialog(null, "Produto não cadastrado!"+erro.getMessage());               
           }
           
        ModeloTabela modelo=new ModeloTabela(dados,colunas);
        tabela1.setModel(modelo);
        
        tabela1.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela1.getColumnModel().getColumn(0).setResizable(false);
        
        tabela1.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabela1.getColumnModel().getColumn(1).setResizable(false);
        
        tabela1.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabela1.getColumnModel().getColumn(2).setResizable(false);
        
        tabela1.getColumnModel().getColumn(3).setPreferredWidth(80);
        tabela1.getColumnModel().getColumn(3).setResizable(false);                
        
        tabela1.getColumnModel().getColumn(4).setPreferredWidth(80);
        tabela1.getColumnModel().getColumn(4).setResizable(false);        
        
        tabela1.getColumnModel().getColumn(5).setPreferredWidth(100);
        tabela1.getColumnModel().getColumn(5).setResizable(false);                
        
        tabela1.getColumnModel().getColumn(6).setPreferredWidth(300);
        tabela1.getColumnModel().getColumn(6).setResizable(false);                
                              
        
        tabela1.getTableHeader().setReorderingAllowed(false);
        tabela1.setAutoResizeMode(tabela1.AUTO_RESIZE_OFF);
        
        tabela1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
    }
    public void limparTabela(){
           
        ArrayList dados=new ArrayList();
        String[] colunas=new String[]{"Codigo","Data","Hora","V.Total","Pagamento","V.Recebido","Troco"};
   
        ModeloTabela modelo=new ModeloTabela(dados,colunas);
        tabela.setModel(modelo);
                
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setAutoResizeMode(tabela.AUTO_RESIZE_OFF);
        
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);       
    }
    public void limparTabelaQTDE(){
           
        ArrayList dados=new ArrayList();
        String[] colunas=new String[]{"Produto","Qtde.","Lucro"};           
           
        ModeloTabela modelo=new ModeloTabela(dados,colunas);
        tabelaQtdeProduto.setModel(modelo);
                
        tabelaQtdeProduto.getTableHeader().setReorderingAllowed(false);
        tabelaQtdeProduto.setAutoResizeMode(tabelaQtdeProduto.AUTO_RESIZE_OFF);
        
        tabelaQtdeProduto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);       
    }
    public void preencherTabelaQtdePro(String QSL){
           
           ArrayList dados=new ArrayList();
           String[] colunas=new String[]{"Produto","Qtde.","Lucro"};
           
           conecta.executaSQL(QSL);
           try{
               conecta.rs.first();
              do{
                dados.add(new Object[]{conecta.rs.getString("pronome"),conecta.rs.getInt("sum(itvqtde)"),"R$"+conecta.rs.getDouble("(propreco-procusto)*sum(itvqtde)")});
                }while(conecta.rs.next());               
           }catch(Exception erro){
//               JOptionPane.showMessageDialog(null, "Produto não cadastrado!"+erro.getMessage());               
           }
           
        ModeloTabela modelo=new ModeloTabela(dados,colunas);
        tabelaQtdeProduto.setModel(modelo);
        
        tabelaQtdeProduto.getColumnModel().getColumn(0).setPreferredWidth(162);
        tabelaQtdeProduto.getColumnModel().getColumn(0).setResizable(false);
        
        tabelaQtdeProduto.getColumnModel().getColumn(1).setPreferredWidth(52);
        tabelaQtdeProduto.getColumnModel().getColumn(1).setResizable(false);                
        
        tabelaQtdeProduto.getColumnModel().getColumn(2).setPreferredWidth(80);
        tabelaQtdeProduto.getColumnModel().getColumn(2).setResizable(false);                
                
        tabelaQtdeProduto.getTableHeader().setReorderingAllowed(false);
        tabelaQtdeProduto.setAutoResizeMode(tabelaQtdeProduto.AUTO_RESIZE_OFF);
        
        tabelaQtdeProduto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);       
    }
    
    public void preencherTabelaItemVenda(String QSL){
           
        ArrayList dados=new ArrayList();
        String[] colunas=new String[]{"Produto","Subtotal","Preço","Qtde."};
           
           conecta.executaSQL(QSL);
           try{
               conecta.rs.first();
              do{
                double valorProduto=conecta.rs.getDouble("propreco");
                int qtdeVendida= conecta.rs.getInt("sum(itvqtde)");
                dados.add(new Object[]{conecta.rs.getString("pronome"),"R$"+valorProduto*qtdeVendida,"R$"+conecta.rs.getDouble("propreco"),conecta.rs.getInt("sum(itvqtde)")});
                }while(conecta.rs.next());               
           }catch(Exception erro){
//               JOptionPane.showMessageDialog(null, "Produto não cadastrado!"+erro.getMessage());               
           }
           
        ModeloTabela modelo=new ModeloTabela(dados,colunas);
        tabelaItemVenda.setModel(modelo);
        
        tabelaItemVenda.getColumnModel().getColumn(0).setPreferredWidth(150);
        tabelaItemVenda.getColumnModel().getColumn(0).setResizable(false);
        
        tabelaItemVenda.getColumnModel().getColumn(1).setPreferredWidth(60);
        tabelaItemVenda.getColumnModel().getColumn(1).setResizable(false);
        
        tabelaItemVenda.getColumnModel().getColumn(2).setPreferredWidth(60);
        tabelaItemVenda.getColumnModel().getColumn(2).setResizable(false);
        
        tabelaItemVenda.getColumnModel().getColumn(3).setPreferredWidth(40);
        tabelaItemVenda.getColumnModel().getColumn(3).setResizable(false);
                
//        
//        tabelapesquisa.getColumnModel().getColumn(4).setPreferredWidth(40);
//        tabelapesquisa.getColumnModel().getColumn(4).setResizable(false);
//        
//        tabelapesquisa.getColumnModel().getColumn(5).setPreferredWidth(200);
//        tabelapesquisa.getColumnModel().getColumn(5).setResizable(false);
//        
//        tabelapesquisa.getColumnModel().getColumn(6).setPreferredWidth(100);
//        tabelapesquisa.getColumnModel().getColumn(6).setResizable(false);
        
        tabelaItemVenda.getTableHeader().setReorderingAllowed(false);
        tabelaItemVenda.setAutoResizeMode(tabelaItemVenda.AUTO_RESIZE_OFF);
        
        tabelaItemVenda.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);       
    }
    public void limparTabelaITV(){
           
        ArrayList dados=new ArrayList();
        String[] colunas=new String[]{"Produto","Subtotal","Preço","Qtde."};
           
        ModeloTabela modelo=new ModeloTabela(dados,colunas);
        tabelaItemVenda.setModel(modelo);
                
        tabelaItemVenda.getTableHeader().setReorderingAllowed(false);
        tabelaItemVenda.setAutoResizeMode(tabelaItemVenda.AUTO_RESIZE_OFF);
        
        tabelaItemVenda.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);       
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelaItemVenda = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelaQtdeProduto = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTotal1 = new javax.swing.JFormattedTextField();
        txtTotal2 = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTotal3 = new javax.swing.JFormattedTextField();
        btImprimir = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabela1 = new javax.swing.JTable();
        btAtualiza = new javax.swing.JButton();
        btImprimir1 = new javax.swing.JButton();
        txtdt2 = new javax.swing.JFormattedTextField();
        txtdt1 = new javax.swing.JFormattedTextField();
        btprocurar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtLucroTotal = new javax.swing.JTextField();
        txtValorTotalP = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Relátorio de abertura e Fechamento de caixa");
        setMinimumSize(new java.awt.Dimension(1094, 395));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setForeground(new java.awt.Color(0, 153, 153));
        jPanel1.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Vendas Realizadas");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(200, 250, 120, 20);

        tabelaItemVenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tabelaItemVenda);

        jPanel1.add(jScrollPane4);
        jScrollPane4.setBounds(520, 270, 316, 123);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Detalhes da Venda");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(630, 250, 114, 15);

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabela);

        jPanel1.add(jScrollPane3);
        jScrollPane3.setBounds(10, 270, 480, 123);

        tabelaQtdeProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabelaQtdeProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaQtdeProdutoMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tabelaQtdeProduto);

        jPanel1.add(jScrollPane5);
        jScrollPane5.setBounds(860, 270, 300, 120);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Quantidade e Lucro total de Produtos vendidos");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(860, 250, 300, 15);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("á");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(180, 30, 10, 30);

        txtTotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotal.setEnabled(false);
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });
        jPanel1.add(txtTotal);
        txtTotal.setBounds(930, 70, 68, 30);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Valor Total de vendas no dinheiro:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(710, 110, 210, 30);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Valor Total de vendas no Debito:");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(710, 150, 201, 30);

        txtTotal1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotal1.setEnabled(false);
        txtTotal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotal1ActionPerformed(evt);
            }
        });
        jPanel1.add(txtTotal1);
        txtTotal1.setBounds(930, 110, 68, 30);

        txtTotal2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotal2.setEnabled(false);
        txtTotal2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotal2ActionPerformed(evt);
            }
        });
        jPanel1.add(txtTotal2);
        txtTotal2.setBounds(930, 150, 68, 30);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Valor Total de vendas no Credito:");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(710, 190, 205, 30);

        txtTotal3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotal3.setEnabled(false);
        txtTotal3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotal3ActionPerformed(evt);
            }
        });
        jPanel1.add(txtTotal3);
        txtTotal3.setBounds(930, 190, 68, 30);

        btImprimir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/printer.png"))); // NOI18N
        btImprimir.setText("Imprimir Relatorio do Caixa");
        btImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btImprimirActionPerformed(evt);
            }
        });
        jPanel1.add(btImprimir);
        btImprimir.setBounds(10, 410, 240, 40);

        tabela1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabela1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabela1MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tabela1);

        jPanel1.add(jScrollPane6);
        jScrollPane6.setBounds(10, 70, 680, 150);

        btAtualiza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/refresh.png"))); // NOI18N
        btAtualiza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAtualizaActionPerformed(evt);
            }
        });
        jPanel1.add(btAtualiza);
        btAtualiza.setBounds(470, 410, 50, 41);

        btImprimir1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btImprimir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/printer.png"))); // NOI18N
        btImprimir1.setText("Imprimir Venda");
        btImprimir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btImprimir1ActionPerformed(evt);
            }
        });
        jPanel1.add(btImprimir1);
        btImprimir1.setBounds(270, 410, 170, 40);

        try {
            txtdt2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtdt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdt2ActionPerformed(evt);
            }
        });
        txtdt2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdt2KeyPressed(evt);
            }
        });
        jPanel1.add(txtdt2);
        txtdt2.setBounds(200, 30, 90, 30);

        try {
            txtdt1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtdt1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdt1KeyPressed(evt);
            }
        });
        jPanel1.add(txtdt1);
        txtdt1.setBounds(80, 30, 90, 30);

        btprocurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/lupa.png"))); // NOI18N
        btprocurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btprocurarActionPerformed(evt);
            }
        });
        jPanel1.add(btprocurar);
        btprocurar.setBounds(300, 20, 65, 41);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Valor Total das vendas no dia:");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(710, 70, 184, 30);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Lucro Total de Vendas no Período:");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(710, 30, 220, 30);

        txtLucroTotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtLucroTotal.setEnabled(false);
        txtLucroTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLucroTotalActionPerformed(evt);
            }
        });
        jPanel1.add(txtLucroTotal);
        txtLucroTotal.setBounds(930, 30, 70, 30);

        txtValorTotalP.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtValorTotalP.setEnabled(false);
        jPanel1.add(txtValorTotalP);
        txtValorTotalP.setBounds(610, 30, 70, 30);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Período:");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(20, 30, 60, 30);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Valor Total de Vendas no Período:");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(400, 30, 210, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1173, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        codi=(int)tabela.getValueAt(tabela.getSelectedRow(),0);
        preencherTabelaItemVenda("select vencod,pronome,(propreco*itvqtde),propreco,sum(itvqtde) from venda inner join itemvenda on itvvencod=vencod inner join produto on itvprocod=procod where vencod="+codi+" group by pronome");                
        
    }//GEN-LAST:event_tabelaMouseClicked

    private void tabelaQtdeProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaQtdeProdutoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaQtdeProdutoMouseClicked

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void txtTotal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotal1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotal1ActionPerformed

    private void txtTotal2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotal2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotal2ActionPerformed

    private void txtTotal3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotal3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotal3ActionPerformed

    private void btImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btImprimirActionPerformed
    final TelaForAguarde carregando= new TelaForAguarde();
    carregando.setVisible(true);
    Thread t= new Thread(){
        public void run(){
            //metodo de imprimir
            try {
            vendao.gerarRelatorioCaixa(cod);
            carregando.dispose();
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatorio/nErro:"+e);
            }
        }
    };
    t.start();
    }//GEN-LAST:event_btImprimirActionPerformed

    private void tabela1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabela1MouseClicked
                            
        cod=(int)tabela1.getValueAt(tabela1.getSelectedRow(),0);        
        
        preencherTabela("select vencod,vendata,hora,ventotal,pagamento,vrecebido,troco from venda where vencxcod="+cod);
        
        preencherTabelaQtdePro("select pronome,sum(itvqtde),(propreco-procusto)*sum(itvqtde) from venda inner join itemvenda on vencod=itvvencod inner join produto on itvprocod=procod where vencxcod='"+cod+"' group by procod");
               
        double total=0;
        try { 
            conecta.executaSQL("select sum(ventotal)from venda where vencxcod='"+cod+"'");
            
            while(conecta.rs.next()){
            total=total+conecta.rs.getDouble("sum(ventotal)");            
            }
            txtTotal.setText("R$"+String.valueOf(total));                        
        } catch (SQLException ex) {
            Logger.getLogger(TelaPesquisarVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        double total1=0;
        try { 
            conecta.executaSQL("select sum(ventotal)from venda where pagamento='dinheiro' and vencxcod='"+cod+"'");
            
            while(conecta.rs.next()){
            total1=total1+conecta.rs.getDouble("sum(ventotal)");            
            }
            txtTotal1.setText("R$"+String.valueOf(total1));                        
        } catch (SQLException ex) {
            Logger.getLogger(TelaPesquisarVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        double total2=0;
        try { 
            conecta.executaSQL("select sum(ventotal)from venda where pagamento='debito' and vencxcod='"+cod+"'");
            
            while(conecta.rs.next()){
            total2=total2+conecta.rs.getDouble("sum(ventotal)");            
            }
            txtTotal2.setText("R$"+String.valueOf(total2));                        
        } catch (SQLException ex) {
            Logger.getLogger(TelaPesquisarVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        double total3=0;
        try { 
            conecta.executaSQL("select sum(ventotal)from venda where pagamento='credito' and vencxcod='"+cod+"'");
            
            while(conecta.rs.next()){
            total3=total3+conecta.rs.getDouble("sum(ventotal)");            
            }
            txtTotal3.setText("R$"+String.valueOf(total3));                        
        } catch (SQLException ex) {
            Logger.getLogger(TelaPesquisarVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
//        try {
//            conecta.executaSQL("");
//        } catch (Exception e) {
//        }
//    
    }//GEN-LAST:event_tabela1MouseClicked
    public void Atualizar(){
        preencherTabela1("select *from caixa order by cxcod DESC");
        limparTabela();
        limparTabelaITV();
        limparTabelaQTDE();
        txtTotal.setText("");
        txtTotal1.setText("");
        txtTotal2.setText("");
        txtTotal3.setText("");
    }
            
    private void btAtualizaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAtualizaActionPerformed
        Atualizar();
    }//GEN-LAST:event_btAtualizaActionPerformed

    private void btImprimir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btImprimir1ActionPerformed
    final TelaForAguarde carregando= new TelaForAguarde();
    carregando.setVisible(true);
    Thread t= new Thread(){
        public void run(){
            //metodo de imprimir
            try {
            vendao.gerarRelatorioVenda(codi);
            carregando.dispose();
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatorio/nErro:"+e);
            }
        }
    };
    t.start();
    }//GEN-LAST:event_btImprimir1ActionPerformed

    private void btprocurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btprocurarActionPerformed
        preencherTabela1("select *from caixa where cxdata>='"+txtdt1.getText()+"' and cxdata<='"+txtdt2.getText()+"' order by cxcod DESC");               
        
        double t=0,lucro=0;
        try {
            conecta.executaSQL("select ((propreco-procusto)*sum(itvqtde)),sum(ventotal) from caixa inner join venda on vencxcod=cxcod inner join itemvenda on vencod=itvvencod inner join produto on itvprocod=procod where cxdthabre>='"+txtdt1.getText()+"' and cxdthabre<='"+txtdt2.getText()+"'");        
            while(conecta.rs.next()){
            lucro=lucro+conecta.rs.getDouble("((propreco-procusto)*sum(itvqtde))");            
            t=t+conecta.rs.getDouble("sum(ventotal)");            
            }
//            JOptionPane.showMessageDialog(null, lucro+' '+t);
            txtValorTotalP.setText("R$ "+String.valueOf(t));            
            txtLucroTotal.setText("R$ "+String.valueOf(lucro));
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Erro:/n"+ex);            
        }                
    }//GEN-LAST:event_btprocurarActionPerformed

    private void txtdt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdt2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdt2ActionPerformed

    private void txtdt1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdt1KeyPressed
    if(evt.getKeyCode()==evt.VK_ENTER){
        txtdt2.requestFocus();
    }
    }//GEN-LAST:event_txtdt1KeyPressed

    private void txtdt2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdt2KeyPressed
    if(evt.getKeyCode()==evt.VK_ENTER){
        preencherTabela1("select *from caixa where cxdata>='"+txtdt1.getText()+"' and cxdata<='"+txtdt2.getText()+"' order by cxcod DESC");               
        
        double t=0,lucro=0;
        try {
            conecta.executaSQL("select ((propreco-procusto)*sum(itvqtde)),sum(ventotal) from caixa inner join venda on vencxcod=cxcod inner join itemvenda on vencod=itvvencod inner join produto on itvprocod=procod where cxdthabre>='"+txtdt1.getText()+"' and cxdthabre<='"+txtdt2.getText()+"'");        
            while(conecta.rs.next()){
            lucro=lucro+conecta.rs.getDouble("((propreco-procusto)*sum(itvqtde))");            
            t=t+conecta.rs.getDouble("sum(ventotal)");            
            }
//            JOptionPane.showMessageDialog(null, lucro+' '+t);
            txtValorTotalP.setText("R$ "+String.valueOf(t));            
            txtLucroTotal.setText("R$ "+String.valueOf(lucro));
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Erro:/n"+ex);            
        }                
    }
    }//GEN-LAST:event_txtdt2KeyPressed

    private void txtLucroTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLucroTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLucroTotalActionPerformed

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
            java.util.logging.Logger.getLogger(TelaPesquisarVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPesquisarVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPesquisarVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPesquisarVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
            public void run() {
                new TelaPesquisarVenda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAtualiza;
    private javax.swing.JButton btImprimir;
    private javax.swing.JButton btImprimir1;
    private javax.swing.JButton btprocurar;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable tabela;
    private javax.swing.JTable tabela1;
    private javax.swing.JTable tabelaItemVenda;
    private javax.swing.JTable tabelaQtdeProduto;
    private javax.swing.JTextField txtLucroTotal;
    private javax.swing.JFormattedTextField txtTotal;
    private javax.swing.JFormattedTextField txtTotal1;
    private javax.swing.JFormattedTextField txtTotal2;
    private javax.swing.JFormattedTextField txtTotal3;
    private javax.swing.JTextField txtValorTotalP;
    private javax.swing.JFormattedTextField txtdt1;
    private javax.swing.JFormattedTextField txtdt2;
    // End of variables declaration//GEN-END:variables
}

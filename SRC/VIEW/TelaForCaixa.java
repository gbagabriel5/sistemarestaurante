package view;

import java.awt.Color;
import java.awt.Toolkit;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import model.bean.Caixa;
import model.bean.ItemVenda;
import model.dao.CaixaDao;
import model.dao.ExecutaSql;
import model.dao.ModeloTabela;
import model.dao.VendaDao;

public class TelaForCaixa extends javax.swing.JFrame {

    ExecutaSql conecta=new ExecutaSql();
    String hinicial,hfinal,desc,dt;
    double saldoInicial,saldoFinal;
    Caixa cx = new Caixa();
    CaixaDao cxdao = new CaixaDao();
    int flag=1,codCaixa,codVenda;
    String usu;
    TelaInicial enviartexto;

    public TelaForCaixa() {
        initComponents();
        conecta.conexao();
        setTitle("Caixa"); 
        transparenciabutton();    
        setIcon();
        conecta.conexao();
        try{
            conecta.executaSQL("select *from caixa where cxstatus='ABERTO'");
            conecta.rs.last();
            codCaixa=conecta.rs.getInt("cxcod");
            labelData.setText(conecta.rs.getString("cxdthabre"));
            txtSaldoInicial.setText(String.valueOf(conecta.rs.getDouble("cxsinicial")));
            txtSaldoInicial1.setText(String.valueOf(conecta.rs.getDouble("cxsinicial")));
            saldoInicial=Double.parseDouble(txtSaldoInicial.getText());        
            txtObs.setText("Aberto "+conecta.rs.getString("cxdthabre"));
            JOptionPane.showMessageDialog(null, "Caixa aberto desde: "+conecta.rs.getString("cxdthabre"));
            ImageIcon img= new ImageIcon(getClass().getResource("/imagens/aberto.png"));
            labelStatus.setIcon(img);
            labelStatus.setText("ABERTO");
            Atualizar();
            preencherTabelaCaixa("select vencod,vendesc,ventotal,vensaida,pagamento from venda where venstatus=1 and vencxcod="+codCaixa);
            Timer timer = new Timer();
            final long SEGUNDOS = (1000*5);
            TimerTask tarefa = new TimerTask() {
            @Override
            public void run() {
                Atualizar();
            }
        };
            timer.scheduleAtFixedRate(tarefa,0, SEGUNDOS);
            txtSaldoInicial.setEnabled(false);
            txtObs.setEnabled(false);
            btAbrirCaixa.setEnabled(false);
            btFecharCaixa.setEnabled(true);
            btAdcionarEntradaSaida.setEnabled(true);
            btAtualiza.setEnabled(true);

            btExcluirEntradaSaida.setEnabled(true);
        }catch(Exception ex){
            

            btAbrirCaixa.setEnabled(true);
            btFecharCaixa.setEnabled(false);
            btAdcionarEntradaSaida.setEnabled(false);
            btAtualiza.setEnabled(false);
            btExcluirEntradaSaida.setEnabled(false);
        }
        
        
    }  
    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagens/caixa.png")));
    }
    public void transparenciabutton(){
        btAtualiza.setOpaque(false);
        btAtualiza.setContentAreaFilled(false);
        btAtualiza.setBorderPainted(false);        
                    
    }
    public void preencherTabelaCaixa(String QSL){
           
           ArrayList dados=new ArrayList();
           String[] colunas=new String[]{"Cod.","Descrição","Entradas","Saida","Observação"};
           
           conecta.executaSQL(QSL);
           try{
               conecta.rs.first();
              do{
                dados.add(new Object[]{conecta.rs.getInt("vencod"),conecta.rs.getString("vendesc"),conecta.rs.getDouble("ventotal"),conecta.rs.getDouble("vensaida"),conecta.rs.getString("pagamento")});;
                }while(conecta.rs.next());               
           }catch(Exception erro){
//               JOptionPane.showMessageDialog(null, "Produto não cadastrado!"+erro.getMessage());               
           }
           
        ModeloTabela modelo=new ModeloTabela(dados,colunas);
        tabelaCaixa.setModel(modelo);
        
        tabelaCaixa.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabelaCaixa.getColumnModel().getColumn(0).setResizable(false);
                
        tabelaCaixa.getColumnModel().getColumn(1).setPreferredWidth(250);
        tabelaCaixa.getColumnModel().getColumn(1).setResizable(false);
        
        tabelaCaixa.getColumnModel().getColumn(2).setPreferredWidth(60);
        tabelaCaixa.getColumnModel().getColumn(2).setResizable(false);        
        
        tabelaCaixa.getColumnModel().getColumn(3).setPreferredWidth(60);
        tabelaCaixa.getColumnModel().getColumn(3).setResizable(false);
        
        tabelaCaixa.getColumnModel().getColumn(4).setPreferredWidth(232);
        tabelaCaixa.getColumnModel().getColumn(4).setResizable(false);               
             
                       
        tabelaCaixa.getTableHeader().setReorderingAllowed(false);
        tabelaCaixa.setAutoResizeMode(tabelaCaixa.AUTO_RESIZE_OFF);
        
        tabelaCaixa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
    }
    public void LimparTabelaCaixa(){
           
        ArrayList dados=new ArrayList();
        String[] colunas=new String[]{"Cod.","Descrição","Entradas","Saida","Forma de Pagamento"};                                               
           
           
        ModeloTabela modelo=new ModeloTabela(dados,colunas);
        tabelaCaixa.setModel(modelo);
                
        tabelaCaixa.getTableHeader().setReorderingAllowed(false);
        tabelaCaixa.setAutoResizeMode(tabelaCaixa.AUTO_RESIZE_OFF);
        
        tabelaCaixa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
    }
    public void preencherTabela(String QSL){
           
           ArrayList dados=new ArrayList();
           String[] colunas=new String[]{"Data","Hora","Descrição","Entrada","F.Pagamento","Observação"};
           
           conecta.executaSQL(QSL);
           try{
               conecta.rs.first();
              do{
                dados.add(new Object[]{conecta.rs.getString("vendata"),conecta.rs.getString("hora"),"Pedido nº "+conecta.rs.getInt("vencod")+" Mesa: "+conecta.rs.getInt("venmcod"),"R$ "+conecta.rs.getDouble("ventotal"),conecta.rs.getString("pagamento"),conecta.rs.getString("cxobs")});;
                }while(conecta.rs.next());               
           }catch(Exception erro){
//               JOptionPane.showMessageDialog(null, "Produto não cadastrado!"+erro.getMessage());               
        }
           
        ModeloTabela modelo=new ModeloTabela(dados,colunas);
        tabela.setModel(modelo);           
        
        tabela.getColumnModel().getColumn(0).setPreferredWidth(75);
        tabela.getColumnModel().getColumn(0).setResizable(false);
        
        tabela.getColumnModel().getColumn(1).setPreferredWidth(70);
        tabela.getColumnModel().getColumn(1).setResizable(false);
        
        tabela.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(2).setResizable(false);                
        
        tabela.getColumnModel().getColumn(3).setPreferredWidth(70);
        tabela.getColumnModel().getColumn(3).setResizable(false);                
                
        tabela.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(4).setResizable(false);                
        
        tabela.getColumnModel().getColumn(5).setPreferredWidth(200);
        tabela.getColumnModel().getColumn(5).setResizable(false);                
        
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setAutoResizeMode(tabela.AUTO_RESIZE_OFF);
        
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator5 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        btAdcionarEntradaSaida = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        btAtualiza = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaCaixa = new javax.swing.JTable();
        btExcluirEntradaSaida = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btAbrirCaixa = new javax.swing.JButton();
        txtSaldoInicial = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObs = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        txtsatus = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        labelData = new javax.swing.JLabel();
        labelStatus = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtSaldoInicial1 = new javax.swing.JTextField();
        txtEntradaDinheiro = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtEntradaDebito = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtEntradaCredito = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTotalSaidas = new javax.swing.JTextField();
        txtSaldoTotalDinheiro = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtSaldoTotal = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btFecharCaixa = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Funcionários");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setEnabled(false);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabelaMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);

        btAdcionarEntradaSaida.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btAdcionarEntradaSaida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/adicionar.png"))); // NOI18N
        btAdcionarEntradaSaida.setText("Adcionar Entrada / Saída");
        btAdcionarEntradaSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdcionarEntradaSaidaActionPerformed(evt);
            }
        });

        jLabel11.setText("Movimentação de Caixa:");

        btAtualiza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/refresh.png"))); // NOI18N
        btAtualiza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAtualizaActionPerformed(evt);
            }
        });

        tabelaCaixa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tabelaCaixa);

        btExcluirEntradaSaida.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btExcluirEntradaSaida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/007-negative.png"))); // NOI18N
        btExcluirEntradaSaida.setText("Excluir Entrada / Saída");
        btExcluirEntradaSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirEntradaSaidaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btAtualiza, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btExcluirEntradaSaida)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btAdcionarEntradaSaida))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btAtualiza)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btAdcionarEntradaSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btExcluirEntradaSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(438, Short.MAX_VALUE)))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Saldo inicial (dinheiro):");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Observação:");

        btAbrirCaixa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btAbrirCaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/abrir.png"))); // NOI18N
        btAbrirCaixa.setText("Abrir Caixa");
        btAbrirCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAbrirCaixaActionPerformed(evt);
            }
        });

        txtSaldoInicial.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSaldoInicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSaldoInicialKeyPressed(evt);
            }
        });

        txtObs.setColumns(20);
        txtObs.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtObs.setRows(5);
        txtObs.setTabSize(0);
        txtObs.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtObs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtObsKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(txtObs);

        txtsatus.setText("Status do Caixa:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setText("Data/Hora de abertura:");

        labelStatus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/fechado.png"))); // NOI18N
        labelStatus.setText("FECHADO");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelData, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtsatus)
                                .addComponent(btAbrirCaixa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtSaldoInicial)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel18))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtsatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSaldoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelData, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btAbrirCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Saldo inicial (dinheiro):");

        txtSaldoInicial1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSaldoInicial1.setEnabled(false);

        txtEntradaDinheiro.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtEntradaDinheiro.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Dinheiro:");

        txtEntradaDebito.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtEntradaDebito.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Cartão - Débito:");

        txtEntradaCredito.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtEntradaCredito.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Cartão - Crédito:");

        txtTotal.setBackground(new java.awt.Color(240, 240, 240));
        txtTotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotal.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Total (Saídas):");

        txtTotalSaidas.setBackground(new java.awt.Color(255, 102, 102));
        txtTotalSaidas.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotalSaidas.setEnabled(false);

        txtSaldoTotalDinheiro.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSaldoTotalDinheiro.setEnabled(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Saldo (dinheiro):");

        txtSaldoTotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSaldoTotal.setEnabled(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Saldo Final:");

        btFecharCaixa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btFecharCaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/fechar.png"))); // NOI18N
        btFecharCaixa.setText("Fechar Caixa");
        btFecharCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFecharCaixaActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 51, 204));
        jLabel14.setText("(+)Todas as Entradas");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 51));
        jLabel15.setText("(+)Todas as Saídas");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("(=) Saldo (Inicial + Entradas - Saídas");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Total (No Caixa):");

        jLabel1.setText("Resumo:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator2)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtEntradaDinheiro, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtEntradaDebito, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtEntradaCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSaldoInicial1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtTotalSaidas, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                                .addComponent(jLabel12)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtSaldoTotalDinheiro, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtSaldoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(1, 1, 1)))
                                .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGap(5, 5, 5)
                                    .addComponent(jLabel16)))
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(btFecharCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator6))
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSaldoInicial1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtEntradaDinheiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtEntradaDebito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtEntradaCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtTotalSaidas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtSaldoTotalDinheiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtSaldoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(btFecharCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btAbrirCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAbrirCaixaActionPerformed
        saldoInicial=Double.parseDouble(txtSaldoInicial.getText());        
        String hora;
        hora = cxdao.MostraHora();
        
        String obs=txtObs.getText();          
        hinicial=cxdao.PegarData();
        dt=cxdao.PegarData1();
        conecta.conexao();
        ImageIcon img= new ImageIcon(getClass().getResource("/imagens/aberto.png"));
        labelStatus.setIcon(img);
        labelStatus.setText("ABERTO");        
        try {            
            
            PreparedStatement pstm= conecta.conn.prepareStatement("insert into caixa (cxdata,cxdthabre,cxsinicial,cxobs,cxstatus)values(?,?,?,?,?)");
            pstm.setString(1, dt);
            pstm.setString(2, hinicial);
            pstm.setDouble(3, saldoInicial);
            pstm.setString(4, obs);
            pstm.setString(5, labelStatus.getText());
            pstm.execute();
            
            conecta.executaSQL("select *from caixa");
            conecta.rs.last();
            codCaixa=conecta.rs.getInt("cxcod");                                     
            
            txtSaldoInicial.setText(String.valueOf("R$ "+saldoInicial));
            txtSaldoInicial1.setText(String.valueOf("R$ "+saldoInicial));
            
            Timer timer = new Timer();
            final long SEGUNDOS = (1000*5);
            TimerTask tarefa = new TimerTask() {
            @Override
            public void run() {
                Atualizar();
            }
        };
            timer.scheduleAtFixedRate(tarefa,0, SEGUNDOS);
        
            btFecharCaixa.setEnabled(true);
            btAdcionarEntradaSaida.setEnabled(true);
            btExcluirEntradaSaida.setEnabled(true);
            btAtualiza.setEnabled(true);
            tabela.setEnabled(true);
            labelData.setText(hinicial);
            preencherTabela("select vencod,cxdthabre,vencod,ventotal,pagamento,cxobs from caixa inner join venda on vencxcod=cxcod inner join itemvenda on itvvencod=vencod inner join produto on itvprocod=procod where vencxcod="+codCaixa+" group by vencod");            

            txtSaldoInicial.setEnabled(false);
            txtObs.setEnabled(false);
            btAbrirCaixa.setEnabled(false);
                        
            String t= txtObs.getText();
            if(t==null ? "" == null : t.equals("")){            
                txtObs.setText("Aberto ás "+hora);
                
                
            }
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro:"+ex);
        }                
                
    }//GEN-LAST:event_btAbrirCaixaActionPerformed
          
    private void btAdcionarEntradaSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdcionarEntradaSaidaActionPerformed
        TelaForEntradaCaixa tela = new TelaForEntradaCaixa();
        tela.setVisible(true);
        tela.setAlwaysOnTop(true);
         Timer timer = new Timer();
            final long SEGUNDOS = (1000*10);
            TimerTask tarefa = new TimerTask() {
            @Override
            public void run() {
                preencherTabelaCaixa("select vencod,vendesc,ventotal,vensaida,pagamento from venda where venstatus=1 and vencxcod="+codCaixa);       
            }
        };
            timer.scheduleAtFixedRate(tarefa,0, SEGUNDOS);            
        
    }//GEN-LAST:event_btAdcionarEntradaSaidaActionPerformed
    public void LimparTabelaPesquisa(){
           
        ArrayList dados=new ArrayList();
        String[] colunas=new String[]{"Data","Hora","Descrição","Entrada","Saída","F.Pagamento","Observação"};                                               
           
           
        ModeloTabela modelo=new ModeloTabela(dados,colunas);
        tabela.setModel(modelo);
                
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setAutoResizeMode(tabela.AUTO_RESIZE_OFF);
        
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
    }
    private void btFecharCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFecharCaixaActionPerformed
        int opcao=JOptionPane.showConfirmDialog(null, "Você deseja fechar o Caixa?");
        switch(opcao){
            case 0:
                String obs=txtObs.getText();          
                hfinal=cxdao.PegarData();
                saldoFinal=Double.parseDouble(txtSaldoTotal.getText());        
                ImageIcon img= new ImageIcon(getClass().getResource("/imagens/fechado.png"));
                labelStatus.setIcon(img);
                labelStatus.setText("FECHADO");
        
                cx.setCxcod(codCaixa);
                cx.setCxdthfecha(hfinal);            
                cx.setCxsfinal(saldoFinal);
                cx.setCxstatus(labelStatus.getText());
                cx.setCxobs(txtObs.getText());
                cxdao.FecharCaixa(cx);                                          
                imprimirRelatorio();
                txtSaldoInicial.setEnabled(true);
                txtObs.setEnabled(true);
                btAbrirCaixa.setEnabled(true);
        
        
                txtSaldoInicial.setText("");
                txtObs.setText("");
                txtSaldoInicial1.setText("");        
                txtEntradaDinheiro.setText("");
                txtEntradaDebito.setText("");
                txtEntradaCredito.setText("");        
                txtTotalSaidas.setText("");
                txtSaldoTotalDinheiro.setText("");
                txtSaldoTotal.setText("");  
                txtTotal.setText("");
                labelData.setText("");
                LimparTabelaPesquisa();
                LimparTabelaCaixa();
                tabela.setEnabled(false);
                codCaixa=0;
                saldoInicial=0;
                saldoFinal=0;
                btFecharCaixa.setEnabled(false);
                btAdcionarEntradaSaida.setEnabled(false);
                btExcluirEntradaSaida.setEnabled(false);
                btAtualiza.setEnabled(false);
            break;
        }
    }//GEN-LAST:event_btFecharCaixaActionPerformed
    public void imprimirRelatorio(){
        int op=JOptionPane.showConfirmDialog(null, "Você deseja imprimir o relatorio?");
        switch(op){
            case 0:               
                final TelaForAguarde carregando= new TelaForAguarde();
                carregando.setVisible(true);
                Thread t= new Thread(){
                    public void run(){
                        //metodo de imprimir
                        try {
                            VendaDao vendao= new VendaDao();
                            vendao.gerarRelatorioCaixa(codCaixa);
                            carregando.dispose();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Erro ao gerar relatorio/nErro:"+e);
                        }
                    }
                };
                t.start();
            break;
        }        
    }
    public void Atualizar(){
        preencherTabela("select vencod,vendata,hora,vencod,venmcod,ventotal,pagamento,cxobs from caixa inner join venda on vencxcod=cxcod inner join itemvenda on itvvencod=vencod inner join produto on itvprocod=procod where vencxcod="+codCaixa+" group by vencod");
        
        double total=0;
        try { 
            conecta.executaSQL("select sum(ventotal)from venda where pagamento='dinheiro' and vencxcod="+codCaixa);
            while(conecta.rs.next()){
                total=total+conecta.rs.getDouble("sum(ventotal)");            
            }
            txtEntradaDinheiro.setText(String.valueOf("R$ "+total));
        } catch (SQLException ex) {
            Logger.getLogger(TelaPesquisarVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        double total1=0;
        try { 
            conecta.executaSQL("select sum(ventotal)from venda where pagamento='debito' and vencxcod="+codCaixa);
            while(conecta.rs.next()){
                total1=total1+conecta.rs.getDouble("sum(ventotal)");            
            }
            txtEntradaDebito.setText(String.valueOf("R$ "+total1));
        } catch (SQLException ex) {
            Logger.getLogger(TelaPesquisarVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        double total2=0;
        try { 
            conecta.executaSQL("select sum(ventotal)from venda where pagamento='credito' and vencxcod="+codCaixa);
            while(conecta.rs.next()){
                total2=total2+conecta.rs.getDouble("sum(ventotal)");            
            } 
            txtEntradaCredito.setText(String.valueOf("R$ "+total2));
        } catch (SQLException ex) {
            Logger.getLogger(TelaPesquisarVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        double total3=0;
        try { 
            conecta.executaSQL("select sum(ventotal)from venda where vencxcod="+codCaixa);
            while(conecta.rs.next()){
                total3=total3+saldoInicial+conecta.rs.getDouble("sum(ventotal)");            
            } 
            txtTotal.setText(String.valueOf("R$ "+total3));
        } catch (SQLException ex) {
            Logger.getLogger(TelaPesquisarVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        double saidas=0;
        try { 
            conecta.executaSQL("select sum(vensaida) from venda where venstatus=1 and vencxcod="+codCaixa);
            while(conecta.rs.next()){
                saidas=conecta.rs.getDouble("sum(vensaida)");                        
                txtTotalSaidas.setText(String.valueOf(saidas));
                txtTotalSaidas.setBackground(Color.red);
            } 
        } catch (SQLException ex) {
            Logger.getLogger(TelaPesquisarVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        double total5=0;
        try { 
            conecta.executaSQL("select sum(vensaida), sum(ventotal)from venda where vencxcod="+codCaixa);
            while(conecta.rs.next()){
                double saidas1=conecta.rs.getDouble("sum(vensaida)");
                total5=total5+saldoInicial+conecta.rs.getDouble("sum(ventotal)")-saidas1;
            }
            txtSaldoTotal.setText(String.valueOf(total5));            
        } catch (SQLException ex) {
            Logger.getLogger(TelaPesquisarVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
            double total6=0;
        try { 
            conecta.executaSQL("select sum(ventotal) from venda where pagamento='entrada - acrescimo' and vencxcod="+codCaixa);
            while(conecta.rs.next()){            
                total6=conecta.rs.getDouble("sum(ventotal)");                                            
            } 
        } catch (SQLException ex) {
            Logger.getLogger(TelaPesquisarVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        double total4=0;
        try { 
            conecta.executaSQL("select sum(ventotal) from venda where pagamento='dinheiro' and vencxcod="+codCaixa);
            while(conecta.rs.next()){
                double saidas2=Double.parseDouble(txtTotalSaidas.getText());
                double t=conecta.rs.getDouble("sum(ventotal)");
                total4=total4+saldoInicial+t+total6-saidas2;            
            } 
            txtSaldoTotalDinheiro.setText(String.valueOf(total4));            
        } catch (SQLException ex) {
            Logger.getLogger(TelaPesquisarVenda.class.getName()).log(Level.SEVERE, null, ex);
        }            
    }
    
    private void btAtualizaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAtualizaActionPerformed
        Atualizar();   
        preencherTabelaCaixa("select vencod,vendesc,ventotal,vensaida,pagamento from venda where venstatus=1 and vencxcod="+codCaixa);
    }//GEN-LAST:event_btAtualizaActionPerformed
    public void atualizarCaixa(){
        preencherTabelaCaixa("select vencod,vendesc,ventotal,vensaida,pagamento from venda where venstatus=1 and vencxcod="+codCaixa);
    }
    private void txtSaldoInicialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSaldoInicialKeyPressed
    if(evt.getKeyCode()==evt.VK_ENTER){
        saldoInicial=Double.parseDouble(txtSaldoInicial.getText());        
        String hora;
        hora = cxdao.MostraHora();
        
        String obs=txtObs.getText();          
        hinicial=cxdao.PegarData();
        dt=cxdao.PegarData1();
        conecta.conexao();
        ImageIcon img= new ImageIcon(getClass().getResource("/imagens/aberto.png"));
        labelStatus.setIcon(img);
        labelStatus.setText("ABERTO");        
        try {            
            
            PreparedStatement pstm= conecta.conn.prepareStatement("insert into caixa (cxdata,cxdthabre,cxsinicial,cxobs,cxstatus)values(?,?,?,?,?)");
            pstm.setString(1, dt);
            pstm.setString(2, hinicial);
            pstm.setDouble(3, saldoInicial);
            pstm.setString(4, obs);
            pstm.setString(5, labelStatus.getText());
            pstm.execute();
            
            conecta.executaSQL("select *from caixa");
            conecta.rs.last();
            codCaixa=conecta.rs.getInt("cxcod");                                     
            
            txtSaldoInicial.setText(String.valueOf("R$ "+saldoInicial));
            txtSaldoInicial1.setText(String.valueOf("R$ "+saldoInicial));
            
            Timer timer = new Timer();
            final long SEGUNDOS = (1000*5);
            TimerTask tarefa = new TimerTask() {
                public void run() {
                    Atualizar();
                }
            };
            timer.scheduleAtFixedRate(tarefa,0, SEGUNDOS);
        
            btFecharCaixa.setEnabled(true);
            btAdcionarEntradaSaida.setEnabled(true);
            btExcluirEntradaSaida.setEnabled(true);
            btAtualiza.setEnabled(true);
            tabela.setEnabled(true);
            labelData.setText(hinicial);
            preencherTabela("select vencod,cxdthabre,vencod,ventotal,pagamento,cxobs from caixa inner join venda on vencxcod=cxcod inner join itemvenda on itvvencod=vencod inner join produto on itvprocod=procod where vencxcod="+codCaixa+" group by vencod");            

            txtSaldoInicial.setEnabled(false);
            txtObs.setEnabled(false);
            btAbrirCaixa.setEnabled(false);
                        
            String t= txtObs.getText();
            if(t==null ? "" == null : t.equals("")){            
                txtObs.setText("Aberto ás "+hora);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro:"+ex);
        }
    }  
    }//GEN-LAST:event_txtSaldoInicialKeyPressed

    private void txtObsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObsKeyPressed
     
    }//GEN-LAST:event_txtObsKeyPressed

    private void btExcluirEntradaSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirEntradaSaidaActionPerformed
    int opcao=JOptionPane.showConfirmDialog(null, "Você deseja excluir?");
    switch(opcao){
        case 0:               
            int cod=(int)tabelaCaixa.getValueAt(tabelaCaixa.getSelectedRow(),0);            
            ItemVenda itv=new ItemVenda();                      
            itv.setItvcod(Integer.valueOf(cod));            
            cxdao.RemoveItem(cod);                                                         
        break;
    }
    }//GEN-LAST:event_btExcluirEntradaSaidaActionPerformed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
//        codVenda=(int)tabela.getValueAt(tabela.getSelectedRow(),0);            
    }//GEN-LAST:event_tabelaMouseClicked

    private void tabelaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaMouseEntered
        
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
            java.util.logging.Logger.getLogger(TelaForCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaForCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaForCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaForCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new TelaForCaixa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAbrirCaixa;
    private javax.swing.JButton btAdcionarEntradaSaida;
    private javax.swing.JButton btAtualiza;
    private javax.swing.JButton btExcluirEntradaSaida;
    private javax.swing.JButton btFecharCaixa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel labelData;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JTable tabela;
    private javax.swing.JTable tabelaCaixa;
    private javax.swing.JTextField txtEntradaCredito;
    private javax.swing.JTextField txtEntradaDebito;
    private javax.swing.JTextField txtEntradaDinheiro;
    private javax.swing.JTextArea txtObs;
    private javax.swing.JTextField txtSaldoInicial;
    private javax.swing.JTextField txtSaldoInicial1;
    private javax.swing.JTextField txtSaldoTotal;
    private javax.swing.JTextField txtSaldoTotalDinheiro;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalSaidas;
    private javax.swing.JLabel txtsatus;
    // End of variables declaration//GEN-END:variables
}

package view;

import java.awt.Toolkit;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.bean.Caixa;
import model.bean.Venda;
import model.dao.ExecutaSql;


public class TelaForEntradaCaixa extends javax.swing.JFrame {
    ExecutaSql conecta=new ExecutaSql();
    double saida,total,entrada;
    String desc,pagamento;
    int codCaixa;

    public TelaForEntradaCaixa() {
        initComponents();        
        transparenciabutton();
        setIcon();
    }
   private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagens/venda.png")));
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Saida = new javax.swing.JRadioButton();
        SaidaDespesa = new javax.swing.JRadioButton();
        Entrada = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtObs = new javax.swing.JTextField();
        btSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Adcionar/Entrada Saída no Caixa");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setEnabled(false);

        Saida.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(Saida);
        Saida.setText("Saída - Sangria");
        Saida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SaidaMouseClicked(evt);
            }
        });

        SaidaDespesa.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(SaidaDespesa);
        SaidaDespesa.setText("Saída - Pagamento de despesas");
        SaidaDespesa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SaidaDespesaMouseClicked(evt);
            }
        });

        Entrada.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(Entrada);
        Entrada.setText("Entrada - acréscimo");
        Entrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EntradaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Saida)
            .addComponent(SaidaDespesa)
            .addComponent(Entrada)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(Saida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SaidaDespesa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Entrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Tipo de operação:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Valor:");

        txtValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorActionPerformed(evt);
            }
        });
        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValorKeyPressed(evt);
            }
        });

        jLabel3.setText("Observação:");

        txtObs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtObsKeyPressed(evt);
            }
        });

        btSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/abrir.png"))); // NOI18N
        btSalvar.setText("Salvar");
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtObs))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btSalvar)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtObs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btSalvar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void txtValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorActionPerformed

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        
        Venda ven= new Venda();
        Caixa cx = new Caixa();
        
        total=Double.parseDouble(txtValor.getText());
        desc= txtObs.getText();        
        
        conecta.conexao();     
        
        try {
            conecta.executaSQL("select *from caixa");
            conecta.rs.last();
            codCaixa=conecta.rs.getInt("cxcod");
            
            PreparedStatement pstm= conecta.conn.prepareStatement("insert into venda (vencxcod,pagamento,ventotal,vensaida,vendesc,venstatus)values(?,?,?,?,?,?)");            
            if(pagamento=="Entrada - Acrescimo"){
            pstm.setInt(1,codCaixa);
            pstm.setString(2, pagamento);
            pstm.setDouble(3,total);    
            pstm.setDouble(4,0);
            pstm.setString(5, desc);            
            pstm.setInt(6,1);
            pstm.execute();
            conecta.desconecta();
            
            }else{
            pstm.setInt(1,codCaixa);
            pstm.setString(2, pagamento);
            pstm.setDouble(3,0);
            pstm.setDouble(4,total);
            pstm.setString(5, desc);            
            pstm.setInt(6,1);
            pstm.execute();
            conecta.desconecta();
            }                     
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao Salvar \n Erro: "+ ex);
        }
    }//GEN-LAST:event_btSalvarActionPerformed

    private void SaidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SaidaMouseClicked
        pagamento="Saida - Sangria";
        txtValor.requestFocus();
    }//GEN-LAST:event_SaidaMouseClicked

    private void SaidaDespesaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SaidaDespesaMouseClicked
        pagamento="Saida - Pagamento de Despesas";
        txtValor.requestFocus();
    }//GEN-LAST:event_SaidaDespesaMouseClicked

    private void EntradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EntradaMouseClicked
        pagamento="Entrada - Acrescimo";
        txtValor.requestFocus();
    }//GEN-LAST:event_EntradaMouseClicked

    private void txtValorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyPressed
        if(evt.getKeyCode()==evt.VK_ENTER){       
            if(txtValor.getText().equals("")){
                txtValor.requestFocus();
                JOptionPane.showMessageDialog(null, "Preencha o campo VALOR!","Aviso ",JOptionPane.WARNING_MESSAGE);
                return;               
            }else{        
                txtObs.requestFocus();
            }
        }
    }//GEN-LAST:event_txtValorKeyPressed

    private void txtObsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObsKeyPressed
        if(evt.getKeyCode()==evt.VK_ENTER){       
            if(txtObs.getText().equals("")){
                txtObs.requestFocus();
                JOptionPane.showMessageDialog(null, "Preencha o campo OBS!","Aviso ",JOptionPane.WARNING_MESSAGE);
                return;               
            }
        
            Venda ven= new Venda();
            Caixa cx = new Caixa();
            
            total=Double.parseDouble(txtValor.getText());
            desc= txtObs.getText();        
        
            conecta.conexao();     
        
            try {
                conecta.executaSQL("select *from caixa");
                conecta.rs.last();
                codCaixa=conecta.rs.getInt("cxcod");
            
                PreparedStatement pstm= conecta.conn.prepareStatement("insert into venda (vencxcod,pagamento,ventotal,vensaida,vendesc,venstatus)values(?,?,?,?,?,?)");            
                if(pagamento=="Entrada - Acrescimo"){
                    pstm.setInt(1,codCaixa);
                    pstm.setString(2, pagamento);
                    pstm.setDouble(3,total);    
                    pstm.setDouble(4,0);
                    pstm.setString(5, desc);            
                    pstm.setInt(6,1);
                    pstm.execute();
                    conecta.desconecta();
            
                }else{
                    pstm.setInt(1,codCaixa);
                    pstm.setString(2, pagamento);
                    pstm.setDouble(3,0);
                    pstm.setDouble(4,total);
                    pstm.setString(5, desc);            
                    pstm.setInt(6,1);
                    pstm.execute();
                    conecta.desconecta();
                }                     
                dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Erro ao Salvar \n Erro: "+ ex);
            }
        }
    }//GEN-LAST:event_txtObsKeyPressed
    public void transparenciabutton(){
        btSalvar.setOpaque(false);
        btSalvar.setContentAreaFilled(true);
        btSalvar.setBorderPainted(true);        
    }
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
            java.util.logging.Logger.getLogger(TelaForEntradaCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaForEntradaCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaForEntradaCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaForEntradaCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaForEntradaCaixa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Entrada;
    private javax.swing.JRadioButton Saida;
    private javax.swing.JRadioButton SaidaDespesa;
    private javax.swing.JButton btSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtObs;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Relatorios;

import View.Consultas.BuscaClientes;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Tadeu
 */
public class Rel_Empr extends javax.swing.JFrame {

    static Connection con = null;
    static Statement st = null;
    static ResultSet rs = null;
    static PreparedStatement ps = null;
    String caminho = "C:/ProjetoFinal/src/Util/config.TXT";
    String diretorio = null;
    String ip = null;
    static String codigoCliente, nomeCliente;

    public Rel_Empr() {
        initComponents();
    }

    public void insereCodigo(String codigo, String nome) {
        codigoCliente = codigo;
        nomeCliente = nome;
    }

    public void leArquivo() throws IOException {
        File file = new File(caminho);
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        BufferedReader br = new BufferedReader(fr);

        String linha = br.readLine();
        ip = linha;
        String linha2 = br.readLine();
        diretorio = linha2;
    }

    public void conecta() throws IOException, SQLException {
        try {
            leArquivo();
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            con = DriverManager.getConnection(
                    "jdbc:firebirdsql://" + ip + ":3050/" + diretorio,
                    "SYSDBA",
                    "masterkey");
            st = con.createStatement();
        } catch (ClassNotFoundException ex)//caso o driver não seja localizado  
        {
            JOptionPane.showMessageDialog(null, "Driver não encontrado!");
        } catch (SQLException ex)//caso a conexão não possa se realizada  
        {
            JOptionPane.showMessageDialog(null, "Problemas na conexao com a fonte de dados");
        }
    }

    public static Connection getConexao() {
        Connection conexao = null;
        conexao = con;
        return conexao;
    }

    public void gerarRelatorioCliente() {
        try {
            HashMap filtro = new HashMap();
            filtro.put("dataInicio", txt_dataInicio.getDate());
            filtro.put("dataFinal", txt_dataFinal.getDate());
            
            JasperPrint print = JasperFillManager.fillReport("Relatorios/Cliente.jasper", filtro);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_ = new javax.swing.JPanel();
        pnl_dadosRel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_dataInicio = new com.toedter.calendar.JDateChooser();
        txt_dataFinal = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_codcliente = new javax.swing.JTextField();
        txt_nomeCliente = new javax.swing.JTextField();
        pnl_rodape = new javax.swing.JPanel();
        btn_gerar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Relatorio de Emprestimo");
        setResizable(false);

        pnl_dadosRel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Data Inicial:");

        jLabel2.setText("Data Final:");

        jLabel3.setText("Cliente:");

        txt_codcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_codclienteActionPerformed(evt);
            }
        });
        txt_codcliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_codclienteKeyPressed(evt);
            }
        });

        txt_nomeCliente.setEditable(false);
        txt_nomeCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_nomeClienteFocusGained(evt);
            }
        });

        javax.swing.GroupLayout pnl_dadosRelLayout = new javax.swing.GroupLayout(pnl_dadosRel);
        pnl_dadosRel.setLayout(pnl_dadosRelLayout);
        pnl_dadosRelLayout.setHorizontalGroup(
            pnl_dadosRelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosRelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosRelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_dadosRelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosRelLayout.createSequentialGroup()
                        .addComponent(txt_dataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_dataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(161, Short.MAX_VALUE))
                    .addGroup(pnl_dadosRelLayout.createSequentialGroup()
                        .addComponent(txt_codcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_nomeCliente))))
        );
        pnl_dadosRelLayout.setVerticalGroup(
            pnl_dadosRelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosRelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosRelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_dataFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_dataInicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_dadosRelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_nomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_codcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11))
        );

        pnl_rodape.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_gerar.setText("Gerar");
        btn_gerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_gerarActionPerformed(evt);
            }
        });

        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_rodapeLayout = new javax.swing.GroupLayout(pnl_rodape);
        pnl_rodape.setLayout(pnl_rodapeLayout);
        pnl_rodapeLayout.setHorizontalGroup(
            pnl_rodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_rodapeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_gerar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_rodapeLayout.setVerticalGroup(
            pnl_rodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_rodapeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_rodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_gerar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout txt_Layout = new javax.swing.GroupLayout(txt_);
        txt_.setLayout(txt_Layout);
        txt_Layout.setHorizontalGroup(
            txt_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txt_Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(txt_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_dadosRel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_rodape, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        txt_Layout.setVerticalGroup(
            txt_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txt_Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_dadosRel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnl_rodape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txt_, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txt_, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        setVisible(false);

    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_gerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_gerarActionPerformed

    }//GEN-LAST:event_btn_gerarActionPerformed

    private void txt_codclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_codclienteActionPerformed

    }//GEN-LAST:event_txt_codclienteActionPerformed

    private void txt_codclienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codclienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BuscaClientes b = new BuscaClientes();
            b.setVisible(true);
        }
    }//GEN-LAST:event_txt_codclienteKeyPressed

    private void txt_nomeClienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_nomeClienteFocusGained
        txt_codcliente.setText(codigoCliente);
        txt_nomeCliente.setText(nomeCliente);
    }//GEN-LAST:event_txt_nomeClienteFocusGained

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
            java.util.logging.Logger.getLogger(Rel_Empr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Rel_Empr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Rel_Empr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Rel_Empr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Rel_Empr().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_gerar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel pnl_dadosRel;
    private javax.swing.JPanel pnl_rodape;
    private javax.swing.JPanel txt_;
    private javax.swing.JTextField txt_codcliente;
    private com.toedter.calendar.JDateChooser txt_dataFinal;
    private com.toedter.calendar.JDateChooser txt_dataInicio;
    private javax.swing.JTextField txt_nomeCliente;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Home;

import View.Cadastros.Frm_Cliente;
import View.Cadastros.Frm_Locacao;
import View.Cadastros.Frm_Produto;
import View.Cadastros.Frm_Usuario;
import View.Relatorios.Rel_Empr;
import View.Relatorios.Rel_Fluxo_trans;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Tadeu
 */
public class Frm_Principal extends javax.swing.JFrame {


    public Frm_Principal() {

        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        txt_usuario.requestFocus();
    }

    public void antesdeLogar() {
        mi_Cadastro.setEnabled(false);
        mi_movimentacao.setEnabled(false);
        mi_relatorios.setEnabled(false);
        mi_ajuda.setEnabled(false);
        atalho_clientes.setEnabled(false);
        atalho_locacao.setEnabled(false);
        atalho_rel_empr.setEnabled(false);

    }

    public void entrar() {
        pnl_login.setVisible(false);
        txt_userLogado.setText("Administrador");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fundo_atalhos = new javax.swing.JPanel();
        atalho_clientes = new javax.swing.JPanel();
        btn_atalho_clientes = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        atalho_locacao = new javax.swing.JPanel();
        btn_atalho_locacao = new javax.swing.JLabel();
        atalho_rel_empr = new javax.swing.JPanel();
        btn_atalho_rel_emp = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        pnl_fundo = new javax.swing.JPanel();
        pnl_login = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_usuario = new javax.swing.JTextField();
        btn_entrar = new javax.swing.JButton();
        btn_sair = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txt_senha = new javax.swing.JPasswordField();
        btn_conexao = new javax.swing.JLabel();
        pnl_barraStatus = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txt_userLogado = new javax.swing.JTextField();
        mb_menus = new javax.swing.JMenuBar();
        mi_Cadastro = new javax.swing.JMenu();
        mi_produto = new javax.swing.JMenuItem();
        mi_cliente = new javax.swing.JMenuItem();
        mi_usuario = new javax.swing.JMenuItem();
        mi_movimentacao = new javax.swing.JMenu();
        mi_locacao = new javax.swing.JMenuItem();
        mi_relatorios = new javax.swing.JMenu();
        mi_relClieCad = new javax.swing.JMenuItem();
        mi_relEmpr = new javax.swing.JMenuItem();
        mi_ajuda = new javax.swing.JMenu();
        mi_help = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Plus 1.0");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        fundo_atalhos.setBackground(new java.awt.Color(204, 204, 204));
        fundo_atalhos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        atalho_clientes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        atalho_clientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                atalho_clientesMouseClicked(evt);
            }
        });

        btn_atalho_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/client.jpg"))); // NOI18N
        btn_atalho_clientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_atalho_clientesMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout atalho_clientesLayout = new javax.swing.GroupLayout(atalho_clientes);
        atalho_clientes.setLayout(atalho_clientesLayout);
        atalho_clientesLayout.setHorizontalGroup(
            atalho_clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_atalho_clientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        atalho_clientesLayout.setVerticalGroup(
            atalho_clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_atalho_clientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel4.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("CLIENTES");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("LOCAÇÃO");

        atalho_locacao.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        atalho_locacao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                atalho_locacaoMouseClicked(evt);
            }
        });

        btn_atalho_locacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/locacao.jpg"))); // NOI18N

        javax.swing.GroupLayout atalho_locacaoLayout = new javax.swing.GroupLayout(atalho_locacao);
        atalho_locacao.setLayout(atalho_locacaoLayout);
        atalho_locacaoLayout.setHorizontalGroup(
            atalho_locacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_atalho_locacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        atalho_locacaoLayout.setVerticalGroup(
            atalho_locacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_atalho_locacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        atalho_rel_empr.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        atalho_rel_empr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                atalho_rel_emprMouseClicked(evt);
            }
        });

        btn_atalho_rel_emp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/RelEmprestimo.jpg"))); // NOI18N

        javax.swing.GroupLayout atalho_rel_emprLayout = new javax.swing.GroupLayout(atalho_rel_empr);
        atalho_rel_empr.setLayout(atalho_rel_emprLayout);
        atalho_rel_emprLayout.setHorizontalGroup(
            atalho_rel_emprLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 96, Short.MAX_VALUE)
            .addGroup(atalho_rel_emprLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btn_atalho_rel_emp, javax.swing.GroupLayout.PREFERRED_SIZE, 92, Short.MAX_VALUE))
        );
        atalho_rel_emprLayout.setVerticalGroup(
            atalho_rel_emprLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(atalho_rel_emprLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btn_atalho_rel_emp, javax.swing.GroupLayout.PREFERRED_SIZE, 66, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("REL.EMPRÉSTIMO");

        javax.swing.GroupLayout fundo_atalhosLayout = new javax.swing.GroupLayout(fundo_atalhos);
        fundo_atalhos.setLayout(fundo_atalhosLayout);
        fundo_atalhosLayout.setHorizontalGroup(
            fundo_atalhosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fundo_atalhosLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(fundo_atalhosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(atalho_clientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fundo_atalhosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(atalho_locacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fundo_atalhosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(atalho_rel_empr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(726, Short.MAX_VALUE))
        );
        fundo_atalhosLayout.setVerticalGroup(
            fundo_atalhosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fundo_atalhosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fundo_atalhosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(fundo_atalhosLayout.createSequentialGroup()
                        .addComponent(atalho_rel_empr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(fundo_atalhosLayout.createSequentialGroup()
                        .addGroup(fundo_atalhosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(atalho_locacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(atalho_clientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(fundo_atalhosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))))
                .addGap(2, 2, 2))
        );

        pnl_login.setBackground(new java.awt.Color(204, 204, 204));
        pnl_login.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnl_login.setToolTipText("Login");

        jLabel1.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jLabel1.setText("Usuário:");

        jLabel2.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jLabel2.setText("Senha:");

        btn_entrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/login.jpg"))); // NOI18N
        btn_entrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_entrarActionPerformed(evt);
            }
        });

        btn_sair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/logout.jpg"))); // NOI18N
        btn_sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sairActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel3.setText("LOGIN");

        btn_conexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/conexao.png"))); // NOI18N
        btn_conexao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_conexaoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnl_loginLayout = new javax.swing.GroupLayout(pnl_login);
        pnl_login.setLayout(pnl_loginLayout);
        pnl_loginLayout.setHorizontalGroup(
            pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_loginLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_loginLayout.createSequentialGroup()
                        .addComponent(btn_sair, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_entrar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(pnl_loginLayout.createSequentialGroup()
                        .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_senha, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5))
                    .addGroup(pnl_loginLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(50, 50, 50)
                        .addComponent(btn_conexao)
                        .addGap(18, 18, 18))))
        );
        pnl_loginLayout.setVerticalGroup(
            pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_loginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(btn_conexao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 20, Short.MAX_VALUE)
                .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(txt_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(txt_senha))
                .addGap(42, 42, 42)
                .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_entrar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_sair, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        pnl_barraStatus.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel7.setText("Usuário:");

        txt_userLogado.setEditable(false);
        txt_userLogado.setBackground(new java.awt.Color(204, 204, 204));
        txt_userLogado.setForeground(new java.awt.Color(51, 51, 51));
        txt_userLogado.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout pnl_barraStatusLayout = new javax.swing.GroupLayout(pnl_barraStatus);
        pnl_barraStatus.setLayout(pnl_barraStatusLayout);
        pnl_barraStatusLayout.setHorizontalGroup(
            pnl_barraStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_barraStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_userLogado, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_barraStatusLayout.setVerticalGroup(
            pnl_barraStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_barraStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addComponent(txt_userLogado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout pnl_fundoLayout = new javax.swing.GroupLayout(pnl_fundo);
        pnl_fundo.setLayout(pnl_fundoLayout);
        pnl_fundoLayout.setHorizontalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_fundoLayout.createSequentialGroup()
                .addContainerGap(785, Short.MAX_VALUE)
                .addComponent(pnl_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(pnl_barraStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnl_fundoLayout.setVerticalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_fundoLayout.createSequentialGroup()
                .addContainerGap(221, Short.MAX_VALUE)
                .addComponent(pnl_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnl_barraStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        mb_menus.setBackground(new java.awt.Color(153, 153, 153));

        mi_Cadastro.setForeground(new java.awt.Color(51, 51, 51));
        mi_Cadastro.setText("Cadastro");
        mi_Cadastro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        mi_produto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK));
        mi_produto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/produto.png"))); // NOI18N
        mi_produto.setText("Produto");
        mi_produto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_produtoActionPerformed(evt);
            }
        });
        mi_Cadastro.add(mi_produto);

        mi_cliente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        mi_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/cliente.jpg"))); // NOI18N
        mi_cliente.setText("Cliente");
        mi_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_clienteActionPerformed(evt);
            }
        });
        mi_Cadastro.add(mi_cliente);

        mi_usuario.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_MASK));
        mi_usuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/user.jpg"))); // NOI18N
        mi_usuario.setText("Usuario");
        mi_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_usuarioActionPerformed(evt);
            }
        });
        mi_Cadastro.add(mi_usuario);

        mb_menus.add(mi_Cadastro);

        mi_movimentacao.setForeground(new java.awt.Color(51, 51, 51));
        mi_movimentacao.setText("Movimentações");
        mi_movimentacao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        mi_locacao.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_MASK));
        mi_locacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/locacao2.jpg"))); // NOI18N
        mi_locacao.setText("Locação");
        mi_locacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_locacaoActionPerformed(evt);
            }
        });
        mi_movimentacao.add(mi_locacao);

        mb_menus.add(mi_movimentacao);

        mi_relatorios.setForeground(new java.awt.Color(51, 51, 51));
        mi_relatorios.setText("Relatórios");
        mi_relatorios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        mi_relClieCad.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mi_relClieCad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/cliente.jpg"))); // NOI18N
        mi_relClieCad.setText("Relatório de Clientes Cadastrados");
        mi_relClieCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_relClieCadActionPerformed(evt);
            }
        });
        mi_relatorios.add(mi_relClieCad);

        mi_relEmpr.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mi_relEmpr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/RelEmprest.jpg"))); // NOI18N
        mi_relEmpr.setText("Relatório de Empréstimos");
        mi_relEmpr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_relEmprActionPerformed(evt);
            }
        });
        mi_relatorios.add(mi_relEmpr);

        mb_menus.add(mi_relatorios);

        mi_ajuda.setForeground(new java.awt.Color(51, 51, 51));
        mi_ajuda.setText("Ajuda");
        mi_ajuda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        mi_help.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        mi_help.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/lupa.png"))); // NOI18N
        mi_help.setText("Help");
        mi_help.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_helpActionPerformed(evt);
            }
        });
        mi_ajuda.add(mi_help);

        mb_menus.add(mi_ajuda);

        setJMenuBar(mb_menus);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fundo_atalhos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_fundo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(fundo_atalhos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnl_fundo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mi_produtoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_produtoActionPerformed
        Frm_Produto p = new Frm_Produto();
        p.setVisible(true);
    }//GEN-LAST:event_mi_produtoActionPerformed

    private void mi_relEmprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_relEmprActionPerformed
        Rel_Empr r1 = new Rel_Empr();
        r1.setVisible(true);
    }//GEN-LAST:event_mi_relEmprActionPerformed

    private void btn_sairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sairActionPerformed
        try {
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        setVisible(false);
    }//GEN-LAST:event_btn_sairActionPerformed

    private void atalho_clientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_atalho_clientesMouseClicked
        Frm_Cliente l = new Frm_Cliente();
        l.setVisible(true);
    }//GEN-LAST:event_atalho_clientesMouseClicked

    private void atalho_locacaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_atalho_locacaoMouseClicked
        Frm_Locacao l = new Frm_Locacao();
        l.setVisible(true);
    }//GEN-LAST:event_atalho_locacaoMouseClicked

    private void atalho_rel_emprMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_atalho_rel_emprMouseClicked
        Rel_Empr r = new Rel_Empr();
        r.setVisible(true);
    }//GEN-LAST:event_atalho_rel_emprMouseClicked

    private void mi_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_clienteActionPerformed
        Frm_Cliente c = new Frm_Cliente();
        c.setVisible(true);
    }//GEN-LAST:event_mi_clienteActionPerformed

    private void mi_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_usuarioActionPerformed
        Frm_Usuario u = new Frm_Usuario();
        u.setVisible(true);
    }//GEN-LAST:event_mi_usuarioActionPerformed

    private void mi_locacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_locacaoActionPerformed
        Frm_Locacao l = new Frm_Locacao();
        l.setVisible(true);
    }//GEN-LAST:event_mi_locacaoActionPerformed

    private void mi_relClieCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_relClieCadActionPerformed
        System.out.println("gerar relatorio de clientes cadastrados");
    }//GEN-LAST:event_mi_relClieCadActionPerformed

    private void mi_helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_helpActionPerformed
        System.out.println("menu ajuda");
    }//GEN-LAST:event_mi_helpActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        try {
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_formWindowClosed

    private void btn_entrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_entrarActionPerformed
        entrar();
    }//GEN-LAST:event_btn_entrarActionPerformed

    private void btn_conexaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_conexaoMouseClicked
    }//GEN-LAST:event_btn_conexaoMouseClicked

    private void btn_atalho_clientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_atalho_clientesMouseClicked
        Frm_Cliente c= new Frm_Cliente();
        c.setVisible(true);
    }//GEN-LAST:event_btn_atalho_clientesMouseClicked

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
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Frm_Principal principal = new Frm_Principal();
                principal.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel atalho_clientes;
    private javax.swing.JPanel atalho_locacao;
    private javax.swing.JPanel atalho_rel_empr;
    private javax.swing.JLabel btn_atalho_clientes;
    private javax.swing.JLabel btn_atalho_locacao;
    private javax.swing.JLabel btn_atalho_rel_emp;
    private javax.swing.JLabel btn_conexao;
    private javax.swing.JButton btn_entrar;
    private javax.swing.JButton btn_sair;
    private javax.swing.JPanel fundo_atalhos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenuBar mb_menus;
    private javax.swing.JMenu mi_Cadastro;
    private javax.swing.JMenu mi_ajuda;
    private javax.swing.JMenuItem mi_cliente;
    private javax.swing.JMenuItem mi_help;
    private javax.swing.JMenuItem mi_locacao;
    private javax.swing.JMenu mi_movimentacao;
    private javax.swing.JMenuItem mi_produto;
    private javax.swing.JMenuItem mi_relClieCad;
    private javax.swing.JMenuItem mi_relEmpr;
    private javax.swing.JMenu mi_relatorios;
    private javax.swing.JMenuItem mi_usuario;
    private javax.swing.JPanel pnl_barraStatus;
    private javax.swing.JPanel pnl_fundo;
    private javax.swing.JPanel pnl_login;
    private javax.swing.JPasswordField txt_senha;
    private javax.swing.JTextField txt_userLogado;
    private javax.swing.JTextField txt_usuario;
    // End of variables declaration//GEN-END:variables
}

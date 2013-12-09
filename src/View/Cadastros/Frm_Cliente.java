/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Cadastros;

import View.Home.Frm_Conexao;
import java.awt.TextField;
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
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Suporte4
 */
public class Frm_Cliente extends javax.swing.JFrame {

    static Connection con = null;
    static Statement st = null;
    static ResultSet rs = null;
    static PreparedStatement ps = null;
    String caminho = "C:/ProjetoFinal/src/Util/config.TXT";
    String diretorio = null;
    String ip = null;

    public Frm_Cliente() {
        initComponents();
        start();
    }

    public void start() {
        try {
            conecta();
            camposOff();
            listaClientes();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
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
            Frm_Conexao f = new Frm_Conexao();
            f.setVisible(true);
            setVisible(false);
        }
    }

    public void camposOn() {
        pnl_abas.setEnabled(true);
        txt_razao.setEnabled(true);
        txt_nomeFantasia.setEnabled(true);
        txt_cpf.setEnabled(true);
        txt_email.setEnabled(true);
        txt_telefone.setEnabled(true);
        txt_contato.setEnabled(true);
        cbx_tipo.setEnabled(true);
        btn_salvar.setEnabled(true);
        btn_cancelar.setEnabled(true);

        txt_razao.setText(null);
        txt_nomeFantasia.setText(null);
        txt_cpf.setText(null);
        txt_email.setText(null);
        txt_telefone.setText(null);
        txt_contato.setText(null);
        txt_cep_ent.setText(null);
        txt_cep_cob.setText(null);
        txt_cidade_cob.setText(null);
        txt_cidade_ent.setText(null);
        txt_bairro_cob.setText(null);
        txt_bairro_ent.setText(null);
        txt_rua_ent.setText(null);
        txt_rua_cob.setText(null);
        txt_numero_cob.setText(null);
        txt_numero_ent.setText(null);
        txt_complemento_cob.setText(null);
        txt_complemento_ent.setText(null);

        txt_razao.requestFocus();
    }

    public void camposOff() {
        pnl_abas.setEnabled(false);

        txt_razao.setEnabled(false);
        txt_nomeFantasia.setEnabled(false);
        txt_cpf.setEnabled(false);
        txt_email.setEnabled(false);
        txt_telefone.setEnabled(false);
        txt_contato.setEnabled(false);
        cbx_tipo.setEnabled(false);
        btn_salvar.setEnabled(false);
        btn_cancelar.setEnabled(false);

        txt_razao.setText(null);
        txt_nomeFantasia.setText(null);
        txt_cpf.setText(null);
        txt_email.setText(null);
        txt_telefone.setText(null);
        txt_contato.setText(null);
        txt_cep_ent.setText(null);
        txt_cep_cob.setText(null);
        txt_cidade_cob.setText(null);
        txt_cidade_ent.setText(null);
        txt_bairro_cob.setText(null);
        txt_bairro_ent.setText(null);
        txt_rua_ent.setText(null);
        txt_rua_cob.setText(null);
        txt_numero_cob.setText(null);
        txt_numero_ent.setText(null);
        txt_complemento_cob.setText(null);
        txt_complemento_ent.setText(null);
    }

    public void limpaTabela() {
        try {
            DefaultTableModel tblRemove = (DefaultTableModel) tb_cliente.getModel();
            while (tblRemove.getRowCount() > 0) {
                for (int i = 1; i <= tblRemove.getRowCount(); i++) {
                    tblRemove.removeRow(0);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void listaClientes() {
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from Cliente");
            DefaultTableModel model = (DefaultTableModel) tb_cliente.getModel();
            while (rs.next()) {
                String[] linha = new String[]{rs.getString("CODCLIENTE"), rs.getString("NOME"), rs.getString("CPF"), rs.getString("TELEFONE")};
                model.addRow(linha);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void listaClientesByCodigo(int codigo) {
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from Cliente where codcliente=" + codigo);
            if (rs.next()) {
                txt_razao.setText(rs.getString("Nome"));
                txt_nomeFantasia.setText(rs.getString("Nomefantasia"));
                cbx_tipo.setSelectedItem(rs.getString("Tipo"));
                txt_cpf.setText(rs.getString("cpf"));
                txt_email.setText(rs.getString("email"));
                txt_telefone.setText(rs.getString("telefone"));
                txt_contato.setText(rs.getString("contato"));
                txt_cep_ent.setText(rs.getString("cep_ent"));
                txt_cidade_ent.setText(rs.getString("cidade_ent"));
                txt_bairro_ent.setText(rs.getString("bairro_ent"));
                txt_rua_ent.setText(rs.getString("rua_ent"));
                txt_numero_ent.setText(rs.getString("numero_ent"));
                txt_complemento_ent.setText(rs.getString("complemento_ent"));
                txt_cep_cob.setText(rs.getString("cep_cob"));
                txt_cidade_cob.setText(rs.getString("cidade_cob"));
                txt_bairro_cob.setText(rs.getString("bairro_cob"));
                txt_rua_cob.setText(rs.getString("rua_cob"));
                txt_numero_cob.setText(rs.getString("numero_cob"));
                txt_complemento_cob.setText(rs.getString("complemento_cob"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void salvar(String nome, String nomeFantasia, String tipo, String cpf, String email, String telefone, String contato,
            String cepEnt, String cidEnt, String baiEnt, String ruaEnt, String numEnt, String compEnt,
            String cepCob, String cidCob, String baiCob, String ruaCob, String numCob, String compCob) {
        try {
            st = con.createStatement();
            int codigo = 0;
            ResultSet rs = st.executeQuery("select max(codcliente) qtde from CLIENTE");
            while (rs.next()) {
                if (rs.getString("qtde") == null) {
                    codigo = 1;
                } else {
                    codigo = Integer.parseInt(rs.getString("qtde")) + 1;
                }
            }
            PreparedStatement ps = con.prepareStatement("INSERT INTO CLIENTE (CODCLIENTE,NOME,NOMEFANTASIA,TIPO,CPF,EMAIL,TELEFONE,CONTATO,"
                    + "CEP_ENT,CIDADE_ENT,BAIRRO_ENT,RUA_ENT,NUMERO_ENT,COMPLEMENTO_ENT, "
                    + "CEP_COB,CIDADE_COB,BAIRRO_COB,RUA_COB,NUMERO_COB,COMPLEMENTO_COB) VALUES (" + codigo + ",'" + nome + "','" + nomeFantasia + "','"
                    + tipo + "','" + cpf + "','" + email + "','" + telefone + "','" + contato + "','" + cepEnt + "','" + cidEnt + "','" + baiEnt + "','" + ruaEnt + "','"
                    + numEnt + "','" + compEnt + "','" + cepCob + "','" + cidCob + "','" + baiCob + "','" + ruaCob + "','" + numCob + "','" + compCob + "')");
            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "Cadastro Efetuado com Sucesso");
            camposOn();
            limpaTabela();
            listaClientes();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Dados Invalidos");
        }
    }

    public void alterar(int codigo, String nome, String nomeFantasia, String tipo, String cpf, String email, String telefone, String contato,
            String cepEnt, String cidEnt, String baiEnt, String ruaEnt, String numEnt, String compEnt,
            String cepCob, String cidCob, String baiCob, String ruaCob, String numCob, String compCob) {
        try {
            st = con.createStatement();
            st.executeUpdate("UPDATE CLIENTE SET NOME='" + nome + "',NOMEFANTASIA='" + nomeFantasia + "',tipo='" + tipo
                    + "',cpf='" + cpf + "',email='" + email + "',telefone='" + telefone + "',contato='" + contato + "',CEP_ENT='" + cepEnt + "',CIDADE_ENT='" + cidEnt
                    + "',BAIRRO_ENT='" + baiEnt + "',RUA_ENT='" + ruaEnt + "',NUMERO_ENT='" + numEnt + "',COMPLEMENTO_ENT='" + compEnt
                    + "',CEP_COB='" + cepCob + "',CIDADE_COB='" + cidCob + "',BAIRRO_COB='" + baiCob + "',RUA_COB='" + ruaCob + "',NUMERO_COB='" + numCob
                    + "',COMPLEMENTO_COB='" + compCob + "' WHERE CODCLIENTE=" + codigo + ";");
            st.close();
            JOptionPane.showMessageDialog(null, "Cliente Alterado com Sucesso");
            camposOn();
            limpaTabela();
            listaClientes();
            camposOff();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void deleta(int codigo) {
        try {
            DefaultTableModel model = (DefaultTableModel) tb_cliente.getModel();
            st = con.createStatement();
            PreparedStatement ps = con.prepareStatement("DELETE FROM cliente WHERE codcliente="
                    + tb_cliente.getValueAt(tb_cliente.getSelectedRow(), 0) + ";");
            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "Cliente removido com Sucesso");
            model.removeRow(tb_cliente.getSelectedRow());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void consultar() {
        TableRowSorter sorter = new TableRowSorter(tb_cliente.getModel());
        tb_cliente.setRowSorter(sorter);
        String texto = txt_consulta.getText();
        try {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Valor Não Encontrado!!!", "AVISO - Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void detalhar(int codigo) {
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from Cliente where codcliente=" + codigo);
            if (rs.next()) {

                JOptionPane.showMessageDialog(null, "Código: " + codigo
                        + "\nNOME/RAZÃO: " + rs.getString("nome")
                        + "\nNOME FANTASIA: " + rs.getString("nomefantasia")
                        + "\nTIPO: " + rs.getString("tipo")
                        + "\nCPF/CNPJ: " + rs.getString("cpf")
                        + "\nEMAIL: " + rs.getString("email")
                        + "\nTELEFONE: " + rs.getString("telefone")
                        + "\nCONTATO: " + rs.getString("contato")
                        + "\nCEP DE ENTREGA: " + rs.getString("cep_Ent")
                        + "\nCIDADE DE ENTREGA: " + rs.getString("cidade_Ent")
                        + "\nBAIRRO DE ENTREGA: " + rs.getString("bairro_Ent")
                        + "\nRUA DE ENTREGA: " + rs.getString("rua_Ent")
                        + "\nNÚMERO DE ENTREGA: " + rs.getString("numero_Ent")
                        + "\nCOMPLEMENTO DE ENTREGA: " + rs.getString("complemento_Ent")
                        + "\nCEP DE COBRANÇA: " + rs.getString("cep_Cob")
                        + "\nCIDADE DE COBRANÇA: " + rs.getString("cidade_Cob")
                        + "\nBAIRRO DE COBRANÇA: " + rs.getString("bairro_Cob")
                        + "\nRUA DE COBRANÇA: " + rs.getString("rua_Cob")
                        + "\nNÚMERO DE COBRANÇA: " + rs.getString("numero_Cob")
                        + "\nCOMPLEMENTO DE COBRANÇA: " + rs.getString("complemento_Cob"));

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void validaCEPent(String cep) {
        try {
            cep = cep.replaceAll("-", "");
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from cep where cep='" + cep + "'");
            DefaultTableModel model = (DefaultTableModel) tb_cliente.getModel();
            if (rs.next()) {
                txt_cidade_ent.setText(rs.getString("cidade"));
                txt_bairro_ent.setText(rs.getString("bairro"));
                txt_rua_ent.setText(rs.getString("rua"));
                txt_numero_ent.requestFocus();
            } else {
                if ((txt_cep_ent.getText().compareTo("") != 0) && (txt_cidade_ent.getText().compareTo("") != 0) && (txt_bairro_ent.getText().compareTo("") != 0) && (txt_rua_ent.getText().compareTo("") != 0)) {
                    if (JOptionPane.showConfirmDialog(null, "Deseja Cadastrar o Cep: " + cep + " ?", "", 0, JOptionPane.YES_NO_OPTION) == 0) {
                        cadastraCep(txt_cep_ent.getText(), txt_cidade_ent.getText(),
                                txt_bairro_ent.getText(), txt_rua_ent.getText());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Preencha os campos em branco");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void cadastraCep(String cep, String cidade, String bairro, String rua) {
        try {
            st = con.createStatement();
            cep = cep.replaceAll("-", "");
            PreparedStatement ps = con.prepareStatement("INSERT INTO CEP (CEP,CIDADE,BAIRRO,RUA) VALUES ('" + cep + "','" + cidade
                    + "','" + bairro + "','" + rua + "');");
            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "Cep Cadastrado Com sucesso!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro" + ex);
        }
    }

    public void validaCEPcob(String cep) {
        try {
            cep = cep.replaceAll("-", "");
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from cep where cep='" + cep + "'");
            DefaultTableModel model = (DefaultTableModel) tb_cliente.getModel();
            if (rs.next()) {
                txt_cidade_cob.setText(rs.getString("cidade"));
                txt_bairro_cob.setText(rs.getString("bairro"));
                txt_rua_cob.setText(rs.getString("rua"));
                txt_numero_ent.requestFocus();
            } else {
                JOptionPane.showConfirmDialog(null, "Deseja Cadastrar o Cep: " + cep + " ?", "", 1, JOptionPane.YES_NO_OPTION);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void enter(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (JOptionPane.showConfirmDialog(null, "Deseja copiar os dados do Endereço de Entrega para o Endereço de Cobrança?", "", 0, JOptionPane.YES_NO_OPTION) == 0) {
                txt_bairro_cob.setText(txt_bairro_ent.getText());
                txt_cep_cob.setText(txt_cep_ent.getText());
                txt_cidade_cob.setText(txt_cidade_ent.getText());
                txt_rua_cob.setText(txt_rua_ent.getText());
                txt_numero_cob.setText(txt_numero_ent.getText());
                txt_complemento_cob.setText(txt_complemento_ent.getText());
            }
        }
    }

    public void alteraMaskara(String tipo) {

    }

    public void validaEmail(String email) {
        try {
            Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
            Matcher m = p.matcher(email);
            boolean matchFound = m.matches();
            if (matchFound) {
                JOptionPane.showMessageDialog(null, "Email Valido");
            } else {
                JOptionPane.showMessageDialog(null, "Email Invalido");
                txt_email.requestFocus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_fundo = new javax.swing.JPanel();
        pnl_tabela = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_cliente = new javax.swing.JTable();
        pnl_dados = new javax.swing.JPanel();
        pnl_botoes1 = new javax.swing.JPanel();
        btn_salvar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        pnl_abas = new javax.swing.JTabbedPane();
        pnl_dadosPessoais = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_razao = new javax.swing.JTextField();
        txt_nomeFantasia = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_contato = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_telefone = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        cbx_tipo = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        txt_cpf = new javax.swing.JFormattedTextField();
        pnl_EnderecoEntrega = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txt_cep_ent = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_cidade_ent = new javax.swing.JTextField();
        txt_rua_ent = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_bairro_ent = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_numero_ent = new javax.swing.JTextField();
        txt_complemento_ent = new javax.swing.JTextField();
        btn_cadCep_ent = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        pnl_EnderecoCobranca = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txt_cep_cob = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        txt_cidade_cob = new javax.swing.JTextField();
        txt_rua_cob = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txt_bairro_cob = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txt_numero_cob = new javax.swing.JTextField();
        txt_complemento_cob = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        btn_cadCep_cob = new javax.swing.JButton();
        pnl_botoes = new javax.swing.JPanel();
        btn_novo = new javax.swing.JButton();
        btn_editar = new javax.swing.JButton();
        btn_apagar = new javax.swing.JButton();
        btn_detalhar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_consulta = new javax.swing.JTextField();
        btn_novo1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lista de Clientes");

        pnl_tabela.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tb_cliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "NOME", "CPF/CNPJ", "TELEFONE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tb_cliente);

        javax.swing.GroupLayout pnl_tabelaLayout = new javax.swing.GroupLayout(pnl_tabela);
        pnl_tabela.setLayout(pnl_tabelaLayout);
        pnl_tabelaLayout.setHorizontalGroup(
            pnl_tabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        pnl_tabelaLayout.setVerticalGroup(
            pnl_tabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
        );

        pnl_botoes1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_salvar.setText("Salvar");
        btn_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvarActionPerformed(evt);
            }
        });

        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_botoes1Layout = new javax.swing.GroupLayout(pnl_botoes1);
        pnl_botoes1.setLayout(pnl_botoes1Layout);
        pnl_botoes1Layout.setHorizontalGroup(
            pnl_botoes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_botoes1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_botoes1Layout.setVerticalGroup(
            pnl_botoes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_botoes1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(pnl_botoes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancelar)
                    .addComponent(btn_salvar))
                .addGap(2, 2, 2))
        );

        pnl_dadosPessoais.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("Nome/Razão:");

        jLabel3.setText("Nome Fantasia:");

        jLabel4.setText("CPF/CNPJ:");

        jLabel5.setText("Contato:");

        jLabel6.setText("Telefone:");

        try {
            txt_telefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_telefone.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_telefone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_telefoneFocusGained(evt);
            }
        });

        jLabel7.setText("Tipo:");

        cbx_tipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fisica", "Juridica" }));

        jLabel8.setText("Email:");

        txt_email.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_emailFocusLost(evt);
            }
        });

        try {
            txt_cpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_cpf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_cpfFocusGained(evt);
            }
        });

        javax.swing.GroupLayout pnl_dadosPessoaisLayout = new javax.swing.GroupLayout(pnl_dadosPessoais);
        pnl_dadosPessoais.setLayout(pnl_dadosPessoaisLayout);
        pnl_dadosPessoaisLayout.setHorizontalGroup(
            pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_nomeFantasia)
                    .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                        .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                                .addComponent(cbx_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4))
                            .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                                .addComponent(txt_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_cpf)
                            .addComponent(txt_contato, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_email, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE))
                    .addComponent(txt_razao))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        pnl_dadosPessoaisLayout.setVerticalGroup(
            pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_razao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_nomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(cbx_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_dadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_contato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txt_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pnl_abas.addTab("Dados Pessoais", pnl_dadosPessoais);

        pnl_EnderecoEntrega.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel9.setText("CEP:");

        try {
            txt_cep_ent.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel10.setText("Cidade:");

        jLabel11.setText("Rua:");

        jLabel12.setText("Bairro:");

        jLabel13.setText("Número:");

        txt_complemento_ent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_complemento_entKeyPressed(evt);
            }
        });

        btn_cadCep_ent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cadCep_entActionPerformed(evt);
            }
        });

        jLabel21.setText("Complemento:");

        javax.swing.GroupLayout pnl_EnderecoEntregaLayout = new javax.swing.GroupLayout(pnl_EnderecoEntrega);
        pnl_EnderecoEntrega.setLayout(pnl_EnderecoEntregaLayout);
        pnl_EnderecoEntregaLayout.setHorizontalGroup(
            pnl_EnderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_EnderecoEntregaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_EnderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12)
                    .addComponent(jLabel9))
                .addGroup(pnl_EnderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnl_EnderecoEntregaLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnl_EnderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnl_EnderecoEntregaLayout.createSequentialGroup()
                                .addComponent(txt_cep_ent, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_cadCep_ent, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10))
                            .addGroup(pnl_EnderecoEntregaLayout.createSequentialGroup()
                                .addComponent(txt_bairro_ent, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11))))
                    .addGroup(pnl_EnderecoEntregaLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txt_numero_ent, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel21)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_EnderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_complemento_ent, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                    .addComponent(txt_rua_ent)
                    .addComponent(txt_cidade_ent))
                .addContainerGap())
        );
        pnl_EnderecoEntregaLayout.setVerticalGroup(
            pnl_EnderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_EnderecoEntregaLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(pnl_EnderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnl_EnderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txt_cep_ent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(txt_cidade_ent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_cadCep_ent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_EnderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txt_rua_ent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txt_bairro_ent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_EnderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnl_EnderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(txt_complemento_ent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_numero_ent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );

        pnl_abas.addTab("Endereço Entrega", pnl_EnderecoEntrega);

        pnl_EnderecoCobranca.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel15.setText("CEP:");

        try {
            txt_cep_cob.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel16.setText("Cidade:");

        jLabel17.setText("Rua:");

        jLabel18.setText("Bairro:");

        jLabel19.setText("Número:");

        jLabel20.setText("Complemento:");

        btn_cadCep_cob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cadCep_cobActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_EnderecoCobrancaLayout = new javax.swing.GroupLayout(pnl_EnderecoCobranca);
        pnl_EnderecoCobranca.setLayout(pnl_EnderecoCobrancaLayout);
        pnl_EnderecoCobrancaLayout.setHorizontalGroup(
            pnl_EnderecoCobrancaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_EnderecoCobrancaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_EnderecoCobrancaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_EnderecoCobrancaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnl_EnderecoCobrancaLayout.createSequentialGroup()
                        .addComponent(txt_cep_cob, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_cadCep_cob, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16))
                    .addGroup(pnl_EnderecoCobrancaLayout.createSequentialGroup()
                        .addComponent(txt_bairro_cob, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17))
                    .addGroup(pnl_EnderecoCobrancaLayout.createSequentialGroup()
                        .addComponent(txt_numero_cob, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel20)))
                .addGap(10, 10, 10)
                .addGroup(pnl_EnderecoCobrancaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_cidade_cob)
                    .addComponent(txt_rua_cob, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
                    .addComponent(txt_complemento_cob))
                .addContainerGap())
        );
        pnl_EnderecoCobrancaLayout.setVerticalGroup(
            pnl_EnderecoCobrancaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_EnderecoCobrancaLayout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(pnl_EnderecoCobrancaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnl_EnderecoCobrancaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(txt_cep_cob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)
                        .addComponent(txt_cidade_cob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_cadCep_cob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_EnderecoCobrancaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txt_rua_cob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(txt_bairro_cob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_EnderecoCobrancaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txt_numero_cob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(txt_complemento_cob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        pnl_abas.addTab("Endereço Cobrança", pnl_EnderecoCobranca);

        javax.swing.GroupLayout pnl_dadosLayout = new javax.swing.GroupLayout(pnl_dados);
        pnl_dados.setLayout(pnl_dadosLayout);
        pnl_dadosLayout.setHorizontalGroup(
            pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_botoes1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_abas))
                .addContainerGap())
        );
        pnl_dadosLayout.setVerticalGroup(
            pnl_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_abas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnl_botoes1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl_botoes.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_novo.setText("Novo");
        btn_novo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_novoActionPerformed(evt);
            }
        });

        btn_editar.setText("Editar");
        btn_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editarActionPerformed(evt);
            }
        });

        btn_apagar.setText("Apagar");
        btn_apagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_apagarActionPerformed(evt);
            }
        });

        btn_detalhar.setText("Detalhar");
        btn_detalhar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_detalharActionPerformed(evt);
            }
        });

        jLabel1.setText("Filtro:");

        txt_consulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_consultaActionPerformed(evt);
            }
        });
        txt_consulta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_consultaKeyReleased(evt);
            }
        });

        btn_novo1.setText("Sair");
        btn_novo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_novo1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_botoesLayout = new javax.swing.GroupLayout(pnl_botoes);
        pnl_botoes.setLayout(pnl_botoesLayout);
        pnl_botoesLayout.setHorizontalGroup(
            pnl_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_botoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_novo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_apagar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_detalhar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_consulta)
                .addGap(18, 18, 18)
                .addComponent(btn_novo1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_botoesLayout.setVerticalGroup(
            pnl_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_botoesLayout.createSequentialGroup()
                .addGroup(pnl_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_novo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_apagar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_detalhar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txt_consulta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_novo1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2))
        );

        javax.swing.GroupLayout pnl_fundoLayout = new javax.swing.GroupLayout(pnl_fundo);
        pnl_fundo.setLayout(pnl_fundoLayout);
        pnl_fundoLayout.setHorizontalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnl_botoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_tabela, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_dados, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(11, 11, 11))
        );
        pnl_fundoLayout.setVerticalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_dados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(13, 13, 13)
                .addComponent(pnl_tabela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_botoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_fundo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_fundo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_novoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_novoActionPerformed
        this.setTitle("Cadastro de Clientes");
        camposOn();
    }//GEN-LAST:event_btn_novoActionPerformed

    private void btn_detalharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_detalharActionPerformed
        if (tb_cliente.getSelectedRowCount() == 1) {
            detalhar(Integer.parseInt(tb_cliente.getValueAt(tb_cliente.getSelectedRow(), 0).toString()));
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma Linha na Tabela");
        }
    }//GEN-LAST:event_btn_detalharActionPerformed

    private void btn_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editarActionPerformed
        if (tb_cliente.getSelectedRowCount() == 1) {
            this.setTitle("Alteração de Clientes");
            camposOn();
            listaClientesByCodigo(Integer.parseInt(tb_cliente.getValueAt(tb_cliente.getSelectedRow(), 0).toString()));
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma Linha na Tabela");
        }
    }//GEN-LAST:event_btn_editarActionPerformed

    private void btn_apagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_apagarActionPerformed
        if (tb_cliente.getSelectedRowCount() == 1) {
            if (JOptionPane.showConfirmDialog(null, "Deseja Apagar o Cliente: "
                    + tb_cliente.getValueAt(tb_cliente.getSelectedRow(), 0), "", 0, JOptionPane.YES_NO_OPTION) == 0) {
                deleta(Integer.parseInt(tb_cliente.getValueAt(tb_cliente.getSelectedRow(), 0).toString()));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma Linha na Tabela");
        }
    }//GEN-LAST:event_btn_apagarActionPerformed

    private void txt_consultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_consultaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_consultaActionPerformed

    private void txt_consultaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_consultaKeyReleased
        consultar();
    }//GEN-LAST:event_txt_consultaKeyReleased

    private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarActionPerformed
        try {
            if (this.getTitle().compareTo("Cadastro de Clientes") == 0) {
                salvar(txt_razao.getText(), txt_nomeFantasia.getText(), cbx_tipo.getSelectedItem().toString(), txt_cpf.getText(), txt_email.getText(), txt_telefone.getText(), txt_contato.getText(),
                        txt_cep_ent.getText(), txt_cidade_ent.getText(), txt_bairro_ent.getText(), txt_rua_ent.getText(), txt_numero_ent.getText(), txt_complemento_ent.getText(),
                        txt_cep_cob.getText(), txt_cidade_cob.getText(), txt_bairro_cob.getText(), txt_rua_cob.getText(), txt_numero_cob.getText(), txt_complemento_cob.getText());
            }
            if (this.getTitle().compareTo("Alteração de Clientes") == 0) {
                alterar(Integer.parseInt(tb_cliente.getValueAt(tb_cliente.getSelectedRow(), 0).toString()), txt_razao.getText(), txt_nomeFantasia.getText(),
                        cbx_tipo.getSelectedItem().toString(), txt_cpf.getText(), txt_email.getText(), txt_telefone.getText(), txt_contato.getText(),
                        txt_cep_ent.getText(), txt_cidade_ent.getText(), txt_bairro_ent.getText(), txt_rua_ent.getText(), txt_numero_ent.getText(), txt_complemento_ent.getText(),
                        txt_cep_cob.getText(), txt_cidade_cob.getText(), txt_bairro_cob.getText(), txt_rua_cob.getText(), txt_numero_cob.getText(), txt_complemento_cob.getText());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Dados Invalidos!");
        }
    }//GEN-LAST:event_btn_salvarActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        camposOff();
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_novo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_novo1ActionPerformed
        setVisible(false);
    }//GEN-LAST:event_btn_novo1ActionPerformed

    private void btn_cadCep_entActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadCep_entActionPerformed
        validaCEPent(txt_cep_ent.getText());
    }//GEN-LAST:event_btn_cadCep_entActionPerformed

    private void btn_cadCep_cobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadCep_cobActionPerformed
        validaCEPcob(txt_cep_cob.getText());
    }//GEN-LAST:event_btn_cadCep_cobActionPerformed

    private void txt_complemento_entKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_complemento_entKeyPressed
        enter(evt);
    }//GEN-LAST:event_txt_complemento_entKeyPressed

    private void txt_cpfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_cpfFocusGained
        alteraMaskara(cbx_tipo.getSelectedItem().toString());
    }//GEN-LAST:event_txt_cpfFocusGained

    private void txt_telefoneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_telefoneFocusGained
        
    }//GEN-LAST:event_txt_telefoneFocusGained

    private void txt_emailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_emailFocusLost
       validaEmail(txt_email.getText());
    }//GEN-LAST:event_txt_emailFocusLost

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
            java.util.logging.Logger.getLogger(Frm_Cliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_Cliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_Cliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_Cliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm_Cliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_apagar;
    private javax.swing.JButton btn_cadCep_cob;
    private javax.swing.JButton btn_cadCep_ent;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_detalhar;
    private javax.swing.JButton btn_editar;
    private javax.swing.JButton btn_novo;
    private javax.swing.JButton btn_novo1;
    private javax.swing.JButton btn_salvar;
    private javax.swing.JComboBox cbx_tipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnl_EnderecoCobranca;
    private javax.swing.JPanel pnl_EnderecoEntrega;
    private javax.swing.JTabbedPane pnl_abas;
    private javax.swing.JPanel pnl_botoes;
    private javax.swing.JPanel pnl_botoes1;
    private javax.swing.JPanel pnl_dados;
    private javax.swing.JPanel pnl_dadosPessoais;
    private javax.swing.JPanel pnl_fundo;
    private javax.swing.JPanel pnl_tabela;
    private javax.swing.JTable tb_cliente;
    private javax.swing.JTextField txt_bairro_cob;
    private javax.swing.JTextField txt_bairro_ent;
    private javax.swing.JFormattedTextField txt_cep_cob;
    private javax.swing.JFormattedTextField txt_cep_ent;
    private javax.swing.JTextField txt_cidade_cob;
    private javax.swing.JTextField txt_cidade_ent;
    private javax.swing.JTextField txt_complemento_cob;
    private javax.swing.JTextField txt_complemento_ent;
    private javax.swing.JTextField txt_consulta;
    private javax.swing.JTextField txt_contato;
    private javax.swing.JFormattedTextField txt_cpf;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_nomeFantasia;
    private javax.swing.JTextField txt_numero_cob;
    private javax.swing.JTextField txt_numero_ent;
    private javax.swing.JTextField txt_razao;
    private javax.swing.JTextField txt_rua_cob;
    private javax.swing.JTextField txt_rua_ent;
    private javax.swing.JFormattedTextField txt_telefone;
    // End of variables declaration//GEN-END:variables
}

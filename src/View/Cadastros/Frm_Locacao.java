/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Cadastros;

import View.Consultas.BuscaLocacao;
import View.Consultas.BuscaClientes;
import View.Consultas.BuscaProdutos;
import static View.Cadastros.Frm_Produto.st;
import View.Home.Frm_Conexao;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.firebirdsql.jdbc.parser.JaybirdSqlParser;

/**
 *
 * @author Suporte4
 */
public class Frm_Locacao extends javax.swing.JFrame {

    static Connection con = null;
    static Statement st = null;
    static ResultSet rs = null;
    static PreparedStatement ps = null;
    String caminho = "C:/ProjetoFinal/src/Util/config.TXT";
    String diretorio = null;
    String ip = null;
    public static int numLocacao = 0;
    static String codcliente = null, cep = null, nome = null, cidade = null, bairro = null, rua = null, numero = null, complemento = null;
    static int codproduto = 0;
    static String descricao = null;

    public Frm_Locacao() {
        initComponents();
        try {
            conecta();
            camposOff();
        } catch (IOException ex) {
            Logger.getLogger(Frm_Locacao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Frm_Locacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscaCliente(String codigo) {
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from Cliente where codcliente='" + codigo + "';");
            while (rs.next()) {
                codcliente = rs.getString("codcliente");
                cep = rs.getString("cep_ent");
                nome = rs.getString("NOME");
                cidade = rs.getString("cidade_ent");
                bairro = rs.getString("bairro_ent");
                rua = rs.getString("rua_ent");
                numero = rs.getString("numero_ent");
                complemento = rs.getString("complemento_ent");
            }
        } catch (Exception e) {
        }
    }

    public void buscaProduto(String codigo) {
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from Produto where CODPROD=" + codigo);
            DefaultTableModel model = (DefaultTableModel) tb_produtos.getModel();
            if (rs.next()) {
                codproduto = Integer.parseInt(rs.getString("CODPROD"));
                descricao = rs.getString("descricao");
            } else {
                codproduto = 0;
                descricao = null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void insereCliente() {
        txt_cep.setText(cep);
        txt_nome.setText(nome);
        txt_codigoCliente.setText(codcliente);
        txt_cidade.setText(cidade);
        txt_bairro.setText(bairro);
        txt_logradouro.setText(rua);
        txt_numero.setText(numero);
        txt_complemento.setText(complemento);
    }

    public void buscaUltima() {

        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(CODLOCACAO) qtde from LOCACAO");
            while (rs.next()) {
                if (rs.getString("qtde") == null) {
                    numLocacao = 1;
                } else {
                    numLocacao = Integer.parseInt(rs.getString("qtde")) + 1;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        txt_loc_numero.setText(numLocacao + "");
        txt_codigoCliente.setEnabled(true);
        txt_observacao.setEnabled(true);
        txt_dataEntrega.setEnabled(true);
        txt_dataDevolucao.setEnabled(true);

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

    public void validaCEPent(String cep) {
        try {
            cep = cep.replaceAll("-", "");
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from cep where cep='" + cep + "'");
            if (rs.next()) {
                txt_cidade.setText(rs.getString("cidade"));
                txt_bairro.setText(rs.getString("bairro"));
                txt_logradouro.setText(rs.getString("rua"));
                txt_numero.requestFocus();
            } else {
                JOptionPane.showConfirmDialog(null, "Deseja Cadastrar o Cep: " + cep + " ?", "", 0, JOptionPane.YES_NO_OPTION);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void enter(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            validaCEPent(txt_cep.getText());
        }
    }

    public void enterLocacao(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            BuscaLocacao b = new BuscaLocacao();
            b.setVisible(true);
        }
    }

    public void insereItem(int codigo, String descricao, int qtde, float valor) {
        try {
            DefaultTableModel model = (DefaultTableModel) tb_produtos.getModel();
            String[] linha = new String[]{codigo + "", descricao, qtde + "", valor + ""};
            model.addRow(linha);
            JOptionPane.showMessageDialog(null, "Item inserido com Sucesso");
            somaTotal(Float.parseFloat(txt_loc_total.getText()));
            txt_descricaoProduto.setText(null);
            txt_codigoProduto.setText(null);
            txt_quantidadeProduto.setText(null);
            txt_valorProduto.setText(null);
            txt_codigoProduto.requestFocus();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir com Sucesso");
        }
    }

    public void enterProduto(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            BuscaProdutos b = new BuscaProdutos();
            b.setVisible(true);
        }
    }

    public void camposOff() {
        btn_carregaCliente.setEnabled(false);
        btn_carregaCep.setEnabled(false);
        btn_editaCep.setEnabled(false);
        btn_ok.setEnabled(false);
        btn_novo.setEnabled(false);
        btn_editar.setEnabled(false);
        btn_apagar.setEnabled(false);
        btn_carregarLocacao.setEnabled(false);
        txt_codigoCliente.setEnabled(false);
        txt_nome.setEnabled(false);
        txt_loc_numero.setEnabled(false);
        txt_dataDevolucao.setEnabled(false);
        txt_dataEntrega.setEnabled(false);
        txt_observacao.setEnabled(false);
        txt_cep.setEnabled(false);
        txt_cidade.setEnabled(false);
        txt_bairro.setEnabled(false);
        txt_logradouro.setEnabled(false);
        txt_numero.setEnabled(false);
        txt_complemento.setEnabled(false);
        txt_codigoProduto.setEnabled(false);
        txt_descricaoProduto.setEnabled(false);
        txt_valorProduto.setEnabled(false);
        txt_quantidadeProduto.setEnabled(false);

        txt_codigoCliente.setText(null);
        txt_nome.setText(null);
        txt_loc_numero.setText(null);
        txt_dataDevolucao.setCalendar(null);
        txt_dataEntrega.setCalendar(null);
        txt_observacao.setText(null);
        txt_cep.setText(null);
        txt_cidade.setText(null);
        txt_bairro.setText(null);
        txt_logradouro.setText(null);
        txt_numero.setText(null);
        txt_complemento.setText(null);
        txt_codigoProduto.setText(null);
        txt_descricaoProduto.setText(null);
        txt_valorProduto.setText(null);
        txt_quantidadeProduto.setText(null);
        txt_loc_total.setText("0.00");

    }

    public void camposOn() {
        txt_codigoProduto.setEnabled(true);
        txt_descricaoProduto.setEnabled(true);
        txt_valorProduto.setEnabled(true);
        txt_quantidadeProduto.setEnabled(true);

        txt_codigoProduto.setText(null);
        txt_descricaoProduto.setText(null);
        txt_valorProduto.setText(null);
        txt_quantidadeProduto.setText(null);
    }

    public void carregaProduto() {
        if (codproduto != 0) {
            txt_codigoProduto.setText(codproduto + "");
            txt_descricaoProduto.setText(descricao);
            txt_quantidadeProduto.requestFocus();
        } else {
            txt_descricaoProduto.setText(null);
            txt_codigoProduto.setText(null);
            txt_codigoProduto.requestFocus();

        }
    }

    public void limpaTabela() {
        try {
            DefaultTableModel tblRemove = (DefaultTableModel) tb_produtos.getModel();
            while (tblRemove.getRowCount() > 0) {
                for (int i = 1; i <= tblRemove.getRowCount(); i++) {
                    tblRemove.removeRow(0);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    public void listaItens() throws Exception {
        st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from produto p inner join item_venda i on p.codprod=i.codproduto where i.codlocacao=" + txt_loc_numero.getText());
        DefaultTableModel model = (DefaultTableModel) tb_produtos.getModel();
        while (rs.next()) {
            String[] linha = new String[]{rs.getString("codproduto"), rs.getString("DESCRICAO"), rs.getString("qtde"), rs.getString("valorunit")};
            model.addRow(linha);
        }
    }

    public void cadastraItens(String numlocacao) {
        try {
            DefaultTableModel model = (DefaultTableModel) tb_produtos.getModel();
            while (tb_produtos.getRowCount() > 0) {
                st = con.createStatement();
                PreparedStatement ps = con.prepareStatement("INSERT INTO Item_venda (CODLOCACAO,CODPRODUTO,QTDE,VALORUNIT) "
                        + "VALUES (" + numlocacao + ",'" + tb_produtos.getValueAt(0, 0) + "','" + tb_produtos.getValueAt(0, 2)
                        + "','" + tb_produtos.getValueAt(0, 3) + "');");
                ps.executeUpdate();
                ps.close();
                st.executeUpdate("UPDATE PRODUTO SET DISPONIVEL = 'Nao' WHERE CODPROD =" + tb_produtos.getValueAt(0, 0));
                st.close();
                model.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void cadastraCabecalho() {
        try {
            st = con.createStatement();
            String dataLoc = null, dataDev = null;
            java.util.Date data1 = txt_dataEntrega.getDate();
            SimpleDateFormat formato1 = new SimpleDateFormat("MM/dd/yyyy");
            dataLoc = (formato1.format(data1));
            java.util.Date data2 = txt_dataDevolucao.getDate();
            SimpleDateFormat formato2 = new SimpleDateFormat("MM/dd/yyyy");
            dataDev = (formato2.format(data2));

            dataLoc = dataLoc.replaceAll("/", "-");
            dataDev = dataDev.replaceAll("/", "-");
            ResultSet rs = st.executeQuery("select count(*) qtde from locacao where codlocacao='" + txt_loc_numero.getText() + "';");
            if (rs.next()) {
                if (Integer.parseInt(rs.getString("qtde").toString()) > 0) {
                    st.executeUpdate("UPDATE locacao SET dataloc='" + dataLoc + "',dataDev='" + dataDev + "',observacao='"
                            + txt_observacao.getText() + "',codcliente='" + txt_codigoCliente.getText() + "',total='" + txt_loc_total.getText()
                            + "' where codlocacao=" + txt_loc_numero.getText() + ";");
                    st.close();
                } else {
                    PreparedStatement ps = con.prepareStatement("INSERT INTO Locacao (CODLOCACAO,DATALOC,DATADEV,OBSERVACAO,CODCLIENTE,TOTAL) "
                            + "VALUES (" + txt_loc_numero.getText() + ",'" + dataLoc + "','" + dataDev + "','" + txt_observacao.getText() + "','" + txt_codigoCliente.getText() + "','" + txt_loc_total.getText() + "');");
                    ps.executeUpdate();
                    ps.close();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void editar() {
        try {
            camposOn();
            txt_codigoProduto.setText(tb_produtos.getValueAt(tb_produtos.getSelectedRow(), 0).toString());
            txt_descricaoProduto.setText(tb_produtos.getValueAt(tb_produtos.getSelectedRow(), 1).toString());
            txt_quantidadeProduto.setText(tb_produtos.getValueAt(tb_produtos.getSelectedRow(), 2).toString());
            txt_valorProduto.setText(tb_produtos.getValueAt(tb_produtos.getSelectedRow(), 3).toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void delete(int codigo) {
        if (tb_produtos.getSelectedRowCount() == 1) {
            if (JOptionPane.showConfirmDialog(null, "Deseja Apagar o Item: " + codigo, "", 0, JOptionPane.YES_NO_OPTION) == 0) {
                try {
                    DefaultTableModel model = (DefaultTableModel) tb_produtos.getModel();
                    model.removeRow(tb_produtos.getSelectedRow());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione Uma Linha da Tabela");
        }
    }

    public void finalizaLocacao() {
        try {
            if (txt_operacaoLocacao.getText().compareTo("Inclusão") == 0) {
                cadastraCabecalho();
                cadastraItens(txt_loc_numero.getText());
                JOptionPane.showMessageDialog(null, "Locação Cadastrada com Sucesso");
            }
            if (txt_operacaoLocacao.getText().compareTo("Alteração") == 0) {
                cadastraCabecalho();
                JOptionPane.showMessageDialog(null, "Locação Alterada com Sucesso");
            }
            limpaTabela();
            camposOff();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void buscaLocacao(String numLocacao) {
        try {
            st = con.createStatement();
            Date dataDev = null;
            Date dataLoc = null;
            String datadev = null, dataloc = null;

            ResultSet rs = st.executeQuery("select * from locacao l inner join item_venda i on l.codlocacao=i.codlocacao inner join cliente c on "
                    + "l.codcliente=c.codcliente where l.codlocacao=" + txt_loc_numero.getText() + ";");
            while (rs.next()) {
                datadev = (rs.getString("datadev"));
                dataloc = (rs.getString("dataloc"));
                dataloc = dataloc.replaceAll("-", "/");
                datadev = datadev.replaceAll("-", "/");
                dataDev = new Date(datadev);
                dataLoc = new Date(dataloc);
                txt_dataDevolucao.setDate(dataDev);
                txt_dataEntrega.setDate(dataLoc);
                txt_observacao.setText(rs.getString("observacao"));
                txt_codigoCliente.setText(rs.getString("codcliente"));
                txt_nome.setText(rs.getString("nome"));
                txt_cep.setText(rs.getString("cep_ent"));
                txt_cidade.setText(rs.getString("cidade_ent"));
                txt_bairro.setText(rs.getString("bairro_ent"));
                txt_logradouro.setText(rs.getString("rua_ent"));
                txt_numero.setText(rs.getString("numero_ent"));
                txt_complemento.setText(rs.getString("complemento_ent"));
                txt_loc_total.setText(rs.getString("total"));
            }
            limpaTabela();
            listaItens();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void alteraProduto(int codigo, int qtde, float valor) {
        try {
            st = con.createStatement();
            st.executeUpdate("UPDATE Item_venda SET qtde=" + qtde + ",valorunit=" + valor + "where codprodUTO=" + codigo);
            st.close();
            JOptionPane.showMessageDialog(null, "Item Alterado com Sucesso");
            txt_codigoProduto.setText("");
            txt_descricaoProduto.setText("");
            txt_quantidadeProduto.setText("");
            txt_valorProduto.setText("");
            txt_codigoCliente.requestFocus();
            limpaTabela();
            listaItens();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void somaTotal(float total) {
        total += Integer.parseInt(txt_quantidadeProduto.getText()) * Float.parseFloat(txt_valorProduto.getText());
        txt_loc_total.setText(total + "");
    }

    public void subtrairTotal(float total) {
        total -= Integer.parseInt(tb_produtos.getValueAt(tb_produtos.getSelectedRow(), 2).toString())
                * Float.parseFloat(tb_produtos.getValueAt(tb_produtos.getSelectedRow(), 3).toString());
        txt_loc_total.setText(total + "");
    }

    public void editaItem(String codigo, String descricao, String qtde, float valor) {
        try {
            DefaultTableModel model = (DefaultTableModel) tb_produtos.getModel();
            String[] linha = new String[]{codigo, descricao, qtde, valor + ""};
            int numlinha = tb_produtos.getSelectedRow();
            model.removeRow(numlinha);
            model.addRow(linha);
            JOptionPane.showMessageDialog(null, "Item inserido com Sucesso");
            somaTotal(Float.parseFloat(txt_loc_total.getText()));
            txt_descricaoProduto.setText(null);
            txt_codigoProduto.setText(null);
            txt_quantidadeProduto.setText(null);
            txt_valorProduto.setText(null);
            txt_codigoProduto.requestFocus();
            tb_produtos.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir com item");
        }
    }

    public void excluiLocacao(String numLocacao) {
        try {

            DefaultTableModel model = (DefaultTableModel) tb_produtos.getModel();
            while (tb_produtos.getRowCount() > 0) {
                st = con.createStatement();
                st.executeUpdate("UPDATE PRODUTO SET DISPONIVEL = 'Sim' WHERE CODPROD =" + tb_produtos.getValueAt(0, 0));
                st.close();
                model.removeRow(0);
            }
            PreparedStatement ps = con.prepareStatement("DELETE FROM Item_Venda WHERE CODLOCACAO=" + numLocacao + ";");
            PreparedStatement ps1 = con.prepareStatement("DELETE FROM Locacao WHERE CODLOCACAO=" + numLocacao + ";");
            ps.executeUpdate();
            ps1.executeUpdate();
            ps.close();
            ps1.close();
            JOptionPane.showMessageDialog(null, "Locaçao removida com Sucesso");
            btn_cancelar.doClick();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_fundo = new javax.swing.JPanel();
        txt_botoes = new javax.swing.JPanel();
        btn_finalizar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        btn_inclusao = new javax.swing.JButton();
        btn_alteracao = new javax.swing.JButton();
        btn_exclusao = new javax.swing.JButton();
        txt_operacaoLocacao = new javax.swing.JTextField();
        btn_consultar = new javax.swing.JButton();
        pnl_dadosCliente = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txt_codigoCliente = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_nome = new javax.swing.JTextField();
        btn_carregaCliente = new javax.swing.JToggleButton();
        pnl_dadosLocacao = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txt_loc_numero = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_dataEntrega = new com.toedter.calendar.JDateChooser();
        txt_loc_total = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_dataDevolucao = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_observacao = new javax.swing.JTextField();
        btn_carregarLocacao = new javax.swing.JButton();
        pnl_enderecoEntrega = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        txt_cidade = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txt_bairro = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txt_logradouro = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txt_numero = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txt_complemento = new javax.swing.JTextField();
        btn_editaCep = new javax.swing.JToggleButton();
        jLabel32 = new javax.swing.JLabel();
        btn_carregaCep = new javax.swing.JButton();
        txt_cep = new javax.swing.JFormattedTextField();
        pnl_itens = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_produtos = new javax.swing.JTable();
        txt_botoesProdutos = new javax.swing.JPanel();
        btn_novo = new javax.swing.JButton();
        btn_editar = new javax.swing.JButton();
        btn_apagar = new javax.swing.JButton();
        txt_operacao = new javax.swing.JTextField();
        pnl_dadosProduto = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_codigoProduto = new javax.swing.JTextField();
        txt_descricaoProduto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_quantidadeProduto = new javax.swing.JTextField();
        txt_valorProduto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btn_ok = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Efetuar Locação");

        txt_botoes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_finalizar.setText("Finalizar");
        btn_finalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_finalizarActionPerformed(evt);
            }
        });

        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        btn_inclusao.setText("Inclusão");
        btn_inclusao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_inclusaoActionPerformed(evt);
            }
        });

        btn_alteracao.setText("Alteração");
        btn_alteracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_alteracaoActionPerformed(evt);
            }
        });

        btn_exclusao.setText("Exclusão");
        btn_exclusao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exclusaoActionPerformed(evt);
            }
        });

        txt_operacaoLocacao.setEditable(false);
        txt_operacaoLocacao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_operacaoLocacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_operacaoLocacaoActionPerformed(evt);
            }
        });

        btn_consultar.setText("Consultar");
        btn_consultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_consultarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout txt_botoesLayout = new javax.swing.GroupLayout(txt_botoes);
        txt_botoes.setLayout(txt_botoesLayout);
        txt_botoesLayout.setHorizontalGroup(
            txt_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txt_botoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_inclusao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_alteracao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_exclusao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_consultar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(txt_operacaoLocacao, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_finalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        txt_botoesLayout.setVerticalGroup(
            txt_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txt_botoesLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(txt_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_finalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_inclusao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_alteracao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_exclusao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_operacaoLocacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_consultar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4))
        );

        pnl_dadosCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados do Cliente", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel6.setText("Código:");

        txt_codigoCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_codigoClienteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_codigoClienteFocusLost(evt);
            }
        });
        txt_codigoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_codigoClienteKeyPressed(evt);
            }
        });

        jLabel7.setText("Nome:");

        txt_nome.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_nomeFocusGained(evt);
            }
        });

        btn_carregaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_carregaClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_dadosClienteLayout = new javax.swing.GroupLayout(pnl_dadosCliente);
        pnl_dadosCliente.setLayout(pnl_dadosClienteLayout);
        pnl_dadosClienteLayout.setHorizontalGroup(
            pnl_dadosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosClienteLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_codigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_carregaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(txt_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_dadosClienteLayout.setVerticalGroup(
            pnl_dadosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_carregaCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnl_dadosClienteLayout.createSequentialGroup()
                        .addGroup(pnl_dadosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_codigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(txt_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pnl_dadosLocacao.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados da Locação", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel8.setText("Código:");

        txt_loc_numero.setEditable(false);
        txt_loc_numero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_loc_numero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_loc_numeroFocusLost(evt);
            }
        });
        txt_loc_numero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_loc_numeroKeyPressed(evt);
            }
        });

        jLabel9.setText("Data Entrega:");

        txt_loc_total.setEditable(false);
        txt_loc_total.setBackground(new java.awt.Color(102, 102, 102));
        txt_loc_total.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        txt_loc_total.setForeground(new java.awt.Color(204, 0, 0));
        txt_loc_total.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_loc_total.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel10.setText("Total:");

        jLabel11.setText("Data Devolução:");

        jLabel12.setText("Observações:");

        btn_carregarLocacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_carregarLocacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_dadosLocacaoLayout = new javax.swing.GroupLayout(pnl_dadosLocacao);
        pnl_dadosLocacao.setLayout(pnl_dadosLocacaoLayout);
        pnl_dadosLocacaoLayout.setHorizontalGroup(
            pnl_dadosLocacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosLocacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_loc_numero, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_carregarLocacao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_dataEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_dataDevolucao, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_observacao, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_loc_total, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_dadosLocacaoLayout.setVerticalGroup(
            pnl_dadosLocacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosLocacaoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_dadosLocacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_dadosLocacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txt_loc_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_observacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_dadosLocacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txt_dataDevolucao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pnl_dadosLocacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_carregarLocacao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnl_dadosLocacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8)
                                .addComponent(txt_loc_numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_dataEntrega, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(11, 11, 11))
        );

        pnl_enderecoEntrega.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Endereço Entrega", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel26.setText("Cidade:");

        jLabel28.setText("Bairro:");

        jLabel29.setText("Logradouro:");

        jLabel30.setText("Número:");

        jLabel31.setText("Complemento:");

        btn_editaCep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editaCepActionPerformed(evt);
            }
        });

        jLabel32.setText("CEP:");

        btn_carregaCep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_carregaCepActionPerformed(evt);
            }
        });

        try {
            txt_cep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout pnl_enderecoEntregaLayout = new javax.swing.GroupLayout(pnl_enderecoEntrega);
        pnl_enderecoEntrega.setLayout(pnl_enderecoEntregaLayout);
        pnl_enderecoEntregaLayout.setHorizontalGroup(
            pnl_enderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_enderecoEntregaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnl_enderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel28)
                    .addComponent(jLabel30)
                    .addComponent(jLabel32))
                .addGap(10, 10, 10)
                .addGroup(pnl_enderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_enderecoEntregaLayout.createSequentialGroup()
                        .addComponent(txt_numero, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel31))
                    .addGroup(pnl_enderecoEntregaLayout.createSequentialGroup()
                        .addGroup(pnl_enderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_bairro)
                            .addGroup(pnl_enderecoEntregaLayout.createSequentialGroup()
                                .addComponent(txt_cep, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_carregaCep, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(pnl_enderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_enderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_complemento, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_logradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_enderecoEntregaLayout.createSequentialGroup()
                        .addComponent(txt_cidade, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_editaCep, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1))
        );
        pnl_enderecoEntregaLayout.setVerticalGroup(
            pnl_enderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_enderecoEntregaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_enderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_carregaCep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnl_enderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_cidade)
                        .addComponent(jLabel26)
                        .addComponent(jLabel32)
                        .addComponent(txt_cep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_editaCep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(pnl_enderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_enderecoEntregaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_enderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addGroup(pnl_enderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel29)
                                .addComponent(txt_bairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnl_enderecoEntregaLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txt_logradouro)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_enderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_enderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_complemento)
                        .addComponent(jLabel31))
                    .addGroup(pnl_enderecoEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel30)
                        .addComponent(txt_numero)))
                .addContainerGap())
        );

        pnl_itens.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Itens", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        tb_produtos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tb_produtos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "DESCRICAO", "QTDE", "VLR UNIT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tb_produtos);
        if (tb_produtos.getColumnModel().getColumnCount() > 0) {
            tb_produtos.getColumnModel().getColumn(0).setMinWidth(100);
            tb_produtos.getColumnModel().getColumn(0).setPreferredWidth(100);
            tb_produtos.getColumnModel().getColumn(0).setMaxWidth(100);
            tb_produtos.getColumnModel().getColumn(2).setMinWidth(80);
            tb_produtos.getColumnModel().getColumn(2).setPreferredWidth(80);
            tb_produtos.getColumnModel().getColumn(2).setMaxWidth(80);
            tb_produtos.getColumnModel().getColumn(3).setMinWidth(80);
            tb_produtos.getColumnModel().getColumn(3).setPreferredWidth(80);
            tb_produtos.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        txt_botoesProdutos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        txt_operacao.setEditable(false);
        txt_operacao.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout txt_botoesProdutosLayout = new javax.swing.GroupLayout(txt_botoesProdutos);
        txt_botoesProdutos.setLayout(txt_botoesProdutosLayout);
        txt_botoesProdutosLayout.setHorizontalGroup(
            txt_botoesProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txt_botoesProdutosLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(btn_novo)
                .addGap(18, 18, 18)
                .addComponent(btn_editar)
                .addGap(18, 18, 18)
                .addComponent(btn_apagar)
                .addGap(191, 191, 191)
                .addComponent(txt_operacao, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        txt_botoesProdutosLayout.setVerticalGroup(
            txt_botoesProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txt_botoesProdutosLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(txt_botoesProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_novo)
                    .addComponent(btn_editar)
                    .addComponent(btn_apagar)
                    .addComponent(txt_operacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2))
        );

        pnl_dadosProduto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Código:");

        txt_codigoProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_codigoProdutoKeyPressed(evt);
            }
        });

        txt_descricaoProduto.setEditable(false);
        txt_descricaoProduto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_descricaoProdutoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_descricaoProdutoFocusLost(evt);
            }
        });

        jLabel2.setText("Descrição:");

        jLabel3.setText("Quantidade:");

        txt_quantidadeProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_quantidadeProdutoActionPerformed(evt);
            }
        });

        txt_valorProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_valorProdutoActionPerformed(evt);
            }
        });

        jLabel4.setText("Vlr Unit:");

        btn_ok.setText("OK");
        btn_ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_okActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_dadosProdutoLayout = new javax.swing.GroupLayout(pnl_dadosProduto);
        pnl_dadosProduto.setLayout(pnl_dadosProdutoLayout);
        pnl_dadosProdutoLayout.setHorizontalGroup(
            pnl_dadosProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_codigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_descricaoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_quantidadeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_valorProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_ok, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl_dadosProdutoLayout.setVerticalGroup(
            pnl_dadosProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dadosProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txt_valorProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_ok))
                    .addGroup(pnl_dadosProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txt_quantidadeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_dadosProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txt_descricaoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_dadosProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txt_codigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnl_itensLayout = new javax.swing.GroupLayout(pnl_itens);
        pnl_itens.setLayout(pnl_itensLayout);
        pnl_itensLayout.setHorizontalGroup(
            pnl_itensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_itensLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_itensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_botoesProdutos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_dadosProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        pnl_itensLayout.setVerticalGroup(
            pnl_itensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_itensLayout.createSequentialGroup()
                .addComponent(txt_botoesProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_dadosProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnl_fundoLayout = new javax.swing.GroupLayout(pnl_fundo);
        pnl_fundo.setLayout(pnl_fundoLayout);
        pnl_fundoLayout.setHorizontalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnl_enderecoEntrega, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_botoes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_dadosLocacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_dadosCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_itens, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_fundoLayout.setVerticalGroup(
            pnl_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fundoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_dadosLocacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_dadosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_enderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnl_itens, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_botoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_fundo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_fundo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        camposOff();
        limpaTabela();
        txt_operacaoLocacao.setText(null);
        btn_inclusao.setEnabled(true);
        btn_consultar.setEnabled(true);
        btn_alteracao.setEnabled(true);
        btn_exclusao.setEnabled(true);
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_finalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_finalizarActionPerformed
        finalizaLocacao();
    }//GEN-LAST:event_btn_finalizarActionPerformed

    private void btn_inclusaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inclusaoActionPerformed
        txt_operacaoLocacao.setText("Inclusão");
        btn_novo.setEnabled(true);
        btn_carregaCep.setEnabled(true);
        btn_editaCep.setEnabled(true);
        btn_carregaCliente.setEnabled(true);
        btn_editar.setEnabled(true);
        btn_apagar.setEnabled(true);
        btn_ok.setEnabled(true);
        btn_inclusao.setEnabled(false);
        btn_alteracao.setEnabled(false);
        btn_exclusao.setEnabled(false);
        btn_consultar.setEnabled(false);
        txt_loc_total.setText("0.00");
        buscaUltima();
    }//GEN-LAST:event_btn_inclusaoActionPerformed

    private void btn_alteracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_alteracaoActionPerformed
        txt_operacaoLocacao.setText("Alteração");
        btn_ok.setEnabled(false);
        btn_carregarLocacao.setEnabled(true);
        btn_inclusao.setEnabled(false);
        btn_alteracao.setEnabled(false);
        btn_exclusao.setEnabled(false);
        btn_novo.setEnabled(false);
        btn_editar.setEnabled(false);
        btn_apagar.setEnabled(false);
        if (txt_loc_numero.getText().compareTo("") != 0) {
            txt_loc_numero.setEditable(true);
            txt_dataDevolucao.setEnabled(true);
            txt_dataEntrega.setEnabled(true);
            txt_observacao.setEnabled(true);
            txt_codigoCliente.setEnabled(true);
        } else {
            txt_loc_numero.setEnabled(true);
            txt_loc_numero.setEditable(true);
            txt_loc_numero.requestFocus();
        }
    }//GEN-LAST:event_btn_alteracaoActionPerformed

    private void btn_exclusaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exclusaoActionPerformed

        if (txt_loc_numero.getText().compareTo("") != 0) {
            txt_operacaoLocacao.setText("Exclusão");
            excluiLocacao(txt_loc_numero.getText());
        } else {
            JOptionPane.showMessageDialog(null, "Numero de Locação Invalido");
        }
    }//GEN-LAST:event_btn_exclusaoActionPerformed

    private void btn_novoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_novoActionPerformed
        txt_operacao.setText("INSERÇÃO");
        txt_codigoCliente.setEnabled(false);
        txt_nome.setEnabled(false);
        txt_loc_numero.setEnabled(false);
        txt_dataDevolucao.setEnabled(false);
        txt_dataEntrega.setEnabled(false);
        txt_observacao.setEnabled(false);
        txt_cep.setEnabled(false);
        txt_cidade.setEnabled(false);
        txt_bairro.setEnabled(false);
        txt_logradouro.setEnabled(false);
        txt_numero.setEnabled(false);
        txt_complemento.setEnabled(false);
        txt_codigoProduto.setEnabled(false);
        txt_descricaoProduto.setEnabled(false);
        txt_valorProduto.setEnabled(false);
        txt_quantidadeProduto.setEnabled(false);
        camposOn();
        txt_codigoProduto.requestFocus();
    }//GEN-LAST:event_btn_novoActionPerformed

    private void btn_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editarActionPerformed
        if (tb_produtos.getSelectedRowCount() == 1) {
            txt_operacao.setText("ALTERAÇÃO");
            tb_produtos.setEnabled(false);
            subtrairTotal(Float.parseFloat(txt_loc_total.getText()));
            editar();
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um Produto na Tabela");
        }
    }//GEN-LAST:event_btn_editarActionPerformed

    private void btn_apagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_apagarActionPerformed
        txt_operacao.setText("EXCLUSÃO");
        delete(Integer.parseInt(tb_produtos.getValueAt(tb_produtos.getSelectedRow(), 0).toString()));
    }//GEN-LAST:event_btn_apagarActionPerformed

    private void txt_codigoClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_codigoClienteFocusLost

    }//GEN-LAST:event_txt_codigoClienteFocusLost

    private void txt_codigoClienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_codigoClienteFocusGained
        txt_codigoCliente.setText(null);
        txt_nome.setText(null);
    }//GEN-LAST:event_txt_codigoClienteFocusGained

    private void btn_editaCepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editaCepActionPerformed
        txt_cep.setEnabled(true);
        txt_cidade.setEnabled(true);
        txt_bairro.setEnabled(true);
        txt_logradouro.setEnabled(true);
        txt_numero.setEnabled(true);
        txt_complemento.setEnabled(true);

        txt_cep.setText("");
        txt_cidade.setText("");
        txt_bairro.setText("");
        txt_logradouro.setText("");
        txt_numero.setText("");
        txt_complemento.setText("");
        txt_cep.requestFocus();
    }//GEN-LAST:event_btn_editaCepActionPerformed

    private void btn_carregaCepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_carregaCepActionPerformed
        validaCEPent(txt_cep.getText());
    }//GEN-LAST:event_btn_carregaCepActionPerformed

    private void txt_quantidadeProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_quantidadeProdutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_quantidadeProdutoActionPerformed

    private void txt_valorProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_valorProdutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_valorProdutoActionPerformed

    private void txt_codigoProdutoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codigoProdutoKeyPressed
        enterProduto(evt);
    }//GEN-LAST:event_txt_codigoProdutoKeyPressed

    private void txt_descricaoProdutoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_descricaoProdutoFocusGained
        carregaProduto();
    }//GEN-LAST:event_txt_descricaoProdutoFocusGained

    private void btn_okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_okActionPerformed
        if (txt_operacao.getText().compareTo("INSERÇÃO") == 0) {
            insereItem(Integer.parseInt(txt_codigoProduto.getText()),
                    txt_descricaoProduto.getText(),
                    Integer.parseInt(txt_quantidadeProduto.getText()),
                    Float.parseFloat(txt_valorProduto.getText()));
        }
        if (txt_operacao.getText().compareTo("ALTERAÇÃO") == 0) {
//            alteraProduto(Integer.parseInt(txt_codigoProduto.getText()), Integer.parseInt(txt_quantidadeProduto.getText()),
//                    Float.parseFloat(txt_valorProduto.getText()));
            editaItem(txt_codigoProduto.getText(),
                    txt_descricaoProduto.getText(),
                    txt_quantidadeProduto.getText(),
                    Float.parseFloat(txt_valorProduto.getText()));
        }
    }//GEN-LAST:event_btn_okActionPerformed

    private void txt_descricaoProdutoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_descricaoProdutoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_descricaoProdutoFocusLost

    private void txt_loc_numeroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_loc_numeroFocusLost

    }//GEN-LAST:event_txt_loc_numeroFocusLost

    private void btn_carregarLocacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_carregarLocacaoActionPerformed

//        txt_dataDevolucao.setEnabled(false);
//        txt_dataEntrega.setEnabled(false);
//        txt_observacao.setEnabled(false);
//        txt_codigo.setEnabled(false);
        txt_loc_numero.setText(numLocacao + "");
        buscaLocacao(txt_loc_numero.getText());
    }//GEN-LAST:event_btn_carregarLocacaoActionPerformed

    private void txt_operacaoLocacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_operacaoLocacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_operacaoLocacaoActionPerformed

    private void txt_loc_numeroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_loc_numeroKeyPressed
        enterLocacao(evt);
    }//GEN-LAST:event_txt_loc_numeroKeyPressed

    private void btn_consultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_consultarActionPerformed
        btn_consultar.setEnabled(false);
        btn_alteracao.setEnabled(true);
        btn_exclusao.setEnabled(true);
        btn_inclusao.setEnabled(false);
        btn_carregarLocacao.setEnabled(true);
        txt_loc_numero.setText(null);
        txt_loc_numero.setEnabled(true);
        txt_loc_numero.setEditable(true);
        txt_loc_numero.requestFocus();

    }//GEN-LAST:event_btn_consultarActionPerformed

    private void txt_codigoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codigoClienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BuscaClientes b = new BuscaClientes();
            b.setVisible(true);
        }
    }//GEN-LAST:event_txt_codigoClienteKeyPressed

    private void txt_nomeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_nomeFocusGained

    }//GEN-LAST:event_txt_nomeFocusGained

    private void btn_carregaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_carregaClienteActionPerformed
        buscaCliente(txt_codigoCliente.getText());
        insereCliente();
    }//GEN-LAST:event_btn_carregaClienteActionPerformed

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
            java.util.logging.Logger.getLogger(Frm_Locacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_Locacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_Locacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_Locacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm_Locacao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_alteracao;
    private javax.swing.JButton btn_apagar;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_carregaCep;
    private javax.swing.JToggleButton btn_carregaCliente;
    private javax.swing.JButton btn_carregarLocacao;
    private javax.swing.JButton btn_consultar;
    private javax.swing.JToggleButton btn_editaCep;
    private javax.swing.JButton btn_editar;
    private javax.swing.JButton btn_exclusao;
    private javax.swing.JButton btn_finalizar;
    private javax.swing.JButton btn_inclusao;
    private javax.swing.JButton btn_novo;
    private javax.swing.JButton btn_ok;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnl_dadosCliente;
    private javax.swing.JPanel pnl_dadosLocacao;
    private javax.swing.JPanel pnl_dadosProduto;
    private javax.swing.JPanel pnl_enderecoEntrega;
    private javax.swing.JPanel pnl_fundo;
    private javax.swing.JPanel pnl_itens;
    private javax.swing.JTable tb_produtos;
    private javax.swing.JTextField txt_bairro;
    private javax.swing.JPanel txt_botoes;
    private javax.swing.JPanel txt_botoesProdutos;
    private javax.swing.JFormattedTextField txt_cep;
    private javax.swing.JTextField txt_cidade;
    private javax.swing.JTextField txt_codigoCliente;
    private javax.swing.JTextField txt_codigoProduto;
    private javax.swing.JTextField txt_complemento;
    private com.toedter.calendar.JDateChooser txt_dataDevolucao;
    private com.toedter.calendar.JDateChooser txt_dataEntrega;
    private javax.swing.JTextField txt_descricaoProduto;
    private javax.swing.JTextField txt_loc_numero;
    private javax.swing.JTextField txt_loc_total;
    private javax.swing.JTextField txt_logradouro;
    private javax.swing.JTextField txt_nome;
    private javax.swing.JTextField txt_numero;
    private javax.swing.JTextField txt_observacao;
    private javax.swing.JTextField txt_operacao;
    private javax.swing.JTextField txt_operacaoLocacao;
    private javax.swing.JTextField txt_quantidadeProduto;
    private javax.swing.JTextField txt_valorProduto;
    // End of variables declaration//GEN-END:variables
}

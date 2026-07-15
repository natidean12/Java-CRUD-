package projeto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class disciplinaae extends JFrame {

    private JTextField txtCodDisc, txtNomeDisc, txtCarga;
    private JRadioButton rb1, rb2, rb3, rb4, rb5, rb6;
    private JButton btnAlterar, btnExcluir, btnLimpar, btnSair;
    private JButton btnConfirmarAlterar, btnConfirmarExcluir;

    public disciplinaae() {
        setTitle("Cadastro / Manutenção de Disciplina");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Painel de fundo com degradê
        JPanel painelFundo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                Color cor1 = new Color(200, 230, 255);
                Color cor2 = new Color(245, 250, 255);
                g2.setPaint(new GradientPaint(0, 0, cor1, 0, getHeight(), cor2));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        painelFundo.setLayout(null);
        setContentPane(painelFundo);

        Font fonteLabel = new Font("Arial", Font.BOLD, 18);
        Font fonteCampo = new Font("Arial", Font.PLAIN, 16);
        Font fontBotao = new Font("Arial", Font.BOLD, 16);

        int baseX = 220;
        int baseY = 130;
        int altura = 35;
        int espacamento = 60;

        // ====== Título ======
        JLabel lbTitulo = new JLabel("Alterar / Excluir Disciplina", SwingConstants.CENTER);
        lbTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lbTitulo.setBounds(0, 30, getWidth(), 40);
        painelFundo.add(lbTitulo);

        // ====== Código ======
        JLabel lbCod = new JLabel("Código Disciplina:");
        lbCod.setFont(fonteLabel);
        lbCod.setBounds(baseX, baseY, 220, altura);
        painelFundo.add(lbCod);

        txtCodDisc = new JTextField();
        txtCodDisc.setFont(fonteCampo);
        txtCodDisc.setBounds(baseX + 230, baseY, 150, altura);
        painelFundo.add(txtCodDisc);

        txtCodDisc.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buscarDisciplinaPorCodigo();
                }
            }
        });

        // ====== Nome ======
        JLabel lbNome = new JLabel("Nome Disciplina:");
        lbNome.setFont(fonteLabel);
        lbNome.setBounds(baseX, baseY + espacamento, 220, altura);
        painelFundo.add(lbNome);

        txtNomeDisc = new JTextField();
        txtNomeDisc.setFont(fonteCampo);
        txtNomeDisc.setBounds(baseX + 230, baseY + espacamento, 300, altura);
        painelFundo.add(txtNomeDisc);

        // ====== Carga Horária ======
        JLabel lbCarga = new JLabel("Carga Horária:");
        lbCarga.setFont(fonteLabel);
        lbCarga.setBounds(baseX, baseY + espacamento * 2, 220, altura);
        painelFundo.add(lbCarga);

        txtCarga = new JTextField();
        txtCarga.setFont(fonteCampo);
        txtCarga.setBounds(baseX + 230, baseY + espacamento * 2, 150, altura);
        painelFundo.add(txtCarga);

        // ======= Aulas por semana =======
        JLabel lbAulas = new JLabel("Aulas por semana:");
        lbAulas.setFont(fonteLabel);
        lbAulas.setBounds(baseX, baseY + espacamento * 3, 180, altura);
        painelFundo.add(lbAulas);

        JPanel painelAulas = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        painelAulas.setBounds(baseX + 220, baseY + espacamento * 3, 320, altura + 5);
        painelAulas.setOpaque(false);

        ButtonGroup grupoAulas = new ButtonGroup();

        rb1 = new JRadioButton("1");
        rb2 = new JRadioButton("2");
        rb3 = new JRadioButton("3");
        rb4 = new JRadioButton("4");
        rb5 = new JRadioButton("5");
        rb6 = new JRadioButton("6");

        JRadioButton[] botoes = {rb1, rb2, rb3, rb4, rb5, rb6};
        for (JRadioButton rb : botoes) {
            rb.setOpaque(false);
            rb.setFont(new Font("Arial", Font.PLAIN, 15));
            grupoAulas.add(rb);
            painelAulas.add(rb);
        }
        painelFundo.add(painelAulas);

        // ====== Botões principais =====
        int yBotoes = baseY + espacamento * 5;
        int larguraBotao = 140;
        int alturaBotao = 40;
        int espaco = 40;

        btnAlterar = criarBotao("✏️ Alterar", new Color(173, 216, 230), fontBotao);
        btnAlterar.setBounds(160, yBotoes, larguraBotao, alturaBotao);
        painelFundo.add(btnAlterar);

        btnExcluir = criarBotao("🗑️ Excluir", new Color(255, 182, 193), fontBotao);
        btnExcluir.setBounds(160 + larguraBotao + espaco, yBotoes, larguraBotao, alturaBotao);
        painelFundo.add(btnExcluir);

        btnLimpar = criarBotao("🧹 Limpar", new Color(255, 255, 204), fontBotao);
        btnLimpar.setBounds(160 + (larguraBotao + espaco) * 2, yBotoes, larguraBotao, alturaBotao);
        painelFundo.add(btnLimpar);

        btnSair = criarBotao("❌ Sair", new Color(255, 160, 122), fontBotao);
        btnSair.setBounds(160 + (larguraBotao + espaco) * 3, yBotoes, larguraBotao, alturaBotao);
        painelFundo.add(btnSair);

        // ====== Botões de confirmação sobrepostos =====
        btnConfirmarAlterar = criarBotao("✅ Confirmar", new Color(200, 255, 200), fontBotao);
        btnConfirmarAlterar.setBounds(btnAlterar.getBounds());
        btnConfirmarAlterar.setVisible(false);
        btnConfirmarAlterar.addActionListener(e -> {
            alterarDisciplina();
            btnConfirmarAlterar.setVisible(false);
            btnAlterar.setVisible(true);
        });
        painelFundo.add(btnConfirmarAlterar);

        btnConfirmarExcluir = criarBotao("❌ Confirmar", new Color(255, 200, 200), fontBotao);
        btnConfirmarExcluir.setBounds(btnExcluir.getBounds());
        btnConfirmarExcluir.setVisible(false);
        btnConfirmarExcluir.addActionListener(e -> {
            excluirDisciplina();
            btnConfirmarExcluir.setVisible(false);
            btnExcluir.setVisible(true);
        });
        painelFundo.add(btnConfirmarExcluir);

        // ====== Eventos principais =====
        btnAlterar.addActionListener(e -> {
            btnAlterar.setVisible(false);
            btnConfirmarAlterar.setVisible(true);
        });

        btnExcluir.addActionListener(e -> {
            btnExcluir.setVisible(false);
            btnConfirmarExcluir.setVisible(true);
        });

        btnLimpar.addActionListener(e -> limparCampos());
        btnSair.addActionListener(e -> dispose());

        // Borda do formulário
        getRootPane().setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(160, 100, 255), 2),
                "Disciplina",
                TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16)
        ));
    }

    private JButton criarBotao(String texto, Color cor, Font fonte) {
        JButton btn = new JButton(texto);
        btn.setFont(fonte);
        btn.setBackground(cor);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { btn.setBackground(cor.brighter()); }
            @Override
            public void mouseExited(MouseEvent e) { btn.setBackground(cor); }
        });
        return btn;
    }

    private void limparCampos() {
        txtCodDisc.setText("");
        txtNomeDisc.setText("");
        txtCarga.setText("");
        rb1.setSelected(false);
        rb2.setSelected(false);
        rb3.setSelected(false);
        rb4.setSelected(false);
        rb5.setSelected(false);
        rb6.setSelected(false);
    }

    private void buscarDisciplinaPorCodigo() {
        String textoCod = txtCodDisc.getText().trim();
        if (textoCod.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o código da disciplina!");
            return;
        }
        try {
            int codDisc = Integer.parseInt(textoCod);
            String sql = "SELECT nome_disc, carga_horaria, aulas_semana FROM Disciplina WHERE cod_disc = ?";
            try (Connection conn = Conexao.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {

                pst.setInt(1, codDisc);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        txtNomeDisc.setText(rs.getString("nome_disc"));
                        txtCarga.setText(String.valueOf(rs.getInt("carga_horaria")));

                        int aulas = rs.getInt("aulas_semana");
                        rb1.setSelected(aulas == 1);
                        rb2.setSelected(aulas == 2);
                        rb3.setSelected(aulas == 3);
                        rb4.setSelected(aulas == 4);
                        rb5.setSelected(aulas == 5);
                        rb6.setSelected(aulas == 6);

                        piscarCampo(txtCodDisc);
                    } else {
                        JOptionPane.showMessageDialog(this, "Disciplina não encontrada!");
                        limparCampos();
                    }
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O código deve ser numérico!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar disciplina: " + ex.getMessage());
        }
    }

    private void alterarDisciplina() {
        // Correção: Validação de campos vazios adicionada para evitar NumberFormatException
        if (txtCodDisc.getText().trim().isEmpty() || 
            txtNomeDisc.getText().trim().isEmpty() || 
            txtCarga.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos antes de alterar!");
            return;
        }

        try {
            int cod = Integer.parseInt(txtCodDisc.getText().trim());
            String nome = txtNomeDisc.getText().trim();
            int carga = Integer.parseInt(txtCarga.getText().trim());
            int aulas = rb1.isSelected()?1:rb2.isSelected()?2:rb3.isSelected()?3:
                        rb4.isSelected()?4:rb5.isSelected()?5: rb6.isSelected()?6:0;

            if (aulas == 0) { JOptionPane.showMessageDialog(this, "Selecione o número de aulas/semana!"); return; }

            String sql = "UPDATE Disciplina SET nome_disc=?, carga_horaria=?, aulas_semana=? WHERE cod_disc=?";
            try (Connection conn = Conexao.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {

                pst.setString(1, nome);
                pst.setInt(2, carga);
                pst.setInt(3, aulas);
                pst.setInt(4, cod);

                int r = pst.executeUpdate();
                if (r > 0) {
                    JOptionPane.showMessageDialog(this, "✅ Disciplina alterada com sucesso!");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Disciplina não encontrada!");
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique se os campos numéricos possuem apenas números!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar: " + ex.getMessage());
        }
    }

    private void excluirDisciplina() {
        String textoCod = txtCodDisc.getText().trim();
        if (textoCod.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o código da disciplina!");
            return;
        }

        try {
            int codDisc = Integer.parseInt(textoCod);

            try (Connection conn = Conexao.getConnection()) {
                String[] tabelas = {"curso_disciplina", "professor_disciplina", "aluno_disciplina"};
                boolean possuiVinculo = false;
                String tabelaVinculada = "";

                // Verifica se há vínculos em outras tabelas
                for (String tabela : tabelas) {
                    String checkSql = "SELECT COUNT(*) FROM " + tabela + " WHERE cod_disc=?";
                    try (PreparedStatement check = conn.prepareStatement(checkSql)) {
                        check.setInt(1, codDisc);
                        try (ResultSet rs = check.executeQuery()) {
                            if (rs.next() && rs.getInt(1) > 0) {
                                possuiVinculo = true;
                                tabelaVinculada = tabela;
                                break;
                            }
                        }
                    }
                }

                String msg = possuiVinculo
                        ? "Esta disciplina está vinculada à tabela: " + tabelaVinculada
                          + "\nClique em Confirmar para apagar a disciplina e todos os vínculos."
                        : "Deseja realmente excluir esta disciplina?";

                int op = JOptionPane.showOptionDialog(
                        this,
                        msg,
                        "Confirmação de Exclusão",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        new Object[]{"OK"},  
                        "Confirmar"
                );

                if (op == JOptionPane.CLOSED_OPTION) {
                    return;
                }

                conn.setAutoCommit(false); // Inicia a transação

                // Correção: Fechamento correto dos PreparedStatements no laço
                for (String tabela : tabelas) {
                    String deleteSql = "DELETE FROM " + tabela + " WHERE cod_disc=?";
                    try (PreparedStatement pst = conn.prepareStatement(deleteSql)) {
                        pst.setInt(1, codDisc);
                        pst.executeUpdate();
                    }
                }

                String deleteDisc = "DELETE FROM Disciplina WHERE cod_disc=?";
                try (PreparedStatement pstDisc = conn.prepareStatement(deleteDisc)) {
                    pstDisc.setInt(1, codDisc);
                    int r = pstDisc.executeUpdate();
                    conn.commit(); // Confirma a transação

                    if (r > 0) {
                        JOptionPane.showMessageDialog(this, "✅ Disciplina e vínculos excluídos com sucesso!");
                        limparCampos();
                    } else {
                        JOptionPane.showMessageDialog(this, "Nenhuma disciplina encontrada com esse código!");
                    }
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O código deve ser numérico!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
        }
    }

    private void piscarCampo(final JTextField campo) {
        final Color original = campo.getBackground();
        final Color destaque = new Color(255, 255, 180);
        final int delay = 150; // Correção: Configurado tempo de resposta visível do timer
        
        Timer timer = new Timer(delay, null);
        final int[] count = {0};
        timer.addActionListener(e -> {
            campo.setBackground(count[0] % 2 == 0 ? destaque : original);
            count[0]++;
            if (count[0] >= 6) {
                timer.stop();
                campo.setBackground(original);
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        new disciplinaae().setVisible(true);
    }
}

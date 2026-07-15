package projeto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class professorae extends JFrame implements ActionListener {

    JTextField tfID, tfNome, tfRua, tfNumero, tfBairro, tfCidade, tfEstado, tfTel, tfCel, tfData;
    JCheckBox cbDireito, cbInformatica, cbMatematica, cbMedicina;
    JCheckBox cbBacharel, cbEsp, cbMestrado, cbDoutorado;
    JButton btnAlterar, btnExcluir, btnLimpar, btnSair, btnConfirmarAlterar, btnConfirmarExcluir;

    public professorae() {
        setTitle("Alterar / Excluir Professor");
        setSize(1100, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ===== Fundo com degradê =====
        JPanel painelFundo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(245, 245, 250),
                        0, getHeight(), new Color(200, 220, 255));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        painelFundo.setLayout(new GridBagLayout());
        setContentPane(painelFundo);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lbTitulo = new JLabel("Alterar / Excluir Professor", SwingConstants.CENTER);
        lbTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lbTitulo.setForeground(new Color(20, 20, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painelFundo.add(lbTitulo, gbc);
        gbc.gridwidth = 1;

        int linha = 1;
        tfID = criarCampo(painelFundo, gbc, linha++, "Identificação:");
        tfNome = criarCampo(painelFundo, gbc, linha++, "Nome Completo:");
        tfData = criarCampo(painelFundo, gbc, linha++, "Data de Nascimento:");
        tfRua = criarCampo(painelFundo, gbc, linha++, "Rua / Avenida:");
        tfNumero = criarCampo(painelFundo, gbc, linha++, "Número:");
        tfBairro = criarCampo(painelFundo, gbc, linha++, "Bairro:");
        tfCidade = criarCampo(painelFundo, gbc, linha++, "Cidade:");
        tfEstado = criarCampo(painelFundo, gbc, linha++, "Estado:");
        tfTel = criarCampo(painelFundo, gbc, linha++, "Telefone Fixo:");
        tfCel = criarCampo(painelFundo, gbc, linha++, "Celular:");

        // ===== Painel de Especialização =====
        JPanel painelEsp = criarPainelTitled("Especialização");
        cbDireito = criarCheckBox("Direito", painelEsp);
        cbInformatica = criarCheckBox("Informática", painelEsp);
        cbMatematica = criarCheckBox("Matemática", painelEsp);
        cbMedicina = criarCheckBox("Medicina", painelEsp);
        gbc.gridx = 0;
        gbc.gridy = linha++;
        gbc.gridwidth = 2;
        painelFundo.add(painelEsp, gbc);
        gbc.gridwidth = 1;

        // ===== Painel de Titulação =====
        JPanel painelTit = criarPainelTitled("Titulação");
        cbBacharel = criarCheckBox("Bacharel", painelTit);
        cbEsp = criarCheckBox("Especialista Lato Sensu", painelTit);
        cbMestrado = criarCheckBox("Mestrado", painelTit);
        cbDoutorado = criarCheckBox("Doutorado", painelTit);
        gbc.gridx = 0;
        gbc.gridy = linha++;
        gbc.gridwidth = 2;
        painelFundo.add(painelTit, gbc);
        gbc.gridwidth = 1;

        // ===== Painel dos Botões =====
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        painelBotoes.setOpaque(false);

        btnAlterar = criarBotao("💾 Alterar", new Color(60, 179, 113));
        btnConfirmarAlterar = criarBotao("✅ Confirmar Alteração", new Color(180, 255, 180));
        btnExcluir = criarBotao("🗑️ Excluir", new Color(255, 99, 71));
        btnConfirmarExcluir = criarBotao("🚫 Confirmar Exclusão", new Color(255, 160, 160));
        btnLimpar = criarBotao("🧹 Limpar", new Color(255, 215, 0));
        btnSair = criarBotao("❌ Sair", new Color(220, 20, 60));

        btnConfirmarAlterar.setVisible(false);
        btnConfirmarExcluir.setVisible(false);

        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnConfirmarAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnConfirmarExcluir);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnSair);

        gbc.gridx = 0;
        gbc.gridy = linha++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        painelFundo.add(painelBotoes, gbc);

        // ===== Eventos =====
        btnAlterar.addActionListener(e -> alternarBotoes(btnAlterar, btnConfirmarAlterar));
        btnConfirmarAlterar.addActionListener(e -> {
            alterarProfessor();
            alternarBotoes(btnConfirmarAlterar, btnAlterar);
        });

        btnExcluir.addActionListener(e -> alternarBotoes(btnExcluir, btnConfirmarExcluir));
        btnConfirmarExcluir.addActionListener(e -> {
            excluirProfessor();
            alternarBotoes(btnConfirmarExcluir, btnExcluir);
        });

        btnLimpar.addActionListener(this);
        btnSair.addActionListener(e -> dispose());

        tfID.addActionListener(e -> buscarProfessorPorID());

        getRootPane().setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(160, 100, 255), 2),
                "Professor", TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16)
        ));
    }

    private JTextField criarCampo(JPanel painel, GridBagConstraints gbc, int linha, String label) {
        JLabel lb = new JLabel(label);
        lb.setFont(new Font("Arial", Font.BOLD, 17));
        gbc.gridx = 0;
        gbc.gridy = linha;
        gbc.weightx = 0.4;
        painel.add(lb, gbc);

        JTextField tf = new JTextField();
        tf.setFont(new Font("Arial", Font.PLAIN, 16));
        tf.setPreferredSize(new Dimension(350, 35));
        gbc.gridx = 1;
        gbc.gridy = linha;
        gbc.weightx = 0.6;
        painel.add(tf, gbc);
        return tf;
    }

    private JPanel criarPainelTitled(String titulo) {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 10));
        painel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.ORANGE, 2), titulo,
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16)
        ));
        painel.setOpaque(false);
        return painel;
    }

    private JCheckBox criarCheckBox(String texto, JPanel painel) {
        JCheckBox cb = new JCheckBox(texto);
        cb.setFont(new Font("Arial", Font.PLAIN, 16));
        cb.setOpaque(false);
        painel.add(cb);
        return cb;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 17));
        btn.setPreferredSize(new Dimension(230, 55));
        btn.setBackground(cor);
        btn.setFocusPainted(false);
        btn.setForeground(Color.BLACK);

        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100, 80), 1, true),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(cor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(cor);
            }
        });

        return btn;
    }

    private void alternarBotoes(JButton ocultar, JButton mostrar) {
        ocultar.setVisible(false);
        mostrar.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLimpar) limparCampos();
    }

    private void limparCampos() {
        tfID.setText(""); tfNome.setText(""); tfRua.setText(""); tfNumero.setText("");
        tfBairro.setText(""); tfCidade.setText(""); tfEstado.setText(""); tfTel.setText("");
        tfCel.setText(""); tfData.setText("");
        cbDireito.setSelected(false); cbInformatica.setSelected(false);
        cbMatematica.setSelected(false); cbMedicina.setSelected(false);
        cbBacharel.setSelected(false); cbEsp.setSelected(false);
        cbMestrado.setSelected(false); cbDoutorado.setSelected(false);
    }

    private void buscarProfessorPorID() {
        if (tfID.getText().trim().isEmpty()) return;

        String sql = "SELECT * FROM Professor WHERE id_prof = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, Integer.parseInt(tfID.getText()));
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                tfNome.setText(rs.getString("nome"));
                tfRua.setText(rs.getString("rua_av"));
                tfNumero.setText(rs.getString("numero"));
                tfBairro.setText(rs.getString("bairro"));
                tfCidade.setText(rs.getString("cidade"));
                tfEstado.setText(rs.getString("estado"));
                tfTel.setText(rs.getString("tel_fixo"));
                tfCel.setText(rs.getString("cel"));
                tfData.setText(rs.getString("data_nasc"));

                marcarCheckBoxes(rs.getString("especializacao"), "esp");
                marcarCheckBoxes(rs.getString("titulacao"), "tit");

            } else {
                JOptionPane.showMessageDialog(this, "Professor não encontrado!");
                limparCampos();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar: " + ex.getMessage());
        }
    }

    private void marcarCheckBoxes(String texto, String tipo) {
        if (texto == null) return;
        if (tipo.equals("esp")) {
            cbDireito.setSelected(texto.contains("Direito"));
            cbInformatica.setSelected(texto.contains("Informática"));
            cbMatematica.setSelected(texto.contains("Matemática"));
            cbMedicina.setSelected(texto.contains("Medicina"));
        } else {
            cbBacharel.setSelected(texto.contains("Bacharel"));
            cbEsp.setSelected(texto.contains("Especialista"));
            cbMestrado.setSelected(texto.contains("Mestrado"));
            cbDoutorado.setSelected(texto.contains("Doutorado"));
        }
    }

    private void alterarProfessor() {
        String sqlUpdate = """
            UPDATE Professor 
            SET nome=?, rua_av=?, numero=?, bairro=?, cidade=?, estado=?, 
                tel_fixo=?, cel=?, data_nasc=?, especializacao=?, titulacao=? 
            WHERE id_prof=?;
        """;

        Map<String, List<String>> mapaCursoPorEspecializacao = new HashMap<>();
        mapaCursoPorEspecializacao.put("Direito", List.of("Direito", "Administração de Empresas"));
        mapaCursoPorEspecializacao.put("Informática", List.of("Ciências da Computação", "Rede de Computadores", "Sistemas de Informações"));
        mapaCursoPorEspecializacao.put("Matemática", List.of("Ciências Biológicas", "Sistemas de Informações", "Administração de Empresas"));
        mapaCursoPorEspecializacao.put("Medicina", List.of("Biomedicina", "Farmacologia", "Educação Física"));

        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstUpdate = conn.prepareStatement(sqlUpdate)) {

            conn.setAutoCommit(false); // Inicia transação

            String especializacao = "";
            if (cbDireito.isSelected()) especializacao += "Direito, ";
            if (cbInformatica.isSelected()) especializacao += "Informática, ";
            if (cbMatematica.isSelected()) especializacao += "Matemática, ";
            if (cbMedicina.isSelected()) especializacao += "Medicina, ";
            if (!especializacao.isEmpty()) especializacao = especializacao.substring(0, especializacao.length() - 2);

            String titulacao = "";
            if (cbBacharel.isSelected()) titulacao += "Bacharel, ";
            if (cbEsp.isSelected()) titulacao += "Especialista Lato Sensu, ";
            if (cbMestrado.isSelected()) titulacao += "Mestrado, ";
            if (cbDoutorado.isSelected()) titulacao += "Doutorado, ";
            if (!titulacao.isEmpty()) titulacao = titulacao.substring(0, titulacao.length() - 2);

            pstUpdate.setString(1, tfNome.getText());
            pstUpdate.setString(2, tfRua.getText());
            pstUpdate.setString(3, tfNumero.getText());
            pstUpdate.setString(4, tfBairro.getText());
            pstUpdate.setString(5, tfCidade.getText());
            pstUpdate.setString(6, tfEstado.getText());
            pstUpdate.setString(7, tfTel.getText());
            pstUpdate.setString(8, tfCel.getText());
            pstUpdate.setString(9, tfData.getText());
            pstUpdate.setString(10, especializacao);
            pstUpdate.setString(11, titulacao);
            pstUpdate.setInt(12, Integer.parseInt(tfID.getText()));

            int linhas = pstUpdate.executeUpdate();

            // ===== Remove vínculos antigos =====
            String sqlDelete = "DELETE FROM curso_professor WHERE id_prof = ?";
            try (PreparedStatement pstDelete = conn.prepareStatement(sqlDelete)) {
                pstDelete.setInt(1, Integer.parseInt(tfID.getText()));
                pstDelete.executeUpdate();
            }

            // ===== Cria novos vínculos conforme especialização marcada =====
            String sqlBuscaCurso = "SELECT cod_curso FROM curso WHERE nome_curso = ?";
            String sqlInsereVinculo = "INSERT INTO curso_professor (cod_curso, id_prof) VALUES (?, ?)";

            try (PreparedStatement pstBusca = conn.prepareStatement(sqlBuscaCurso);
                 PreparedStatement pstInsert = conn.prepareStatement(sqlInsereVinculo)) {

                int idProf = Integer.parseInt(tfID.getText());

                for (String especializacaoKey : mapaCursoPorEspecializacao.keySet()) {
                    boolean marcada = false;

                    if (especializacaoKey.equals("Direito") && cbDireito.isSelected()) marcada = true;
                    if (especializacaoKey.equals("Informática") && cbInformatica.isSelected()) marcada = true;
                    if (especializacaoKey.equals("Matemática") && cbMatematica.isSelected()) marcada = true;
                    if (especializacaoKey.equals("Medicina") && cbMedicina.isSelected()) marcada = true;

                    if (marcada) {
                        for (String nomeCurso : mapaCursoPorEspecializacao.get(especializacaoKey)) {
                            pstBusca.setString(1, nomeCurso);
                            try (ResultSet rs = pstBusca.executeQuery()) {
                                if (rs.next()) {
                                    pstInsert.setInt(1, rs.getInt("cod_curso"));
                                    pstInsert.setInt(2, idProf);
                                    pstInsert.executeUpdate();
                                }
                            }
                        }
                    }
                }
            }

            conn.commit(); // Confirma transação
            
            if (linhas > 0) {
                JOptionPane.showMessageDialog(this, "✅ Professor alterado e associações atualizadas com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Professor não encontrado.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar: " + ex.getMessage());
        }
    }

    private void excluirProfessor() {
        if (tfID.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite a identificação do professor!");
            return;
        }

        try {
            int idProf = Integer.parseInt(tfID.getText().trim());

            // Tabelas associativas que possuem chaves estrangeiras apontando para id_prof
            Map<String, String> tabelasVinculadas = new HashMap<>();
            tabelasVinculadas.put("curso_professor", "Cursos");
            tabelasVinculadas.put("professor_disciplina", "Disciplinas");

            StringBuilder aviso = new StringBuilder();
            try (Connection conn = Conexao.getConnection()) {
                boolean possuiVinculos = false;
                
                for (String tabela : tabelasVinculadas.keySet()) {
                    String sqlCheck = "SELECT COUNT(*) FROM " + tabela + " WHERE id_prof=?";
                    try (PreparedStatement pst = conn.prepareStatement(sqlCheck)) {
                        pst.setInt(1, idProf);
                        try (ResultSet rs = pst.executeQuery()) {
                            if (rs.next() && rs.getInt(1) > 0) {
                                possuiVinculos = true;
                                aviso.append("✅ Vínculo encontrado em ").append(tabelasVinculadas.get(tabela)).append("\n");
                            }
                        }
                    }
                }

                if (possuiVinculos) {
                    int opcao = JOptionPane.showOptionDialog(
                            this,
                            "O professor possui vínculos:\n" + aviso + 
                            "\nClique em OK para apagar os vínculos e excluir o professor.",
                            "Aviso de Exclusão",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE,
                            null,
                            new Object[]{"OK", "Cancelar"},
                            "OK"
                    );

                    if (opcao != 0) {
                        return; // Cancela operação se não clicar em OK
                    }

                    // Apaga vínculos das tabelas associativas primeiro
                    for (String tabela : tabelasVinculadas.keySet()) {
                        String sqlDel = "DELETE FROM " + tabela + " WHERE id_prof=?";
                        try (PreparedStatement pst = conn.prepareStatement(sqlDel)) {
                            pst.setInt(1, idProf);
                            pst.executeUpdate();
                        }
                    }
                }

                // Exclui o professor definitivamente da tabela principal
                String sqlDelProf = "DELETE FROM Professor WHERE id_prof=?";
                try (PreparedStatement pst = conn.prepareStatement(sqlDelProf)) {
                    pst.setInt(1, idProf);
                    int r = pst.executeUpdate();
                    if (r > 0) {
                        JOptionPane.showMessageDialog(this, "✅ Registro excluído com sucesso!");
                        limparCampos();
                    } else {
                        JOptionPane.showMessageDialog(this, "⚠️ Nenhum registro encontrado com este ID.");
                    }
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ O ID deve ser um número válido!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new professorae().setVisible(true);
        });
    }
}

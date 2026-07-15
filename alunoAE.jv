package projeto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class alunoae extends JFrame {

    JTextField tfMatricula, tfNome, tfDataNasc, tfCodCurso, tfNota1, tfNota2, tfMedia, tfFaltas;
    JList<String> listDisciplinas;
    JButton btnAlterar, btnExcluir, btnLimpar, btnSair;
    JButton btnConfirmarAlterar, btnConfirmarExcluir;

    public alunoae() {
        setTitle("Alterar / Excluir Aluno");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ==== Painel com degradê ====
        JPanel painelFundo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                Color cor1 = new Color(200, 255, 230);
                Color cor2 = new Color(245, 255, 245);
                g2.setPaint(new GradientPaint(0, 0, cor1, 0, getHeight(), cor2));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        painelFundo.setLayout(null);
        setContentPane(painelFundo);

        Font fonteLabel = new Font("Arial", Font.BOLD, 16);
        Font fonteCampo = new Font("Arial", Font.PLAIN, 15);
        Font fonteBotao = new Font("Arial", Font.BOLD, 15);

        JLabel lbTitulo = new JLabel("Alterar / Excluir Aluno");
        lbTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lbTitulo.setBounds(350, 20, 400, 40);
        painelFundo.add(lbTitulo);

        int baseX = 220, baseY = 90, espacamento = 50, larguraLabel = 180, altura = 35, larguraCampo = 300;

        // ===== Campos básicos =====
        JLabel lbMat = new JLabel("Matrícula:");
        lbMat.setFont(fonteLabel);
        lbMat.setBounds(baseX, baseY, larguraLabel, altura);
        painelFundo.add(lbMat);

        tfMatricula = new JTextField();
        tfMatricula.setFont(fonteCampo);
        tfMatricula.setBounds(baseX + larguraLabel, baseY, larguraCampo, altura);
        painelFundo.add(tfMatricula);

        tfMatricula.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buscarAlunoPorMatricula();
                }
            }
        });

        JLabel lbNome = new JLabel("Nome:");
        lbNome.setFont(fonteLabel);
        lbNome.setBounds(baseX, baseY + espacamento, larguraLabel, altura);
        painelFundo.add(lbNome);

        tfNome = new JTextField();
        tfNome.setFont(fonteCampo);
        tfNome.setBounds(baseX + larguraLabel, baseY + espacamento, larguraCampo, altura);
        painelFundo.add(tfNome);

        JLabel lbData = new JLabel("Data Nasc (AAAA-MM-DD):");
        lbData.setFont(fonteLabel);
        lbData.setBounds(baseX, baseY + espacamento * 2, larguraLabel + 80, altura);
        painelFundo.add(lbData);

        tfDataNasc = new JTextField();
        tfDataNasc.setFont(fonteCampo);
        tfDataNasc.setBounds(baseX + larguraLabel + 80, baseY + espacamento * 2, 180, altura);
        painelFundo.add(tfDataNasc);

        JLabel lbCodCurso = new JLabel("Código Curso:");
        lbCodCurso.setFont(fonteLabel);
        lbCodCurso.setBounds(baseX, baseY + espacamento * 3, larguraLabel, altura);
        painelFundo.add(lbCodCurso);

        tfCodCurso = new JTextField();
        tfCodCurso.setFont(fonteCampo);
        tfCodCurso.setBounds(baseX + larguraLabel, baseY + espacamento * 3, larguraCampo, altura);
        painelFundo.add(tfCodCurso);

        JLabel lbDisc = new JLabel("Disciplinas:");
        lbDisc.setFont(fonteLabel);
        lbDisc.setBounds(baseX, baseY + espacamento * 4, larguraLabel, altura);
        painelFundo.add(lbDisc);

        // ===== Carregar disciplinas =====
        DefaultListModel<String> modelo = new DefaultListModel<>();
        try (Connection conn = Conexao.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT cod_disc, nome_disc FROM Disciplina")) {
            while (rs.next()) {
                modelo.addElement(rs.getInt("cod_disc") + " - " + rs.getString("nome_disc"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar disciplinas: " + ex.getMessage());
        }

        listDisciplinas = new JList<>(modelo);
        listDisciplinas.setFont(fonteCampo);
        listDisciplinas.setVisibleRowCount(5);
        listDisciplinas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane spDisc = new JScrollPane(listDisciplinas);
        spDisc.setBounds(baseX + larguraLabel, baseY + espacamento * 4, larguraCampo, 110);
        painelFundo.add(spDisc);

        // ===== Notas e Faltas =====
        int baseYNotas = baseY + espacamento * 6 + 10;

        String[] labels = {"Nota NPA1:", "Nota NPA2:", "Média:", "Faltas:"};
        int[] posXLabels = {baseX, baseX + 220, baseX + 410, baseX + 580};
        int[] posXCampos = {baseX + 130, baseX + 320, baseX + 480, baseX + 650};
        int larguraCampo1 = 70;

        for (int i = 0; i < labels.length; i++) {
            JLabel lb = new JLabel(labels[i]);
            lb.setFont(fonteLabel);
            lb.setBounds(posXLabels[i], baseYNotas, (i < 2 ? 120 : 80), altura);
            painelFundo.add(lb);

            JTextField tf = new JTextField();
            tf.setFont(fonteCampo);
            tf.setBounds(posXCampos[i], baseYNotas, larguraCampo1, altura);
            painelFundo.add(tf);

            switch (i) {
                case 0 -> tfNota1 = tf;
                case 1 -> tfNota2 = tf;
                case 2 -> tfMedia  = tf;
                case 3 -> tfFaltas = tf;
            }
        }

        // ===== Botões =====
        int yBtn = baseY + espacamento * 9;
        int larguraBotao = 140, alturaBotao = 40, espaco = 40;

        btnAlterar = criarBotao("✏️ Alterar", new Color(100, 180, 255), fonteBotao);
        btnAlterar.setBounds(220, yBtn, larguraBotao, alturaBotao);
        painelFundo.add(btnAlterar);

        btnExcluir = criarBotao("🗑️ Excluir", new Color(255, 120, 120), fonteBotao);
        btnExcluir.setBounds(220 + larguraBotao + espaco, yBtn, larguraBotao, alturaBotao);
        painelFundo.add(btnExcluir);

        btnLimpar = criarBotao("🧹 Limpar", new Color(255, 220, 120), fonteBotao);
        btnLimpar.setBounds(220 + (larguraBotao + espaco) * 2, yBtn, larguraBotao, alturaBotao);
        painelFundo.add(btnLimpar);

        btnSair = criarBotao("❌ Sair", new Color(200, 100, 100), fonteBotao);
        btnSair.setBounds(220 + (larguraBotao + espaco) * 3, yBtn, larguraBotao, alturaBotao);
        btnSair.addActionListener(e -> dispose());
        painelFundo.add(btnSair);

        // ===== Botões de confirmação sobrepostos =====
        btnConfirmarAlterar = criarBotao("✅ Confirmar", new Color(200, 255, 200), fonteBotao);
        btnConfirmarAlterar.setBounds(btnAlterar.getBounds());
        btnConfirmarAlterar.setVisible(false);
        btnConfirmarAlterar.addActionListener(e -> {
            alterarAluno();
            btnConfirmarAlterar.setVisible(false);
            btnAlterar.setVisible(true);
        });
        painelFundo.add(btnConfirmarAlterar);

        btnConfirmarExcluir = criarBotao("❌ Confirmar", new Color(255, 200, 200), fonteBotao);
        btnConfirmarExcluir.setBounds(btnExcluir.getBounds());
        btnConfirmarExcluir.setVisible(false);
        btnConfirmarExcluir.addActionListener(e -> {
            excluirAluno();
            btnConfirmarExcluir.setVisible(false);
            btnExcluir.setVisible(true);
        });
        painelFundo.add(btnConfirmarExcluir);

        // ===== Eventos principais =====
        btnAlterar.addActionListener(e -> {
            btnAlterar.setVisible(false);
            btnConfirmarAlterar.setVisible(true);
        });

        btnExcluir.addActionListener(e -> {
            btnExcluir.setVisible(false);
            btnConfirmarExcluir.setVisible(true);
        });

        btnLimpar.addActionListener(e -> limparCampos());

        getRootPane().setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GREEN, 2),
                "Aluno", TitledBorder.CENTER, TitledBorder.TOP));
    }

    private JButton criarBotao(String texto, Color cor, Font fonte) {
        JButton btn = new JButton(texto);
        btn.setFont(fonte);
        btn.setBackground(cor);
        btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { btn.setBackground(cor.brighter()); }
            @Override
            public void mouseExited(MouseEvent e) { btn.setBackground(cor); }
        });
        return btn;
    }

    // ===== Buscar aluno =====
    private void buscarAlunoPorMatricula() {
        try {
            int matricula = Integer.parseInt(tfMatricula.getText().trim());
            String sql = "SELECT * FROM Aluno WHERE matricula=?";

            try (Connection conn = Conexao.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {

                pst.setInt(1, matricula);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    tfNome.setText(rs.getString("nome"));
                    tfDataNasc.setText(rs.getDate("data_nasc").toString());
                    tfCodCurso.setText(String.valueOf(rs.getInt("cod_curso")));
                    tfNota1.setText(String.valueOf(rs.getDouble("np1")));
                    tfNota2.setText(String.valueOf(rs.getDouble("np2")));
                    tfMedia.setText(String.valueOf(rs.getDouble("media")));
                    tfFaltas.setText(String.valueOf(rs.getInt("faltas")));
                    piscarCampo(tfMatricula);
                    carregarDisciplinasAluno(matricula);
                } else {
                    JOptionPane.showMessageDialog(this, "⚠️ Aluno não encontrado!");
                    limparCampos();
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Matrícula inválida!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ Erro: " + ex.getMessage());
        }
    }

    // ===== Carregar disciplinas do aluno =====
    private void carregarDisciplinasAluno(int matricula) {
        try (Connection conn = Conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(
                     "SELECT cod_disc FROM Aluno_Disciplina WHERE matricula=?")) {

            pst.setInt(1, matricula);
            ResultSet rs = pst.executeQuery();

            List<Integer> sel = new ArrayList<>();
            DefaultListModel<String> modelo = (DefaultListModel<String>) listDisciplinas.getModel();

            while (rs.next()) {
                int cod = rs.getInt("cod_disc");
                for (int i = 0; i < modelo.getSize(); i++) {
                    if (modelo.get(i).startsWith(cod + " - ")) sel.add(i);
                }
            }

            int[] selArr = sel.stream().mapToInt(i -> i).toArray();
            listDisciplinas.setSelectedIndices(selArr);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar disciplinas do aluno: " + ex.getMessage());
        }
    }

    // ===== Alterar aluno e disciplinas =====
    private void alterarAluno() {
        try {
            int matricula = Integer.parseInt(tfMatricula.getText().trim());
            String sql = "UPDATE Aluno SET nome=?, data_nasc=?, cod_curso=?, np1=?, np2=?, media=?, faltas=? WHERE matricula=?";

            try (Connection conn = Conexao.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {

                pst.setString(1, tfNome.getText());
                pst.setDate(2, Date.valueOf(tfDataNasc.getText()));
                pst.setInt(3, Integer.parseInt(tfCodCurso.getText()));
                pst.setDouble(4, Double.parseDouble(tfNota1.getText()));
                pst.setDouble(5, Double.parseDouble(tfNota2.getText()));
                pst.setDouble(6, Double.parseDouble(tfMedia.getText()));
                pst.setInt(7, Integer.parseInt(tfFaltas.getText()));
                pst.setInt(8, matricula);

                int r = pst.executeUpdate();
                if (r > 0) {
                    atualizarDisciplinasAluno(matricula);
                    JOptionPane.showMessageDialog(this, "✅ Dados e disciplinas alterados com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(this, "⚠️ Aluno não encontrado!");
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Erro ao alterar: " + ex.getMessage());
        }
    }

    // ===== Atualizar disciplinas do aluno =====
    private void atualizarDisciplinasAluno(int matricula) throws SQLException {
        List<String> selecionadas = listDisciplinas.getSelectedValuesList();

        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement del = conn.prepareStatement(
                    "DELETE FROM Aluno_Disciplina WHERE matricula=?")) {
                del.setInt(1, matricula);
                del.executeUpdate();
            }

            String sqlInsert = "INSERT INTO Aluno_Disciplina (matricula, cod_disc) VALUES (?, ?)";
            try (PreparedStatement ins = conn.prepareStatement(sqlInsert)) {
                for (String s : selecionadas) {
                    int codDisc = Integer.parseInt(s.split(" - ")[0]);
                    ins.setInt(1, matricula);
                    ins.setInt(2, codDisc);
                    ins.addBatch();
                }
                ins.executeBatch();
            }

            conn.commit();
        }
    }

    // ===== Excluir aluno =====
    private void excluirAluno() {
        try {
            if (tfMatricula.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite a matrícula do aluno!");
                return;
            }
            int matricula = Integer.parseInt(tfMatricula.getText().trim());

            try (Connection conn = Conexao.getConnection()) {

                // ===== Verifica vínculos =====
                String checkSql = "SELECT COUNT(*) FROM Aluno_Disciplina WHERE matricula=?";
                int totalVinculos = 0;
                try (PreparedStatement check = conn.prepareStatement(checkSql)) {
                    check.setInt(1, matricula);
                    ResultSet rs = check.executeQuery();
                    if (rs.next()) totalVinculos = rs.getInt(1);
                }

                String msg = totalVinculos > 0
                        ? "Este aluno está vinculado a " + totalVinculos + " disciplina(s)."
                          + "\nClique em Confirmar para excluir o aluno e todos os vínculos."
                        : "Deseja realmente excluir este aluno?";

                int op = JOptionPane.showOptionDialog(
                        this,
                        msg,
                        "Confirmação de Exclusão",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        new Object[]{"Confirmar", "Cancelar"},
                        "Confirmar"
                );

                if (op != 0) return; // Se não for clicado o botão "Confirmar"

                conn.setAutoCommit(false); // inicia transação

                // ===== Excluir vínculos =====
                try (PreparedStatement delVinc = conn.prepareStatement(
                        "DELETE FROM Aluno_Disciplina WHERE matricula=?")) {
                    delVinc.setInt(1, matricula);
                    delVinc.executeUpdate();
                }

                // ===== Excluir aluno =====
                try (PreparedStatement delAluno = conn.prepareStatement(
                        "DELETE FROM Aluno WHERE matricula=?")) {
                    delAluno.setInt(1, matricula);
                    int r = delAluno.executeUpdate();
                    conn.commit();

                    if (r > 0) {
                        JOptionPane.showMessageDialog(this, "✅ Aluno e vínculos excluídos com sucesso!");
                        limparCampos();
                    } else {
                        JOptionPane.showMessageDialog(this, "⚠️ Nenhum aluno encontrado com essa matrícula!");
                    }
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Matrícula deve ser numérica!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ Erro ao excluir aluno: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        tfMatricula.setText("");
        tfNome.setText("");
        tfDataNasc.setText("");
        tfCodCurso.setText("");
        tfNota1.setText("");
        tfNota2.setText("");
        tfMedia.setText("");
        tfFaltas.setText("");
        listDisciplinas.clearSelection();
    }

    private void piscarCampo(final JTextField campo) {
        final Color original = campo.getBackground();
        final Color destaque = new Color(255, 255, 180);
        
        // Define o Timer para piscar a cada 250ms com um listener associado
        final int[] count = {0};
        Timer timer = new Timer(250, null);
        timer.addActionListener(e -> {
            campo.setBackground(count[0] % 2 == 0 ? destaque : original);
            count[0]++;
            if (count[0] >= 6) {
                campo.setBackground(original); // Garante a volta à cor padrão
                timer.stop();
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new alunoae().setVisible(true);
        });
    }
}

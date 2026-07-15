package projeto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class aluno extends JFrame implements ActionListener {

    private JTextField tfMatricula, tfNome, tfDataNasc, tfCodCurso, tfNota1, tfNota2, tfMedia, tfFaltas;
    private JList<String> listDisciplinas;
    private JButton btnSalvar, btnLimpar, btnSair;

    public aluno() {
        setTitle("Cadastro de Aluno");
        setSize(850, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ===== Fundo com Degradê Claro Suave =====
        JPanel painelFundo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color cor1 = new Color(224, 242, 254); // Azul bebê suave
                Color cor2 = new Color(255, 255, 255); // Branco puro
                g2.setPaint(new GradientPaint(0, 0, cor1, 0, getHeight(), cor2));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        painelFundo.setLayout(new GridBagLayout());
        setContentPane(painelFundo);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 12, 6, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fonteLabel = new Font("Segoe UI Semibold", Font.PLAIN, 15);
        Font fonteCampo = new Font("Segoe UI", Font.PLAIN, 14);

        // ===== Título =====
        JLabel lbTitulo = new JLabel("🎒 Cadastro de Aluno", SwingConstants.CENTER);
        lbTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lbTitulo.setForeground(new Color(15, 23, 42));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4;
        gbc.insets = new Insets(15, 12, 15, 12);
        painelFundo.add(lbTitulo, gbc);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(6, 12, 6, 12);

        // ===== Matrícula =====
        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.25;
        JLabel lbMat = new JLabel("Matrícula:");
        lbMat.setFont(fonteLabel);
        lbMat.setForeground(new Color(51, 65, 85));
        painelFundo.add(lbMat, gbc);

        tfMatricula = criarTextField(fonteCampo);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 0.75;
        painelFundo.add(tfMatricula, gbc);
        gbc.gridwidth = 1;

        // ===== Nome =====
        gbc.gridy++; gbc.gridx = 0;
        JLabel lbNome = new JLabel("Nome Completo:");
        lbNome.setFont(fonteLabel);
        lbNome.setForeground(new Color(51, 65, 85));
        painelFundo.add(lbNome, gbc);

        tfNome = criarTextField(fonteCampo);
        gbc.gridx = 1; gbc.gridwidth = 3;
        painelFundo.add(tfNome, gbc);
        gbc.gridwidth = 1;

        // ===== Data Nascimento e Código do Curso =====
        gbc.gridy++; gbc.gridx = 0;
        JLabel lbData = new JLabel("Nascimento (AAAA-MM-DD):");
        lbData.setFont(fonteLabel);
        lbData.setForeground(new Color(51, 65, 85));
        painelFundo.add(lbData, gbc);

        tfDataNasc = criarTextField(fonteCampo);
        gbc.gridx = 1;
        painelFundo.add(tfDataNasc, gbc);

        gbc.gridx = 2;
        JLabel lbCodCurso = new JLabel("Código do Curso:");
        lbCodCurso.setFont(fonteLabel);
        lbCodCurso.setForeground(new Color(51, 65, 85));
        painelFundo.add(lbCodCurso, gbc);

        tfCodCurso = criarTextField(fonteCampo);
        gbc.gridx = 3;
        painelFundo.add(tfCodCurso, gbc);

        // ===== Disciplinas =====
        gbc.gridy++; gbc.gridx = 0;
        JLabel lbDisc = new JLabel("Disciplinas:");
        lbDisc.setFont(fonteLabel);
        lbDisc.setForeground(new Color(51, 65, 85));
        painelFundo.add(lbDisc, gbc);

        DefaultListModel<String> modelo = new DefaultListModel<>();
        try (Connection conn = Conexao.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT cod_disc, nome_disc FROM Disciplina")) {
            while (rs.next()) {
                modelo.addElement(rs.getInt("cod_disc") + " - " + rs.getString("nome_disc"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Erro ao carregar disciplinas: " + ex.getMessage());
        }

        listDisciplinas = new JList<>(modelo);
        listDisciplinas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listDisciplinas.setFont(fonteCampo);
        JScrollPane spDisc = new JScrollPane(listDisciplinas);
        spDisc.setPreferredSize(new Dimension(100, 90));
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.BOTH;
        painelFundo.add(spDisc, gbc);
        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== Notas e Faltas (Agrupados) =====
        gbc.gridy++; gbc.gridx = 0;
        JLabel lbNota1 = new JLabel("Nota NPA:");
        lbNota1.setFont(fonteLabel);
        lbNota1.setForeground(new Color(51, 65, 85));
        painelFundo.add(lbNota1, gbc);

        tfNota1 = criarTextField(fonteCampo);
        gbc.gridx = 1;
        painelFundo.add(tfNota1, gbc);

        gbc.gridx = 2;
        JLabel lbNota2 = new JLabel("Nota NPL:");
        lbNota2.setFont(fonteLabel);
        lbNota2.setForeground(new Color(51, 65, 85));
        painelFundo.add(lbNota2, gbc);

        tfNota2 = criarTextField(fonteCampo);
        gbc.gridx = 3;
        painelFundo.add(tfNota2, gbc);

        gbc.gridy++; gbc.gridx = 0;
        JLabel lbMedia = new JLabel("Média Final:");
        lbMedia.setFont(fonteLabel);
        lbMedia.setForeground(new Color(51, 65, 85));
        painelFundo.add(lbMedia, gbc);

        tfMedia = criarTextField(fonteCampo);
        gbc.gridx = 1;
        painelFundo.add(tfMedia, gbc);

        gbc.gridx = 2;
        JLabel lbFaltas = new JLabel("Faltas:");
        lbFaltas.setFont(fonteLabel);
        lbFaltas.setForeground(new Color(51, 65, 85));
        painelFundo.add(lbFaltas, gbc);

        tfFaltas = criarTextField(fonteCampo);
        gbc.gridx = 3;
        painelFundo.add(tfFaltas, gbc);

        // ===== Painel de Botões =====
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        painelBotoes.setOpaque(false);

        btnSalvar = criarBotao("💾 Salvar", new Color(134, 239, 172));
        btnLimpar = criarBotao("🧹 Limpar", new Color(241, 245, 249));
        btnSair = criarBotao("❌ Sair", new Color(254, 202, 202));

        btnSalvar.addActionListener(this);
        btnLimpar.addActionListener(this);
        btnSair.addActionListener(e -> dispose());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnSair);

        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 4;
        gbc.insets = new Insets(20, 12, 10, 12);
        painelFundo.add(painelBotoes, gbc);
    }

    private JTextField criarTextField(Font fonte) {
        JTextField tf = new JTextField();
        tf.setFont(fonte);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return tf;
    }

    private JButton criarBotao(String texto, Color corFundo) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        btn.setPreferredSize(new Dimension(135, 40));
        btn.setForeground(Color.BLACK);
        btn.setBackground(corFundo);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(corFundo.darker(), 1));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { btn.setBackground(corFundo.darker()); }
            @Override
            public void mouseExited(MouseEvent e) { btn.setBackground(corFundo); }
        });
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSalvar) salvarAluno();
        else if (e.getSource() == btnLimpar) limparCampos();
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

    private void salvarAluno() {
        if (tfMatricula.getText().trim().isEmpty() || listDisciplinas.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(this, "⚠️ Matrícula e Disciplina são obrigatórias!");
            return;
        }

        int matricula, codCurso, codDisc, faltas;
        double nota1, nota2, media;
        java.sql.Date dataNascSql;

        try {
            matricula = Integer.parseInt(tfMatricula.getText().trim());
            codCurso = Integer.parseInt(tfCodCurso.getText().trim());
            nota1 = Double.parseDouble(tfNota1.getText().trim());
            nota2 = Double.parseDouble(tfNota2.getText().trim());
            media = Double.parseDouble(tfMedia.getText().trim());
            faltas = Integer.parseInt(tfFaltas.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Verifique as informações numéricas!");
            return;
        }

        String disciplinaSelecionada = listDisciplinas.getSelectedValue();
        codDisc = Integer.parseInt(disciplinaSelecionada.split(" - ")[0]);

        try {
            java.util.Date data = new java.text.SimpleDateFormat("yyyy-MM-dd")
                    .parse(tfDataNasc.getText().trim());
            dataNascSql = new java.sql.Date(data.getTime());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Formato de data inválido! Use AAAA-MM-DD.");
            return;
        }

        Connection conn = null;
        try {
            conn = Conexao.getConnection();
            conn.setAutoCommit(false);

            String sqlAluno = "INSERT INTO Aluno (matricula, nome, data_nasc, cod_curso, np1, np2, media, faltas) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE nome=?, data_nasc=?, cod_curso=?, np1=?, np2=?, media=?, faltas=?";
            try (PreparedStatement pst = conn.prepareStatement(sqlAluno)) {
                pst.setInt(1, matricula);
                pst.setString(2, tfNome.getText().trim());
                pst.setDate(3, dataNascSql);
                pst.setInt(4, codCurso);
                pst.setDouble(5, nota1);
                pst.setDouble(6, nota2);
                pst.setDouble(7, media);
                pst.setInt(8, faltas);
                
                pst.setString(9, tfNome.getText().trim());
                pst.setDate(10, dataNascSql);
                pst.setInt(11, codCurso);
                pst.setDouble(12, nota1);
                pst.setDouble(13, nota2);
                pst.setDouble(14, media);
                pst.setInt(15, faltas);
                pst.executeUpdate();
            }

            String sqlDisc = "INSERT INTO Aluno_Disciplina (matricula, cod_disc, np1, np2, media, faltas) " +
                    "VALUES (?, ?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE np1=?, np2=?, media=?, faltas=?";
            try (PreparedStatement pstDisc = conn.prepareStatement(sqlDisc)) {
                pstDisc.setInt(1, matricula);
                pstDisc.setInt(2, codDisc);
                pstDisc.setDouble(3, nota1);
                pstDisc.setDouble(4, nota2);
                pstDisc.setDouble(5, media);
                pstDisc.setInt(6, faltas);
                
                pstDisc.setDouble(7, nota1);
                pstDisc.setDouble(8, nota2);
                pstDisc.setDouble(9, media);
                pstDisc.setInt(10, faltas);
                pstDisc.executeUpdate();
            }

            conn.commit();
            JOptionPane.showMessageDialog(this, "✅ Aluno e notas salvos com sucesso!");
            limparCampos();

        } catch (SQLException ex) {
            ex.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (SQLException r) { r.printStackTrace(); }
            JOptionPane.showMessageDialog(this, "Erro ao salvar no banco.");
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (SQLException ignored) {}
        }
    }

    public static void main(String[] args) {
        new aluno().setVisible(true);
    }
}

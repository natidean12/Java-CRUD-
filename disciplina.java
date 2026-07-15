package projeto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class disciplina extends JFrame implements ActionListener {

    private JTextField txtCodDisc, txtNome, txtCarga;
    private JRadioButton[] rbAulas;
    private ButtonGroup grupoAulas;
    private JButton btnSalvar, btnLimpar, btnSair;

    public disciplina() {
        setTitle("Cadastro de Disciplina");
        setSize(750, 550);
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
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fonteLabel = new Font("Segoe UI Semibold", Font.PLAIN, 15);
        Font fonteCampo = new Font("Segoe UI", Font.PLAIN, 14);

        // ===== Título =====
        JLabel lbTitulo = new JLabel("📚 Cadastro de Disciplina", SwingConstants.CENTER);
        lbTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lbTitulo.setForeground(new Color(15, 23, 42));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.weightx = 1;
        painelFundo.add(lbTitulo, gbc);

        gbc.gridwidth = 1;

        // ===== Código =====
        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.3;
        JLabel lbCod = new JLabel("Código da Disciplina:");
        lbCod.setFont(fonteLabel);
        lbCod.setForeground(new Color(51, 65, 85));
        painelFundo.add(lbCod, gbc);

        txtCodDisc = criarTextField(fonteCampo);
        gbc.gridx = 1; gbc.weightx = 0.7;
        painelFundo.add(txtCodDisc, gbc);

        // ===== Nome =====
        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.3;
        JLabel lbNome = new JLabel("Nome da Disciplina:");
        lbNome.setFont(fonteLabel);
        lbNome.setForeground(new Color(51, 65, 85));
        painelFundo.add(lbNome, gbc);

        txtNome = criarTextField(fonteCampo);
        gbc.gridx = 1; gbc.weightx = 0.7;
        painelFundo.add(txtNome, gbc);

        // ===== Carga Horária =====
        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.3;
        JLabel lbCarga = new JLabel("Carga Horária:");
        lbCarga.setFont(fonteLabel);
        lbCarga.setForeground(new Color(51, 65, 85));
        painelFundo.add(lbCarga, gbc);

        txtCarga = criarTextField(fonteCampo);
        gbc.gridx = 1; gbc.weightx = 0.7;
        painelFundo.add(txtCarga, gbc);

        // ===== Aulas por Semana =====
        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.3;
        JLabel lbAulas = new JLabel("Aulas por semana:");
        lbAulas.setFont(fonteLabel);
        lbAulas.setForeground(new Color(51, 65, 85));
        painelFundo.add(lbAulas, gbc);

        JPanel painelAulas = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        painelAulas.setOpaque(false);
        grupoAulas = new ButtonGroup();
        rbAulas = new JRadioButton[6];
        for (int i = 0; i < 6; i++) {
            rbAulas[i] = new JRadioButton(String.valueOf(i + 1));
            rbAulas[i].setOpaque(false);
            rbAulas[i].setFont(fonteCampo);
            rbAulas[i].setForeground(new Color(51, 65, 85));
            grupoAulas.add(rbAulas[i]);
            painelAulas.add(rbAulas[i]);
        }
        gbc.gridx = 1; gbc.weightx = 0.7;
        painelFundo.add(painelAulas, gbc);

        // ===== Painel de Botões =====
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
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

        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 15, 10, 15);
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
        btn.setPreferredSize(new Dimension(130, 40));
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
        if (e.getSource() == btnSalvar) cadastrarDisciplina();
        else if (e.getSource() == btnLimpar) limparCampos();
    }

    private void limparCampos() {
        txtCodDisc.setText("");
        txtNome.setText("");
        txtCarga.setText("");
        grupoAulas.clearSelection();
    }

    private void cadastrarDisciplina() {
        int aulasSemana = 0;
        for (int i = 0; i < 6; i++) {
            if (rbAulas[i].isSelected()) {
                aulasSemana = i + 1;
                break;
            }
        }

        if (txtCodDisc.getText().trim().isEmpty() || txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Código e Nome são obrigatórios!");
            return;
        }

        if (aulasSemana == 0) {
            JOptionPane.showMessageDialog(this, "⚠️ Selecione o número de aulas por semana!");
            return;
        }

        try (Connection conn = Conexao.getConnection()) {
            PreparedStatement check = conn.prepareStatement("SELECT COUNT(*) FROM Disciplina WHERE cod_disc=?");
            check.setInt(1, Integer.parseInt(txtCodDisc.getText().trim()));
            ResultSet rs = check.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "❌ Código já existe! Escolha outro.");
                return;
            }

            String sql = "INSERT INTO Disciplina (cod_disc, nome_disc, carga_horaria, aulas_semana) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtCodDisc.getText().trim()));
            pst.setString(2, txtNome.getText().trim());
            pst.setInt(3, Integer.parseInt(txtCarga.getText().trim()));
            pst.setInt(4, aulasSemana);
            pst.executeUpdate();

            String sqlCurso = "SELECT cod_curso FROM Curso ORDER BY cod_curso DESC LIMIT 1";
            Statement st = conn.createStatement();
            ResultSet rsCurso = st.executeQuery(sqlCurso);
            int codCurso;
            if (rsCurso.next()) {
                codCurso = rsCurso.getInt("cod_curso");
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Nenhum curso encontrado para associar!");
                return;
            }

            String sqlAssoc = "INSERT INTO Curso_Disciplina (cod_curso, cod_disc) VALUES (?, ?)";
            PreparedStatement pstAssoc = conn.prepareStatement(sqlAssoc);
            pstAssoc.setInt(1, codCurso);
            pstAssoc.setInt(2, Integer.parseInt(txtCodDisc.getText().trim()));
            pstAssoc.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Disciplina cadastrada e associada automaticamente ao curso!");
            limparCampos();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro no banco: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Verifique os campos numéricos!");
        }
    }

    public static void main(String[] args) {
        new disciplina().setVisible(true);
    }
}

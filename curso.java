package projeto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class curso extends JFrame implements ActionListener {

    JTextField tfCarga;
    JComboBox<String> cbNome;
    JButton btnSalvar, btnLimpar, btnSair;

    public curso() {
        setTitle("Cadastro de Curso");
        setSize(700, 500); // Tamanho mais compacto e elegante para cadastro
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
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fonteLabel = new Font("Segoe UI Semibold", Font.PLAIN, 16);
        Font fonteCampo = new Font("Segoe UI", Font.PLAIN, 15);

        // ===== Título =====
        JLabel lbTitulo = new JLabel("Cadastro de Curso", SwingConstants.CENTER);
        lbTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lbTitulo.setForeground(new Color(15, 23, 42)); // Cinza ardósia escuro
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        painelFundo.add(lbTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.weightx = 0.3;

        // ===== Campo: Nome do Curso =====
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lbNome = new JLabel("Nome do Curso:");
        lbNome.setFont(fonteLabel);
        lbNome.setForeground(new Color(51, 65, 85));
        painelFundo.add(lbNome, gbc);

        cbNome = new JComboBox<>(new String[]{
                "Administração de Empresas", "Biomedicina", "Ciências Biológicas",
                "Ciência da Computação", "Direito", "Educação Física",
                "Farmacologia", "Rede de Computadores", "Sistemas de Informação"
        });
        cbNome.setFont(fonteCampo);
        cbNome.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        painelFundo.add(cbNome, gbc);

        // ===== Campo: Duração (Carga / Semestres) =====
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        JLabel lbCarga = new JLabel("Duração (Semestres):");
        lbCarga.setFont(fonteLabel);
        lbCarga.setForeground(new Color(51, 65, 85));
        painelFundo.add(lbCarga, gbc);

        tfCarga = new JTextField();
        tfCarga.setFont(fonteCampo);
        tfCarga.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        painelFundo.add(tfCarga, gbc);

        // ===== Painel de Botões =====
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        painelBotoes.setOpaque(false);

        btnSalvar = criarBotao("💾 Salvar", new Color(14, 116, 144), Color.WHITE); // Azul corporativo
        btnLimpar = criarBotao("🧹 Limpar", new Color(241, 245, 249), new Color(15, 23, 42)); // Cinza claro neutro
        btnSair = criarBotao("❌ Sair", new Color(239, 68, 68), Color.WHITE); // Vermelho moderno

        Dimension tamanhoBotao = new Dimension(130, 40);
        btnSalvar.setPreferredSize(tamanhoBotao);
        btnLimpar.setPreferredSize(tamanhoBotao);
        btnSair.setPreferredSize(tamanhoBotao);

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnSair);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        painelFundo.add(painelBotoes, gbc);

        btnSalvar.addActionListener(this);
        btnLimpar.addActionListener(this);
        btnSair.addActionListener(e -> dispose());
    }

    // Método para fabricar botões elegantes e modernos
    private JButton criarBotao(String texto, Color corFundo, Color corTexto) {
        JButton btn = new JButton(texto);
        btn.setForeground(corTexto);
        btn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        btn.setBackground(corFundo);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(corFundo.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(corFundo);
            }
        });
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSalvar) salvarCurso();
        else if (e.getSource() == btnLimpar) limparCampos();
    }

    private void limparCampos() {
        tfCarga.setText("");
        cbNome.setSelectedIndex(0);
    }

    private void salvarCurso() {
        // Validação básica de preenchimento
        if (tfCarga.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Por favor, digite a duração do curso!");
            return;
        }

        try {
            int duracao = Integer.parseInt(tfCarga.getText());
            String nomeCurso = cbNome.getSelectedItem().toString();

            try (Connection conn = Conexao.getConnection()) {
                // Query ajustada para bater com a tabela 'curso' (nome, duracao)
                String sql = "INSERT INTO curso (nome, duracao) VALUES (?, ?)";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, nomeCurso);
                pst.setInt(2, duracao);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "✅ Curso cadastrado com sucesso!");
                limparCampos();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "❌ Insira um valor numérico válido para os semestres!");
        } catch (SQLException ex) {
            if (ex.getMessage().contains("Duplicate entry")) {
                JOptionPane.showMessageDialog(this, "❌ Este curso já está cadastrado no sistema!");
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar no banco: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new curso().setVisible(true);
    }
}

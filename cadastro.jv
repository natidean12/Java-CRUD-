package projeto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class cadastrousuario extends JFrame {
    private JTextField tfUsuario;
    private JPasswordField pfSenha;
    private JButton btnCadastrar, btnCancelar;

    public cadastrousuario() {
        setTitle("Cadastro de Usuário");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Fundo com o mesmo degradê moderno da tela de Login
        JPanel painelFundo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color cor1 = new Color(20, 30, 48);
                Color cor2 = new Color(36, 59, 85);
                g2.setPaint(new GradientPaint(0, 0, cor1, 0, getHeight(), cor2));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        painelFundo.setLayout(new GridBagLayout()); 
        setContentPane(painelFundo);

        // Painel central transparente
        JPanel painelCentro = new JPanel(new GridBagLayout());
        painelCentro.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Título Principal
        JLabel lbTitulo = new JLabel("CRIAR NOVA CONTA", SwingConstants.CENTER);
        lbTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lbTitulo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weighty = 0.1;
        painelCentro.add(lbTitulo, gbc);

        // Subtítulo
        JLabel lbSubtitulo = new JLabel("Cadastre suas credenciais de acesso", SwingConstants.CENTER);
        lbSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbSubtitulo.setForeground(new Color(200, 200, 200));
        gbc.gridy = 1;
        painelCentro.add(lbSubtitulo, gbc);

        // Campo Usuário
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel lbUsuario = new JLabel("Usuário:");
        lbUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbUsuario.setForeground(Color.WHITE);
        painelCentro.add(lbUsuario, gbc);

        tfUsuario = otimizarCampoTexto(new JTextField(15));
        gbc.gridx = 1;
        painelCentro.add(tfUsuario, gbc);

        // Campo Senha
        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel lbSenha = new JLabel("Senha:");
        lbSenha.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbSenha.setForeground(Color.WHITE);
        painelCentro.add(lbSenha, gbc);

        pfSenha = (JPasswordField) otimizarCampoTexto(new JPasswordField(15));
        gbc.gridx = 1;
        painelCentro.add(pfSenha, gbc);

        // Painel de Botões lado a lado
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        painelBotoes.setOpaque(false);

        btnCadastrar = criarBotao("Cadastrar", new Color(46, 204, 113), new Color(39, 174, 96));
        btnCadastrar.addActionListener(e -> cadastrarUsuario());
        painelBotoes.add(btnCadastrar);

        btnCancelar = criarBotao("Cancelar", new Color(231, 76, 60), new Color(192, 41, 43));
        btnCancelar.addActionListener(e -> dispose());
        painelBotoes.add(btnCancelar);

        // Adiciona os botões ao grid central
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 15, 10, 15);
        painelCentro.add(painelBotoes, gbc);

        // Adiciona ao fundo e centraliza tudo
        painelFundo.add(painelCentro);
    }

    // Estiliza os campos de texto no padrão moderno
    private JTextField otimizarCampoTexto(JTextField campo) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campo.setForeground(new Color(50, 50, 50));
        campo.setBackground(Color.WHITE);
        campo.setCaretColor(Color.BLUE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return campo;
    }

    // Cria botões com efeito Hover (mesmo estilo do Login)
    private JButton criarBotao(String texto, Color corPadrao, Color corHover) {
        JButton btn = new JButton(texto);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(corPadrao);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(corHover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(corPadrao);
            }
        });

        return btn;
    }

    private void cadastrarUsuario() {
        String usuario = tfUsuario.getText();
        String senha = new String(pfSenha.getPassword());

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "INSERT INTO usuario (login, senha) VALUES (?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, usuario);
            pst.setString(2, senha);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        
        SwingUtilities.invokeLater(() -> new cadastrousuario().setVisible(true));
    }
}

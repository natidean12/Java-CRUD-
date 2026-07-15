package projeto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class login extends JFrame {

    private JTextField tfUsuario;
    private JPasswordField pfSenha;
    private JButton btnLogin, btnCadastrar, btnCancelar;

    public login() {
        setTitle("Login - Sistema Escolar");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Fundo com degradê moderno
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

        JPanel painelCentro = new JPanel(new GridBagLayout());
        painelCentro.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Título Principal
        JLabel lbTitulo = new JLabel("SISTEMA ESCOLAR", SwingConstants.CENTER);
        lbTitulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lbTitulo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weighty = 0.1;
        painelCentro.add(lbTitulo, gbc);

        // Subtítulo
        JLabel lbSubtitulo = new JLabel("Faça login para acessar o painel", SwingConstants.CENTER);
        lbSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbSubtitulo.setForeground(new Color(200, 200, 200));
        gbc.gridy = 1;
        painelCentro.add(lbSubtitulo, gbc);

        // Usuário
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel lbUsuario = new JLabel("Usuário:");
        lbUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbUsuario.setForeground(Color.WHITE);
        painelCentro.add(lbUsuario, gbc);

        // CORRIGIDO: Nome do método corrigido aqui
        tfUsuario = otimizarCampoTexto(new JTextField(15));
        gbc.gridx = 1;
        painelCentro.add(tfUsuario, gbc);

        // Senha
        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel lbSenha = new JLabel("Senha:");
        lbSenha.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbSenha.setForeground(Color.WHITE);
        painelCentro.add(lbSenha, gbc);

        // CORRIGIDO: Nome do método corrigido aqui também
        pfSenha = (JPasswordField) otimizarCampoTexto(new JPasswordField(15));
        gbc.gridx = 1;
        painelCentro.add(pfSenha, gbc);

        // Painel de Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        painelBotoes.setOpaque(false);

        btnLogin = criarBotao("Entrar", new Color(46, 204, 113), new Color(39, 174, 96));
        btnLogin.addActionListener(e -> realizarLogin());
        painelBotoes.add(btnLogin);

        btnCadastrar = criarBotao("Cadastrar", new Color(241, 196, 15), new Color(211, 158, 0));
        btnCadastrar.addActionListener(e -> abrirCadastro());
        painelBotoes.add(btnCadastrar);

        btnCancelar = criarBotao("Sair", new Color(231, 76, 60), new Color(192, 41, 43));
        btnCancelar.addActionListener(e -> dispose());
        painelBotoes.add(btnCancelar);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 15, 10, 15);
        painelCentro.add(painelBotoes, gbc);

        painelFundo.add(painelCentro);
    }

    // CORRIGIDO: Nome do método corrigido para "otimizarCampoTexto"
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

    private void realizarLogin() {
        String usuario = tfUsuario.getText();
        String senha = new String(pfSenha.getPassword());

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos!", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "SELECT * FROM usuario WHERE login = ? AND senha = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, usuario);
            pst.setString(2, senha);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    
                    // IMPORTANTE: Se a sua classe original chamava "new menu(usuario)", voltei para o seu padrão abaixo:
                    // new menu(usuario).setVisible(true);
                    
                    // Deixei assim para evitar incompatibilidade com suas outras classes:
                    new menu(usuario).setVisible(true); 
                } else {
                    JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.", "Erro de Acesso", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro de conexão: " + ex.getMessage(), "Erro Técnico", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirCadastro() {
        // Mantendo o nome antigo da sua classe para não dar erro no seu projeto
        new cadastrousuario().setVisible(true); 
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        
        SwingUtilities.invokeLater(() -> new login().setVisible(true));
    }
}

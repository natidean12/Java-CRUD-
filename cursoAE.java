package projeto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class cursoae extends JFrame implements ActionListener {

    JTextField txtCodCurso, txtCarga, txtCodInst;
    JComboBox<String> comboNomeCurso;
    JRadioButton rbBacharel, rbGestao, rbOutros;
    JButton btnAlterar, btnExcluir, btnLimpar, btnSair;
    JButton btnConfirmarAlterar, btnConfirmarExcluir;

    public cursoae() {
        setTitle("Cadastro / Manutenção de Curso");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        // Fundo degradê
        JPanel painelFundo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                Color cor1 = new Color(255, 200, 150);
                Color cor2 = new Color(255, 245, 200);
                g2.setPaint(new GradientPaint(0, 0, cor1, 0, getHeight(), cor2));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        painelFundo.setLayout(null);
        setContentPane(painelFundo);

        Font fonteLabel = new Font("Arial", Font.BOLD, 18);
        Font fonteCampo = new Font("Arial", Font.PLAIN, 17);

        // ===== TÍTULO =====
        JLabel lbTitulo = new JLabel("Alterar / Excluir Curso", SwingConstants.CENTER);
        lbTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lbTitulo.setBounds(0, 40, getWidth(), 40);
        painelFundo.add(lbTitulo);

        int lblX = 150; 
        int txtX = 380; 
        int larguraLabel = 200;
        int larguraCampo = 350;
        int alturaCampo = 40;
        int y = 130;
        int espacamento = 60;

        // ===== CAMPOS =====
        JLabel lbCodCurso = new JLabel("Código do Curso:");
        lbCodCurso.setFont(fonteLabel);
        lbCodCurso.setBounds(lblX, y, larguraLabel, 30);
        painelFundo.add(lbCodCurso);

        txtCodCurso = new JTextField();
        txtCodCurso.setFont(fonteCampo);
        txtCodCurso.setBounds(txtX, y, larguraCampo, alturaCampo);
        painelFundo.add(txtCodCurso);
        
        txtCodCurso.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buscarCursoPorCodigo();
                }
            }
        });

        y += espacamento;

        JLabel lbNome = new JLabel("Nome do Curso:");
        lbNome.setFont(fonteLabel);
        lbNome.setBounds(lblX, y, larguraLabel, 30);
        painelFundo.add(lbNome);

        comboNomeCurso = new JComboBox<>(new String[]{
                "Administração de Empresas", "Biomedicina", "Ciências Biológicas",
                "Ciência da Computação", "Direito", "Educação Física",
                "Farmacologia", "Rede de Computadores", "Sistemas de Informação"
        });
        comboNomeCurso.setFont(fonteCampo);
        comboNomeCurso.setBounds(txtX, y, larguraCampo, alturaCampo);
        painelFundo.add(comboNomeCurso);

        y += espacamento;

        JLabel lbTipo = new JLabel("Tipo do Curso:");
        lbTipo.setFont(fonteLabel);
        lbTipo.setBounds(lblX, y, larguraLabel, 30);
        painelFundo.add(lbTipo);

        rbBacharel = new JRadioButton("Bacharel");
        rbGestao = new JRadioButton("Gestão");
        rbOutros = new JRadioButton("Outros");

        ButtonGroup bg = new ButtonGroup();
        bg.add(rbBacharel);
        bg.add(rbGestao);
        bg.add(rbOutros);

        rbBacharel.setFont(fonteCampo);
        rbGestao.setFont(fonteCampo);
        rbOutros.setFont(fonteCampo);

        JPanel painelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        painelTipo.setBounds(txtX, y, larguraCampo, alturaCampo);
        painelTipo.setOpaque(false);
        painelTipo.add(rbBacharel);
        painelTipo.add(rbGestao);
        painelTipo.add(rbOutros);
        painelFundo.add(painelTipo);

        y += espacamento;

        JLabel lbCarga = new JLabel("Carga Horária:");
        lbCarga.setFont(fonteLabel);
        lbCarga.setBounds(lblX, y, larguraLabel, 30);
        painelFundo.add(lbCarga);

        txtCarga = new JTextField();
        txtCarga.setFont(fonteCampo);
        txtCarga.setBounds(txtX, y, larguraCampo, alturaCampo);
        painelFundo.add(txtCarga);

        y += espacamento;

        JLabel lbCodInst = new JLabel("Código do Instituto:");
        lbCodInst.setFont(fonteLabel);
        lbCodInst.setBounds(lblX, y, larguraLabel, 30);
        painelFundo.add(lbCodInst);

        txtCodInst = new JTextField();
        txtCodInst.setFont(fonteCampo);
        txtCodInst.setBounds(txtX, y, larguraCampo, alturaCampo);
        painelFundo.add(txtCodInst);

        // ===== BOTÕES =====
        Font fonteBotao = new Font("Arial", Font.BOLD, 16);
        int yBtn = y + 80;
        int larguraBotao = 140;
        int alturaBotao = 45;

        btnAlterar = criarBotao("Alterar", new Color(70, 130, 180), fonteBotao);
        btnAlterar.setBounds(180, yBtn, larguraBotao, alturaBotao);
        btnAlterar.addActionListener(this);
        painelFundo.add(btnAlterar);

        btnExcluir = criarBotao("Excluir", new Color(220, 70, 70), fonteBotao);
        btnExcluir.setBounds(340, yBtn, larguraBotao, alturaBotao);
        btnExcluir.addActionListener(this);
        painelFundo.add(btnExcluir);

        btnLimpar = criarBotao("Limpar", new Color(60, 179, 113), fonteBotao);
        btnLimpar.setBounds(500, yBtn, larguraBotao, alturaBotao);
        btnLimpar.addActionListener(this);
        painelFundo.add(btnLimpar);

        btnSair = criarBotao("Sair", new Color(100, 100, 100), fonteBotao);
        btnSair.setBounds(660, yBtn, larguraBotao, alturaBotao);
        btnSair.addActionListener(e -> dispose());
        painelFundo.add(btnSair);

        // ===== BOTÕES DE CONFIRMAÇÃO =====
        btnConfirmarAlterar = criarBotao("✅ Confirmar", new Color(144, 238, 144), fonteBotao);
        btnConfirmarAlterar.setBounds(btnAlterar.getBounds());
        btnConfirmarAlterar.setVisible(false);
        btnConfirmarAlterar.addActionListener(e -> {
            alterarCurso();
            btnConfirmarAlterar.setVisible(false);
            btnAlterar.setVisible(true);
        });
        painelFundo.add(btnConfirmarAlterar);

        btnConfirmarExcluir = criarBotao("🗑️ Confirmar", new Color(255, 182, 193), fonteBotao);
        btnConfirmarExcluir.setBounds(btnExcluir.getBounds());
        btnConfirmarExcluir.setVisible(false);
        btnConfirmarExcluir.addActionListener(e -> {
            excluirCurso();
            btnConfirmarExcluir.setVisible(false);
            btnExcluir.setVisible(true);
        });
        painelFundo.add(btnConfirmarExcluir);

        // ===== BORDA EXTERNA =====
        getRootPane().setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                "Curso",
                TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 18),
                new Color(70, 130, 180)
        ));
    }

    private JButton criarBotao(String texto, Color corFundo, Font fonte) {
        JButton btn = new JButton(texto);
        btn.setFont(fonte);
        btn.setForeground(Color.black);
        btn.setBackground(corFundo);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 2, true));
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
        Object src = e.getSource();
        if (src == btnAlterar) {
            btnAlterar.setVisible(false);
            btnConfirmarAlterar.setVisible(true);
        } else if (src == btnExcluir) {
            btnExcluir.setVisible(false);
            btnConfirmarExcluir.setVisible(true);
        } else if (src == btnLimpar) {
            limparCampos();
        }
    }

    private void limparCampos() {
        txtCodCurso.setText("");
        txtCarga.setText("");
        txtCodInst.setText("");
        comboNomeCurso.setSelectedIndex(0);
        rbBacharel.setSelected(false);
        rbGestao.setSelected(false);
        rbOutros.setSelected(false);
    }

    private void alterarCurso() {
        if (txtCodCurso.getText().trim().isEmpty() ||
            txtCarga.getText().trim().isEmpty() ||
            txtCodInst.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de alterar!");
            return;
        }

        try {
            int codCurso = Integer.parseInt(txtCodCurso.getText().trim());
            int cargaHoraria = Integer.parseInt(txtCarga.getText().trim());
            int codInst = Integer.parseInt(txtCodInst.getText().trim());

            String sql = "UPDATE Curso SET nome_curso=?, tipo_curso=?, carga_horaria=?, cod_instituto=? WHERE cod_curso=?";
            try (Connection conn = Conexao.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {

                pst.setString(1, comboNomeCurso.getSelectedItem().toString());
                String tipo = rbBacharel.isSelected() ? "Bacharel"
                        : rbGestao.isSelected() ? "Gestão" : "Outros";
                pst.setString(2, tipo);
                pst.setInt(3, cargaHoraria);
                pst.setInt(4, codInst);
                pst.setInt(5, codCurso);

                int r = pst.executeUpdate();
                if (r > 0) {
                    JOptionPane.showMessageDialog(this, "✅ Curso alterado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(this, "⚠️ Curso não encontrado!");
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Os campos numéricos devem conter apenas números!");
        } catch (SQLException ex) {
            if (ex.getMessage().contains("Duplicate entry"))
                JOptionPane.showMessageDialog(this, "❌ Código de curso já cadastrado!");
            else
                JOptionPane.showMessageDialog(this, "Erro ao alterar: " + ex.getMessage());
        }
    }

    private void excluirCurso() {
        String textoCod = txtCodCurso.getText().trim();
        if (textoCod.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o código do curso para excluir!");
            return;
        }

        try {
            int codCurso = Integer.parseInt(textoCod);

            String checkSql = "SELECT COUNT(*) FROM aluno WHERE cod_curso=?";
            String deleteSql = "DELETE FROM curso WHERE cod_curso=?";

            try (Connection conn = Conexao.getConnection();
                 PreparedStatement check = conn.prepareStatement(checkSql)) {

                check.setInt(1, codCurso);
                try (ResultSet rs = check.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(this,
                                "❌ Não é possível excluir este curso.\nExistem alunos vinculados a ele!",
                                "Erro de exclusão", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                try (PreparedStatement delete = conn.prepareStatement(deleteSql)) {
                    delete.setInt(1, codCurso);
                    int r = delete.executeUpdate();
                    if (r > 0) {
                        JOptionPane.showMessageDialog(this, "🗑️ Curso excluído com sucesso!");
                        limparCampos();
                    } else {
                        JOptionPane.showMessageDialog(this, "⚠️ Nenhum curso encontrado com esse código!");
                    }
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O código do curso deve ser numérico!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
        }
    }

    private void buscarCursoPorCodigo() {
        String textoCod = txtCodCurso.getText().trim();

        if (textoCod.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o código do curso!");
            return;
        }

        try {
            int codCurso = Integer.parseInt(textoCod);

            String sql = "SELECT nome_curso, tipo_curso, carga_horaria, cod_instituto FROM curso WHERE cod_curso = ?";
            try (Connection conn = Conexao.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {

                pst.setInt(1, codCurso);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        comboNomeCurso.setSelectedItem(rs.getString("nome_curso"));
                        txtCarga.setText(String.valueOf(rs.getInt("carga_horaria")));
                        txtCodInst.setText(String.valueOf(rs.getInt("cod_instituto")));

                        String tipo = rs.getString("tipo_curso");
                        rbBacharel.setSelected("Bacharel".equalsIgnoreCase(tipo));
                        rbGestao.setSelected("Gestão".equalsIgnoreCase(tipo));
                        rbOutros.setSelected("Outros".equalsIgnoreCase(tipo));

                        piscarCampo(txtCodCurso);
                    } else {
                        JOptionPane.showMessageDialog(this, "Curso não encontrado!");
                        limparCampos();
                    }
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O código deve ser numérico!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar curso: " + ex.getMessage());
        }
    }
    
    private void piscarCampo(final JTextField campo) {
        final Color corOriginal = campo.getBackground();
        final Color corDestaque = new Color(255, 255, 180); // Amarelo claro
        final int flashes = 3;
        final int delay = 150; // Correção: Configurado para 150ms para o efeito ser visível

        Timer timer = new Timer(delay, null);
        final int[] count = {0};
        timer.addActionListener(e -> {
            if (count[0] % 2 == 0) {
                campo.setBackground(corDestaque);
            } else {
                campo.setBackground(corOriginal);
            }
            count[0]++;
            if (count[0] >= flashes * 2) {
                timer.stop();
                campo.setBackground(corOriginal);
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        new cursoae().setVisible(true);
    }
}

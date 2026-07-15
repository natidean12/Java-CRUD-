package projeto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class professor extends JFrame implements ActionListener {

    JTextField tfID, tfNome, tfRua, tfNumero, tfBairro, tfCidade, tfEstado, tfTel, tfCel, tfData;
    JCheckBox cbDireito, cbInformatica, cbMatematica, cbMedicina;
    JCheckBox cbBacharel, cbEsp, cbMestrado, cbDoutorado;
    JButton btnSalvar, btnLimpar, btnSair;

    public professor() {
        setTitle("Cadastro de Professor");
        setSize(950, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ===== Fundo com Degradê Claro Suave (Padronizado) =====
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

        // ===== Título =====
        JLabel lbTitulo = new JLabel("🎓 Cadastro de Professor", SwingConstants.CENTER);
        lbTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lbTitulo.setForeground(new Color(15, 23, 42));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 12, 15, 12);
        painelFundo.add(lbTitulo, gbc);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 12, 5, 12); // Reseta margem interna

        // ===== Geração dos Campos =====
        int linha = 1;
        tfID = criarCampo(painelFundo, gbc, linha++, "Identificação (ID):");
        tfNome = criarCampo(painelFundo, gbc, linha++, "Nome Completo:");
        tfData = criarCampo(painelFundo, gbc, linha++, "Data de Nascimento (AAAA-MM-DD):");
        tfRua = criarCampo(painelFundo, gbc, linha++, "Rua / Avenida:");
        tfNumero = criarCampo(painelFundo, gbc, linha++, "Número:");
        tfBairro = criarCampo(painelFundo, gbc, linha++, "Bairro:");
        tfCidade = criarCampo(painelFundo, gbc, linha++, "Cidade:");
        tfEstado = criarCampo(painelFundo, gbc, linha++, "Estado:");
        tfTel = criarCampo(painelFundo, gbc, linha++, "Telefone Fixo:");
        tfCel = criarCampo(painelFundo, gbc, linha++, "Celular:");

        // Estilo comum para as fontes de grupo
        Font fonteBorda = new Font("Segoe UI Semibold", Font.PLAIN, 14);
        Color corBorda = new Color(148, 163, 184); // Cinza moderno

        // ===== Painel de Especialização =====
        JPanel painelEsp = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 5));
        painelEsp.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(corBorda, 1, true),
                "Especialização", TitledBorder.LEFT, TitledBorder.TOP, fonteBorda, new Color(71, 85, 105)
        ));
        painelEsp.setOpaque(false);
        cbDireito = criarCheckBox("Direito", painelEsp);
        cbInformatica = criarCheckBox("Informática", painelEsp);
        cbMatematica = criarCheckBox("Matemática", painelEsp);
        cbMedicina = criarCheckBox("Medicina", painelEsp);
        
        gbc.gridx = 0; gbc.gridy = linha++; gbc.gridwidth = 2;
        gbc.insets = new Insets(12, 12, 6, 12);
        painelFundo.add(painelEsp, gbc);

        // ===== Painel de Titulação =====
        JPanel painelTit = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 5));
        painelTit.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(corBorda, 1, true),
                "Titulação", TitledBorder.LEFT, TitledBorder.TOP, fonteBorda, new Color(71, 85, 105)
        ));
        painelTit.setOpaque(false);
        cbBacharel = criarCheckBox("Bacharel", painelTit);
        cbEsp = criarCheckBox("Especialista", painelTit);
        cbMestrado = criarCheckBox("Mestrado", painelTit);
        cbDoutorado = criarCheckBox("Doutorado", painelTit);
        
        gbc.gridy = linha++;
        painelFundo.add(painelTit, gbc);
        gbc.gridwidth = 1;

        // ===== Painel de Botões =====
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 5));
        painelBotoes.setOpaque(false);

        btnSalvar = criarBotao("💾 Salvar", new Color(134, 239, 172), painelBotoes); // Verde menta
        btnLimpar = criarBotao("🧹 Limpar", new Color(241, 245, 249), painelBotoes); // Cinza claro
        btnSair = criarBotao("❌ Sair", new Color(254, 202, 202), painelBotoes);     // Vermelho suave

        btnSalvar.addActionListener(this);
        btnLimpar.addActionListener(this);
        btnSair.addActionListener(e -> dispose());
        
        gbc.gridx = 0; gbc.gridy = linha++; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 12, 15, 12);
        painelFundo.add(painelBotoes, gbc);
    }

    private JTextField criarCampo(JPanel painel, GridBagConstraints gbc, int linha, String label) {
        JLabel lb = new JLabel(label);
        lb.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        lb.setForeground(new Color(51, 65, 85));
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.weightx = 0.3;
        painel.add(lb, gbc);

        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        gbc.gridx = 1; gbc.gridy = linha;
        gbc.weightx = 0.7;
        painel.add(tf, gbc);
        return tf;
    }

    private JCheckBox criarCheckBox(String texto, JPanel painel) {
        JCheckBox cb = new JCheckBox(texto);
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cb.setForeground(new Color(51, 65, 85));
        cb.setOpaque(false);
        painel.add(cb);
        return cb;
    }

    private JButton criarBotao(String texto, Color corFundo, JPanel painel) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
        btn.setPreferredSize(new Dimension(150, 42));
        btn.setForeground(Color.BLACK); // Letras pretas para leitura perfeita
        btn.setBackground(corFundo);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(corFundo.darker(), 1));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(corFundo.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(corFundo);
            }
        });

        painel.add(btn);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnSalvar) salvarProfessor();
        else if(e.getSource() == btnLimpar) limparCampos();
    }

    private void limparCampos() {
        tfID.setText(""); tfNome.setText(""); tfRua.setText(""); tfNumero.setText("");
        tfBairro.setText(""); tfCidade.setText(""); tfEstado.setText("");
        tfTel.setText(""); tfCel.setText(""); tfData.setText("");
        cbDireito.setSelected(false); cbInformatica.setSelected(false);
        cbMatematica.setSelected(false); cbMedicina.setSelected(false);
        cbBacharel.setSelected(false); cbEsp.setSelected(false);
        cbMestrado.setSelected(false); cbDoutorado.setSelected(false);
    }

    private void salvarProfessor() {
        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);

            int idProf = Integer.parseInt(tfID.getText().trim());
            String nome = tfNome.getText().trim();
            String rua = tfRua.getText().trim();
            String numero = tfNumero.getText().trim();
            String bairro = tfBairro.getText().trim();
            String cidade = tfCidade.getText().trim();
            String estado = tfEstado.getText().trim();
            String tel = tfTel.getText().trim();
            String cel = tfCel.getText().trim();

            java.sql.Date dataNascSql = java.sql.Date.valueOf(tfData.getText().trim());

            // Titulação
            String titulacao = "";
            if(cbBacharel.isSelected()) titulacao += "Bacharel, ";
            if(cbEsp.isSelected()) titulacao += "Especialista, ";
            if(cbMestrado.isSelected()) titulacao += "Mestrado, ";
            if(cbDoutorado.isSelected()) titulacao += "Doutorado, ";
            if(!titulacao.isEmpty()) titulacao = titulacao.substring(0, titulacao.length()-2);

            // Especializações selecionadas
            List<String> especializacoesSelecionadas = new ArrayList<>();
            if(cbDireito.isSelected()) especializacoesSelecionadas.add("Direito");
            if(cbInformatica.isSelected()) especializacoesSelecionadas.add("Informática");
            if(cbMatematica.isSelected()) especializacoesSelecionadas.add("Matemática");
            if(cbMedicina.isSelected()) especializacoesSelecionadas.add("Medicina");

            // Mapa fixo de cursos por especialização
            Map<String, List<String>> mapaCursoPorEspecializacao = new HashMap<>();
            mapaCursoPorEspecializacao.put("Direito", List.of("Direito", "Administração de Empresas"));
            mapaCursoPorEspecializacao.put("Informática", List.of("Ciência da Computação", "Rede de Computadores", "Sistemas de Informação"));
            mapaCursoPorEspecializacao.put("Matemática", List.of("Ciências Biológicas",  "Sistemas de Informação", "Administração de Empresas"));
            mapaCursoPorEspecializacao.put("Medicina", List.of("Biomedicina", "Farmacologia", "Educação Física"));

            // Inserir ou atualizar professor
            String sql = """
                INSERT INTO Professor (id_prof, nome, rua_av, numero, bairro, cidade, estado, tel_fixo, cel, especializacao, titulacao, data_nasc)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                nome=?, rua_av=?, numero=?, bairro=?, cidade=?, estado=?, tel_fixo=?, cel=?, especializacao=?, titulacao=?, data_nasc=?;
            """;

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idProf);
            pst.setString(2, nome);
            pst.setString(3, rua);
            pst.setString(4, numero);
            pst.setString(5, bairro);
            pst.setString(6, cidade);
            pst.setString(7, estado);
            pst.setString(8, tel);
            pst.setString(9, cel);
            pst.setString(10, String.join(", ", especializacoesSelecionadas));
            pst.setString(11, titulacao);
            pst.setDate(12, dataNascSql);
            // UPDATE
            pst.setString(13, nome);
            pst.setString(14, rua);
            pst.setString(15, numero);
            pst.setString(16, bairro);
            pst.setString(17, cidade);
            pst.setString(18, estado);
            pst.setString(19, tel);
            pst.setString(20, cel);
            pst.setString(21, String.join(", ", especializacoesSelecionadas));
            pst.setString(22, titulacao);
            pst.setDate(23, dataNascSql);

            pst.executeUpdate();

            // Vincular professor aos cursos
            for(String esp : especializacoesSelecionadas) {
                List<String> cursos = mapaCursoPorEspecializacao.get(esp);
                if(cursos != null) {
                    for(String curso : cursos) {
                        String sqlCursoProf = """
                            INSERT INTO curso_professor (cod_curso, id_prof)
                            SELECT cod_curso, ? FROM curso
                            WHERE nome_curso = ?
                            AND NOT EXISTS (
                                SELECT 1 FROM curso_professor 
                                WHERE cod_curso = curso.cod_curso AND id_prof = ?
                            )
                        """;
                        PreparedStatement pstCurso = conn.prepareStatement(sqlCursoProf);
                        pstCurso.setInt(1, idProf);
                        pstCurso.setString(2, curso);
                        pstCurso.setInt(3, idProf);
                        pstCurso.executeUpdate();
                    }
                }
            }

            conn.commit();
            JOptionPane.showMessageDialog(this, "✅ Professor salvo e vinculado aos cursos corretos!");
            limparCampos();

        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new professor().setVisible(true);
    }
}

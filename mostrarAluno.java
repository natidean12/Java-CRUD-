package projeto;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class mostraraluno extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton btnDetalhes, btnFechar;
    private JLabel lbStatus;

    public mostraraluno() {
        setTitle("Alunos Detalhados");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ===== Painel de fundo com degradê =====
        JPanel painelFundo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(245, 250, 255),
                        0, getHeight(), new Color(200, 220, 255));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        painelFundo.setLayout(new BorderLayout(10, 10));
        setContentPane(painelFundo);

        // ===== Cabeçalho =====
        JLabel titulo = new JLabel("Alunos com Disciplinas e Notas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(new Color(20, 20, 80));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        painelFundo.add(titulo, BorderLayout.NORTH);

        // ===== Tabela =====
        String[] colunas = {"Matrícula", "Nome", "Data Nasc", "Curso",
                            "Disciplina", "NP1", "NP2", "Média", "Faltas"};
        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(26);
        tabela.setFont(new Font("Arial", Font.PLAIN, 15));
        tabela.getTableHeader().setReorderingAllowed(false);

        // Cabeçalho estilizado
        JTableHeader header = tabela.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        // Linhas alternadas
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(new Color(135, 206, 250));
                } else {
                    c.setBackground(row % 2 == 0 ? new Color(235, 245, 255) : Color.WHITE);
                }
                setHorizontalAlignment(column == 1 || column == 3 || column == 4 ? LEFT : CENTER);
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(8, 12, 8, 12),
                BorderFactory.createLineBorder(new Color(180, 180, 180), 2)
        ));
        painelFundo.add(scroll, BorderLayout.CENTER);

        // ===== Rodapé com botões =====
        JPanel painelBaixo = new JPanel(new BorderLayout(6, 6));
        painelBaixo.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
        painelBaixo.setOpaque(false);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        botoes.setOpaque(false);

        btnDetalhes = criarBotao("💡 Detalhes", new Color(60, 179, 113));
        btnFechar = criarBotao("❌ Fechar", new Color(220, 20, 60));

        botoes.add(btnDetalhes);
        botoes.add(btnFechar);
        painelBaixo.add(botoes, BorderLayout.EAST);

        lbStatus = new JLabel(" ");
        lbStatus.setForeground(new Color(50, 50, 50));
        painelBaixo.add(lbStatus, BorderLayout.WEST);

        painelFundo.add(painelBaixo, BorderLayout.SOUTH);

        // ===== Ações =====
        btnDetalhes.addActionListener(e -> mostrarDetalhes());
        btnFechar.addActionListener(e -> dispose());

        // Carregar dados
        carregarAlunosDetalhado();
    }

    private JButton criarBotao(String texto, Color corFundo) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(corFundo);
        btn.setForeground(Color.black);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1, true));
        // Efeito hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(corFundo.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(corFundo); }
        });
        return btn;
    }

    private void carregarAlunosDetalhado() {
        modelo.setRowCount(0);
        String sql = """
            SELECT a.matricula, a.nome, a.data_nasc, c.nome_curso,
                   d.nome_disc, a.np1, a.np2, a.media, a.faltas
            FROM Aluno a
            LEFT JOIN Curso c ON a.cod_curso = c.cod_curso
            LEFT JOIN Aluno_Disciplina ad ON a.matricula = ad.matricula
            LEFT JOIN Disciplina d ON ad.cod_disc = d.cod_disc
            ORDER BY a.nome, d.nome_disc
        """;

        int linhas = 0;

        try (Connection conn = Conexao.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("matricula"));
                row.add(rs.getString("nome"));
                row.add(rs.getDate("data_nasc") != null ? rs.getDate("data_nasc") : "-");
                row.add(rs.getString("nome_curso") != null ? rs.getString("nome_curso") : "-");
                row.add(rs.getString("nome_disc") != null ? rs.getString("nome_disc") : "-");
                row.add(rs.getObject("np1") != null ? rs.getObject("np1") : "-");
                row.add(rs.getObject("np2") != null ? rs.getObject("np2") : "-");
                row.add(rs.getObject("media") != null ? rs.getObject("media") : "-");
                row.add(rs.getObject("faltas") != null ? rs.getObject("faltas") : "-");
                modelo.addRow(row);
                linhas++;
            }

            lbStatus.setText(linhas + " registro(s) carregado(s).");

        } catch (SQLException ex) {
            lbStatus.setText("Erro ao carregar alunos.");
            JOptionPane.showMessageDialog(this, "Erro ao buscar alunos: " + ex.getMessage(),
                                          "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDetalhes() {
        int sel = tabela.getSelectedRow();
        if (sel == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno para ver detalhes.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Object matricula = modelo.getValueAt(sel, 0);
        Object nome = modelo.getValueAt(sel, 1);
        Object dataNasc = modelo.getValueAt(sel, 2);
        Object curso = modelo.getValueAt(sel, 3);
        Object disciplina = modelo.getValueAt(sel, 4);
        Object np1 = modelo.getValueAt(sel, 5);
        Object np2 = modelo.getValueAt(sel, 6);
        Object media = modelo.getValueAt(sel, 7);
        Object faltas = modelo.getValueAt(sel, 8);

        String msg = "<html><b>Matrícula:</b> " + matricula +
                     "<br><b>Nome:</b> " + nome +
                     "<br><b>Data Nasc:</b> " + dataNasc +
                     "<br><b>Curso:</b> " + curso +
                     "<br><b>Disciplina:</b> " + disciplina +
                     "<br><b>NP1:</b> " + np1 +
                     "<br><b>NP2:</b> " + np2 +
                     "<br><b>Média:</b> " + media +
                     "<br><b>Faltas:</b> " + faltas + "</html>";

        JOptionPane.showMessageDialog(this, msg, "Detalhes do Aluno", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new mostraraluno().setVisible(true);
    }
}

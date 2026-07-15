package projeto;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class mostrardisciplina extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton btnDetalhes, btnFechar;
    private JLabel lbStatus;

    public mostrardisciplina() {
        setTitle("Disciplinas");
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
        JLabel titulo = new JLabel("Lista de Disciplinas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(new Color(20, 20, 80));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        painelFundo.add(titulo, BorderLayout.NORTH);

        // ===== Tabela =====
        String[] colunas = {"Código", "Nome da Disciplina", "Carga Horária", "Aulas/Semana"};
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
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(new Color(135, 206, 250));
                } else {
                    c.setBackground(row % 2 == 0 ? new Color(235, 245, 255) : Color.WHITE);
                }
                setHorizontalAlignment(column == 1 ? LEFT : CENTER);
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(8, 12, 8, 12),
                BorderFactory.createLineBorder(new Color(180, 180, 180), 2)
        ));
        painelFundo.add(scroll, BorderLayout.CENTER);

        // ===== Rodapé =====
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
        carregarDisciplinas();
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

    private void carregarDisciplinas() {
        modelo.setRowCount(0);
        String sql = "SELECT cod_disc, nome_disc, carga_horaria, aulas_semana FROM Disciplina ORDER BY nome_disc";
        int linhas = 0;

        try (Connection conn = Conexao.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("cod_disc"));
                row.add(rs.getString("nome_disc"));
                row.add(rs.getInt("carga_horaria"));
                row.add(rs.getInt("aulas_semana"));
                modelo.addRow(row);
                linhas++;
            }

            lbStatus.setText(linhas + " disciplina(s) carregada(s).");

        } catch (SQLException ex) {
            lbStatus.setText("Erro ao carregar disciplinas.");
            JOptionPane.showMessageDialog(this, "Erro ao buscar disciplinas: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDetalhes() {
        int sel = tabela.getSelectedRow();
        if (sel == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma disciplina para ver detalhes.", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Object cod = modelo.getValueAt(sel, 0);
        Object nome = modelo.getValueAt(sel, 1);
        Object carga = modelo.getValueAt(sel, 2);
        Object aulas = modelo.getValueAt(sel, 3);

        String msg = "<html><b>Código:</b> " + cod +
                     "<br><b>Nome:</b> " + nome +
                     "<br><b>Carga Horária:</b> " + carga +
                     "<br><b>Aulas/Semana:</b> " + aulas + "</html>";

        JOptionPane.showMessageDialog(this, msg, "Detalhes do Disciplina", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new mostrardisciplina().setVisible(true);
    }
}

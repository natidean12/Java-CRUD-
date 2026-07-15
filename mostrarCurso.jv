package projeto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class mostrarcurso extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton btnDetalhes, btnFechar;
    private JLabel lbStatus;

    public mostrarcurso() {
        setTitle("Lista de Cursos");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ====== Painel de fundo com degradê ======
        JPanel painelFundo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 245, 250),
                        0, getHeight(), new Color(210, 230, 255));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        painelFundo.setLayout(new BorderLayout(10, 10));
        painelFundo.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(painelFundo);

        // ====== Cabeçalho ======
        JLabel titulo = new JLabel("Cursos Disponíveis", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(new Color(30, 60, 120));
        titulo.setBorder(new EmptyBorder(10, 0, 20, 0));
        painelFundo.add(titulo, BorderLayout.NORTH);

        // ====== Tabela ======
        String[] colunas = {"Código", "Nome do Curso", "Tipo", "Carga Horária", "Código Instituto"};
        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(28);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabela.setFillsViewportHeight(true);

        // Cabeçalho com degradê
        JTableHeader header = tabela.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 32));
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(70, 130, 180));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        // Linhas alternadas
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                if (!isSelected)
                    c.setBackground(row % 2 == 0 ? new Color(235, 245, 255) : Color.WHITE);
                else
                    c.setBackground(new Color(180, 210, 255));
                setHorizontalAlignment(column == 1 ? LEFT : CENTER); // nome alinhado à esquerda
                return c;
            }
        });

        // Ajuste de colunas
        TableColumnModel cm = tabela.getColumnModel();
        cm.getColumn(0).setPreferredWidth(80);
        cm.getColumn(1).setPreferredWidth(380);
        cm.getColumn(2).setPreferredWidth(120);
        cm.getColumn(3).setPreferredWidth(120);
        cm.getColumn(4).setPreferredWidth(120);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true));
        painelFundo.add(scroll, BorderLayout.CENTER);

        // ====== Rodapé com botões ======
        JPanel painelBaixo = new JPanel(new BorderLayout(6, 6));
        painelBaixo.setOpaque(false);

        lbStatus = new JLabel(" ");
        lbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbStatus.setForeground(new Color(60, 60, 60));
        painelBaixo.add(lbStatus, BorderLayout.WEST);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        botoes.setOpaque(false);

        btnDetalhes = criarBotao("💡 Detalhes", new Color(60, 179, 113));
        btnFechar = criarBotao("❌ Fechar", new Color(220, 20, 60));

        botoes.add(btnDetalhes);
        botoes.add(btnFechar);
        painelBaixo.add(botoes, BorderLayout.EAST);

        painelFundo.add(painelBaixo, BorderLayout.SOUTH);

        // ====== Ações ======
        btnDetalhes.addActionListener(e -> mostrarDetalhes());
        btnFechar.addActionListener(e -> dispose());

        // Carregar dados automaticamente
        carregarCursos();
    }

    private JButton criarBotao(String texto, Color corFundo) {
        JButton btn = new JButton(texto);
        btn.setBackground(corFundo);
        btn.setForeground(Color.black);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(corFundo.darker(), 2, true));
        // Efeito hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(corFundo.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(corFundo);
            }
        });
        return btn;
    }

    private void carregarCursos() {
        modelo.setRowCount(0);
        String sql = "SELECT cod_curso, nome_curso, tipo_curso, carga_horaria, cod_instituto FROM Curso ORDER BY nome_curso";
        int linhas = 0;

        try (Connection conn = Conexao.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("cod_curso"));
                row.add(rs.getString("nome_curso"));
                row.add(rs.getString("tipo_curso"));
                row.add(rs.getInt("carga_horaria"));
                row.add(rs.getInt("cod_instituto"));
                modelo.addRow(row);
                linhas++;
            }

            lbStatus.setText(linhas + " curso(s) carregado(s).");

        } catch (SQLException ex) {
            lbStatus.setText("Erro ao carregar cursos.");
            JOptionPane.showMessageDialog(this, "Erro ao buscar cursos: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDetalhes() {
        int sel = tabela.getSelectedRow();
        if (sel == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um curso para ver detalhes.", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Object cod = modelo.getValueAt(sel, 0);
        Object nome = modelo.getValueAt(sel, 1);
        Object tipo = modelo.getValueAt(sel, 2);
        Object carga = modelo.getValueAt(sel, 3);
        Object inst = modelo.getValueAt(sel, 4);

        String msg = "<html><b>Código:</b> " + cod
                + "<br><b>Nome:</b> " + nome
                + "<br><b>Tipo:</b> " + tipo
                + "<br><b>Carga Horária:</b> " + carga
                + "<br><b>Cód. Instituto:</b> " + inst + "</html>";

        JOptionPane.showMessageDialog(this, msg, "Detalhes do Curso", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new mostrarcurso().setVisible(true);
    }
}

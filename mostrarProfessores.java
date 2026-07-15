package projeto;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class mostrarprofessor extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton btnDetalhes, btnFechar;
    private JLabel lbStatus;

    public mostrarprofessor() {
        setTitle("Lista de Professores");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ===== Painel com degradê =====
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
        painelFundo.setLayout(new BorderLayout(10,10));
        setContentPane(painelFundo);

        // ===== Cabeçalho =====
        JLabel titulo = new JLabel("Lista de Professores", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(new Color(20,20,80));
        titulo.setBorder(BorderFactory.createEmptyBorder(15,0,15,0));
        painelFundo.add(titulo, BorderLayout.NORTH);

        // ===== Tabela =====
        String[] colunas = {"ID", "Nome", "Rua", "Nº", "Bairro", "Cidade", "Estado",
                            "Tel Fixo", "Celular", "Data Nasc", "Especialização", "Titulação"};
        modelo = new DefaultTableModel(colunas,0) {
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
        header.setBackground(new Color(70,130,180));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        // Linhas alternadas
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(new Color(135,206,250));
                } else {
                    c.setBackground(row % 2 == 0 ? new Color(235,245,255) : Color.WHITE);
                }
                setHorizontalAlignment(column == 1 || column == 2 || column == 5 || column == 6 ? LEFT : CENTER);
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(8,12,8,12),
                BorderFactory.createLineBorder(new Color(180,180,180),2)
        ));
        painelFundo.add(scroll, BorderLayout.CENTER);

        // ===== Rodapé com botões =====
        JPanel painelBaixo = new JPanel(new BorderLayout(6,6));
        painelBaixo.setBorder(BorderFactory.createEmptyBorder(0,12,12,12));
        painelBaixo.setOpaque(false);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,0));
        botoes.setOpaque(false);

        btnDetalhes = criarBotao("💡 Detalhes", new Color(60,179,113));
        btnFechar = criarBotao("❌ Fechar", new Color(220,20,60));

        botoes.add(btnDetalhes);
        botoes.add(btnFechar);
        painelBaixo.add(botoes, BorderLayout.EAST);

        lbStatus = new JLabel(" ");
        lbStatus.setForeground(new Color(50,50,50));
        painelBaixo.add(lbStatus, BorderLayout.WEST);

        painelFundo.add(painelBaixo, BorderLayout.SOUTH);

        // ===== Ações =====
        btnDetalhes.addActionListener(e -> mostrarDetalhes());
        btnFechar.addActionListener(e -> dispose());

        // Carrega dados
        carregarProfessores();
    }

    private JButton criarBotao(String texto, Color corFundo) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD,14));
        btn.setBackground(corFundo);
        btn.setForeground(Color.black);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(new Color(50,50,50),1,true));
        // efeito hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(corFundo.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(corFundo); }
        });
        return btn;
    }

    private void carregarProfessores() {
        modelo.setRowCount(0);
        String sql = "SELECT * FROM Professor ORDER BY nome";
        int linhas = 0;

        try (Connection conn = Conexao.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id_prof"));
                row.add(rs.getString("nome"));
                row.add(rs.getString("rua_av"));
                row.add(rs.getString("numero"));
                row.add(rs.getString("bairro"));
                row.add(rs.getString("cidade"));
                row.add(rs.getString("estado"));
                row.add(rs.getString("tel_fixo"));
                row.add(rs.getString("cel"));
                row.add(rs.getDate("data_nasc"));
                row.add(rs.getString("especializacao"));
                row.add(rs.getString("titulacao"));
                modelo.addRow(row);
                linhas++;
            }

            lbStatus.setText(linhas + " registro(s) carregado(s).");

        } catch (SQLException ex) {
            lbStatus.setText("Erro ao carregar professores.");
            JOptionPane.showMessageDialog(this,"Erro ao buscar professores: "+ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDetalhes() {
        int sel = tabela.getSelectedRow();
        if(sel == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um professor para ver detalhes.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Object id = modelo.getValueAt(sel,0);
        Object nome = modelo.getValueAt(sel,1);
        Object rua = modelo.getValueAt(sel,2);
        Object numero = modelo.getValueAt(sel,3);
        Object bairro = modelo.getValueAt(sel,4);
        Object cidade = modelo.getValueAt(sel,5);
        Object estado = modelo.getValueAt(sel,6);
        Object tel = modelo.getValueAt(sel,7);
        Object cel = modelo.getValueAt(sel,8);
        Object dataNasc = modelo.getValueAt(sel,9);
        Object esp = modelo.getValueAt(sel,10);
        Object tit = modelo.getValueAt(sel,11);

        String msg = "<html><b>ID:</b> "+id+
                     "<br><b>Nome:</b> "+nome+
                     "<br><b>Rua:</b> "+rua+
                     "<br><b>Nº:</b> "+numero+
                     "<br><b>Bairro:</b> "+bairro+
                     "<br><b>Cidade:</b> "+cidade+
                     "<br><b>Estado:</b> "+estado+
                     "<br><b>Tel Fixo:</b> "+tel+
                     "<br><b>Celular:</b> "+cel+
                     "<br><b>Data Nasc:</b> "+dataNasc+
                     "<br><b>Especialização:</b> "+esp+
                     "<br><b>Titulação:</b> "+tit+"</html>";

        JOptionPane.showMessageDialog(this,msg,"Detalhes do Professor",JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
       new mostrarprofessor().setVisible(true);
    }
}

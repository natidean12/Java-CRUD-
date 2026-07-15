package projeto;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class graficoprofessordisciplina extends JFrame {

    private static graficoprofessordisciplina instance;

    public graficoprofessordisciplina() {
        super("👩‍🏫 Professor x Disciplina");
        setSize(1100, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== Painel Centro com gráficos =====
        JPanel painelCentro = new JPanel(new GridLayout(1, 2, 10, 10));
        painelCentro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelCentro.setOpaque(false);
        painelCentro.add(criarGraficoBarras());
        painelCentro.add(criarGraficoPizza());

        // Painel de fundo com degradê suave
        JPanel painelFundo = new JPanel(new BorderLayout(10, 10)) {
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
        painelFundo.add(painelCentro, BorderLayout.CENTER);
        setContentPane(painelFundo);

        // ===== Botão para mostrar associações =====
        JButton btnAssociacoes = new JButton("🔍 Ver Associações");
        btnAssociacoes.setBackground(new Color(60, 179, 113));
        btnAssociacoes.setForeground(Color.BLACK);
        btnAssociacoes.setFont(new Font("Arial", Font.BOLD, 14));
        btnAssociacoes.setFocusPainted(false);
        btnAssociacoes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAssociacoes.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1, true));
        btnAssociacoes.addActionListener(e -> mostrarAssociacoes());

        // Efeito hover no botão
        btnAssociacoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btnAssociacoes.setBackground(new Color(60, 179, 113).brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btnAssociacoes.setBackground(new Color(60, 179, 113)); }
        });

        JPanel painelSul = new JPanel();
        painelSul.setOpaque(false);
        painelSul.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        painelSul.add(btnAssociacoes);
        painelFundo.add(painelSul, BorderLayout.SOUTH);
    }

    public static void abrir() {
        if (instance == null || !instance.isVisible()) {
            instance = new graficoprofessordisciplina();
            instance.setVisible(true);
        } else {
            instance.toFront();
        }
    }

    // ================= Gráfico de Barras =================
    ChartPanel criarGraficoBarras() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String sql = """
            SELECT p.nome AS nome, COUNT(DISTINCT cd.cod_disc) AS total 
            FROM Professor p 
            LEFT JOIN Curso_Professor cp ON p.id_prof = cp.id_prof 
            LEFT JOIN Curso_Disciplina cd ON cp.cod_curso = cd.cod_curso 
            GROUP BY p.nome
        """;

        try (Connection con = Conexao.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                dataset.addValue(rs.getInt("total"), "Disciplinas", rs.getString("nome"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar gráfico de barras: " + e.getMessage());
        }

        JFreeChart graficoBarra = ChartFactory.createBarChart(
                "Disciplinas por Professor",
                "Professor",
                "Quantidade",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        graficoBarra.setBackgroundPaint(new Color(240, 245, 250));
        CategoryPlot plot = graficoBarra.getCategoryPlot();
        plot.setBackgroundPaint(new Color(230, 245, 255));
        plot.setRangeGridlinePaint(Color.GRAY);

        return new ChartPanel(graficoBarra);
    }

    // ================= Gráfico de Pizza =================
    ChartPanel criarGraficoPizza() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        String sql = """
            SELECT p.nome AS nome, COUNT(DISTINCT cd.cod_disc) AS total 
            FROM Professor p 
            LEFT JOIN Curso_Professor cp ON p.id_prof = cp.id_prof 
            LEFT JOIN Curso_Disciplina cd ON cp.cod_curso = cd.cod_curso 
            GROUP BY p.nome
        """;

        try (Connection con = Conexao.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                dataset.setValue(rs.getString("nome"), rs.getInt("total"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar gráfico de pizza: " + e.getMessage());
        }

        JFreeChart graficoPizza = ChartFactory.createPieChart(
                "Distribuição de Disciplinas por Professor",
                dataset,
                true, true, false
        );

        graficoPizza.setBackgroundPaint(new Color(240, 245, 250));
        PiePlot plot = (PiePlot) graficoPizza.getPlot();
        plot.setBackgroundPaint(new Color(240, 245, 250));

        return new ChartPanel(graficoPizza);
    }

    // ================= Mostrar Associações =================
    void mostrarAssociacoes() {
        String sql = """
            SELECT p.nome AS professor, d.nome_disc AS disciplina, c.nome_curso AS curso
            FROM Professor p
            LEFT JOIN Curso_Professor cp ON p.id_prof = cp.id_prof
            LEFT JOIN Curso_Disciplina cd ON cp.cod_curso = cd.cod_curso
            LEFT JOIN Disciplina d ON cd.cod_disc = d.cod_disc
            LEFT JOIN Curso c ON cd.cod_curso = c.cod_curso
            ORDER BY p.nome, c.nome_curso, d.nome_disc
        """;

        StringBuilder sb = new StringBuilder();
        try (Connection con = Conexao.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            String professorAtual = "";
            while (rs.next()) {
                String prof = rs.getString("professor");
                String disc = rs.getString("disciplina");
                String curso = rs.getString("curso");

                if (!prof.equals(professorAtual)) {
                    sb.append("\n👩‍🏫 ").append(prof).append(":\n");
                    professorAtual = prof;
                }
                if (disc != null && curso != null) {
                    sb.append("   📘 ").append(curso).append(" -> ").append(disc).append("\n");
                } else {
                    sb.append("   ⚠️ Sem disciplinas vinculadas.\n");
                }
            }

        } catch (SQLException e) {
            sb.append("Erro ao buscar associações: ").append(e.getMessage());
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(800, 400));

        JOptionPane.showMessageDialog(this, scroll, "Associações Professor x Disciplina", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(graficoprofessordisciplina::abrir);
    }
}

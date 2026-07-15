package projeto;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class graficocursodisciplina extends JFrame {

    private JPanel painelCentral;

    public graficocursodisciplina() {
        setTitle("📊 Gráfico - Curso x Disciplina");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== Menu Superior =====
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGraficos = new JMenu("Selecionar Gráfico");

        JMenuItem itemBarras = new JMenuItem("📈 Gráfico de Barras");
        JMenuItem itemPizza = new JMenuItem("🥧 Gráfico de Pizza");

        itemBarras.addActionListener(e -> mostrarGrafico("barras"));
        itemPizza.addActionListener(e -> mostrarGrafico("pizza"));

        menuGraficos.add(itemBarras);
        menuGraficos.add(itemPizza);
        menuBar.add(menuGraficos);
        setJMenuBar(menuBar);

        // ===== Painel Central com degradê =====
        painelCentral = new JPanel(new BorderLayout()) {
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
        add(painelCentral, BorderLayout.CENTER);

        // ===== Painel Inferior =====
        JPanel painelInferior = new JPanel();
        painelInferior.setOpaque(false);
        painelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));

        JButton btnVerAssociacoes = new JButton("📄 Ver Associações");
        btnVerAssociacoes.setFont(new Font("Arial", Font.BOLD, 14));
        btnVerAssociacoes.setBackground(new Color(60, 179, 113));
        btnVerAssociacoes.setForeground(Color.black);
        btnVerAssociacoes.setFocusPainted(false);
        btnVerAssociacoes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVerAssociacoes.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1, true));
        btnVerAssociacoes.addActionListener(e -> mostrarAssociacoes());

        // Efeito hover no botão inferior
        btnVerAssociacoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btnVerAssociacoes.setBackground(new Color(60, 179, 113).brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btnVerAssociacoes.setBackground(new Color(60, 179, 113)); }
        });

        painelInferior.add(btnVerAssociacoes);
        add(painelInferior, BorderLayout.SOUTH);

        mostrarGrafico("barras");
    }

    private void mostrarGrafico(String tipo) {
        painelCentral.removeAll();
        ChartPanel chartPanel;

        if (tipo.equals("barras")) {
            chartPanel = criarGraficoBarras();
        } else {
            chartPanel = criarGraficoPizza();
        }

        chartPanel.setOpaque(false);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painelCentral.add(chartPanel, BorderLayout.CENTER);
        painelCentral.revalidate();
        painelCentral.repaint();
    }

    // ===== Gráfico de Barras =====
    ChartPanel criarGraficoBarras() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Integer> cursoQtdDisc = new LinkedHashMap<>();

        String sql = """
            SELECT c.nome_curso, COUNT(cd.cod_disc) AS qtd
            FROM Curso c
            LEFT JOIN Curso_Disciplina cd ON c.cod_curso = cd.cod_curso
            GROUP BY c.nome_curso
            ORDER BY c.nome_curso
        """;

        try (Connection conn = Conexao.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String curso = rs.getString("nome_curso");
                int qtd = rs.getInt("qtd");
                cursoQtdDisc.put(curso, qtd);
            }

            for (Map.Entry<String, Integer> entry : cursoQtdDisc.entrySet()) {
                dataset.addValue(entry.getValue(), "Disciplinas", entry.getKey());
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Disciplinas por Curso",
                "Curso",
                "Quantidade de Disciplinas",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        chart.setBackgroundPaint(new Color(240, 245, 250));
        chart.getTitle().setPaint(new Color(20, 40, 80));
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 18));

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(245, 248, 255));
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(70, 130, 180));
        renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter());
        renderer.setShadowVisible(true);

        return new ChartPanel(chart);
    }

    // ===== Gráfico de Pizza =====
    ChartPanel criarGraficoPizza() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        String sql = """
            SELECT c.nome_curso, COUNT(cd.cod_disc) AS total_disc
            FROM Curso c
            LEFT JOIN Curso_Disciplina cd ON c.cod_curso = cd.cod_curso
            GROUP BY c.nome_curso
            ORDER BY c.nome_curso
        """;

        try (Connection conn = Conexao.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                dataset.setValue(rs.getString("nome_curso"), rs.getInt("total_disc"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Distribuição de Disciplinas por Curso",
                dataset,
                true, true, false
        );

        chart.setBackgroundPaint(new Color(240, 245, 250));
        chart.getTitle().setPaint(new Color(20, 40, 80));
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 18));

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(240, 245, 250));
        plot.setLabelFont(new Font("Arial", Font.PLAIN, 12));
        plot.setOutlineVisible(false);
        plot.setSectionOutlinesVisible(false);

        return new ChartPanel(chart);
    }

    // ===== Mostrar Associações =====
    private void mostrarAssociacoes() {
        StringBuilder sb = new StringBuilder();
        String sql = """
            SELECT c.nome_curso, d.nome_disc
            FROM Curso c
            LEFT JOIN Curso_Disciplina cd ON c.cod_curso = cd.cod_curso
            LEFT JOIN Disciplina d ON cd.cod_disc = d.cod_disc
            ORDER BY c.nome_curso, d.nome_disc
        """;

        try (Connection conn = Conexao.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            String cursoAtual = "";
            while (rs.next()) {
                String curso = rs.getString("nome_curso");
                String disc = rs.getString("nome_disc");

                if (!curso.equals(cursoAtual)) {
                    sb.append("\n\n📘 ").append(curso).append(":\n");
                    cursoAtual = curso;
                }
                if (disc != null) {
                    sb.append("  • ").append(disc).append("\n");
                } else {
                    sb.append("  • Sem disciplinas cadastradas.\n");
                }
            }

            JTextArea area = new JTextArea(sb.toString());
            area.setEditable(false);
            area.setFont(new Font("Consolas", Font.PLAIN, 13));
            JScrollPane scroll = new JScrollPane(area);
            scroll.setPreferredSize(new Dimension(550, 400));

            JOptionPane.showMessageDialog(this, scroll, "Associações Curso ↔ Disciplina", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar associações: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new graficocursodisciplina().setVisible(true));
    }
}

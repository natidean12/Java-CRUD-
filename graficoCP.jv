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
import java.util.List;

public class graficocursoprofessor extends JFrame {

    private JPanel painelCentral;

    public graficocursoprofessor() {
        setTitle("📊 Gráfico - Curso x Professor");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== MENU SUPERIOR =====
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

        // ===== PAINEL CENTRAL COM DEGRADÊ =====
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

        // ===== PAINEL INFERIOR =====
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

        // Efeito hover no botão
        btnVerAssociacoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btnVerAssociacoes.setBackground(new Color(60, 179, 113).brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btnVerAssociacoes.setBackground(new Color(60, 179, 113)); }
        });

        painelInferior.add(btnVerAssociacoes);
        add(painelInferior, BorderLayout.SOUTH);

        // Mostra o gráfico de barras por padrão
        mostrarGrafico("barras");
    }

    // ======================= MOSTRAR GRÁFICO =======================
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

    // ======================= GRÁFICO DE BARRAS =======================
    public ChartPanel criarGraficoBarras() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, List<String>> cursoProfessores = new LinkedHashMap<>();

        String sql = """
            SELECT c.nome_curso, p.nome
            FROM Curso c
            LEFT JOIN Curso_Professor cp ON c.cod_curso = cp.cod_curso
            LEFT JOIN Professor p ON cp.id_prof = p.id_prof
            ORDER BY c.nome_curso, p.nome;
        """;

        try (Connection conn = Conexao.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String curso = rs.getString("nome_curso");
                String prof = rs.getString("nome");
                if (prof != null) {
                    cursoProfessores.computeIfAbsent(curso, k -> new ArrayList<>()).add(prof);
                } else {
                    // Garante que o curso apareça no gráfico mesmo sem professores
                    cursoProfessores.putIfAbsent(curso, new ArrayList<>());
                }
            }

            for (Map.Entry<String, List<String>> entry : cursoProfessores.entrySet()) {
                dataset.addValue(entry.getValue().size(), "Professores", entry.getKey());
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "📊 Professores por Curso",
                "Curso",
                "Quantidade de Professores",
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

    // ======================= GRÁFICO DE PIZZA =======================
    public ChartPanel criarGraficoPizza() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        String sql = """
            SELECT c.nome_curso, COUNT(cp.id_prof) AS total_prof
            FROM Curso c
            LEFT JOIN Curso_Professor cp ON c.cod_curso = cp.cod_curso
            GROUP BY c.nome_curso
            ORDER BY c.nome_curso;
        """;

        try (Connection conn = Conexao.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                dataset.setValue(rs.getString("nome_curso"), rs.getInt("total_prof"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "🥧 Distribuição de Professores por Curso",
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

    // ======================= VER ASSOCIAÇÕES =======================
    public void mostrarAssociacoes() {
        StringBuilder sb = new StringBuilder();
        String sql = """
            SELECT c.nome_curso, p.nome
            FROM Curso c
            LEFT JOIN Curso_Professor cp ON c.cod_curso = cp.cod_curso
            LEFT JOIN Professor p ON cp.id_prof = p.id_prof
            ORDER BY c.nome_curso, p.nome;
        """;

        try (Connection conn = Conexao.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            String cursoAtual = "";
            while (rs.next()) {
                String curso = rs.getString("nome_curso");
                String prof = rs.getString("nome");

                if (!curso.equals(cursoAtual)) {
                    sb.append("\n\n📘 ").append(curso).append(":\n");
                    cursoAtual = curso;
                }
                if (prof != null) {
                    sb.append("  • ").append(prof).append("\n");
                } else {
                    sb.append("  • Sem professores alocados.\n");
                }
            }

            JTextArea area = new JTextArea(sb.toString());
            area.setEditable(false);
            area.setFont(new Font("Consolas", Font.PLAIN, 13));
            JScrollPane scroll = new JScrollPane(area);
            scroll.setPreferredSize(new Dimension(550, 400));

            JOptionPane.showMessageDialog(this, scroll, "Associações Curso ↔ Professor", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar associações: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new graficocursoprofessor().setVisible(true));
    }
}

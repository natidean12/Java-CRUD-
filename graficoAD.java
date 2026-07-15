package projeto;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleInsets;

public class graficoalunodisciplina extends JFrame {

    private static graficoalunodisciplina instance;
    private JPanel painelGraficos;

    public graficoalunodisciplina() {
        super("📈 Gráfico - Aluno x Disciplinas");
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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
        setContentPane(painelFundo);

        // Painel para gráficos lado a lado
        painelGraficos = new JPanel(new GridLayout(1, 2, 15, 10));
        painelGraficos.setOpaque(false);
        painelGraficos.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painelFundo.add(painelGraficos, BorderLayout.CENTER);

        // Botão "Ver Associações"
        JButton btnVerAssociacoes = new JButton("📄 Ver Associações");
        btnVerAssociacoes.setFont(new Font("Arial", Font.BOLD, 14));
        btnVerAssociacoes.setBackground(new Color(60, 179, 113));
        btnVerAssociacoes.setForeground(Color.black);
        btnVerAssociacoes.setFocusPainted(false);
        btnVerAssociacoes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVerAssociacoes.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1, true));
        btnVerAssociacoes.setPreferredSize(new Dimension(200, 40));
        btnVerAssociacoes.addActionListener(e -> mostrarAssociacoes());

        // Efeito hover no botão
        btnVerAssociacoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btnVerAssociacoes.setBackground(new Color(60, 179, 113).brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btnVerAssociacoes.setBackground(new Color(60, 179, 113)); }
        });

        JPanel painelSul = new JPanel();
        painelSul.setOpaque(false);
        painelSul.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        painelSul.add(btnVerAssociacoes);
        painelFundo.add(painelSul, BorderLayout.SOUTH);

        atualizarGraficos();
    }

    public static void abrir() {
        if (instance == null || !instance.isVisible()) {
            instance = new graficoalunodisciplina();
            instance.setVisible(true);
        } else {
            instance.toFront();
        }
    }

    private void atualizarGraficos() {
        painelGraficos.removeAll();

        try {
            painelGraficos.add(criarGraficoBarra());
            painelGraficos.add(criarGraficoPizza());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar gráficos: " + e.getMessage());
            e.printStackTrace();
        }

        painelGraficos.revalidate();
        painelGraficos.repaint();
    }

    // ================= Gráfico de Barras =================
    ChartPanel criarGraficoBarra() throws SQLException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String sql = """
            SELECT a.nome AS nome_aluno, COUNT(ad.cod_disc) AS total_disciplinas
            FROM Aluno a
            LEFT JOIN Aluno_Disciplina ad ON a.matricula = ad.matricula
            GROUP BY a.nome
            ORDER BY total_disciplinas DESC;
        """;

        try (Connection con = Conexao.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                dataset.addValue(rs.getInt("total_disciplinas"), "Disciplinas", rs.getString("nome_aluno"));
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "📊 Disciplinas por Aluno",
                "Aluno",
                "Quantidade de Disciplinas",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        chart.setBackgroundPaint(new Color(240, 245, 250));
        chart.getTitle().setPaint(new Color(30, 60, 120));
        chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 18));
        chart.setPadding(new RectangleInsets(10, 10, 10, 10));

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(new Color(200, 200, 200));
        plot.setRangeGridlinePaint(new Color(200, 200, 200));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(70, 130, 180));
        renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter());
        renderer.setShadowVisible(true);

        return new ChartPanel(chart);
    }

    // ================= Gráfico de Pizza =================
    ChartPanel criarGraficoPizza() throws SQLException {
        DefaultPieDataset dataset = new DefaultPieDataset();

        String sql = """
            SELECT a.nome AS nome_aluno, COUNT(ad.cod_disc) AS total_disciplinas
            FROM Aluno a
            LEFT JOIN Aluno_Disciplina ad ON a.matricula = ad.matricula
            GROUP BY a.nome
            ORDER BY total_disciplinas DESC;
        """;

        try (Connection con = Conexao.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                dataset.setValue(rs.getString("nome_aluno"), rs.getInt("total_disciplinas"));
            }
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "🧑‍🎓 Disciplinas por Aluno - Pizza",
                dataset,
                true, true, false
        );

        chart.setBackgroundPaint(new Color(240, 245, 250));
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(240, 245, 250));
        plot.setLabelFont(new Font("Arial", Font.PLAIN, 11));
        plot.setOutlineVisible(false);

        return new ChartPanel(chart);
    }

    // ================= Botão "Ver Associações" =================
    void mostrarAssociacoes() {
        StringBuilder sb = new StringBuilder();
        String sql = """
            SELECT a.nome AS nome_aluno, d.nome_disc
            FROM Aluno a
            LEFT JOIN Aluno_Disciplina ad ON a.matricula = ad.matricula
            LEFT JOIN Disciplina d ON ad.cod_disc = d.cod_disc
            ORDER BY a.nome, d.nome_disc;
        """;

        try (Connection con = Conexao.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            String alunoAtual = "";
            while (rs.next()) {
                String aluno = rs.getString("nome_aluno");
                String disc = rs.getString("nome_disc");

                if (!aluno.equals(alunoAtual)) {
                    if (!alunoAtual.isEmpty()) {
                        // Remove a vírgula final do aluno anterior antes de pular linha
                        if (sb.toString().endsWith(", ")) {
                            sb.setLength(sb.length() - 2);
                        }
                        sb.append("\n");
                    }
                    sb.append("🎓 ").append(aluno).append(": ");
                    alunoAtual = aluno;
                }

                if (disc != null) {
                    sb.append(disc).append(", ");
                } else {
                    sb.append("Sem disciplinas vinculadas, ");
                }
            }

            // Limpa a vírgula do último item
            String texto = sb.toString();
            if (texto.endsWith(", ")) {
                texto = texto.substring(0, texto.length() - 2);
            }

            JTextArea area = new JTextArea(texto);
            area.setEditable(false);
            area.setFont(new Font("Consolas", Font.PLAIN, 13));
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            
            JScrollPane scroll = new JScrollPane(area);
            scroll.setPreferredSize(new Dimension(800, 400));

            JOptionPane.showMessageDialog(this, scroll, "Associações Aluno / Disciplina", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar associações: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(graficoalunodisciplina::abrir);
    }
}

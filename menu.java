package projeto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.jfree.chart.ChartPanel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class menu extends JFrame implements ActionListener {

    JMenuBar barraMenu;
    JMenu menuCadastro, menuMostrar, menuManutencao, menuSair;
    JMenuItem mCurso, mDisciplina, mAluno, mProfessor;
    JMenuItem mCurso1, mDisciplina1, mAluno1, mProfessor1;
    JMenuItem mProfessores, mDisciplinas, mProfessores1, mAluno2;
    JMenuItem mAltCurso, mAltDisc, mAltAluno, mAltProf;
    JMenuItem msair;

    private String usuarioLogado;

    public menu(String usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        // ===== Configuração da janela =====
        setTitle("Sistema Escolar - Semestre 4");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // TESTAR CONEXÃO
        Conexao.testarConexao(this);

        // ===== Fundo com Degradê Claro e Moderno =====
        JPanel painelFundo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Degradê suave: Azul bem clarinho para o branco
                Color cor1 = new Color(224, 242, 254); // Azul bebê suave
                Color cor2 = new Color(255, 255, 255); // Branco puro
                g2.setPaint(new GradientPaint(0, 0, cor1, 0, getHeight(), cor2));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        painelFundo.setLayout(new BorderLayout());
        setContentPane(painelFundo);

        // ===== Barra de Menu Integrada e Clara =====
        barraMenu = new JMenuBar() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fundo branco translúcido (Glassmorphism claro)
                g2.setColor(new Color(255, 255, 255, 180));
                g2.fillRect(0, 0, getWidth(), getHeight());
                
                // Linha fina cinza claro na parte inferior
                g2.setColor(new Color(226, 232, 240));
                g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
                g2.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(super.getPreferredSize().width, 50);
            }
        };
        barraMenu.setOpaque(false);
        barraMenu.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setJMenuBar(barraMenu);

        // ===== Criando os Menus com Cores Escuras para Alto Contraste =====
        menuCadastro = criarMenu("Cadastrar");
        mCurso = criarMenuItem("📚  Curso"); mCurso.addActionListener(this);
        mDisciplina = criarMenuItem("📖  Disciplina"); mDisciplina.addActionListener(this);
        mAluno = criarMenuItem("🧑  Aluno"); mAluno.addActionListener(this);
        mProfessor = criarMenuItem("👨‍🏫  Professor"); mProfessor.addActionListener(this);
        menuCadastro.add(mCurso); menuCadastro.add(mDisciplina);
        menuCadastro.add(mAluno); menuCadastro.add(mProfessor);
        barraMenu.add(menuCadastro);

        menuMostrar = criarMenu("Mostrar");
        mCurso1 = criarMenuItem("📚  Cursos"); mCurso1.addActionListener(this);
        mDisciplina1 = criarMenuItem("📖  Disciplinas"); mDisciplina1.addActionListener(this);
        mAluno1 = criarMenuItem("🧑  Alunos"); mAluno1.addActionListener(this);
        mProfessor1 = criarMenuItem("👨‍🏫  Professores"); mProfessor1.addActionListener(this);
        mProfessores = criarMenuItem("📊  Cursos/Professores"); mProfessores.addActionListener(this);
        mDisciplinas = criarMenuItem("📊  Cursos/Disciplinas"); mDisciplinas.addActionListener(this);
        mProfessores1 = criarMenuItem("📊  Professores/Disciplinas"); mProfessores1.addActionListener(this);
        mAluno2 = criarMenuItem("📊  Alunos/Disciplinas"); mAluno2.addActionListener(this);
        menuMostrar.add(mCurso1); menuMostrar.add(mDisciplina1);
        menuMostrar.add(mAluno1); menuMostrar.add(mProfessor1);
        menuMostrar.addSeparator();
        menuMostrar.add(mProfessores); menuMostrar.add(mDisciplinas);
        menuMostrar.add(mProfessores1); menuMostrar.add(mAluno2);
        barraMenu.add(menuMostrar);

        menuManutencao = criarMenu("Manutenção");
        mAltCurso = criarMenuItem("⚙️  Alterar/Excluir Curso"); mAltCurso.addActionListener(this);
        mAltDisc = criarMenuItem("⚙️  Alterar/Excluir Disciplina"); mAltDisc.addActionListener(this);
        mAltAluno = criarMenuItem("⚙️  Alterar/Excluir Aluno"); mAltAluno.addActionListener(this);
        mAltProf = criarMenuItem("⚙️  Alterar/Excluir Professor"); mAltProf.addActionListener(this);
        menuManutencao.add(mAltCurso); 
        menuManutencao.add(mAltDisc); 
        menuManutencao.add(mAltAluno); 
        menuManutencao.add(mAltProf);
        barraMenu.add(menuManutencao);

        menuSair = criarMenu("Sair");
        msair = criarMenuItem("❌  Sair do Sistema"); msair.addActionListener(e -> System.exit(0));
        menuSair.add(msair);
        barraMenu.add(menuSair);

        // ===== Painel Central (Clean e Elegante) =====
        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.CENTER;

        // Subtítulo
        JLabel lbBoasVindas = new JLabel("BEM-VINDO AO", SwingConstants.CENTER);
        lbBoasVindas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbBoasVindas.setForeground(new Color(14, 116, 144)); // Azul ciano escuro elegante
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 5, 0);
        painelCentral.add(lbBoasVindas, gbc);

        // Título Principal
        JLabel lbTitulo = new JLabel("SISTEMA ESCOLAR", SwingConstants.CENTER);
        lbTitulo.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lbTitulo.setForeground(new Color(15, 23, 42)); // Cinza ardósia bem escuro (quase preto)
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        painelCentral.add(lbTitulo, gbc);

        // Linha divisória
        JPanel linhaDiv = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(14, 116, 144, 80));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        linhaDiv.setPreferredSize(new Dimension(150, 2));
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 20, 0);
        painelCentral.add(linhaDiv, gbc);

        // Usuário Ativo
        JLabel lbUsuario = new JLabel("Operador: " + this.usuarioLogado, SwingConstants.CENTER);
        lbUsuario.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        lbUsuario.setForeground(new Color(71, 85, 105)); // Cinza médio corporativo
        gbc.gridy = 3;
        painelCentral.add(lbUsuario, gbc);

        painelFundo.add(painelCentral, BorderLayout.CENTER);

        // ===== Barra de Status Inferior Sutil =====
        JPanel barraStatus = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        barraStatus.setOpaque(false);
        JLabel lbStatusConexao = new JLabel("● Banco de Dados Conectado");
        lbStatusConexao.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbStatusConexao.setForeground(new Color(34, 197, 94)); // Verde sucesso
        barraStatus.add(lbStatusConexao);
        painelFundo.add(barraStatus, BorderLayout.SOUTH);
    }

    // ===== Estilização dos Menus Principais (Texto Escuro) =====
    private JMenu criarMenu(String nome) {
        JMenu menu = new JMenu("  " + nome + "  ");
        menu.setOpaque(false);
        menu.setForeground(new Color(51, 65, 85)); // Texto cinza-escuro elegante
        menu.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        menu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Efeito hover (muda para o azul ciano ao focar)
        menu.addChangeListener(e -> {
            if (menu.isSelected()) {
                menu.setForeground(new Color(14, 116, 144));
            } else {
                menu.setForeground(new Color(51, 65, 85));
            }
        });

        return menu;
    }

    // ===== Estilização dos Submenus Dropdown (Fundo Claro) =====
    private JMenuItem criarMenuItem(String texto) {
        JMenuItem item = new JMenuItem(texto);
        item.setOpaque(true);
        item.setBackground(Color.WHITE); // Fundo totalmente branco e limpo
        item.setForeground(new Color(15, 23, 42)); // Texto bem escuro
        item.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        item.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        // Hover sutil: fundo fica azul-bebê bem clarinho ao passar o mouse
        item.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                item.setBackground(new Color(224, 242, 254)); 
                item.setForeground(new Color(14, 116, 144));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                item.setBackground(Color.WHITE);
                item.setForeground(new Color(15, 23, 42));
            }
        });
        return item;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        // ===== Cadastro =====
        if (src == mCurso) new curso().setVisible(true);
        else if (src == mDisciplina) new disciplina().setVisible(true);
        else if (src == mAluno) new aluno().setVisible(true);
        else if (src == mProfessor) new professor().setVisible(true);

        // ===== Mostrar =====
        else if (src == mCurso1) new mostrarcurso().setVisible(true);
        else if (src == mDisciplina1) new mostrardisciplina().setVisible(true);
        else if (src == mAluno1) new mostraraluno().setVisible(true);
        else if (src == mProfessor1) new mostrarprofessor().setVisible(true);

        // ===== Cursos/Professores =====
        else if (src == mProfessores) {
            String[] opcoes = {"Gráfico de Barras", "Gráfico de Pizza"};
            int escolha = JOptionPane.showOptionDialog(
                    this,
                    "Qual tipo de gráfico você deseja visualizar?",
                    "Escolha do Gráfico",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]
            );

            graficocursoprofessor grafico = new graficocursoprofessor();

            JFrame frame = new JFrame("📊 Curso × Professor");
            frame.setSize(1100, 700);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            ChartPanel painelGrafico;
            if (escolha == 0) {
                painelGrafico = grafico.criarGraficoBarras();
            } else {
                painelGrafico = grafico.criarGraficoPizza();
            }

            frame.add(painelGrafico, BorderLayout.CENTER);

            JButton btnVerAssociacoes = new JButton("📄 Ver Associações");
            btnVerAssociacoes.setBackground(new Color(14, 116, 144)); // Azul escuro moderno
            btnVerAssociacoes.setForeground(Color.WHITE);
            btnVerAssociacoes.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnVerAssociacoes.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnVerAssociacoes.addActionListener(ae -> grafico.mostrarAssociacoes());

            JPanel painelSul = new JPanel();
            painelSul.setBackground(new Color(241, 245, 249)); // Cinza claro neutro
            painelSul.add(btnVerAssociacoes);
            frame.add(painelSul, BorderLayout.SOUTH);

            frame.setVisible(true);
        }

        // ===== Cursos/Disciplinas =====
        else if (src == mDisciplinas) {
            String[] opcoes = {"Gráfico de Barras", "Gráfico de Pizza"};
            int escolha = JOptionPane.showOptionDialog(
                    this,
                    "Qual tipo de gráfico você deseja visualizar?",
                    "Escolha do Gráfico",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]
            );

            graficocursodisciplina grafico = new graficocursodisciplina();
            grafico.painelCentral.removeAll();

            ChartPanel painelGrafico = (escolha == 0)
                    ? grafico.criarGraficoBarras()
                    : grafico.criarGraficoPizza();

            grafico.painelCentral.add(painelGrafico, BorderLayout.CENTER);
            grafico.painelCentral.revalidate();
            grafico.painelCentral.repaint();

            grafico.setVisible(true);
        }

        // ===== Professores/Disciplinas =====
        else if (src == mProfessores1) {
            String[] opcoes = {"Gráfico de Barras", "Gráfico de Pizza"};
            int escolha = JOptionPane.showOptionDialog(
                    this,
                    "Qual tipo de gráfico você deseja visualizar?",
                    "Escolha do Gráfico",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]
            );

            graficoprofessordisciplina grafico = new graficoprofessordisciplina();

            JFrame frame = new JFrame("Professor x Disciplina");
            frame.setSize(1100, 700);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            ChartPanel painelGrafico = (escolha == 0)
                    ? grafico.criarGraficoBarras()
                    : grafico.criarGraficoPizza();
            frame.add(painelGrafico, BorderLayout.CENTER);

            JButton btnVerAssociacoes = new JButton("👀 Ver Associações");
            btnVerAssociacoes.setBackground(new Color(14, 116, 144));
            btnVerAssociacoes.setForeground(Color.WHITE);
            btnVerAssociacoes.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnVerAssociacoes.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnVerAssociacoes.addActionListener(ae -> grafico.mostrarAssociacoes());

            JPanel painelSul = new JPanel();
            painelSul.setBackground(new Color(241, 245, 249));
            painelSul.add(btnVerAssociacoes);
            frame.add(painelSul, BorderLayout.SOUTH);

            frame.setVisible(true);
        }

        // ===== Aluno/Disciplinas =====
        else if (src == mAluno2) {
            String[] opcoes = {"Gráfico de Barras", "Gráfico de Pizza"};
            int escolha = JOptionPane.showOptionDialog(
                    this,
                    "Qual tipo de gráfico você deseja visualizar?",
                    "Escolha do Gráfico",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]
            );

            graficoalunodisciplina grafico = new graficoalunodisciplina();
            JFrame frame = new JFrame("Aluno x Disciplinas");
            frame.setSize(1100, 700);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            try {
                ChartPanel painelGrafico;
                if (escolha == 0) painelGrafico = grafico.criarGraficoBarra();
                else painelGrafico = grafico.criarGraficoPizza();

                frame.add(painelGrafico, BorderLayout.CENTER);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar gráficos: " + ex.getMessage());
                ex.printStackTrace();
            }

            JButton btnVerAssociacoes = new JButton("👀 Ver Associações");
            btnVerAssociacoes.setBackground(new Color(14, 116, 144));
            btnVerAssociacoes.setForeground(Color.WHITE);
            btnVerAssociacoes.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnVerAssociacoes.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnVerAssociacoes.addActionListener(ae -> grafico.mostrarAssociacoes());
            
            JPanel painelSul = new JPanel();
            painelSul.setBackground(new Color(241, 245, 249));
            painelSul.add(btnVerAssociacoes);
            frame.add(painelSul, BorderLayout.SOUTH);

            frame.setVisible(true);
        }

        // ===== Manutenção =====
        else if (src == mAltCurso) new cursoae().setVisible(true);
        else if (src == mAltDisc) new disciplinaae().setVisible(true);
        else if (src == mAltAluno) new alunoae().setVisible(true);
        else if (src == mAltProf) new professorae().setVisible(true);
    }

    public static void main(String[] args) {
         new login().setVisible(true);
    }
}

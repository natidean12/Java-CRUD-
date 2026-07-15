package projeto;

import java.sql.*;
import javax.swing.*;

public class Conexao {
    private static final String URL = "";
    private static final String USER = "";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void testarConexao(JFrame frame) {
        try (Connection conn = getConnection()) {
            JOptionPane.showMessageDialog(frame, "✅ Conectado ao banco com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "❌ Erro de conexão: " + e.getMessage());
        }
    }

    public static Connection conectar() throws SQLException {
        return getConnection();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author rival
 */
public class conexao {
      private static final String URL = "jdbc:mysql://localhost:3306/Reserva_Esportiva?useSSL=false&useTimezone=true&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "";

    public static Connection conectar() {
        Connection conexao = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            JOptionPane.showMessageDialog(null,"Conexão realizada com sucesso.");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,"O driver não foi encontrado.");
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Erro ao conectar com o banco de dados.");
        }
        return conexao;
    }

    public static PreparedStatement prepareStatmen(String sql) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

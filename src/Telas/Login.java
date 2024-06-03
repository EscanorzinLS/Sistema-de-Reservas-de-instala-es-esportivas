

package Telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame {
    private JTextField nomeField;
    private JPasswordField senhaField;
    private JButton loginButton;
    private JButton fechar;
    private String adm;
    private MenuADM menuADM;
    private Menu menu;
  private JPanel fundo;
  private JLabel l;
    
    
    
    

    // Configurações do banco de dados
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/Reserva_Esportiva";
    static final String USER = "root";
    static final String PASS = "";
    
    

    public Login() {
        super("Login");

        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        fundo= new JPanel();
        fundo.setBackground(Color.BLACK);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setFont(new Font("Arial",Font.BOLD,16));
        nomeLabel.setBounds(50, 30, 80, 25);
        panel.add(nomeLabel);

        nomeField = new JTextField();
        nomeField.setBounds(120, 30, 120, 25);
        panel.add(nomeField);

        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setFont(new Font("Arial",Font.BOLD,16));
        senhaLabel.setBounds(50, 70, 80, 25);
        panel.add(senhaLabel);

        senhaField = new JPasswordField();
        senhaField.setBounds(120, 70, 120, 25);
        panel.add(senhaField);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 120, 100, 25);
        panel.add(loginButton);
        
        
        ImageIcon imgIcon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\logo2.png");
        Image img = imgIcon.getImage();
        Image resizedImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Redimensionar a imagem
        ImageIcon resizedIcon = new ImageIcon(resizedImg);
        l = new JLabel(resizedIcon);
        fundo.add(l, BorderLayout.CENTER);
        add(fundo,BorderLayout.WEST);
        
        
        fechar = new JButton("Fechar");
        loginButton.setBounds(100, 120, 100, 25);
        panel.add(fechar);
        

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String senha = new String(senhaField.getPassword());

                // Autenticando o usuário no banco de dados
                if (autenticarUsuario(nome, senha)) {
                    

                    // Verificando o tipo de usuário e abrindo a tela correspondente
                    if (getTipoUsuario(nome).equals("ADMIN")) {
                        
                        abrirTelaA();
                    } else if (getTipoUsuario(nome).equals("Normal")) {
                        abrirTelaB();
                    } else {
                        JOptionPane.showMessageDialog(null, "Tipo de usuário inválido!");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Nome de usuário ou senha incorretos!");
                }
            }
        });

        add(panel);
        setVisible(true);
    }

    private boolean autenticarUsuario(String nome, String senha) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "SELECT * FROM usuarios WHERE nome='" + nome + "' AND senha='" + senha + "'";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private String getTipoUsuario(String nome) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "SELECT Nivel FROM usuarios WHERE nome='" + nome + "'";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                
                return rs.getString("Nivel");
                
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
    
   

    private void abrirTelaA() {
        // Abra a tela A
        
        adm=nomeField.getText();
         menuADM = new MenuADM();
         menuADM.setLabelText(adm);
        menuADM.setVisible(true);
        setVisible(false);
        
    }

    private void abrirTelaB() {
        // Abra a tela B
        adm=nomeField.getText();
        menu = new Menu();
        menu.setLabelText(adm);
        menu.setVisible(true);
        setVisible(false);
    }
    
    

    public static void main(String[] args) {
        new Login();
    }
}

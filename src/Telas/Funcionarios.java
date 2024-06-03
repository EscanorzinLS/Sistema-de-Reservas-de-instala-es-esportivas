/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Telas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Funcionarios extends JFrame {

    private JTextField txtNome, txtApelido, txtDataNascimento, txtBairro, txtEmail, txtContacto, txtBI;
    private JButton btnAdd, btnUpdate, btnDelete, btnSearch, voltar;
    private JTable table;
    private DefaultTableModel model;
    private JLabel LabId;
    private JPasswordField senha;
    private JComboBox<String> nivelBox;

    private static final String URL = "jdbc:mysql://localhost:3306/reserva_esportiva";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public Funcionarios() {
        setTitle("Gerenciamento de Pessoas");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Painel de Entrada de Dados
        JPanel inputPanel = new JPanel(new GridLayout(10, 2));
inputPanel.setBackground(Color.WHITE);
        inputPanel.add(new JLabel("ID:"));
        LabId = new JLabel();
        inputPanel.add(LabId);

        inputPanel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        inputPanel.add(txtNome);

        inputPanel.add(new JLabel("Apelido:"));
        txtApelido = new JTextField();
        inputPanel.add(txtApelido);

        inputPanel.add(new JLabel("Data de Nascimento:"));
        txtDataNascimento = new JTextField();
        inputPanel.add(txtDataNascimento);

        inputPanel.add(new JLabel("Bairro:"));
        txtBairro = new JTextField();
        inputPanel.add(txtBairro);

        inputPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        inputPanel.add(txtEmail);

        inputPanel.add(new JLabel("Contacto:"));
        txtContacto = new JTextField();
        inputPanel.add(txtContacto);

        inputPanel.add(new JLabel("BI:"));
        txtBI = new JTextField();
        inputPanel.add(txtBI);
        
        inputPanel.add(new JLabel("Tipo:"));
        nivelBox = new JComboBox<>(new String[]{"Normal", "ADMIN"});
        inputPanel.add(nivelBox);
        
        
        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setBounds(50, 70, 80, 25);
        inputPanel.add(senhaLabel);

        senha = new JPasswordField();
        senha.setBounds(120, 70, 120, 25);
        inputPanel.add(senha);

        add(inputPanel, BorderLayout.NORTH);

        // Painel de Botões
        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 2, 2));
        
         ImageIcon addicon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\AdicionarF.png");
        Image img = addicon.getImage();
        Image resizedImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar a imagem
        addicon = new ImageIcon(resizedImg);
        
        btnAdd = new JButton(addicon);
        
          ImageIcon atualicon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\refresh.png");
        Image img2 = atualicon.getImage();
        Image resizedImg2 = img2.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar a imagem
        atualicon = new ImageIcon(resizedImg2);
        btnUpdate = new JButton(atualicon);
        
        ImageIcon apagaricon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\apagar.png");
        Image img3 = apagaricon.getImage();
        Image resizedImg3 = img3.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar a imagem
        apagaricon = new ImageIcon(resizedImg3);
        btnDelete = new JButton(apagaricon);
        
        ImageIcon pesquicon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\Pesquisar.png");
        Image img4 = pesquicon.getImage();
        Image resizedImg4 = img4.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar a imagem
        pesquicon = new ImageIcon(resizedImg4);
        btnSearch = new JButton(pesquicon);
        
        
buttonPanel.add(btnSearch);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        

        add(buttonPanel, BorderLayout.EAST);
        
        
         ImageIcon sairicon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\voltar.png");
        Image img6 = sairicon.getImage();
        Image resizedImg6 = img6.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar a imagem
        sairicon = new ImageIcon(resizedImg6);

        JPanel volta = new JPanel(new GridLayout(2, 2, 80, 100));
        voltar = new JButton(sairicon);
        voltar.setBounds(1, 1, 10, 10);
        voltar.addActionListener(this::Voltar);
        volta.add(voltar);
        add(volta, BorderLayout.WEST);

        // Tabela
        table = new JTable();
        model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("Apelido");
        model.addColumn("Data de Nascimento");
        model.addColumn("Bairro");
        model.addColumn("Email");
        model.addColumn("Contacto");
        model.addColumn("BI");

        JScrollPane tableScrollPane = new JScrollPane(table);
        add(tableScrollPane, BorderLayout.CENTER);

        // Eventos dos botões
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                
                String email = txtEmail.getText();
            if (isValidEmail(email)) {
                
                if(txtBI.getText().length()==13){
                    String nomee = txtNome.getText();
                    if(nomee.matches("[a-zA-Z\\s]+")){
                       adicionarPessoa(); 
                    }else{
                        JOptionPane.showMessageDialog(null, "O nome deve conter somente letras!");
                    }
                     
                }else {
                JOptionPane.showMessageDialog(null, "O numero de BI deve ter 13 caracteres");
            }
               
                
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, insira um endereço de e-mail válido.");
            }
                
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                   String email = txtEmail.getText();
            if (isValidEmail(email)) {
                
                if(txtBI.getText().length()==13){
                    String nomee = txtNome.getText();
                    if(nomee.matches("[a-zA-Z\\s]+")){
                       atualizarPessoa(); 
                    }else{
                        JOptionPane.showMessageDialog(null, "O nome deve conter somente letras!");
                    }
                   
                }else {
                JOptionPane.showMessageDialog(null, "O numero de BI deve ter 13 caracteres");
            }
               
                
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, insira um endereço de e-mail válido.");
            }
                
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apagarPessoa();
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pesquisarPessoas();
                
            }
        });

        setVisible(true);
    }

    private void Voltar(ActionEvent ee) {
        MenuADM madm = new MenuADM();
        madm.setVisible(true);
        dispose();
    }

    private void adicionarPessoa() {
        String nome = txtNome.getText();
        String apelido = txtApelido.getText();
        Date dataNascimento = Date.valueOf(txtDataNascimento.getText());
        String bairro = txtBairro.getText();
        String email = txtEmail.getText();
        String contacto = txtContacto.getText();
        String bi = txtBI.getText();

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO Funcionarios (Nome, Apelido, Data_de_nascimento, Bairro, email, Contacto, bi) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, apelido);
            stmt.setDate(3, dataNascimento);
            stmt.setString(4, bairro);
            stmt.setString(5, email);
            stmt.setString(6, contacto);
            stmt.setString(7, bi);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Pessoa adicionada com sucesso!");
            pesquisarPessoas();
            adicionar_usuario();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao adicionar pessoa.\n" + e);
        }
    }
    
    private void adicionar_usuario() {
        String nome = txtNome.getText();
        String Senha = new String(senha.getPassword());
        String Nivel = new String((String) nivelBox.getSelectedItem());

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO usuarios (Nome, senha, Nivel) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, Senha);
            stmt.setString(3, Nivel);
            
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario adicionado com sucesso!");
            pesquisarPessoas();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao adicionar o usuario.\n" + e);
        }
    }

    private void atualizarPessoa() {
int idfu=Integer.parseInt(LabId.getText());
        String nome = txtNome.getText();
        String apelido = txtApelido.getText();
        Date dataNascimento = Date.valueOf(txtDataNascimento.getText());
        String bairro = txtBairro.getText();
        String email = txtEmail.getText();
        int contacto = Integer.parseInt(txtContacto.getText());
        String bi = txtBI.getText();

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = "UPDATE Funcionarios SET nome = ?, apelido = ?, Data_de_nascimento = ?, bairro = ?, email = ?, contacto = ?, bi = ? WHERE id_func = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, apelido);
            stmt.setDate(3, dataNascimento);
            stmt.setString(4, bairro);
            stmt.setString(5, email);
            stmt.setInt(6, contacto);
            stmt.setString(7, bi);
            stmt.setInt(8,idfu);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Pessoa atualizada com sucesso!");
            pesquisarPessoas();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar pessoa.\n" + e);
        }
    }

    private void apagarPessoa() {
        String BI = txtBI.getText();

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "DELETE FROM Funcionarios WHERE bi =?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, BI);
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Pessoa apagada com sucesso!");
            pesquisarPessoas();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao apagar pessoa./n" + e);
        }
        
        apagar_usuario();
    }
    
    
    private void apagar_usuario(){
        
         String nome = txtNome.getText();
        String Senha = new String(senha.getPassword());
        

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "DELETE FROM usuarios WHERE Nome =? AND senha=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, Senha);
            
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "usuario apagado com sucesso!");
            pesquisarPessoas();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao apagar Usuario./n" + e);
        }
        
        
    }
    

    private void pesquisarPessoas() {
        String Nome = txtNome.getText();
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM Funcionarios WHERE Nome=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, Nome);
            ResultSet rs = stmt.executeQuery();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_func"),
                    rs.getString("nome"),
                    rs.getString("apelido"),
                    rs.getDate("Data_de_nascimento"),
                    rs.getString("Bairro"),
                    rs.getString("email"),
                    rs.getString("contacto"),
                    rs.getString("bi")

                });
                int id = rs.getInt("id_func");
                LabId.setText("" + id);
                txtApelido.setText(rs.getString("apelido"));
                txtDataNascimento.setText("" + rs.getDate("Data_de_nascimento"));
                txtBairro.setText(rs.getString("Bairro"));
                txtEmail.setText(rs.getString("email"));
                txtContacto.setText(rs.getString("contacto"));
                txtBI.setText(rs.getString("bi"));
                
                

            }
            pesquisar_usuario();
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao pesquisar pessoas.\n" + e);
        }
    }
    
    private void pesquisar_usuario(){
        int idusu=Integer.parseInt(LabId.getText());
         String Nome = txtNome.getText();
         
         
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM usuarios WHERE id_usuarios=? AND nome =?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idusu);
            stmt.setString(2, Nome);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
            senha.setText(rs.getString("senha"));
                nivelBox.setSelectedItem(rs.getString("Nivel"));
                
            }

            }
        catch (SQLException ea) {
            ea.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao pesquisar usuario.\n" + ea);
        }
        
        
    }
    
    
    private static boolean isValidEmail(String email) {
        // Expressão regular para validação básica de e-mail
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }
    
    

    public static void main(String[] args) {
        new Funcionarios();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Telas;

import static Telas.Menu.DB_URL;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rival
 */
public class MenuADM extends JFrame {

    private JButton fechar;
    private JButton verfunc;
    private JButton ver_locais,pagamentos;
    private JButton verreservas, excluir_reserva;
    private JLabel id,Logotipo;
    private JPanel tabela;
    private JTable table;
    private DefaultTableModel model;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/Reserva_Esportiva";
    static final String USER = "root";
    static final String PASS = "";

    public MenuADM() {
        setTitle("Menu do Administrador");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        BorderLayout bl = new BorderLayout();

        JPanel painel = new JPanel(new GridLayout(7, 1));
        painel.setBackground(Color.black);
        tabela = new JPanel();
        
        ImageIcon imgIcon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\logo2.png");
        Image img = imgIcon.getImage();
        Image resizedImg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Redimensionar a imagem
        ImageIcon resizedIcon = new ImageIcon(resizedImg);
        JLabel l = new JLabel(resizedIcon);
        painel.add(l);
        
        
        ImageIcon icon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\Funcionarios.png");
        Image img2 = icon.getImage();
        Image resizedImg2 = img2.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar a imagem
        icon = new ImageIcon(resizedImg2);
        
        ImageIcon icon2 = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\Locais.png");
        Image img3 = icon2.getImage();
        Image resizedImg3 = img3.getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Redimensionar a imagem
        icon2 = new ImageIcon(resizedImg3);
        
        ImageIcon reservaicon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\Reservas.png");
        Image img4 = reservaicon.getImage();
        Image resizedImg4 = img4.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar a imagem
        reservaicon = new ImageIcon(resizedImg4);
        
         ImageIcon pagamentoicon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\Pagamentos.png");
        Image img5 = pagamentoicon.getImage();
        Image resizedImg5 = img5.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar a imagem
        pagamentoicon = new ImageIcon(resizedImg5);
        
        ImageIcon sairicon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\voltar.png");
        Image img6 = sairicon.getImage();
        Image resizedImg6 = img6.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar a imagem
        sairicon = new ImageIcon(resizedImg6);

        id = new JLabel("ADM: ");
        id.setForeground(Color.white);
        id.setFont(new Font("Arial",Font.BOLD,18));

        painel.add(id);

        verfunc = new JButton(icon);
        
        
        verfunc.addActionListener(this::funcionarios);
        painel.add(verfunc);

        ver_locais = new JButton(icon2);
        ver_locais.addActionListener(this::ver_locais);
        painel.add(ver_locais);

        verreservas = new JButton(reservaicon);
        verreservas.addActionListener(this::reservas);
        painel.add(verreservas);
        
        
ImageIcon logo = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\logosport.png");
        Image img0 = logo.getImage();
        Image resizedImg0 = img0.getScaledInstance(400, 250, Image.SCALE_SMOOTH); // Redimensionar a imagem
        logo = new ImageIcon(resizedImg0);
        Logotipo=new JLabel(logo);
        Logotipo.setBounds(230, 80, 400, 250);
        add(Logotipo,BorderLayout.CENTER);
        
        pagamentos = new JButton(pagamentoicon);
        pagamentos.addActionListener(this::ver_pagamentos);
        painel.add(pagamentos);
        
        
        fechar = new JButton(sairicon);
        fechar.addActionListener(this::voltar);

        painel.add(fechar);

        table = new JTable();
        model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("ID");
        model.addColumn("Nome do cliente");
        model.addColumn("Local da Reserva");
        model.addColumn("Data incial");
        model.addColumn("Data Final");
        model.addColumn("Descricao");
        model.addColumn("Preco total");
        model.addColumn("ID funcionario");
        model.addColumn("ID Local");

        JScrollPane tableScrollPane = new JScrollPane(table);
        excluir_reserva = new JButton("Excluir");
        excluir_reserva.addActionListener(this::excluir);

        tabela.add(excluir_reserva);
        tabela.add(tableScrollPane);
        tabela.setVisible(false);
        add(tabela, BorderLayout.CENTER);
        add(painel, BorderLayout.WEST);
        setVisible(true);

    }

    private void funcionarios(ActionEvent e) {
        Funcionarios func = new Funcionarios();
        func.setVisible(true);
        dispose();
    }
    
    private void ver_pagamentos(ActionEvent e) {
        pagamento_consulta pc = new pagamento_consulta();
        pc.setVisible(true);
        
    }

    private void ver_locais(ActionEvent ee) {
        Espacos local = new Espacos();
        local.setVisible(true);

    }

    private void reservas(ActionEvent a) {
        tabela.setVisible(true);

        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM reservas";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_reserva"),
                    rs.getString("cliente"),
                    rs.getString("local"),
                    rs.getDate("Data_inicio"),
                    rs.getString("Data_fim"),
                    rs.getString("Descricao"),
                    rs.getString("Preco_total"),
                    rs.getInt("id_funcionario"),
                    rs.getInt("id_local")

                });

            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao pesquisar pessoas.\n" + e);
        }
    }

    private void excluir(ActionEvent ex) {
        // Obtém o índice da linha selecionada na tabela
        int selectedRow = table.getSelectedRow();

        // Verifica se alguma linha está selecionada
        if (selectedRow != -1) {
            // Obtém o ID da reserva na linha selecionada
            int reservaID = (int) model.getValueAt(selectedRow, 0);

            int resposta = JOptionPane.showConfirmDialog(null, "Deseja Cancelar a reserva?", "Confirmação", JOptionPane.YES_NO_OPTION);

            // Verifica a resposta do usuário
            if (resposta == JOptionPane.YES_OPTION) {
                try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS)) {
                    String sql = "DELETE FROM Reservas WHERE id_reserva = ?";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setInt(1, reservaID);

                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Reserva cancelada com sucesso!");
                        // Remove a linha cancelada da tabela
                        model.removeRow(selectedRow);
                    } else {
                        JOptionPane.showMessageDialog(this, "Nenhuma reserva encontrada com o ID: " + reservaID);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erro ao cancelar reserva.\n" + e);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma reserva para cancelar.");
        }
    }

    private void voltar(ActionEvent aea) {
        int resposta=JOptionPane.showConfirmDialog(null, "Deseja realmente terminar a secção?","Terminar Secção",JOptionPane.YES_NO_OPTION);
        if(resposta==JOptionPane.YES_OPTION){
            Login lg = new Login();
        lg.setVisible(true);
        dispose();
        }else if (resposta==JOptionPane.NO_OPTION){
            
        }
    }

    public void setLabelText(String adm) {
        id.setText("ADM: " + adm);
    }

    public static void main(String a[]) {

        new MenuADM();
    }

}

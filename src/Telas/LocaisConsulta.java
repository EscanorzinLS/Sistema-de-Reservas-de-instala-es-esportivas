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

public class LocaisConsulta extends JFrame{
    
    private JComboBox<String> comboBox;
    private JButton ver, fechar;
    private JTable table;
    private DefaultTableModel tableModel;

    public LocaisConsulta() {
        setTitle("Sports Facilities");
        setSize(1100, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create components
        comboBox = new JComboBox<>(new String[]{"Campo", "Ginasio", "Piscina", "Quadra"});
        ver = new JButton("Verificar");
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        // Set up table columns
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Tipo");
        tableModel.addColumn("Comprimento");
        tableModel.addColumn("Largura");
        tableModel.addColumn("Capacidade");
        tableModel.addColumn("Cobertura");
        tableModel.addColumn("Localizacao");
        tableModel.addColumn("Hora Entrada");
        tableModel.addColumn("Hora Saida");
        tableModel.addColumn("Dias Funcionamento");
        tableModel.addColumn("Preco por Hora");
        tableModel.addColumn("Estado");

        
        
        
        
        
         ImageIcon sairicon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\voltar.png");
        Image img6 = sairicon.getImage();
        Image resizedImg6 = img6.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Redimensionar a imagem
        sairicon = new ImageIcon(resizedImg6);
        fechar = new JButton(sairicon);
        fechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               dispose();
            }
        });
        
        ImageIcon logo2 = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\logo2.png");
        Image img12 = logo2.getImage();
        Image resizedImg12 = img12.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Redimensionar a imagem
        logo2 = new ImageIcon(resizedImg12);
        JLabel l2=new JLabel(logo2);
        l2.setBounds(0, 0, 80, 40);
        add(l2,BorderLayout.NORTH);
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        JPanel painel2=new JPanel(new GridLayout(7,4,10,10));
        painel2.setBackground(Color.black);
        panel.setLayout(new FlowLayout());
        panel.add(comboBox);
        panel.add(ver);
        painel2.add(fechar);

        add(painel2,BorderLayout.WEST);
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

       
        ver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscar();
            }
        });
    }

    private void buscar() {
        String selectedType = comboBox.getSelectedItem().toString();
        String url = "jdbc:mysql://localhost:3306/reserva_esportiva";
        String user = "root";
        String password = "";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            String query = "SELECT * FROM local WHERE tipo = '" + selectedType + "'";
            rs = stmt.executeQuery(query);

            // Clear existing data
            tableModel.setRowCount(0);

            // Populate table with data from database
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id_local"),
                        rs.getString("Nome"),
                        rs.getString("Tipo"),
                        rs.getDouble("Comprimento"),
                        rs.getDouble("Largura"),
                        rs.getInt("Capacidade"),
                        rs.getString("Cobertura"),
                        rs.getString("Localizacao"),
                        rs.getTime("Hora_entrada"),
                        rs.getTime("Hora_saida"),
                        rs.getString("Dias_funcionamento"),
                        rs.getDouble("Preco_por_hora"),
                        rs.getString("Estado")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from database: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LocaisConsulta().setVisible(true);
            }
        });
    }
}


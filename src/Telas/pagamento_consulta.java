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

public class pagamento_consulta extends JFrame{
    
    
    private JButton ver, fechar;
    private JTable table;
    private DefaultTableModel tableModel;

    public pagamento_consulta() {
        setTitle("Pagamentos");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        
        ver = new JButton("Verificar");
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        
        tableModel.addColumn("ID");
        tableModel.addColumn("Data do pagamento");
        tableModel.addColumn("Valor pago");
        tableModel.addColumn("ID funcionario");
        tableModel.addColumn("ID Local");
        tableModel.addColumn("ID Reserva");
        
        
        
        
         ImageIcon sairicon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\voltar.png");
        Image img6 = sairicon.getImage();
        Image resizedImg6 = img6.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar a imagem
        sairicon = new ImageIcon(resizedImg6);

        fechar = new JButton(sairicon);
        fechar.setBackground(Color.white);
        fechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               dispose();
            }
        });
        
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        JPanel painel2=new JPanel(new GridLayout(5,4,10,10));
        painel2.setBackground(Color.black);
        panel.setLayout(new FlowLayout());
        
        panel.add(ver);
        painel2.add(fechar);
        
        
        ImageIcon logo2 = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\logo2.png");
        Image img12 = logo2.getImage();
        Image resizedImg12 = img12.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Redimensionar a imagem
        logo2 = new ImageIcon(resizedImg12);
        JLabel l2=new JLabel(logo2);
        l2.setBounds(0, 0, 80, 40);
        add(l2,BorderLayout.NORTH);

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
        
        String url = "jdbc:mysql://localhost:3306/reserva_esportiva";
        String user = "root";
        String password = "";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            String query = "SELECT * FROM pagamento";
            rs = stmt.executeQuery(query);

            
            tableModel.setRowCount(0);

            
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id_pagamento"),
                        rs.getString("Data_pagamento"),
                        rs.getDouble("Valor_pago"),
                        rs.getInt("id_funcionario"),
                        rs.getInt("id_local"),
                        rs.getInt("id_reserva")
                        
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
                new pagamento_consulta().setVisible(true);
            }
        });
    }
}


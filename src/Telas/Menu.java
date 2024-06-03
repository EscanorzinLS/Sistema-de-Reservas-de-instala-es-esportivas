/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Telas;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import jdk.nashorn.internal.scripts.JO;

/**
 *
 * @author rival
 */
public class Menu extends JFrame {
    
    private JButton reservar;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JButton cancelar,ocultar,ver,sair,local;
    private JTable table;
    private DefaultTableModel model;
    private JPanel tabela;
    private JPanel painel_pesquisa;
    private JPanel painel, painel_reserva;
  
    private JTextField area_pesquisa, clienteField,localField,dataInicialField,dataFinalField,descricaoField,precoField;
    private JButton pesquisa;
    private JLabel nomefunc,l,idfuncionario,idlocal,Logotipo,l2;
    private String funcionario;
    
    
    //BD
     static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/Reserva_Esportiva";
    static final String USER = "root";
    static final String PASS = "";
    
    
    public Menu(){
        super("Menu");
        
        
        setSize(700,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setResizable(false);
        setLocationRelativeTo(null);
        
        BorderLayout bl = new BorderLayout();
       
        
       
        
       painel = new JPanel(new GridLayout(6,1));
       painel.setBackground(Color.black);
         tabela= new JPanel();
         
       painel_pesquisa = new JPanel();
       painel_pesquisa.setBackground(Color.black);
       painel_pesquisa.setLayout(new BoxLayout(painel_pesquisa,BoxLayout.X_AXIS));
        painel_pesquisa.setBorder(new EmptyBorder(10,10,10,10));
       
        JLabel pes=new JLabel("                                         Digite o nome do Local Reservado: ");
                
                pes.setForeground(Color.white);
        pes.setFont(new Font("Arial",Font.BOLD,14));
        painel_pesquisa.add(pes);
        
        ImageIcon logo = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\logosport.png");
        Image img0 = logo.getImage();
        Image resizedImg0 = img0.getScaledInstance(400, 250, Image.SCALE_SMOOTH); // Redimensionar a imagem
        logo = new ImageIcon(resizedImg0);
        Logotipo=new JLabel(logo);
        Logotipo.setBounds(230, 80, 400, 250);
        add(Logotipo,BorderLayout.CENTER);
        
        
        
        ImageIcon logo2 = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\logo2.png");
        Image img12 = logo2.getImage();
        Image resizedImg12 = img12.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Redimensionar a imagem
        logo2 = new ImageIcon(resizedImg12);
        l2=new JLabel(logo2);
        l2.setBounds(20, 0, 80, 80);
        add(l2,BorderLayout.NORTH);
        
        area_pesquisa= new JTextField();
        
        painel_pesquisa.add(area_pesquisa);
        
        
        
        ImageIcon pesquicon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\Pesquisar.png");
        Image img7 = pesquicon.getImage();
        Image resizedImg7 = img7.getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Redimensionar a imagem
        pesquicon = new ImageIcon(resizedImg7);
        
        pesquisa= new JButton(pesquicon);
        pesquisa.addActionListener(this::pesquisarReserva);
        painel_pesquisa.add(pesquisa);
       
        nomefunc = new JLabel("ID");
        nomefunc.setForeground(Color.white);
        nomefunc.setFont(new Font("Arial",Font.BOLD,18));
       painel.add(nomefunc,BorderLayout.WEST);
       
       
       ImageIcon reservaricon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\Reservar.png");
        Image imgr = reservaricon.getImage();
        Image resizedImgr = imgr.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar a imagem
        reservaricon = new ImageIcon(resizedImgr);
       
        reservar = new JButton(reservaricon);
                reservar.setBounds(1, 100, 50, 20);
                reservar.addActionListener(this::reserva);
                painel.add(reservar,BorderLayout.WEST);
                
                
                ImageIcon canceicon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\cancelar.png");
        Image imgc = canceicon.getImage();
        Image resizedImgc = imgc.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar a imagem
        canceicon = new ImageIcon(resizedImgc);
                
                cancelar = new JButton(canceicon);
                cancelar.setBounds(1, 100, 50, 20);
                cancelar.addActionListener(this::cancelamento);
                painel.add(cancelar,BorderLayout.WEST);
                
                
                ImageIcon reservaicon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\Reservas.png");
        Image img4 = reservaicon.getImage();
        Image resizedImg4 = img4.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar a imagem
        reservaicon = new ImageIcon(resizedImg4);
               ver = new JButton(reservaicon);
                ver.setBounds(1, 1, 100, 20);
                ver.addActionListener(this::mostrar);
               painel.add(ver,BorderLayout.WEST);
               
               
               ImageIcon icon2 = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\Locais.png");
        Image img3 = icon2.getImage();
        Image resizedImg3 = img3.getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Redimensionar a imagem
        icon2 = new ImageIcon(resizedImg3);
               local = new JButton(icon2);
        local.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               LocaisConsulta lc = new LocaisConsulta();
               lc.setVisible(true);
            }
        });
               painel.add(local);
               
               
                ImageIcon sairicon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\voltar.png");
        Image img6 = sairicon.getImage();
        Image resizedImg6 = img6.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar a imagem
        sairicon = new ImageIcon(resizedImg6);
                 sair = new JButton(sairicon);
                sair.setBounds(1, 1, 100, 20);
                sair.addActionListener(this::logout);
               painel.add(sair,BorderLayout.WEST);


         table = new JTable();
        model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("ID");
        model.addColumn("Cliente");
        model.addColumn("Estabelecimento da Reserva");
        model.addColumn("Data incial");
        model.addColumn("Data Final");
        model.addColumn("Descrição");
        model.addColumn("Preço total");
        
       

        JScrollPane tableScrollPane = new JScrollPane(table);
        
        ImageIcon ocultaricon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\ocultar.png");
        Image img11 = ocultaricon.getImage();
        Image resizedImg11 = img11.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar a imagem
        ocultaricon = new ImageIcon(resizedImg11);
        ocultar = new JButton(ocultaricon);
        ocultar.addActionListener(this::Ocultar);
        tabela.add(ocultar);
        tabela.add(tableScrollPane);
        tabela.setVisible(false);
                
                
      
      
      
        
     
      
      
             
             
             add(painel_pesquisa,BorderLayout.NORTH);
                add(painel,BorderLayout.WEST);
               
                add(tabela, BorderLayout.CENTER);
            setVisible(true);    
          
        
        
                
                
    }
    
    private void reserva(ActionEvent e){
        funcionario=nomefunc.getText();
        fazer_reserva fazer = new fazer_reserva();
        fazer.setFuncText(funcionario);
        fazer.setVisible(true);
        
    }
    
    private void cancelamento(ActionEvent ex) {
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

        
        public void pesquisarReserva(ActionEvent e){
            tabela.setVisible(true);
         String Local = area_pesquisa.getText();
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM reservas WHERE local=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, Local);
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
                    

                });
        
              

            }
        } catch (SQLException ee) {
            ee.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao pesquisar pessoas.\n" + e);
        }
    }
        
    
    
    private void mostrar(ActionEvent ee){
        
       
        
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
                    

                });
        
        
     } }catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao pesquisar pessoas.\n" + e);
        }
    }
    
    private void Ocultar(ActionEvent ea){
        tabela.setVisible(false);
        
    }
    
    private void logout(ActionEvent r){
        
        int resposta=JOptionPane.showConfirmDialog(null, "Deseja realmente terminar a secção?","Terminar Secção",JOptionPane.YES_NO_OPTION);
        if(resposta==JOptionPane.YES_OPTION){
            Login lg = new Login();
        lg.setVisible(true);
        dispose();
        }else if (resposta==JOptionPane.NO_OPTION){
            
        }
        
    }
    
    public void setLabelText(String adm){
        nomefunc.setText(adm);
    }
    
   
    
    
                public static void main(String a[]){
                    
                    new Menu();
                }
    }
    


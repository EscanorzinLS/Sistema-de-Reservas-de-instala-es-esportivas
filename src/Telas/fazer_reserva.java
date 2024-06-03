/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Telas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author rival
 */
public class fazer_reserva extends JFrame{
     private JTextField area_pesquisa, clienteField,localField,dataInicialField,dataFinalField,descricaoField;
    private JButton adicionar, cancelar,calcular;
    private JLabel nomefunc,l,idfuncionario,idlocal,precolbl;
    private JPanel painel_reserva,painel_btn, pn_esquerdo,pn_direito;
    private DateTimeFormatter format;
    private int id=0;
    private int IdF=0;
    private int IDreserva=0;
    private double precoTotal = 0.00;

    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/reserva_esportiva";
    static final String USER = "root";
    static final String PASS = "";
    
    public fazer_reserva(){
        
        super("Reservar");
        
        
        setSize(400,290);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
       setResizable(false);
        setLocationRelativeTo(null);
        
        BorderLayout bl = new BorderLayout();
        painel_reserva= new JPanel(new GridLayout(8,1,10,10));
        painel_btn= new JPanel(new GridLayout(1,6,80,5));
        pn_direito= new JPanel();
        pn_esquerdo= new JPanel();
        
        clienteField = new JTextField();
        localField = new JTextField();
        dataInicialField = new JTextField();
        dataFinalField = new JTextField();
        descricaoField = new JTextField();
      
        idfuncionario = new JLabel();
        idlocal = new JLabel();
        clienteField = new JTextField();
        localField = new JTextField();
        dataInicialField = new JTextField();
        dataFinalField = new JTextField();
        descricaoField = new JTextField();
        precolbl = new JLabel("Total: ");
        
        idfuncionario = new JLabel("ID");
        idlocal = new JLabel("ID Lcl");
        
        
        calcular = new JButton("Calcular");
        calcular.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                
              
                String data_i= dataInicialField.getText();
                String data_f= dataFinalField.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                
                    
                    
                
                try {
                    
                    
                        LocalDateTime startDate = LocalDateTime.parse(data_i, formatter);
                        LocalDateTime endDate = LocalDateTime.parse(data_f, formatter);
                        Duration duration = Duration.between(startDate, endDate);
                        long hours = duration.toHours();
                        double preco = getPreco();
                        double calculationResult = hours * preco;
                        int Id=getID();
                        int idfuncio = Buscar_id();
                        int idR= BuscarIDR();
                        precoTotal = calculationResult;
                        id=Id;
                        IdF=idfuncio;
                        IDreserva=idR;
                        
                        if(precoTotal<0){
                            JOptionPane.showMessageDialog(null,"A data final não pode ser antes Da Data Inicial");
                        }else{
                        
                        precolbl.setText(precoTotal + "MT");
                        idlocal.setText("ID do Local: "+ id);
                        idfuncionario.setText("ID do Funcionario: "+ IdF);
                        
                        }

                       
                    } catch (DateTimeParseException ex) {
                        precolbl.setText("Formato inválido! Siga a sintaxe: yyyy-MM-dd HH:mm:ss.");
                    }catch(NullPointerException i){
                        JOptionPane.showMessageDialog(null, i);
                    }catch(RuntimeException c){
                        JOptionPane.showMessageDialog(null, c);
                    }
                
            }
            
    });
        
        
        
        painel_reserva.add(new JLabel("Cliente:"));
      painel_reserva.add(clienteField);
      
      painel_reserva.add(new JLabel("Local:"));
      painel_reserva.add(localField);
      
      painel_reserva.add(new JLabel("Data inicial:"));
      painel_reserva.add(dataInicialField);
      
      painel_reserva.add(new JLabel("Data final:"));
      painel_reserva.add(dataFinalField);
      
      painel_reserva.add(new JLabel("Descrição:"));
      painel_reserva.add(descricaoField);
      
      painel_reserva.add(new JLabel("Preço total:"));
      painel_reserva.add(precolbl);
      painel_reserva.add(idfuncionario);
      painel_reserva.add(calcular);
      painel_reserva.add(idlocal);
        
         add(painel_reserva,BorderLayout.CENTER);
         
         
         adicionar= new JButton("Reservar");
         adicionar.addActionListener(this::reservarr);
         painel_btn.add(adicionar);
         
          cancelar= new JButton("Cancelar");
          cancelar.addActionListener(this::fechar);
         painel_btn.add(cancelar);
         
         add(painel_btn,BorderLayout.SOUTH);
         add(pn_direito,BorderLayout.EAST);
         add(pn_esquerdo,BorderLayout.WEST);
        setVisible(true);
        
        
    }
    
    
    public void reservarr(ActionEvent g){
        
        
        
        
         String nome = clienteField.getText();
        String local = localField.getText();
        String datainicio = dataInicialField.getText();
       String datafim = dataFinalField.getText();
        String descricao = descricaoField.getText();
        double preco = precoTotal;

        
       
            try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS)) {
            
            
            
            String sql = "INSERT INTO reservas (cliente, local, Data_inicio, data_fim, descricao, preco_total) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, local);
            stmt.setString(3, datainicio);
            stmt.setString(4, datafim);
            stmt.setString(5, descricao);
            stmt.setDouble(6, preco);
            
            stmt.executeUpdate();
            registar_pagamento();
            JOptionPane.showMessageDialog(null, "Reserva adicionada com sucesso!");
           
        } catch (SQLException ee) {
            ee.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao adicionar Reserva.\n" + ee);
        }
         

        
    }
    
    
    
                
     public void registar_pagamento(){
         
         
        
        double preco = precoTotal;
        int id_fun = IdF;
        int id_loc = id;
        int id_reser = BuscarIDR();

        
       
            try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS)) {
            
            
            
            String sql = "INSERT INTO pagamento (Valor_pago,id_funcionario,id_local,id_reserva) VALUES (?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDouble(1, preco);
            stmt.setInt(2, id_fun);
            stmt.setInt(3, id_loc);
            stmt.setInt(4, id_reser);
            
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "pagamento adicionado com sucesso!");
           
        } catch (SQLException ee) {
            ee.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao adicionar pagamento.\n" + ee);
        }
         
         
         
         
     }           
                
                
     
    
    
    
    
    public double getPreco() {
        String nome = localField.getText();
       Statement stmt = null;
double preco = 0.00;

try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
    stmt = connection.createStatement(); // Inicialize o objeto Statement aqui

    String sql = "SELECT preco_por_hora FROM local WHERE nome='" + nome + "'";
    ResultSet rs = stmt.executeQuery(sql);

    if (rs.next()) {
        preco = rs.getDouble("preco_por_hora");
        getID();
    }else{
        JOptionPane.showMessageDialog(null, "O local digitado nao existe");
    }

} catch (SQLException e) {
    JOptionPane.showMessageDialog(null, e);
} finally {
    try {
        if (stmt != null) {
            stmt.close(); // Certifique-se de fechar o Statement no bloco finally
        }
    } catch (SQLException ex) {
       JOptionPane.showMessageDialog(null, ex); ;
    }
}

return preco;

    }
    
    
    public int getID() {
        String nome = localField.getText();
       Statement stmt = null;
int id = 0;

try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
    stmt = connection.createStatement(); // Inicialize o objeto Statement aqui

    String sql = "SELECT id_local FROM local WHERE nome='" + nome + "'";
    ResultSet rs = stmt.executeQuery(sql);

    if (rs.next()) {
        id = rs.getInt("id_local");
    }else{
        JOptionPane.showMessageDialog(null, "O local digitado nao existe");
    }

} catch (SQLException e) {
    JOptionPane.showMessageDialog(null, e);
} finally {
    try {
        if (stmt != null) {
            stmt.close(); // Certifique-se de fechar o Statement no bloco finally
        }
    } catch (SQLException ex) {
       JOptionPane.showMessageDialog(null, ex); ;
    }
}

return id;
    }
    
    
    public void setFuncText(String funcionario){
        idfuncionario.setText(funcionario);
        Buscar_id();
    }
    
    public int Buscar_id(){
        int id_f=0;
        String nome=idfuncionario.getText();
        Statement stmt = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
    stmt = connection.createStatement(); // Inicialize o objeto Statement aqui

    String sql = "SELECT id_usuarios FROM usuarios WHERE nome='" + nome + "'";
    ResultSet rs = stmt.executeQuery(sql);

    if (rs.next()) {
        id_f = rs.getInt("id_usuarios");
        
        
    }else{
        JOptionPane.showMessageDialog(null, "O usuario nao existe");
    }

} catch (SQLException e) {
    JOptionPane.showMessageDialog(null, e);
} finally {
    try {
        if (stmt != null) {
            stmt.close(); // Certifique-se de fechar o Statement no bloco finally
        }
    } catch (SQLException ex) {
       JOptionPane.showMessageDialog(null, ex); ;
    }
}
        
        return id_f;
    }
    
    public int BuscarIDR(){
        int idRes=0;
        String cliente=clienteField.getText();
        String local= localField.getText();
                String datai= dataInicialField.getText();
                
                     Statement stmt = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
    stmt = connection.createStatement(); // Inicialize o objeto Statement aqui

    String sql = "SELECT id_reserva FROM reservas WHERE cliente='" + cliente + "'" + "AND local='"+local+"'"+"AND data_inicio='"+datai+"'";
    ResultSet rs = stmt.executeQuery(sql);

    if (rs.next()) {
        idRes = rs.getInt("id_reserva");
        
        
    }

} catch (SQLException e) {
    JOptionPane.showMessageDialog(null, e);
} finally {
    try {
        if (stmt != null) {
            stmt.close(); // Certifique-se de fechar o Statement no bloco finally
        }
    } catch (SQLException ex) {
       JOptionPane.showMessageDialog(null, ex); ;
    }
}
        
        
        
        return idRes;
    }
    
    private void fechar(ActionEvent ee){
        dispose();
    }
    
    
    public static void main(String a[]){
        
        new fazer_reserva();
    }
    
    
    
}

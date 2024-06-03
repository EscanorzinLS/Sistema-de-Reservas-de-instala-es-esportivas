/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.table.DefaultTableModel;
import static sun.rmi.registry.RegistryImpl.getID;

public class Espacos extends JFrame {

    private JTextField nomeField, comprimentoField, larguraField, capacidadeField, localizacao, horaEntradaField, horaSaidaField, diasFuncField, precoField;
    private JComboBox<String> tipoBox, coberturaBox, estadoBox;
    private JButton addButton, deleteButton, searchButton, updateButton, mostrarBtn, voltar;
    private JTable tabela;
    private DefaultTableModel model;
    private JPanel painel_btn, painel_dados, p_voltar;

    private static final String URL = "jdbc:mysql://localhost:3306/reserva_esportiva";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public Espacos() {
        setTitle("Gerenciamento de Locais Esportivos");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        painel_dados = new JPanel(new GridLayout(14, 2));
        painel_btn = new JPanel(new GridLayout(3, 1, 10, 10));
        p_voltar = new JPanel(new GridLayout(3, 1, 10, 10));
        Dimension dimencao = new Dimension(500, 400);
        Dimension dimencao2 = new Dimension(50, 40);
        painel_dados.setPreferredSize(dimencao);
        

        painel_dados.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        painel_dados.add(nomeField);

        painel_dados.add(new JLabel("Tipo:"));
        tipoBox = new JComboBox<>(new String[]{"Ginásio", "Quadra", "Piscina", "Campo"});
        painel_dados.add(tipoBox);

        painel_dados.add(new JLabel("Comprimento:"));
        comprimentoField = new JTextField();
        painel_dados.add(comprimentoField);

        painel_dados.add(new JLabel("Largura:"));
        larguraField = new JTextField();
        painel_dados.add(larguraField);

        painel_dados.add(new JLabel("Capacidade:"));
        capacidadeField = new JTextField();
        painel_dados.add(capacidadeField);

        painel_dados.add(new JLabel("Cobertura:"));
        coberturaBox = new JComboBox<>(new String[]{"Aberto", "Fechado"});
        painel_dados.add(coberturaBox);

        painel_dados.add(new JLabel("Localização:"));
        localizacao = new JTextField();
        painel_dados.add(localizacao);

        painel_dados.add(new JLabel("Hora de Entrada:"));
        horaEntradaField = new JTextField();
        painel_dados.add(horaEntradaField);

        painel_dados.add(new JLabel("Hora de Saída:"));
        horaSaidaField = new JTextField();
        painel_dados.add(horaSaidaField);

        painel_dados.add(new JLabel("Dias de Funcionamento:"));
        diasFuncField = new JTextField();
        painel_dados.add(diasFuncField);

        painel_dados.add(new JLabel("Preço por Hora:"));
        precoField = new JTextField();
        painel_dados.add(precoField);

        painel_dados.add(new JLabel("Estado:"));
        estadoBox = new JComboBox<>(new String[]{"Disponível", "Indisponível"});
        painel_dados.add(estadoBox);

        ImageIcon pesquicon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\Pesquisar.png");
        Image img4 = pesquicon.getImage();
        Image resizedImg4 = img4.getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Redimensionar a imagem
        pesquicon = new ImageIcon(resizedImg4);
        searchButton = new JButton(pesquicon);
        searchButton.setPreferredSize(dimencao2);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pesquisarLocal();
            }
        });
        painel_dados.add(searchButton);

        ImageIcon addicon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\Adicionar.png");
        Image img = addicon.getImage();
        Image resizedImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar a imagem
        addicon = new ImageIcon(resizedImg);
        addButton = new JButton(addicon);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String hora_i = horaEntradaField.getText();
                String hora_f = horaSaidaField.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                try {

                    LocalTime startTime = LocalTime.parse(hora_i, formatter);
                    LocalTime endTime = LocalTime.parse(hora_f, formatter);
                    Duration duration = Duration.between(startTime, endTime);
                    long hours = duration.toHours();

                    if (hours < 1) {
                        JOptionPane.showMessageDialog(null, "A duração entre hora de entrada e de saida deve ser de pelo menos 1 hora");
                    } else {

                        adicionarLocal();
                    }
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Formato inválido! Siga a sintaxe: HH:mm:ss.");
                } catch (NullPointerException i) {
                    JOptionPane.showMessageDialog(null, i);
                } catch (RuntimeException c) {
                    JOptionPane.showMessageDialog(null, c);
                }

            }
        });
        painel_btn.add(addButton);

        ImageIcon apagaricon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\apagar.png");
        Image img3 = apagaricon.getImage();
        Image resizedImg3 = img3.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar a imagem
        apagaricon = new ImageIcon(resizedImg3);
        deleteButton = new JButton(apagaricon);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarLocal();
            }
        });
        painel_btn.add(deleteButton);

        ImageIcon atualicon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\refresh.png");
        Image img2 = atualicon.getImage();
        Image resizedImg2 = img2.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar a imagem
        atualicon = new ImageIcon(resizedImg2);

        updateButton = new JButton(atualicon);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String hora_i = horaEntradaField.getText();
                String hora_f = horaSaidaField.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                try {

                    LocalTime startTime = LocalTime.parse(hora_i, formatter);
                    LocalTime endTime = LocalTime.parse(hora_f, formatter);
                    Duration duration = Duration.between(startTime, endTime);
                    long hours = duration.toHours();

                    if (hours < 1) {
                        JOptionPane.showMessageDialog(null, "A duração entre hora de entrada e de saida deve ser de pelo menos 1 hora");
                    } else {
                        
                        
                        

                        atualizarLocal();
                    }
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Formato inválido! Siga a sintaxe: HH:mm:ss.");
                } catch (NullPointerException i) {
                    JOptionPane.showMessageDialog(null, i);
                } catch (RuntimeException c) {
                    JOptionPane.showMessageDialog(null, c);
                }

            }
        });
        painel_btn.add(updateButton);

        tabela = new JTable();
        model = new DefaultTableModel();
        tabela.setModel(model);
        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("Tipo");
        model.addColumn("Comprimento(Metros)");
        model.addColumn("Largura(Metros)");
        model.addColumn("Capacidade(pessoas)");
        model.addColumn("Cobertura");
        model.addColumn("Localização");
        model.addColumn("Hora de entrada");
        model.addColumn("Hora de saida");
        model.addColumn("Dias de funcionamento");
        model.addColumn("Preço por hora");
        model.addColumn("Estado");

        JScrollPane tableScrollPane = new JScrollPane(tabela);

        ImageIcon sairicon = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\voltar.png");
        Image img6 = sairicon.getImage();
        Image resizedImg6 = img6.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar a imagem
        sairicon = new ImageIcon(resizedImg6);
        voltar = new JButton(sairicon);
        voltar.addActionListener(this::voltar);
        p_voltar.add(voltar);

        add(p_voltar, BorderLayout.WEST);
        add(tableScrollPane, BorderLayout.CENTER);
        add(painel_dados, BorderLayout.NORTH);
        add(painel_btn, BorderLayout.EAST);

        setVisible(true);

    }

    private void voltar(ActionEvent aa) {
        dispose();
    }

    private void adicionarLocal() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO local (nome, tipo, comprimento, largura, capacidade, cobertura, localizacao, hora_entrada, hora_saida, dias_funcionamento, preco_por_hora, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nomeField.getText());
                stmt.setString(2, (String) tipoBox.getSelectedItem());
                stmt.setFloat(3, Float.parseFloat(comprimentoField.getText()));
                stmt.setFloat(4, Float.parseFloat(larguraField.getText()));
                stmt.setInt(5, Integer.parseInt(capacidadeField.getText()));
                stmt.setString(6, (String) coberturaBox.getSelectedItem());
                stmt.setString(7, localizacao.getText());
                stmt.setTime(8, Time.valueOf(horaEntradaField.getText()));
                stmt.setTime(9, Time.valueOf(horaSaidaField.getText()));
                stmt.setString(10, diasFuncField.getText());
                stmt.setBigDecimal(11, new BigDecimal(precoField.getText()));
                stmt.setString(12, (String) estadoBox.getSelectedItem());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Local adicionado com sucesso!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao adicionar local: " + ex.getMessage());
        }
    }

    private void eliminarLocal() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do local a ser eliminado:");
        if (nome != null && !nome.isEmpty()) {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "DELETE FROM local WHERE nome = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, nome);
                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(this, "Local eliminado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Nenhum local encontrado com o nome especificado.");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao eliminar local: " + ex.getMessage());
            }
        }
    }

    private void pesquisarLocal() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do local a ser pesquisado:");
        if (nome != null && !nome.isEmpty()) {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT * FROM local WHERE nome = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, nome);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            nomeField.setText(rs.getString("nome"));
                            tipoBox.setSelectedItem(rs.getString("tipo"));
                            comprimentoField.setText(String.valueOf(rs.getFloat("comprimento")));
                            larguraField.setText(String.valueOf(rs.getFloat("largura")));
                            capacidadeField.setText(String.valueOf(rs.getInt("capacidade")));
                            coberturaBox.setSelectedItem(rs.getString("cobertura"));
                            localizacao.setText(rs.getString("localizacao"));
                            horaEntradaField.setText(rs.getTime("hora_entrada").toString());
                            horaSaidaField.setText(rs.getTime("hora_saida").toString());
                            diasFuncField.setText(rs.getString("dias_funcionamento"));
                            precoField.setText(rs.getBigDecimal("preco_por_hora").toString());
                            estadoBox.setSelectedItem(rs.getString("estado"));

                            model.setRowCount(0);

                            //while (rs.next()) {
                            model.addRow(new Object[]{
                                rs.getInt("id_local"),
                                rs.getString("nome"),
                                rs.getString("tipo"),
                                rs.getFloat("comprimento"),
                                rs.getFloat("largura"),
                                rs.getInt("capacidade"),
                                rs.getString("cobertura"),
                                rs.getString("localizacao"),
                                rs.getTime("hora_entrada"),
                                rs.getTime("hora_saida"),
                                rs.getString("dias_funcionamento"),
                                rs.getBigDecimal("preco_por_hora"),
                                rs.getString("estado")

                            });

                            //   }
                        } else {
                            JOptionPane.showMessageDialog(this, "Nenhum local encontrado com o nome especificado.");
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao pesquisar local: " + ex.getMessage());
            }
        }
    }

    private void atualizarLocal() {
        String nome = nomeField.getText();
        if (nome != null && !nome.isEmpty()) {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "UPDATE local SET tipo = ?, comprimento = ?, largura = ?, capacidade = ?, cobertura = ?, localizacao = ?, hora_entrada = ?, hora_saida = ?, dias_funcionamento = ?, preco_por_hora = ?, estado = ? WHERE nome = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, (String) tipoBox.getSelectedItem());
                    stmt.setFloat(2, Float.parseFloat(comprimentoField.getText()));
                    stmt.setFloat(3, Float.parseFloat(larguraField.getText()));
                    stmt.setInt(4, Integer.parseInt(capacidadeField.getText()));
                    stmt.setString(5, (String) coberturaBox.getSelectedItem());
                    stmt.setString(6, (String) localizacao.getText());
                    stmt.setTime(7, Time.valueOf(horaEntradaField.getText()));
                    stmt.setTime(8, Time.valueOf(horaSaidaField.getText()));
                    stmt.setString(9, diasFuncField.getText());
                    stmt.setBigDecimal(10, new BigDecimal(precoField.getText()));
                    stmt.setString(11, (String) estadoBox.getSelectedItem());
                    stmt.setString(12, nome);
                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(this, "Local atualizado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Nenhum local encontrado com o nome especificado.");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao atualizar local: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Espacos();
    }
}

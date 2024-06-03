/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Telas;

/**
 *
 * @author rival
 */
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Tela_inicial extends JFrame {
    private Login lg;
    private JProgressBar progressBar;

    public Tela_inicial() {
        
        setTitle("Carregando o sistema");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        

        JPanel painel = new JPanel();
        
        painel.setBackground(Color.black);
        
        
        ImageIcon logo = new ImageIcon("C:\\Users\\rival\\Documents\\NetBeansProjects\\Sistema de Reservas\\src\\imagens\\logo2.png");
        Image img0 = logo.getImage();
        Image resizedImg0 = img0.getScaledInstance(400, 300, Image.SCALE_SMOOTH); // Redimensionar a imagem
        logo = new ImageIcon(resizedImg0);
        
        JLabel imageLabel = new JLabel(logo);
        imageLabel.setBounds(0, 0, 600, 300);
        painel.add(imageLabel);

        // Configurando a barra de progresso
        progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(50, 320, 500, 30);
        progressBar.setStringPainted(true);
        add(progressBar);
        add(painel);

        // Iniciar o carregamento
        startLoading();
    }

    private void startLoading() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            private int progress = 0;

            @Override
            public void run() {
                progressBar.setValue(progress);
                progress += 1;
                if (progress > 100) {
                    timer.cancel();
                    openNextScreen();
                }
            }
        };
        timer.schedule(task, 0, 50); // Atualiza a barra de progresso a cada 50ms
    }

    private void openNextScreen() {
        // Fecha a tela de carregamento
        dispose();

        // Abre a nova tela
        lg = new Login();
        lg.setVisible(true);
    }

    public static void main(String[] args) {
        // Cria e exibe a tela de carregamento
        Tela_inicial tela_inicial = new Tela_inicial();
        tela_inicial.setVisible(true);
    }
}


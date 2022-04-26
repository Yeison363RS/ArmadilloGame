package test;

import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.*;
 
public class EjemploCapturar extends JFrame {
 
    private JPanel contentPane;
 
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EjemploCapturar frame = new EjemploCapturar();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
 
    /**
     * Create the frame.
     */
    public EjemploCapturar() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
 
        //Invocamos el método, ahora si funcionara
        contentPane.setFocusable(true);
 
        //Eventos
 
        contentPane.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e){
                //Aqui no funcionara
            }
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    JOptionPane.showMessageDialog(contentPane, "Has pulsado Enter");
                }
                if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
                    System.exit(0);
                }
            }
            public void keyReleased(KeyEvent e){
                //Aqui tambien puedes insertar el codigo
            }
        });
    }
 
}

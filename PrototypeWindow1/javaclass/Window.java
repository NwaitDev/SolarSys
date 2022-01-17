package javaclass;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;


public class Window extends JFrame{
    private Dimension dim = new Dimension(1000,500);
    private Container content;
    private JPanel menu, system;



    public Window(){
        super("SolarSys");
        this.setSize(dim);
        content = this.getContentPane();
        content.setLayout(new BorderLayout());

        menu = new JPanel();
        menu.setLayout(new BorderLayout());
        menu.add(new JLabel("menu"));

        system = new JPanel();
        system.setLayout(new BorderLayout());
        system.add(new JLabel("system"));

        //ajout des jpanel au content
        content.add(menu);
        content.add(system, BorderLayout.NORTH);


        //
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    // public static void main(String[] args) {
    //     new Window();
    // }

}

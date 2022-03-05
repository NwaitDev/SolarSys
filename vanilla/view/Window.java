package vanilla.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import vanilla.controller.MapListener;
import vanilla.model.CelestialBody;


public class Window extends JFrame{
    public static final int sizeMap = 1000;
    private Dimension dim = new Dimension(sizeMap, sizeMap);
    private Container content;
    private ArrayList<JPanel> panels;


    public Window(CelestialBody s){
        super("SolarSys");
        this.setSize(dim);
        this.setLocationRelativeTo(null);
        content = this.getContentPane();
        content.setLayout(new BorderLayout());
        panels = new ArrayList<JPanel>();

        MapPanel map = new MapPanel(s, sizeMap);
        map.setBackground(Color.BLACK);


        panels.add(map);

        content.add(map);

        MouseInputListener listener = new MapListener(map);
        map.addMouseListener(listener);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}

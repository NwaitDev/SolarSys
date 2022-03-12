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
    public static final int sizeMap = 700;
    private Dimension dim = new Dimension(sizeMap, sizeMap);
    private Container content;
    private ArrayList<JPanel> panels;


    public void addMapPanel(CelestialBody centricBody){
        MapPanel map = new MapPanel(centricBody, sizeMap);
        map.setBackground(Color.BLACK);
        panels.add(map);
        content.add(map);
        MouseInputListener listener = new MapListener(map);
        map.addMouseListener(listener);
    }

    public Window(CelestialBody s){
        super("SolarSys : "+ s.getName()+" and its satellites");
        this.setSize(dim);
        this.setLocationRelativeTo(null);
        content = this.getContentPane();
        content.setLayout(new BorderLayout());
        panels = new ArrayList<JPanel>();

        addMapPanel(s);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}

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
import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;
import java.awt.event.*;


import java.awt.event.MouseEvent;

import vanilla.controller.MapListener;
import vanilla.model.CelestialBody;


public class Window extends JFrame{
    public static final int sizeMap = 700;
    private Dimension dim = new Dimension(sizeMap, sizeMap);
    private Container content;
    private ArrayList<JPanel> panels;


    public MapPanel addMapPanel(CelestialBody centricBody){
        MapPanel map = new MapPanel(centricBody, sizeMap);
        map.setBackground(Color.BLACK);
        panels.add(map);
        content.add(map);
        MouseInputListener listener = new MapListener(map);
        map.addMouseListener(listener);
        map.addMouseMotionListener(listener);
        return map;
    }

    public void addPanelInfo(CelestialBody body, MapPanel map){
        JPanel info = new JPanel();
        info.setLayout(new BorderLayout());
        info.setBackground(Color.GRAY);
        VisibleBody vBody = new VisibleBody(map,body);
        info.add(new JLabel(vBody.usefullDataToString()));
        info.setVisible(false);

        JButton but =new JButton("Informations");
        
        but.setVisible(true);
        panels.get(0).add(but, BorderLayout.WEST);

        panels.add(info);
        panels.get(0).add(info);

        but.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                info.setVisible(!(info.isVisible()));
            }
        });
        
    }

    public Window(CelestialBody s){
        super("SolarSys : "+ s.getName()+" and its satellite(s)");
        this.setSize(dim);
        this.setLocationRelativeTo(null);
        content = this.getContentPane();
        content.setLayout(new BorderLayout());
        panels = new ArrayList<JPanel>();

        MapPanel map = addMapPanel(s);
        addPanelInfo(s , map);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}

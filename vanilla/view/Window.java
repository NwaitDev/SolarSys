package vanilla.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import vanilla.controller.ButtonListener;
import vanilla.model.CelestialBody;

import javax.swing.JLabel;


public class Window extends JFrame{
    public static final int sizeMap = 1000;
    private Dimension dim = new Dimension(sizeMap, sizeMap);
    private Container content;
    private ArrayList<JPanel> panels;
    private int currentPanel;
    private JPanel sideBar;

    public ArrayList<JPanel> getPanels(){
        return panels;
    }

    public void changePanel(){
        content.remove(0);
        currentPanel = (currentPanel+1)%panels.size();
        content.add(panels.get(currentPanel),0);
        content.add(sideBar, BorderLayout.EAST);
        content.repaint();
    }

    public Window(CelestialBody s){
        super("SolarSys");
        this.setSize(dim);
        this.setLocationRelativeTo(null);
        content = this.getContentPane();
        content.setLayout(new BorderLayout());
        panels = new ArrayList<JPanel>();

        JPanel menu = new JPanel();
        menu.setLayout(new BorderLayout());
        menu.setBackground(Color.BLUE);
        menu.add(new JLabel("MENU"));
        panels.add(menu);

        JPanel map = new MapPanel(s, sizeMap);
        map.setLayout(new BorderLayout());
        map.setBackground(Color.BLACK);
        map.add(new JLabel("MAP"));
        panels.add(map);

        sideBar = new JPanel();
        sideBar.add(new JLabel("sidebar"));
        JButton switchButton = new JButton("SWITCH");
        switchButton.addActionListener(new ButtonListener(this,1));
        sideBar.add(switchButton);
        JButton updateMapButton = new JButton("UPDATE");
        updateMapButton.addActionListener(new ButtonListener(this,2));
        sideBar.add(updateMapButton);

        //ajout des jpanel au content
        // currentPanel = 0;
        // content.add(panels.get(currentPanel),0);
        // content.add(sideBar, BorderLayout.EAST);
        content.add(map);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}

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

import javax.swing.JLabel;


public class Window extends JFrame{
    private Dimension dim = new Dimension(500,500);
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
        content.validate();
    }

    public Window(){
        super("SolarSys");
        this.setSize(dim);
        content = this.getContentPane();
        content.setLayout(new BorderLayout());
        panels = new ArrayList<JPanel>();

        JPanel menu = new JPanel();
        menu.setLayout(new BorderLayout());
        menu.setBackground(Color.BLUE);
        panels.add(menu);

        JPanel map = new MapPanel();
        map.setLayout(new BorderLayout());
        map.setBackground(Color.BLACK);
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
        currentPanel = 0;
        content.add(panels.get(currentPanel),0);
        content.add(sideBar, BorderLayout.EAST);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}

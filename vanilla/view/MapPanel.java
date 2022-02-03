package vanilla.view;

import java.util.ArrayList;
import java.util.*;

import java.awt.*;
import javax.swing.*;

import vanilla.model.CelestialBody;
import vanilla.model.SolarSystem;



public class MapPanel extends JPanel{

    // private SolarSystem solarSystem;
    private CelestialBody referenceFrame;
    private int sizePanel;


    MapPanel(CelestialBody referenceFrame , int sizePanel){
        super();
        this.referenceFrame = referenceFrame ;
        this.setSize(sizePanel,sizePanel);
        this.sizePanel=sizePanel;
    }

    // public void updateView(){
    //     referenceFrame.print();
    // }

    // public void addSun(CelestialBody body){
    //     this.referenceFrame.addSun(body);
    // }

    // public void addPlanet(CelestialBody body){
    //     this.solarSystem.addPlanet(body);
    // }


    /*********************** REPERE *********************/

    private int getCoord(int x){
        return x + sizePanel/2;
    }

    private void drawOval(Graphics2D g2d, int x, int y, int w, int h){
        g2d.fillOval(getCoord(x) - w/2,getCoord(y) - h/2, w,h);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;


        g2d.setColor(Color.red);

        g2d.drawLine(sizePanel/2,0,sizePanel/2,sizePanel);
        g2d.drawLine(0,sizePanel/2,sizePanel,sizePanel/2);

        Iterator<CelestialBody> IterPlanetList = this.referenceFrame.getSatelliteList().iterator();
        //afficher les planetes une par une
        // g2d.setColor(Color.yellow);
        // int x = 0;
        // int y = 0;
        // int w = 50;
        // int h = 50;

        g2d.setColor(Color.yellow);
        g2d.setFont(new Font("Purisa", Font.PLAIN, 13));

        // printing planets
        //finding scale
        CelestialBody sun = this;
        int farthest = (int) this.referenceFrame.getFarthest(sun); //calcul de la plus grande distance pour calculer l'echelle d'affichage
        int h=0;
        while (IterSatelliteList.hasNext()){
            CelestialBody curr = IterSatelliteList.next();
            String xS = Integer.toString((int) ((curr.getPosition().x * (0.9*(sizePanel/2))) / farthest));
            String yS = Integer.toString((int) ((curr.getPosition().y * (0.9*(sizePanel/2))) / farthest));

            int x = (int) (curr.getPosition().x*(0.9*(sizePanel/2)))/farthest;
            int y = (int) (curr.getPosition().y*(0.9*(sizePanel/2)))/farthest;
            // float w = curr.getDiameter();
            int w =30;
            drawOval(g2d, x*100, y*100, w, w);


            String name= curr.getName();
            h+=20;
            g2d.drawString(name, 20, h);
            g2d.drawString(xS, 60, h);
            
        }
    }


    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

}

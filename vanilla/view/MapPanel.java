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

    public void updateView(){
        //referenceFrame.print();
        
    }

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

    private int getPosRelative(int curr, int ref, float farthest, int sizePanel){
        return (int) ((curr*(0.9*(sizePanel/2)))/farthest);
       
    }

   

    private void drawOval(Graphics2D g2d, int x, int y, int w, int h){
        g2d.fillOval(getCoord(x) - w/2,getCoord(y) - h/2, w,h);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;


        // repere : 
        g2d.setColor(Color.red);

        g2d.drawLine(sizePanel/2,0,sizePanel/2,sizePanel);
        g2d.drawLine(0,sizePanel/2,sizePanel,sizePanel/2);



        // planetes :
        Iterator<CelestialBody> IterSatelliteList = this.referenceFrame.getSatelliteList().iterator();

        g2d.setColor(Color.yellow);
        g2d.setFont(new Font("Purisa", Font.PLAIN, 13));

        //draw Reference
        int refWidth = 30;
        drawOval(g2d, 0, 0, refWidth, refWidth);

        g2d.setColor(Color.blue);

        // finding scale for distance
        int farthest = (int) this.referenceFrame.getFarthest(); // récupérer la plus grande distance pour calculer l'echelle d'affichage

        //finding scale for width
        

        int h=0;
        while (IterSatelliteList.hasNext()){
            CelestialBody curr = IterSatelliteList.next();


            String xS = Integer.toString(getPosRelative(curr.getPosition().x, this.referenceFrame.getPosition().x, farthest, sizePanel));
            String yS = Integer.toString(getPosRelative(curr.getPosition().y, this.referenceFrame.getPosition().y, farthest, sizePanel));

            int x = getPosRelative(curr.getPosition().x, this.referenceFrame.getPosition().x, farthest, sizePanel);
            int y = getPosRelative(curr.getPosition().y, this.referenceFrame.getPosition().y, farthest, sizePanel);

            //float w = curr.getDiameter() * scaleWidth;
            int w =15;
            drawOval(g2d, x, y, w, w);


            String name= curr.getName();
            h+=20;
            g2d.drawString(name, 20, h);
            g2d.drawString(xS, 100, h);
            g2d.drawString(yS, 180, h);
            
        }
        g2d.drawString(Integer.toString(farthest), 300, h);
        
    }


    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

}

package vanilla.view;

import java.util.*;

import java.awt.*;
import javax.swing.*;

import vanilla.model.CelestialBody;

public class MapPanel extends JPanel{

    // private SolarSystem solarSystem;
    private CelestialBody referenceFrame;
    private int sizePanel;
    private VisibleBody refBody;
    private ArrayList<VisibleBody> satelliteList;

    public CelestialBody getReferenceFrame() {
        return referenceFrame;
    }

    MapPanel(CelestialBody referenceFrame , int sizePanel){
        super();
        this.referenceFrame = referenceFrame ;
        this.setSize(sizePanel,sizePanel);
        this.sizePanel=sizePanel;
        this.satelliteList = new ArrayList<>();
        refBody = new VisibleBody(this, referenceFrame);
        ArrayList<CelestialBody> temp = referenceFrame.getSatelliteList();
        for (CelestialBody body : temp) {
            satelliteList.add(new VisibleBody(this, body));
        }

    }

    public ArrayList<VisibleBody> getSatelliteList(){
        return satelliteList;
    }

    public VisibleBody getRefBody(){
        return refBody;
    }

    /*********************** REPERE *********************/


    private void drawOval(Graphics2D g2d, int x, int y, int w, int h){
        g2d.fillOval(x - w/2,y - h/2, w,h);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        // repere : 
        g2d.setColor(Color.red);

        g2d.drawLine(sizePanel/2,0,sizePanel/2,sizePanel);
        g2d.drawLine(0,sizePanel/2,sizePanel,sizePanel/2);

        // planetes :
        Iterator<VisibleBody> IterSatelliteList = satelliteList.iterator();

        g2d.setColor(Color.yellow);
        g2d.setFont(new Font("Purisa", Font.PLAIN, 13));

        //draw Reference
        int refWidth = 30;
        drawOval(g2d, sizePanel/2, sizePanel/2, refWidth, refWidth);

        g2d.setColor(Color.blue);

        int h=0;
        while (IterSatelliteList.hasNext()){
            VisibleBody curr = IterSatelliteList.next();
            System.out.println(curr);
            String xS = Integer.toString(curr.getxPos()-sizePanel/2);
            String yS = Integer.toString(curr.getyPos()-sizePanel/2);

            int x = curr.getxPos();
            int y = curr.getyPos();

            //float w = curr.getDiameter() * scaleWidth;
            int w =15;
            drawOval(g2d, x, y, w, w);


            String name= curr.getName();
            h+=20;
            g2d.drawString(name, 20, h);
            g2d.drawString(xS, 100, h);
            g2d.drawString(yS, 180, h);
            
        }
        
    }


    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

}

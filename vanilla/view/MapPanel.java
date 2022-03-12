package vanilla.view;

import java.util.*;

import java.awt.*;
import javax.swing.*;

import vanilla.model.CelestialBody;
import vanilla.model.SpacePoint;

public class MapPanel extends JPanel{

    // private SolarSystem solarSystem;
    private CelestialBody referenceFrame;
    private int sizePanel;
    private VisibleBody refBody;
    private ArrayList<VisibleBody> satelliteList;

    //ratio here not to recalculate ratio for every
    private double ratioFarthest;
    private double ratioDiameter;

    public CelestialBody getReferenceFrame() {
        return referenceFrame;
    }

    MapPanel(CelestialBody referenceFrame , int sizePanel){
        super();
        this.referenceFrame = referenceFrame ;
        this.setSize(sizePanel,sizePanel);
        this.sizePanel=sizePanel;

        ratioFarthest =  0.8 * ( (Math.min(this.getSize().getWidth(), this.getSize().getHeight())/2) /   (this.getReferenceFrame().getFarthest())); 

        this.satelliteList = new ArrayList<>();
        refBody = new VisibleBody(this, referenceFrame);
        ArrayList<CelestialBody> temp = referenceFrame.getSatelliteList();
        for (CelestialBody body : temp) {
            insertInOrder(body);
            //satelliteList.add(new VisibleBody(this, body));
        }

        ratioDiameter = calculRatioDiameter();
        //set diameter according to previously calculated ratio for all visible bodies
        setDiameterVisibleBodies();

    }

    /*
    *   Function to insert in order (accroding to distance from origin) a new visibleBody in the satellite list
    */
    private void insertInOrder(CelestialBody body){
        VisibleBody vBody = new VisibleBody(this, body);
        double distNew = vBody.getDistance(this);
        Iterator<VisibleBody> IterSatelliteList = satelliteList.iterator();
        int cpt=0;
        while (IterSatelliteList.hasNext()){
            VisibleBody curr = IterSatelliteList.next();
            
            if (curr.getDistance(this) >= distNew){
                satelliteList.add(cpt , vBody);
                return;
            }
            cpt ++;
        }
        satelliteList.add(vBody);
    }

    public double calculRatioDiameter(){
        Iterator<VisibleBody> satelliteIter = satelliteList.iterator();
        double minRatio= Double.POSITIVE_INFINITY;
        double currRatio=0;

        VisibleBody prevBody = null;
        double prevRealRadius=0;
        double prevDist = 0;

        if (satelliteIter.hasNext()){
            prevBody = satelliteIter.next();
            prevRealRadius = prevBody.getRealDiameter()/2;
            //prevRealPos = prevBody.getRealPosition();
            prevDist = prevBody.getDistance(this);
        }

        VisibleBody currBody;
        double currRealRadius=0;
        double currDist = 0;
        while (satelliteIter.hasNext()){
            currBody = satelliteIter.next();
            currRealRadius = currBody.getRealDiameter()/2;
            //currRealPos = currBody.getRealPosition();
            currDist = currBody.getDistance(this);


             double distBetween = Math.sqrt(Math.pow( Math.abs(prevBody.getxPos() - currBody.getxPos()), 2) + Math.pow(Math.abs(prevBody.getyPos() - currBody.getyPos()), 2));
            // System.out.println(distBetween);
            
            //double x = (100 * (prevRealRadius + currRealRadius)) / distBetween;
           // double distBetween = currDist - prevDist;
            double newMaxRadius = (distBetween * Math.max(prevRealRadius , currRealRadius)) / (prevRealRadius + currRealRadius);
            currRatio = newMaxRadius / Math.max(prevRealRadius , currRealRadius);
            
            if ( currRatio < minRatio){
                // System.out.print(currBody);
                // System.out.print("    dist  : " + distBetween + "   r1 : " + prevRealRadius + "    r2   : " + currRealRadius);
                // System.out.println("    " + prevBody);
                minRatio = currRatio;
            }

            prevBody = currBody;
            prevRealRadius = currRealRadius;
            prevDist = currDist;
        }

        return minRatio;
    }

    public void setDiameterVisibleBodies(){
        Iterator<VisibleBody> satelliteIter = satelliteList.iterator();
        while (satelliteIter.hasNext()){
            VisibleBody currBody = satelliteIter.next();
            currBody.setDiameterAccordingToRatio(ratioDiameter);
        }
    }

    public ArrayList<VisibleBody> getSatelliteList(){
        return satelliteList;
    }

    public VisibleBody getRefBody(){
        return refBody;
    }

    public double getRatioFarthest(){
        return this.ratioFarthest;
    }

    public double getRatioDiameter(){
        return this.ratioDiameter;
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
            String xS = Integer.toString(curr.getxPos()-sizePanel/2);
            String yS = Integer.toString(curr.getyPos()-sizePanel/2);

            int x = curr.getxPos();
            int y = curr.getyPos();

            int w = curr.getDiameter();
            //int w =15;
            drawOval(g2d, x, y, w, w);


            String name= curr.getName();
            h+=20;
            g2d.setColor(Color.red);
            g2d.drawString(name, x, y);
            g2d.setColor(Color.blue);
            g2d.drawString(name, 20, h);
            g2d.drawString(xS, 100, h);
            g2d.drawString(yS, 180, h);
            
        }



        // //affichage pour debug
        // Iterator<VisibleBody> IterSatelliteList2 = satelliteList.iterator();
        // int h2=40;
        // while (IterSatelliteList2.hasNext()){
        //      g2d.setColor(Color.red);
        //     VisibleBody curr = IterSatelliteList2.next();
            
        //     String blabla = Integer.toString(curr.getDiameter());
        //     String blabla2 = Integer.toString((int)curr.getRealDiameter());
        //     h2+=20;
        //     g2d.drawString(blabla, 100, h2);
        //     g2d.drawString(blabla2, 200, h2);
            
        // }

        // g2d.setColor(Color.green);
        // g2d.drawString( Double.toString(ratioDiameter), 200, 500);
        
    }


    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

}

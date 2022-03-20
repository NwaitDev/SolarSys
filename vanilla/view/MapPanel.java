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
    private VisibleBody bodyWithTooltip;

    //ratio here not to recalculate ratio for every
    private double ratioFarthest;
    private double ratioDiameter;

    private boolean refAtRatio; //true if the reference frame respect rztiodiameter

    private double distanceBetweenConst;

    public CelestialBody getReferenceFrame() {
        return referenceFrame;
    }

    MapPanel(CelestialBody referenceFrame , int sizePanel){
        super();

        this.referenceFrame = referenceFrame ;
        this.setSize(sizePanel,sizePanel);
        this.sizePanel=sizePanel;

        refAtRatio = false;

       ratioFarthest =  0.8 * ( (Math.min(this.getSize().getWidth(), this.getSize().getHeight())/2) /   (this.getReferenceFrame().getFarthest())); 
        

        this.satelliteList = new ArrayList<>();
        refBody = new VisibleBody(this, referenceFrame);
        ArrayList<CelestialBody> temp = referenceFrame.getSatelliteList();
        for (CelestialBody body : temp) {
            insertInOrder(body);
        }

        //move all visible bodies in order to have the same distrance between all of them
        setDistanceBetweenConst();

        ratioDiameter = calculRatioDiameter();
        //set diameter according to previously calculated ratio for all visible bodies
        setDiameterVisibleBodies();


        //TEST
        // Iterator<VisibleBody> satelliteIter = satelliteList.iterator();
        // int x=(int)(this.getSize().getHeight()/2);
        // int y=(int)(this.getSize().getHeight()/2);
        // double distBetween = this.getSize().getWidth() / 2 / satelliteList.size();
        // while (satelliteIter.hasNext()){
        //     VisibleBody currBody = satelliteIter.next();
        //     currBody.setPos(x,y);
            
        //     x+= distBetween ;
        //     y+= distBetween;
        // }
        
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

    /*
        Function that set distances between all satellite with the same distance
    */
    private void setDistanceBetweenConst(){
        distanceBetweenConst = (this.getSize().getWidth()/2) / (referenceFrame.getSatelliteList().size() + 1);
        
        double distance = distanceBetweenConst;

        Iterator<VisibleBody> IterSatelliteList = satelliteList.iterator();
        
        while (IterSatelliteList.hasNext()){
            VisibleBody curr = IterSatelliteList.next();
           
            curr.moveAccordingDistance(distance, this);
            distance += distanceBetweenConst;
        }
    }

    private double getbiggestDiameter(){
        Iterator<VisibleBody> satelliteIter = satelliteList.iterator();
        double maxDiameter = 0;
        while (satelliteIter.hasNext()){
           VisibleBody currBody = satelliteIter.next();
            if (currBody.getRealDiameter() > maxDiameter){
                maxDiameter=currBody.getRealDiameter();
            }
        }
        return maxDiameter;
    }


    public double calculRatioDiameter(){
        Iterator<VisibleBody> satelliteIter = satelliteList.iterator();
        double minRatio= Double.POSITIVE_INFINITY;
        double currRatio=0;


        VisibleBody prevBody = null;
        double prevRealRadius=0;
        double prevDist = 0;

        if ((referenceFrame.getDiameter() / this.getbiggestDiameter()) < 5){
            refAtRatio = true;
            prevBody = new VisibleBody(this, referenceFrame);
            prevRealRadius=referenceFrame.getDiameter()/2;
            prevDist = 0;
            //System.out.println("hello"+ this.getbiggestDiameter() +"   " + referenceFrame.getDiameter());
        }

        VisibleBody currBody;
        double currRealRadius=0;
        double currDist = 0;
        

        if (satelliteIter.hasNext()){
            currBody = satelliteIter.next();
            currRealRadius = currBody.getRealDiameter()/2;
            currDist = currBody.getDistance(this);
            if (refAtRatio){
                double newMaxRadius = (distanceBetweenConst * Math.max(prevRealRadius , currRealRadius)) / (prevRealRadius + currRealRadius);
                minRatio = newMaxRadius / Math.max(prevRealRadius , currRealRadius);
            }else{
                minRatio = (Math.min(this.getSize().getWidth(), this.getSize().getHeight())/4) /  currRealRadius ; // min ratio initialization in the case of one satellite
            }
            prevBody = currBody;
            prevRealRadius = currRealRadius;
            prevDist = currDist;
        }

        
        while (satelliteIter.hasNext()){
            currBody = satelliteIter.next();
            currRealRadius = currBody.getRealDiameter()/2;
            currDist = currBody.getDistance(this);


            //double distBetween = Math.sqrt(Math.pow( Math.abs(prevBody.getxPos() - currBody.getxPos()), 2) + Math.pow(Math.abs(prevBody.getyPos() - currBody.getyPos()), 2));
            // System.out.println(distBetween);
            double distBetween = distanceBetweenConst;
            
            double newMaxRadius = (distBetween * Math.max(prevRealRadius , currRealRadius)) / (prevRealRadius + currRealRadius);
            currRatio = newMaxRadius / Math.max(prevRealRadius , currRealRadius);
            
            if ( currRatio < minRatio){
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

    public void  setTooltipBody(VisibleBody body){
        this.bodyWithTooltip = body;
    }

    private void drawStringForTooltip(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n")){
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }
    }

    private void  printTooltip(Graphics2D g2d){
        VisibleBody body = bodyWithTooltip;
        if (body == null){
            return;
        }
        
        String tooltip = body.usefullDataTooltipToString(30);
        g2d.setColor(Color.BLACK);
        g2d.fillRoundRect(body.getxPos()-105, body.getyPos()-100-body.getDiameter()/2, 210,100, 8,8);
        g2d.setColor(body.getColor());
        g2d.drawRoundRect(body.getxPos()-105, body.getyPos()-100-body.getDiameter()/2, 210,100, 8, 8);
        
        drawStringForTooltip(g2d, tooltip, (body.getxPos()-100), (body.getyPos()-90-body.getDiameter()/2));

    }

    /*********************** REPERE *********************/


    private void drawOval(Graphics2D g2d, int x, int y, int w, int h){
        g2d.fillOval(x - w/2,y - h/2, w,h);
    }

    Color chooseColor(String bodyName){
        Color c = Color.GRAY;
        if(bodyName.equals("Sun")){
            c = Color.YELLOW;
        }
        if(bodyName.equals("Mercury")){
            c = Color.ORANGE;
        }
        if(bodyName.equals("Venus")){
            c = Color.lightGray;
        }
        if(bodyName.equals("Earth")){
            c = Color.BLUE;
        }
        if(bodyName.equals("Mars")){
            c = Color.ORANGE;
        }
        if(bodyName.equals("Jupiter")){
            c = Color.ORANGE;
        }
        if(bodyName.equals("Saturn")){
            c = Color.YELLOW;
        }
        if(bodyName.equals("Uranus")){
            c = Color.lightGray;
        }
        if(bodyName.equals("Neptune")){
            c = Color.BLUE;
        }
        return c;
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        /*
        // repere : 
        g2d.setColor(Color.red);

        g2d.drawLine(sizePanel/2,0,sizePanel/2,sizePanel);
        g2d.drawLine(0,sizePanel/2,sizePanel,sizePanel/2);
        */
        // planetes :
        Iterator<VisibleBody> IterSatelliteList = satelliteList.iterator();


        //TODO visible body pour soleil
        g2d.setColor(chooseColor(getRefBody().getName()));
        g2d.setFont(new Font("Purisa", Font.PLAIN, 13));

        //draw Reference
        //int refWidth = (int) (referenceFrame.getDiameter() * ratioDiameter);
        
        int refWidth = refAtRatio ? (int) (referenceFrame.getDiameter() * ratioDiameter) : 40;
        drawOval(g2d, sizePanel/2, sizePanel/2, refWidth, refWidth);

        int h=0;
        while (IterSatelliteList.hasNext()){
            VisibleBody curr = IterSatelliteList.next();
            int x = curr.getxPos();
            int y = curr.getyPos();

            int w = curr.getDiameter();
            //int w =15;

            g2d.setColor(curr.getColor());
            drawOval(g2d, x, y, w, w);

            String name= curr.getName();
            g2d.setColor(Color.red);
            //g2d.drawString(name, x, y);
            g2d.setColor(Color.blue);
            
            //debug
            // String xS = Integer.toString(curr.getxPos()-sizePanel/2);
            // String yS = Integer.toString(curr.getyPos()-sizePanel/2);
            // h+=20;
            // g2d.drawString(name, 20, h);
            // g2d.drawString(xS, 100, h);
            // g2d.drawString(yS, 180, h);
            
        }

        printTooltip(g2d);
        //System.out.println(ratioDiameter);



        //affichage pour debug
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

package vanilla.view;

import vanilla.model.CelestialBody;
import vanilla.model.SpacePoint;

import java.awt.*;

public class VisibleBody {
    
    //private static double ratioFarthest;
    //private static double ratioDiameter;

    private CelestialBody actualCelestialBody;
    private static final int DEFAULT_TEXT_WIDTH = 30;
    private int xPos;
    private int yPos;
    private int diameter;
    public static VisibleBody selected = null;
    public Color color;


    public VisibleBody(MapPanel mp, CelestialBody body){

        this.actualCelestialBody = body;

        double ratioFarthest = mp.getRatioFarthest();

        if (mp.getReferenceFrame() == body) {
            xPos = yPos = mp.getWidth()/2;
        }else{
            /*change les distances en la distance à laquelle elle correspond à l'écran
            (la planète la plus éloignée est à 90% 
            de la moitié de la taille de la fenêtre)*/

            xPos = (int)  (((float) (body.getPosition().x))  *ratioFarthest + (float) (mp.getWidth()/2.0));  
            yPos = (int)  (((float) (body.getPosition().y))  *ratioFarthest + (float) (mp.getHeight()/2.0));
            
            
            
            // xPos = (int)(((body.getPosition().x) * 30) / Math.sqrt(Math.pow((body.getPosition().x), 2) + Math.pow((body.getPosition().y), 2))) + (int)(mp.getWidth()/2.0);
            // yPos = (int)(((body.getPosition().y) * 30) / Math.sqrt(Math.pow((body.getPosition().x), 2) + Math.pow((body.getPosition().y), 2))) + (int) (mp.getWidth()/2.0);

        }

        /* diameter is set after alla visibleBodies are added in mapPanel
        because of the calcul of the ratio
        so for now diameter is just 0*/
        diameter = 0;

        color = chooseColor(body.getName());
    } 

    /*
     Mercure : 11 000 000, 40 000 000
     Venus : 104 000 000, -2 000 000
     Terre : -129 061 154, -70 580 590
     Mars : -149 713 201, 125 470 730
     ratio 1.612589196147937E-6
     */

    public CelestialBody getActualCelestialBody(){
        return actualCelestialBody;
    }

    public String toString(){
        return actualCelestialBody.getName()+" ("+xPos+","+yPos+")";
    }

    public int getDiameter() {
        return diameter;
    }

    public float getRealDiameter() {
        return actualCelestialBody.getDiameter();
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public void setDiameterAccordingToRatio(double ratioDiameter){
        diameter =(int) (actualCelestialBody.getDiameter() * ratioDiameter);
        if (diameter < 5){
            diameter = 5;
        }
    }

    public void setPos(int x, int y){
        this.xPos = x ;
        this.yPos = y;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public double getDistance(MapPanel mp){
        return Math.sqrt(Math.pow(xPos-(mp.getSize().getHeight()/2), 2) + Math.pow(yPos-(mp.getSize().getWidth()/2), 2));
    }

    public void moveAccordingDistance(double dist, MapPanel mp){
        int x2 = (int)(xPos - (mp.getWidth()/2));
        int y2 = (int)(yPos - (mp.getWidth()/2));

        x2 = (int)((x2 * dist) / Math.sqrt(Math.pow(x2, 2) + Math.pow(y2, 2)));
        y2 = (int)((y2 * dist) / Math.sqrt(Math.pow(x2, 2) + Math.pow(y2, 2)));

        this.xPos = (int)(x2 + (mp.getSize().getWidth()/2));
        this.yPos = (int)(y2 + (mp.getSize().getHeight()/2));
    }

    public SpacePoint getRealPosition() {
        return actualCelestialBody.getPosition();
    }

    public Color getColor() {
        return color;
    }

    public String getName(){
        return actualCelestialBody.getName();
    }

    private Color chooseColor(String bodyName){
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

    /**
     * action plaçant des retours à la ligne dans une chaîne de caractères
     * @param txt la chaîne à formater
     * @param maxWidth le nombre de caractères minimum avant un retour à la ligne
     */
    private String format(String txt, int maxWidth){
        if (txt==null) {
            return "no text";
        }
        int ptr = 0;
        StringBuffer txtBuffer = new StringBuffer(txt);
        int l = txtBuffer.length();
        for(int i = 0; i<l; i++){
            if(ptr>maxWidth){
                if(txtBuffer.charAt(i)==' '){
                    txtBuffer.insert(i+1,'\n');
                    ptr = 0;
                }
            }else{
                ptr++;
            }
        }
        return txtBuffer.toString();
    }

    public String usefullDataToString() {
        return format("Name : "+actualCelestialBody.getName() +
                "\nDiameter : "+actualCelestialBody.getDiameter()+
                "\nAverage distance to "+actualCelestialBody.getReferenceFrame()+" : "+
                actualCelestialBody.getDistanceFromOrigin()+
                "\nPeriod of revolution around "+actualCelestialBody.getReferenceFrame()+" : "+actualCelestialBody.getPeriodOfRevolution()+" earth days\n"+
                "Period of rotation : "+actualCelestialBody.getPeriodOfRotation()+" hours",DEFAULT_TEXT_WIDTH);
    }
}

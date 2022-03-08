package vanilla.view;

import vanilla.model.CelestialBody;


public class VisibleBody {
    
    private static final double RATIO_FARTHEST = 0.8;
    private CelestialBody actualCelestialBody;
    private static final int DEFAULT_TEXT_WIDTH = 30;
    private int xPos;
    private int yPos;
    private int diameter;
    public static VisibleBody selected = null;


    public VisibleBody(MapPanel mp, CelestialBody body){

        this.actualCelestialBody = body;
        if (mp.getReferenceFrame() == body) {
            xPos = yPos = mp.getWidth()/2;
        }else{

            /*change les distances en la distance à laquelle elle correspond à l'écran
            (la planète la plus éloignée est à 90% 
            de la moitié de la taille de la fenêtre)*/

            xPos = (int) ((body.getPosition().x*(RATIO_FARTHEST*(mp.getSize().getWidth()/2)))/mp.getReferenceFrame().getFarthest()) + mp.getWidth()/2;
            yPos = (int) ((body.getPosition().y*(RATIO_FARTHEST*(mp.getSize().getWidth()/2)))/mp.getReferenceFrame().getFarthest()) + mp.getWidth()/2;

            System.out.println(body.getName()+" : Position a l'écran -> ("+xPos+","+yPos+")");
        }
    }

    public String toString(){
        return actualCelestialBody.getName()+" ("+xPos+","+yPos+")";
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
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
                "\nAverage distance to "+actualCelestialBody.getReferenceFrame().getName()+" : "+
                actualCelestialBody.getDistanceFromOrigin()+
                "\nPeriod of revolution around "+actualCelestialBody.getReferenceFrame().getName()+" : "+actualCelestialBody.getPeriodOfRevolution()+" earth days\n"+
                "Period of rotation : "+actualCelestialBody.getPeriodOfRotation()+" hours",DEFAULT_TEXT_WIDTH);
    }


}

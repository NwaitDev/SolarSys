package vanilla.view;

import vanilla.model.CelestialBody;
import java.awt.Point;


public class VisibleBody {
    
    private CelestialBody actualCelestialBody;
    private Point screenPos = new Point();


    public VisibleBody(MapPanel mp, CelestialBody body){

        //TODO : Recoder ça, parce que mdr, ça va tout casser si on refactorise mapPanel, c'est pas foufou
        // Et puis de toute façon, ce serait plus propre que le code relatif à la position d'un objet à l'écran
        // soit écrit ici -> cette solution doit rester temporaire !
        
        screenPos.x = mp.getPosRelative(body.getPosition().getX(), body.getFarthest(),(int) mp.getSize().getWidth());
        screenPos.y =  mp.getPosRelative(body.getPosition().getX(), body.getFarthest(),(int) mp.getSize().getWidth());
        System.out.println(screenPos);

    }

    public void setPos(Point p){
        this.screenPos = p;
    }

    public Point getPos(){
        return screenPos;
    }


    public String usefullDataToString() {
        return "Name : "+actualCelestialBody.getName() +
                "\nDiameter : "+actualCelestialBody.getDiameter()+
                "\nAverage distance to "+actualCelestialBody.getReferenceFrame().getName()+" : "+
                actualCelestialBody.getDistanceFromOrigin()+
                "\nPeriod of revolution : "+actualCelestialBody.getPeriodOfRevolution()+" earth days\n"+
                "Period of rotation : "+actualCelestialBody.getPeriodOfRotation();
    }
}

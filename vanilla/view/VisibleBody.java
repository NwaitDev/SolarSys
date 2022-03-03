package vanilla.view;

import vanilla.model.CelestialBody;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class VisibleBody {
    
    private CelestialBody actualCelestialBody;
    private Point screenPos;
    private int width;

    public VisibleBody(CelestialBody body){
        //screenPos.x = 
        //screenPos.y = 
    }

    public void setPos(Point p){

    }

    public Point getPos(){
        return screenPos;
    }

    public MouseEvent getPosition() {
        return null;
    }

    public char[] usefullDataToString() {
        return null;
    }
}

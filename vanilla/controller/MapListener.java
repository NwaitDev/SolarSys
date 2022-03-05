package vanilla.controller;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.event.MouseInputListener;

import vanilla.model.CelestialBody;
import vanilla.view.MapPanel;
import vanilla.view.VisibleBody;

public class MapListener implements MouseInputListener {

    private static final double CLICK_DIST = 10;
    VisibleBody refBody;
    ArrayList<VisibleBody> satList;

    public MapListener(MapPanel mp){

        refBody = new VisibleBody(mp, mp.getReferenceFrame());
        ArrayList<CelestialBody> temp = mp.getReferenceFrame().getSatelliteList();
        satList = new ArrayList<>();
        for (CelestialBody body : temp) {
            satList.add(new VisibleBody(mp, body));
        }
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("click at :" + e.getPoint());
        for (VisibleBody body : satList) {
            if(e.getLocationOnScreen().distance(body.getPos().getX(), body.getPos().getX())<CLICK_DIST){ 
                System.out.println(body.usefullDataToString());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    
}

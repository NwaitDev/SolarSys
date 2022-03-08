package vanilla.controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.event.MouseInputListener;

import vanilla.model.CelestialBody;
import vanilla.view.MapPanel;
import vanilla.view.VisibleBody;

public class MapListener implements MouseInputListener {

    private static final double CLICK_DIST = 15;
    VisibleBody refBody;
    ArrayList<VisibleBody> satList;
    MapPanel mp;

    public MapListener(MapPanel mp){
        this.mp = mp;
        refBody = new VisibleBody(mp, mp.getReferenceFrame());
        ArrayList<CelestialBody> temp = mp.getReferenceFrame().getSatelliteList();
        satList = new ArrayList<>();
        for (CelestialBody body : temp) {
            satList.add(new VisibleBody(mp, body));
        }
    }

    public void mouseClicked(MouseEvent e) {
        for (VisibleBody body : satList) {
            Point pos = e.getPoint();
            double distance = Math.sqrt((pos.x - body.getxPos())*(pos.x - body.getxPos())+(pos.y - body.getyPos())*(pos.y - body.getyPos()));
            if(distance<CLICK_DIST){ 
                VisibleBody.selected = body;
                System.out.println(body);
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

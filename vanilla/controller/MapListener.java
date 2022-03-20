package vanilla.controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.event.MouseInputListener;

import vanilla.view.MapPanel;
import vanilla.view.VisibleBody;
import vanilla.view.Window;

public class MapListener implements MouseInputListener {

    VisibleBody refBody;
    ArrayList<VisibleBody> satList;
    MapPanel mp;

    public MapListener(MapPanel mp){
        this.mp = mp;
        refBody = mp.getRefBody();
        satList =  mp.getSatelliteList();
        
    }

    public void mouseClicked(MouseEvent e) {
        for (VisibleBody body : satList) {
            Point pos = e.getPoint();
            double distance = Math.sqrt((pos.x - body.getxPos())*(pos.x - body.getxPos())+(pos.y - body.getyPos())*(pos.y - body.getyPos()));
            if(distance<1+body.getDiameter()/2){ //Le +1, c'est pour avoir une chance de cliquer sur pluton et mercure mdr
                VisibleBody.selected = body;
                new Window(body.getActualCelestialBody());
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
        for (VisibleBody body : satList) {
            Point pos = e.getPoint();
            double distance = Math.sqrt((pos.x - body.getxPos())*(pos.x - body.getxPos())+(pos.y - body.getyPos())*(pos.y - body.getyPos()));
            if(distance<1+body.getDiameter()/2){ //Le +1, c'est pour avoir une chance de cliquer sur pluton et mercure mdr
                VisibleBody.selected = body;
                //afficher tooltip
                mp.setTooltipBody(body);
                mp.repaint();
                return;
            }
        }

        //Raffraichir page
        mp.setTooltipBody(null);
        mp.repaint();
    }

    
}

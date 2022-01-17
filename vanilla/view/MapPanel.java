package vanilla.view;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

import vanilla.model.CelestialBody;

public class MapPanel extends JPanel{

    private ArrayList<CelestialBody> bodies;

    MapPanel(){
        super();
        this.bodies = new ArrayList<>();
        this.setBackground(Color.BLACK);
    }

    public void updateView(){
        this.removeAll();
        for (CelestialBody body : bodies) {
            System.out.println(body.getName());
        }
    }

    public void addBody(String name){
        this.bodies.add(new CelestialBody(name));
    }

}

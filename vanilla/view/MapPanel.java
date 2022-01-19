package vanilla.view;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import vanilla.model.CelestialBody;
import vanilla.model.SolarSystem;

public class MapPanel extends JPanel{

    private SolarSystem solarSystem;

    MapPanel(SolarSystem solarSystem){
        super();
        this.solarSystem = solarSystem ;
    }

    public void updateView(){
        solarSystem.print();
    }

    public void addSun(CelestialBody body){
        this.solarSystem.addSun(body);
    }

    public void addPlanet(CelestialBody body){
        this.solarSystem.addPlanet(body);
    }

}

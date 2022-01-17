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
        this.removeAll();
        for (CelestialBody body : solarSystem.getSunList()) {
            this.add(new JLabel(body.getName()));
        }
        for (CelestialBody body : solarSystem.getPlanetList()) {
            this.add(new JLabel(body.getName()));   
        }
        System.out.println("update clicked");
        this.repaint();
    }

    public void addSun(CelestialBody body){
        this.solarSystem.addSun(body);
    }

    public void addPlanet(CelestialBody body){
        this.solarSystem.addPlanet(body);
    }

}

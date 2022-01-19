package vanilla.model;

import java.util.ArrayList;

public class SolarSystem {
    private ArrayList<CelestialBody> sunList = new ArrayList<CelestialBody>();
    private ArrayList<CelestialBody> planetList = new ArrayList<CelestialBody>();

    public ArrayList<CelestialBody> getSunList(){
        return sunList;
    }

    public ArrayList<CelestialBody> getPlanetList(){
        return planetList;
    }

    public void addSun(CelestialBody sun){
        this.sunList.add(sun);
    }
    public void addPlanet(CelestialBody planet){
        this.planetList.add(planet);
    }

    public void print(){
        for (CelestialBody celestialBody : planetList) {
            System.out.println(celestialBody.getName());
            System.out.println("Diameter :"+celestialBody.getDiameter());
            System.out.println("Position :"+celestialBody.getPosition());
            System.out.println("Scale :"+celestialBody.getScale());
            System.out.println("Period of revolution :"+celestialBody.getPeriodOfRevolution());
        }
    }
}

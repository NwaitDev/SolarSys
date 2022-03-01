package vanilla.model;

import java.util.ArrayList;
import java.util.*;

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

    //get distance of the farthest planet arount the reference in parameter to find the appropriate scale
    public float getFarthest(CelestialBody ref){
        Iterator<CelestialBody> IterPlanetList = this.planetList.iterator();
        float max=0;
        while (IterPlanetList.hasNext()){
            CelestialBody curr = IterPlanetList.next();
            //if (curr.getReferenceFrame().isSameAs(ref)){
                if (curr.getDistanceFromOrigin()>max){
                    max = curr.getDistanceFromOrigin();
                }
            //}
        }
        return max;
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

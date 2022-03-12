package vanilla.model;

import java.util.ArrayList;
import java.util.*;

public class CelestialBody {
    private String name;
    private String referenceFrame;
    private SpacePoint position;
    private float diameter;
    private float scale;
    private float periodOfRevolution;
    private float periodOfRotation;
    private float distanceFromOrigin;
    private ArrayList<CelestialBody> satelliteList;
    private float farthest;
    private float closest;
    private float biggest;

    public CelestialBody(String name, String referenceFrame, SpacePoint position, float diameter, float scale,
            float periodOfRevolution, float periodOfRotation, float distanceFromOrigin) {
        this.name = name;
        this.referenceFrame = referenceFrame;
        this.position = position;
        this.diameter = diameter;
        this.scale = scale;
        this.periodOfRevolution = periodOfRevolution;
        this.periodOfRotation = periodOfRotation;
        this.distanceFromOrigin = distanceFromOrigin;
        this.satelliteList = new ArrayList<CelestialBody>();
    }

    public String getName() {
        return this.name;
    }

    public SpacePoint getPosition() {
        return this.position;
    }

    public String getReferenceFrame() {
        return referenceFrame;
    }

    public float getDiameter() {
        return diameter;
    }

    public float getScale() {
        return scale;
    }

    public float getPeriodOfRevolution() {
        return periodOfRevolution;
    }

    public float getPeriodOfRotation() {
        return periodOfRotation;
    }

    public float getDistanceFromOrigin() {
        return distanceFromOrigin;
    }


    public ArrayList<CelestialBody> getSatelliteList(){
        return this.satelliteList;
    }


    public boolean isSameAs( CelestialBody other){
        return this.name== other.name;
    }

    public void jAddSatellite(CelestialBody body){
        System.out.println("appel de la methode java");
        this.satelliteList.add(body);
        this.updateClosest();
        this.updateFarthest();
        this.updateBiggest();
    }


    public void updateBiggest(){
        Iterator<CelestialBody> satelliteIter = this.satelliteList.iterator();
        float max=0;
        while (satelliteIter.hasNext()){
            CelestialBody curr = satelliteIter.next();
                if (curr.getDiameter()>max){
                    max = curr.getDiameter();
                }
        }
        this.biggest=max;
    }

    public void updateFarthest(){
        Iterator<CelestialBody> satelliteIter = this.satelliteList.iterator();
        double max=0;
        while (satelliteIter.hasNext()){
            
            CelestialBody curr = satelliteIter.next();
            double distcurr = Math.sqrt(Math.pow(curr.getPosition().x, 2) + Math.pow(curr.getPosition().y, 2));
                if (distcurr>max){
                    max = distcurr;
                }
        }
        this.farthest= (float) max;
    }

    public void updateClosest()
    {
        Iterator<CelestialBody> satelliteIter = this.satelliteList.iterator();
        double min= Double.MAX_VALUE;
        while (satelliteIter.hasNext()){
            
            CelestialBody curr = satelliteIter.next();
            double distcurr = Math.sqrt(Math.pow(curr.getPosition().x, 2) + Math.pow(curr.getPosition().y, 2));
                if (distcurr<min){
                    min = distcurr;
                }
        }
        this.closest= (float) min;
    }

    public float getFarthest(){
       return this.farthest;
    }

    public float getClosest()
    {
        return this.closest;
    }

    public float getBiggest(){
       return this.biggest;
    }



}
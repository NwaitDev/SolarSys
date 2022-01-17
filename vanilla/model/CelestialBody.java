package vanilla.model;

import java.awt.Point;

public class CelestialBody {
    private String name;
    private CelestialBody referenceFrame;
    private Point position;
    private float diameter;
    private float scale;
    private float periodOfRevolution;
    private float periodOfRotation;
    private float distanceFromOrigin;
    private boolean clockwise;

    public CelestialBody(String name, /*CelestialBody referenceFrame,*/ Point position, float diameter, float scale,
            float periodOfRevolution, float periodOfRotation, float distanceFromOrigin, boolean clockwise) {
        this.name = name;
        //this.referenceFrame = referenceFrame;
        this.position = position;
        this.diameter = diameter;
        this.scale = scale;
        this.periodOfRevolution = periodOfRevolution;
        this.periodOfRotation = periodOfRotation;
        this.distanceFromOrigin = distanceFromOrigin;
        this.clockwise = clockwise;
    }

    public String getName() {
        return this.name;
    }

    public Point getPosition() {
        return this.position;
    }

    public CelestialBody getReferenceFrame() {
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

    public boolean isClockwise() {
        return clockwise;
    }

}

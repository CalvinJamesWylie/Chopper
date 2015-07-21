package uk.co.calvinwylie.chopperv2.lights;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;

/**
 * Created by Calvin on 21/07/2015.
 */
public class AmbientLight {
    private Vector3 color;
    public int colorLocation;

    public AmbientLight(Vector3 color) {
        this.color = color;
    }

    public Vector3 getColor() {
        return color;
    }

    public void setColor(Vector3 color) {
        this.color = color;
    }
}

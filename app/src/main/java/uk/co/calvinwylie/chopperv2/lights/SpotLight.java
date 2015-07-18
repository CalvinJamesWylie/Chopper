package uk.co.calvinwylie.chopperv2.lights;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;


public class SpotLight {

    private PointLight pointLight;
    private Vector3 direction;
    private float cutOff;

    public SpotLight(PointLight pointLight, Vector3 direction, float cutOff) {
        this.pointLight = pointLight;
        this.direction = direction;
        this.direction.normalise();
        this.cutOff = cutOff;
    }

    public PointLight getPointLight() {
        return pointLight;
    }

    public void setPointLight(PointLight pointLight) {
        this.pointLight = pointLight;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public void setDirection(Vector3 direction) {
        this.direction = direction;
        this.direction.normalise();
    }

    public float getCutOff() {
        return cutOff;
    }

    public void setCutOff(float cutOff) {
        this.cutOff = cutOff;
    }
}

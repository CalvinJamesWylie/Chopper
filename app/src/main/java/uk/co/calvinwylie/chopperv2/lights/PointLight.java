package uk.co.calvinwylie.chopperv2.lights;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;


public class PointLight {
    private BaseLight base;
    private Attenuation atten;
    private Vector3 position;
    private float range;

    public PointLight(BaseLight base, Attenuation atten, Vector3 position, float range) {
        this.base = base;
        this.atten = atten;
        this.position = position;
        this.range = range;
    }

    public BaseLight getBase() {
        return base;
    }

    public void setBase(BaseLight base) {
        this.base = base;
    }

    public Attenuation getAtten() {
        return atten;
    }

    public void setAtten(Attenuation atten) {
        this.atten = atten;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }
}

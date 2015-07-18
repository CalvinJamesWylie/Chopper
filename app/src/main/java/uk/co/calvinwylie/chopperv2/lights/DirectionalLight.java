package uk.co.calvinwylie.chopperv2.lights;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;


public class DirectionalLight {
    private BaseLight base;
    private Vector3 direction;

    public DirectionalLight(BaseLight base, Vector3 direction) {
        this.base = base;
        this.direction = direction;
        this.direction.normalise();
    }

    public Vector3 getDirection() {
        return direction;
    }

    public void setDirection(Vector3 direction) {
        this.direction = direction;
        this.direction.normalise();
    }

    public BaseLight getBase() {
        return base;
    }

    public void setBase(BaseLight base) {
        this.base = base;
    }
}

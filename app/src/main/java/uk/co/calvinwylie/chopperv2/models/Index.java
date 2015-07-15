package uk.co.calvinwylie.chopperv2.models;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;

/**
 * Created by Calvin on 10/07/2015.
 */
public class Index {
    public int vertexIndex;
    public int texCoordIndex;

    public Index(short vertexIndex, short texCoordIndex){
        this.vertexIndex = vertexIndex;
        this.texCoordIndex = texCoordIndex;
    }

}

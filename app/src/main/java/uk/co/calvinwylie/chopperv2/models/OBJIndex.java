package uk.co.calvinwylie.chopperv2.models;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;


public class OBJIndex {
    public int vertexIndex;
    public int texCoordIndex;
    public int normalIndex;

    public OBJIndex(short vertexIndex, short texCoordIndex, short normalIndex){
        this.vertexIndex = vertexIndex;
        this.texCoordIndex = texCoordIndex;
        this.normalIndex = normalIndex;
    }

}

package uk.co.calvinwylie.chopperv2.models;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector2;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;

/**
 * Created by Calvin on 07/07/2015.
 */
public class Vertex {
    public static final int SIZE = 3; //// CHANGE THIS ERRR TIME!

    private Vector3 m_Position;
    private Vector2 m_TexCoords;

    public Vertex(Vector3 position){
        this(position, new Vector2());
    }

    public Vertex(Vector3 position, Vector2 texCoord){
        m_TexCoords = texCoord;
        m_Position = position;
    }

    public Vector3 getPosition(){
        return m_Position;
    }
    public Vector2 getUVCoord(){
        return m_TexCoords;
    }

    public void setPosition(Vector3 pos){
        m_Position = pos;
    }
}

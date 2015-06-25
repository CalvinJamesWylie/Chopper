package uk.co.calvinwylie.chopperv2.gameObjects;

/**
 * Created by Calvin on 22/04/2015.
 */

import uk.co.calvinwylie.chopperv2.dataTypes.Vector2;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.gameObjects.Geometry.Ray;
import static uk.co.calvinwylie.chopperv2.util.MatrixHelper.perspectiveM;

import static android.opengl.Matrix.invertM;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.multiplyMV;
import static android.opengl.Matrix.setLookAtM;


/**
 * Created by Calvin on 29/03/2015.
 */
public class Camera {

    private String tag = this.getClass().getSimpleName();


    float[] modelViewProjectionMatrix = new float[16];
    private final float[] m_ProjectionMatrix = new float[16];
    private final float[] m_ViewMatrix = new float[16];
    private final float[] m_ViewProjectionMatrix = new float[16];
    private final float[] m_InvertedViewProjectionMatrix = new float[16];

    private Vector3 m_Position;
    private Vector3 m_LookAt;
    private Vector3 m_UpVector;

    private Vector3 m_Velocity = new Vector3();

    private GameObject m_TargetObject;
    private Vector2 m_VectorToTarget = new Vector2();


    private final float m_FollowRadius = 5.0f;

    public Camera(float x, float y, float z, float lookAtX, float lookAtY, float lookAtZ, float upX, float upY, float upZ){
        m_Position = new Vector3(x, y, z);
        m_LookAt = new Vector3(lookAtX, lookAtY, lookAtZ);
        m_UpVector = new Vector3(upX, upY, upZ);
    }

    public void onSurfaceChanged(int width, int height) {
        perspectiveM(m_ProjectionMatrix, 45, (float) width / (float) height, 1.0f, 1000.0f);
        setLookAtM(m_ViewMatrix, 0, m_Position.X, m_Position.Y, m_Position.Z, m_LookAt.X, m_LookAt.Y, m_LookAt.Z, m_UpVector.X, m_UpVector.Y, m_UpVector.Z);
    }

    public void onDrawFrame() {
        setLookAtM(m_ViewMatrix, 0, m_Position.X, m_Position.Y, m_Position.Z, m_LookAt.X, m_LookAt.Y, m_LookAt.Z, m_UpVector.X, m_UpVector.Y, m_UpVector.Z);
        multiplyMM(m_ViewProjectionMatrix, 0, m_ProjectionMatrix, 0, m_ViewMatrix, 0);
        invertM(m_InvertedViewProjectionMatrix, 0, m_ViewProjectionMatrix, 0);
    }

    public float[] getMVPMatrix(float[] modelMatrix) {
        multiplyMM(modelViewProjectionMatrix, 0, m_ViewProjectionMatrix, 0, modelMatrix, 0);
        return modelViewProjectionMatrix;
    }

    public void setFollow(GameObject go){
        m_TargetObject = go;
    }

    public Ray convertNormalised2DPointToRay(float normalisedX, float normalisedY) {
        // We'll convert these normalized device coordinates into world-space
        // coordinates. We'll pick a point on the near and far planes, and draw a
        // line between them. To do this transform, we need to first multiply by
        // the inverse matrix, and then we need to undo the perspective divide.

        final float[] nearPointNdc = {normalisedX, normalisedY, -1, 1};
        final float[] farPointNdc = {normalisedX, normalisedY, 1, 1};

        final float[] nearPointWorld = new float[4];
        final float[] farPointWorld = new float[4];

        multiplyMV(nearPointWorld, 0, m_InvertedViewProjectionMatrix, 0, nearPointNdc, 0);
        multiplyMV(farPointWorld, 0, m_InvertedViewProjectionMatrix, 0, farPointNdc, 0);

        divideByW(nearPointWorld);
        divideByW(farPointWorld);

        Vector3 nearPointRay = new Vector3(nearPointWorld[0], nearPointWorld[1], nearPointWorld[2]);
        Vector3 farPointRay = new Vector3(farPointWorld[0], farPointWorld[1], farPointWorld[2]);

        return new Geometry.Ray(nearPointRay, Vector3.vector3Between(nearPointRay, farPointRay));
    }

    private void divideByW(float[] vector) {
        vector[0] /= vector[3];
        vector[1] /= vector[3];
        vector[2] /= vector[3];
    }

    public void update(double deltaTime) {
        Vector3.vector2Between(m_VectorToTarget, m_Position, m_TargetObject.getPosition(), "XZ");
        m_VectorToTarget.scaleBy((float) deltaTime);
        m_Velocity.set(m_VectorToTarget);
        move();
    }

    private void move(){
        m_Position.add(m_Velocity);
        m_LookAt.add(m_Velocity);
    }
}

package uk.co.calvinwylie.chopperv2.util;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;

/**
 * Created by Calvin on 23/02/2015.
 */
public class MatrixHelper {
    public static void perspectiveM(
            float[] m,              //matrix being passed in.
            float yFovInDegrees,    //FOV in degrees
            float aspect,           //aspect ratio of screen (width/height)
            float n,                //near plane distance
            float f)                //far plane distance
    {
        final float angleInRadians = (float) (yFovInDegrees * Math.PI /180.0);  // angle of in Rads
        final float a = (float) (1.0/Math.tan(angleInRadians/2.0));             //focal length of the camera

        m[0]  = a/aspect;
        m[1]  = 0.0f;
        m[2]  = 0.0f;
        m[3]  = 0.0f;

        m[4]  = 0.0f;
        m[5]  = a;
        m[6]  = 0.0f;
        m[7]  = 0.0f;

        m[8]  = 0.0f;
        m[9]  = 0.0f;
        m[10] = -((f+n)/(f-n));
        m[11] = -1.0f;

        m[12] = 0.0f;
        m[13] = 0.0f;
        m[14] = -((2.0f * f * n)/(f - n));
        m[15] = 0.0f;
    }

    public static Vector3 getColumn(Vector3 rv, int column, float[] matrix){
        column -= 1;
        column *= 4;

        rv.set(matrix[column], matrix[column+1], matrix[column+2]);
        return rv;

    }
}

package uk.co.calvinwylie.chopperv2.shaderPrograms;

import android.content.Context;

import uk.co.calvinwylie.chopperv2.R;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;

/**
 * Created by Calvin on 16/04/2015.
 */
public class ColorShader extends Shader {
    //Uniform Locations.
    private final int u_MatrixLocation;
    private final int uColorLocation;

    //Attribute Locations
    private final int a_PositionLocation;

    public ColorShader(Context context){

        super(context, R.raw.color_vertex_shader, R.raw.color_fragment_shader);
        //Retrieve uniform locations for the shader program.
        u_MatrixLocation = glGetUniformLocation(program, U_MATRIX);
        uColorLocation = glGetUniformLocation(program, U_COLOR);
        //Retrieve attribute locations for the shader program.
        a_PositionLocation = glGetAttribLocation(program, A_POSITION);
    }

    public void setUniforms(float[] matrix, float r, float g, float b){
        //Pass the matrix into the shader program.
        glUniformMatrix4fv(u_MatrixLocation, 1, false, matrix, 0);
        glUniform4f(uColorLocation, r, g, b, 1.0f);
    }

    public int getPositionAttributeLocation(){
        return a_PositionLocation;
    }

//    public int getColorAttributeLocation(){
//        return uColorLocation;
//    }
}

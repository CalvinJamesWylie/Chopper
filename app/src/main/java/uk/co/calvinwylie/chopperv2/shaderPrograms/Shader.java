package uk.co.calvinwylie.chopperv2.shaderPrograms;

import android.content.Context;

import java.util.HashMap;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.util.ShaderHelper;
import uk.co.calvinwylie.chopperv2.util.TextResourceReader;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1f;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniform3f;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;


public class Shader {

    //Shader program
    protected final int program;
    protected HashMap <String, Integer> m_Uniforms;
    protected HashMap <String, Integer> m_Attributes;

    protected Shader(Context context, int vertexShaderResourceId, int fragmentShaderResourceId){

        m_Uniforms = new HashMap<String, Integer>();
        m_Attributes = new HashMap<String, Integer>();

        program = ShaderHelper.buildProgram(
                TextResourceReader.readTextFileFromResource(context, vertexShaderResourceId),
                TextResourceReader.readTextFileFromResource(context, fragmentShaderResourceId)
        );
    }

    public void useProgram(){
        //Set the current OpenGL shader program to this program.
        glUseProgram(program);
    }

    protected void addUniform(String uniform){
        int uniformLocation = glGetUniformLocation(program, uniform);
        m_Uniforms.put(uniform, uniformLocation);
    }
    public void setUniform(String uniform, int value){
        glUniform1i(m_Uniforms.get(uniform), value);
    }
    public void setUniform(int uniformLocation, int value){
        glUniform1i(uniformLocation, value);
    }

    public void setUniform(String uniform, float value){
        glUniform1f(m_Uniforms.get(uniform), value);
    }
    public void setUniform(int uniformLocation, float value){
        glUniform1f(uniformLocation, value);
    }

    public void setUniform(String uniform, Vector3 value){
        glUniform3f(m_Uniforms.get(uniform), value.X, value.Y, value.Z);
    }
    public void setUniform(int uniformLocation, Vector3 value){
        glUniform3f(uniformLocation, value.X, value.Y, value.Z);
    }

    public void setUniform(String uniform, float[] value){
        glUniformMatrix4fv(m_Uniforms.get(uniform), 1, false, value, 0);
    }
    public void setUniform(int uniformLocation, float[] value){
        glUniformMatrix4fv(uniformLocation, 1, false, value, 0);
    }

    protected void addAttribute(String attribute){
        int attributeLocation = glGetAttribLocation(program, attribute);
        m_Attributes.put(attribute, attributeLocation);
    }
    protected int getAttributeLocation(String attribute){
        return m_Attributes.get(attribute);
    }


}

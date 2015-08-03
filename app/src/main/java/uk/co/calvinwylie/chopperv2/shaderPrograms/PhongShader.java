package uk.co.calvinwylie.chopperv2.shaderPrograms;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.HashMap;

import uk.co.calvinwylie.chopperv2.R;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
import uk.co.calvinwylie.chopperv2.dataTypes.VertexArray;
import uk.co.calvinwylie.chopperv2.lights.AmbientLight;
import uk.co.calvinwylie.chopperv2.lights.Attenuation;
import uk.co.calvinwylie.chopperv2.lights.BaseLight;
import uk.co.calvinwylie.chopperv2.lights.DirectionalLight;
import uk.co.calvinwylie.chopperv2.lights.PointLight;
import uk.co.calvinwylie.chopperv2.lights.SpotLight;
import uk.co.calvinwylie.chopperv2.models.Material;
import uk.co.calvinwylie.chopperv2.models.TextureManager;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1f;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniform3f;
import static android.opengl.GLES20.glUniformMatrix4fv;


public class PhongShader  extends Shader {

    private static final String tag = "PhongShader";

    private static final int MAX_POINT_LIGHTS = 4;
    private static final int MAX_SPOT_LIGHTS = 4;

    private static AmbientLight m_AmbientLight = new AmbientLight(new Vector3(0.1f,0.1f,0.3f));//(0.5f, 0.5f, 0.5f);
    private static DirectionalLight m_DirectionalLight = new DirectionalLight(
                                                                new BaseLight(new Vector3(1.0f,1.0f,1.0f), 1.0f),
                                                                new Vector3(0.5f,1,0.5f));

    private static PointLight[] m_PointLights = new PointLight[]{};
    private static SpotLight[] m_SpotLights = new SpotLight[]{};

    public PhongShader(Context context){
        super(context, R.raw.phong_vertex_shader, R.raw.phong_fragment_shader);

        //Camera and model data
        addUniform("u_MVPMatrix");
        addUniform("u_MMatrix");
        addUniform("u_EyePos");

        //Light Data
        addUniform("u_AmbientLight");

        addUniform("u_DirectionalLight.base.color");
        addUniform("u_DirectionalLight.base.intensity");
        addUniform("u_DirectionalLight.direction");

        for(int i  = 0; i < MAX_POINT_LIGHTS; i++){
            addUniform("u_PointLights[" + i + "].base.color");
            addUniform("u_PointLights[" + i + "].base.intensity");
            addUniform("u_PointLights[" + i + "].atten.constant");
            addUniform("u_PointLights[" + i + "].atten.linear");
            addUniform("u_PointLights[" + i + "].atten.exponent");
            addUniform("u_PointLights[" + i + "].position");
            addUniform("u_PointLights[" + i + "].range");
        }

        for(int i  = 0; i < MAX_SPOT_LIGHTS; i++){
            addUniform("u_SpotLights[" + i + "].pointLight.base.color");
            addUniform("u_SpotLights[" + i + "].pointLight.base.intensity");
            addUniform("u_SpotLights[" + i + "].pointLight.atten.constant");
            addUniform("u_SpotLights[" + i + "].pointLight.atten.linear");
            addUniform("u_SpotLights[" + i + "].pointLight.atten.exponent");
            addUniform("u_SpotLights[" + i + "].pointLight.position");
            addUniform("u_SpotLights[" + i + "].pointLight.range");
            addUniform("u_SpotLights[" + i + "].direction");
            addUniform("u_SpotLights[" + i + "].cutOff");

        }

        //Material Data
        addUniform("u_TextureUnit");
        addUniform("u_SpecularIntensity");
        addUniform("u_SpecularPower");

        //Vertex Data
        addAttribute("a_Position");
        addAttribute("a_TextureCoords");
        addAttribute("a_Normal");
    }

    public void setUniforms(float[] MVPMatrix, float[] modelMatrix, Vector3 cameraPos, TextureManager textureManager, Material material){

        setUniform("u_MVPMatrix", MVPMatrix);                         //Pass the matrix into the shader program
        setUniform("u_MMatrix", modelMatrix);                         //Pass the matrix into the shader program
        setUniform("u_EyePos", cameraPos);

        setUniform(m_AmbientLight);
        setUniform(m_DirectionalLight);


        for(int i = 0; i < m_PointLights.length; i++){
            setUniform(m_PointLights[i]);//setUniform("u_PointLights[" + i + "]", m_PointLights[i]);
        }

        for(int i = 0; i < m_SpotLights.length; i++){
            setUniform(m_SpotLights[i]);
        }

        glActiveTexture(GL_TEXTURE0);                                                       //Set the active texture unit to texture unit 0.
        glBindTexture(GL_TEXTURE_2D, textureManager.getTexture(material.getTextureType())); //Bind the texture to this unit.
        setUniform("u_TextureUnit", 0);                                                     //Tell the texture uniform sample to use this texture in the shader by telling it to read from texture unit 0.
        setUniform("u_SpecularIntensity", material.getSpecularIntensity());
        setUniform("u_SpecularPower", material.getSpecularPower());
    }



    public int getPositionAttributeLocation(){
        int result = getAttributeLocation("a_Position");
        if(result == -1){
            Log.e(tag, "a_Position aint a thing");
            return result;
        }else {
            return result;
        }
    }
    public int getTextureCoordinatesAttributeLocation(){
        int result = getAttributeLocation("a_TextureCoords");
        if(result == -1){
            Log.e(tag, "a_TextureCoords aint a thing");
            return result;
        }else {
            return result;
        }
    }
    public int getNormalAttributeLocation() {
        int result = getAttributeLocation("a_Normal");
        if(result == -1){
            Log.e(tag, "a_Normal aint a thing");
            return result;
        }else {
            return result;
        }
    }

    public static AmbientLight getAmbientLight() {
        return m_AmbientLight;
    }

    public static DirectionalLight getDirectionalLight() {
        return m_DirectionalLight;
    }

    public static PointLight[] getPointLights(){
        return m_PointLights;
    }
    public static SpotLight[] getSpotLights(){
        return m_SpotLights;
    }

    public static void setAmbientLight(Vector3 ambientLight) {
        m_AmbientLight.setColor(ambientLight);
    }

    public static void setDirectionalLight(DirectionalLight directionalLight){
        m_DirectionalLight = directionalLight;
    }
    public static void setPointLights(PointLight[] pointLights){
        if(pointLights.length> MAX_POINT_LIGHTS){
            Log.e(tag, "ERROR: you passed in too many point lights");
            new Exception().printStackTrace();
            System.exit(1);
        }
        m_PointLights = pointLights;
    }

    public static void setSpotLights(SpotLight[] spotLights) {
        if (spotLights.length > MAX_POINT_LIGHTS) {
            Log.e(tag, "ERROR: you passed in too many spot lights");
            new Exception().printStackTrace();
            System.exit(1);
        }
        m_SpotLights = spotLights;
    }

    public void setLightHandles(){
        for (short i = 0; i < m_PointLights.length; i++){
            m_PointLights[i].getBase().colorLocation     = m_Uniforms.get("u_PointLights[" + i + "].base.color");
            m_PointLights[i].getBase().intensityLocation = m_Uniforms.get("u_PointLights[" + i + "].base.intensity");

            m_PointLights[i].getAtten().constantLocation = m_Uniforms.get("u_PointLights[" + i + "].atten.constant");
            m_PointLights[i].getAtten().linearLocation   = m_Uniforms.get("u_PointLights[" + i + "].atten.linear");
            m_PointLights[i].getAtten().exponentLocation = m_Uniforms.get("u_PointLights[" + i + "].atten.exponent");

            m_PointLights[i].positionLocation            = m_Uniforms.get("u_PointLights[" + i + "].position");
            m_PointLights[i].rangeLocation               = m_Uniforms.get("u_PointLights[" + i + "].range");
        }

        for (int i = 0; i < m_SpotLights.length; i++){
            m_SpotLights[i].getPointLight().getBase().colorLocation     = m_Uniforms.get("u_SpotLights[" + i + "].pointLight.base.color");
            m_SpotLights[i].getPointLight().getBase().intensityLocation = m_Uniforms.get("u_SpotLights[" + i + "].pointLight.base.intensity");

            m_SpotLights[i].getPointLight().getAtten().constantLocation = m_Uniforms.get("u_SpotLights[" + i + "].pointLight.atten.constant");
            m_SpotLights[i].getPointLight().getAtten().linearLocation   = m_Uniforms.get("u_SpotLights[" + i + "].pointLight.atten.linear");
            m_SpotLights[i].getPointLight().getAtten().exponentLocation = m_Uniforms.get("u_SpotLights[" + i + "].pointLight.atten.exponent");

            m_SpotLights[i].getPointLight().positionLocation = m_Uniforms.get("u_SpotLights[" + i + "].pointLight.position");
            m_SpotLights[i].getPointLight().rangeLocation               = m_Uniforms.get("u_SpotLights[" + i + "].pointLight.range");

            m_SpotLights[i].directionLocation                           = m_Uniforms.get("u_SpotLights[" + i + "].direction");
            m_SpotLights[i].cutOffLocation = m_Uniforms.get("u_SpotLights[" + i + "].cutOff");
        }

        m_DirectionalLight.getBase().colorLocation = m_Uniforms.get("u_DirectionalLight.base.color");
        m_DirectionalLight.getBase().intensityLocation = m_Uniforms.get("u_DirectionalLight.base.intensity");
        m_DirectionalLight.directionLocation = m_Uniforms.get("u_DirectionalLight.direction");

        m_AmbientLight.colorLocation = m_Uniforms.get("u_AmbientLight");
    }


    public void setUniform(AmbientLight ambientLight){//(String uniform, AmbientLight ambientLight){
        setUniform(ambientLight.colorLocation, ambientLight.getColor());
    }

    public void setUniform(DirectionalLight directionalLight){//(String uniform, DirectionalLight directionalLight){
        setUniform(directionalLight.getBase().colorLocation,
                   directionalLight.getBase().getColor());
        setUniform(directionalLight.getBase().intensityLocation,
                   directionalLight.getBase().getIntensity());
        setUniform(directionalLight.directionLocation,
                   directionalLight.getDirection());

//        setUniform(uniform + ".base", directionalLight.getBase());
//        setUniform(uniform + ".direction", directionalLight.getDirection());
    }

    public void setUniform(PointLight pointLight) {//(String uniform, PointLight pointLight) {

        setUniform(pointLight.getBase().colorLocation,
                   pointLight.getBase().getColor());
        setUniform(pointLight.getBase().intensityLocation,
                   pointLight.getBase().getIntensity());
        setUniform(pointLight.getAtten().constantLocation,
                   pointLight.getAtten().getConstant());
        setUniform(pointLight.getAtten().linearLocation,
                   pointLight.getAtten().getLinear());
        setUniform(pointLight.getAtten().exponentLocation,
                   pointLight.getAtten().getExponent());
        setUniform(pointLight.positionLocation,
                   pointLight.getPosition());
        setUniform(pointLight.rangeLocation,
                   pointLight.getRange());

//
//        setUniform(uniform + ".base", pointLight.getBase());
//        setUniform(uniform + ".atten.constant", pointLight.getAtten().getConstant());
//        setUniform(uniform + ".atten.linear", pointLight.getAtten().getLinear());
//        setUniform(uniform + ".atten.exponent", pointLight.getAtten().getExponent());
//        setUniform(uniform + ".position", pointLight.getPosition());
//        setUniform(uniform + ".range", pointLight.getRange());
    }

    public void setUniform(SpotLight spotLight){//(String uniform , SpotLight spotlight){
        setUniform(spotLight.getPointLight().getBase().colorLocation,
                spotLight.getPointLight().getBase().getColor());

        setUniform(spotLight.getPointLight().getBase().intensityLocation,
                spotLight.getPointLight().getBase().getIntensity());

        setUniform(spotLight.getPointLight().getAtten().constantLocation,
                spotLight.getPointLight().getAtten().getConstant());

        setUniform(spotLight.getPointLight().getAtten().linearLocation,
                spotLight.getPointLight().getAtten().getLinear());

        setUniform(spotLight.getPointLight().getAtten().exponentLocation,
                spotLight.getPointLight().getAtten().getExponent());

        setUniform(spotLight.getPointLight().positionLocation,
                spotLight.getPointLight().getPosition());

        setUniform(spotLight.getPointLight().rangeLocation,
                spotLight.getPointLight().getRange());

        setUniform(spotLight.directionLocation,
                spotLight.getDirection());

        setUniform(spotLight.cutOffLocation,
                spotLight.getCutOff());




//        setUniform(uniform + ".pointLight", spotlight.getPointLight());
//        setUniform(uniform + ".direction", spotlight.getDirection());
//        setUniform(uniform + ".cutOff", spotlight.getCutOff());
    }

}



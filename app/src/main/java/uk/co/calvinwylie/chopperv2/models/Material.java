package uk.co.calvinwylie.chopperv2.models;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;

/**
 * Created by Calvin on 16/07/2015.
 */
public class Material {
    private TextureType m_TextureType;
    private Vector3 m_Color;
    private float m_SpecularIntensity;
    private float m_SpecularPower;

    private int m_TextureId;

    public Material(TextureType textureType){
        this(textureType, new Vector3(1,1,1));
    }

    public Material(TextureType textureType, Vector3 color){
        this(textureType, color, 1, 8);
    }

    public Material(TextureType textureType, Vector3 color, float specularIntensity, float specularPower){
        m_TextureType = textureType;
        m_Color = color;
        m_SpecularIntensity = specularIntensity;
        m_SpecularPower = specularPower;
    }

    public TextureType getTextureType() {
        return m_TextureType;
    }

    public void setTextureType(TextureType textureType) {
        m_TextureType = textureType;
    }

    public Vector3 getColor() {
        return m_Color;
    }

    public void setColor(Vector3 color) {
        m_Color = color;
    }

    public float getSpecularIntensity() {
        return m_SpecularIntensity;
    }

    public void setSpecularIntensity(float specularIntensity) {
        m_SpecularIntensity = specularIntensity;
    }

    public float getSpecularPower() {
        return m_SpecularPower;
    }

    public void setSpecularPower(float specularPower) {
        m_SpecularPower = specularPower;
    }

    public int getTextureId() {
        return m_TextureId;
    }

    public void setTextureId(int textureId) {
        m_TextureId = textureId;
    }
}

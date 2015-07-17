precision mediump float;

const int MAX_POINT_LIGHTS = 4;
const int MAX_SPOT_LIGHTS = 4;

varying vec2 v_TextureCoords;
varying vec3 v_Normal;
varying vec3 v_WorldPos;

struct BaseLight{
    vec3 color;
    float intensity;
};

struct DirectionalLight{
    BaseLight base;
    vec3 direction;
};

struct Attenuation{
    float constant;
    float linear;
    float exponent;
};

struct PointLight{
    BaseLight base;
    Attenuation atten;
    vec3 position;
    float range;
};

struct SpotLight{
    PointLight pointLight;
    vec3 direction;
    float cutOff;
};

uniform sampler2D u_TextureUnit;
uniform vec3 u_AmbientLight;
uniform vec3 u_EyePos;

uniform float u_SpecularIntensity;
uniform float u_SpecularPower;

uniform DirectionalLight u_DirectionalLight;
uniform PointLight u_PointLights[MAX_POINT_LIGHTS];
uniform SpotLight u_SpotLights[MAX_SPOT_LIGHTS];

vec4 calcLight(BaseLight base, vec3 direction, vec3 normal){
    float diffuseFactor = dot(normal, direction);

    vec4 diffuseColor = vec4(0,0,0,0);
    vec4 specularColor = vec4(0,0,0,0);

    if(diffuseFactor > 0.0){
        diffuseColor = vec4(base.color, 1.0) * base.intensity * diffuseFactor;

        vec3 directionToEye = normalize(u_EyePos - v_WorldPos);
        vec3 reflectDirection = normalize(reflect(direction, normal));

        float specularFactor = dot(directionToEye, reflectDirection);
        specularFactor = pow(specularFactor, u_SpecularPower);

        if(specularFactor > 0.0){
            specularColor = vec4(base.color, 1.0) * u_SpecularIntensity * specularFactor;
        }
    }

    return diffuseColor + specularColor;
}

vec4 calcDirectionalLight(DirectionalLight directionalLight, vec3 normal){
    return calcLight(directionalLight.base, directionalLight.direction, normal);
}

vec4 calcPointLight(PointLight pointLight, vec3 normal){

    vec3 lightDirection = v_WorldPos - pointLight.position;
    float distanceToPoint = length(lightDirection);

    if(distanceToPoint > pointLight.range){
        return vec4(0,0,0,0);
    }

    lightDirection = normalize(lightDirection);

    vec4 color = calcLight(pointLight.base, -lightDirection, normal);

    float attenuation = pointLight.atten.constant +
                        pointLight.atten.linear * distanceToPoint +
                        pointLight.atten.exponent * distanceToPoint * distanceToPoint +
                        0.0001;

    return color / attenuation;
}

vec4 calcSpotLight(SpotLight spotLight, vec3 normal){
    vec3 lightDirection = normalize(v_WorldPos - spotLight.pointLight.position);
    float spotFactor = dot(lightDirection, spotLight.direction);

    vec4 color = vec4(0,0,0,0);

    if(spotFactor > spotLight.cutOff){
        color = calcPointLight(spotLight.pointLight, normal)*
                (1.0 - (1.0 - spotFactor)/(1.0 - spotLight.cutOff));
    }

    return color;
}

void main()
{

    vec4 totalLight = vec4(u_AmbientLight, 1);
    vec4 textureColour = texture2D(u_TextureUnit, v_TextureCoords);

    vec3 normal = normalize(v_Normal);

    totalLight += calcDirectionalLight(u_DirectionalLight, normal);

    for(int i = 0; i < MAX_POINT_LIGHTS; i++){
        if(u_PointLights[i].base.intensity > 0.0)
            totalLight += calcPointLight(u_PointLights[i], normal);
    }

    for(int i = 0; i < MAX_SPOT_LIGHTS; i++){
        if(u_SpotLights[i].pointLight.base.intensity > 0.0)
            totalLight += calcSpotLight(u_SpotLights[i], normal);
    }

    gl_FragColor = textureColour * totalLight;

}
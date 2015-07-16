precision mediump float;

uniform sampler2D u_TextureUnit;
uniform vec3 u_AmbientLight;
varying vec2 v_TextureCoords;

void main()
{

    vec4 totalLight = vec4(u_AmbientLight, 1);
    vec4 textureColour = texture2D(u_TextureUnit, v_TextureCoords);

    gl_FragColor = textureColour * totalLight;

}
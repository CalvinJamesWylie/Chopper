precision mediump float;

uniform sampler2D u_TextureUnit;
varying vec2 v_TextureCoordinates;

varying vec3 v_Normal;

void main()
{
    gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates);
}
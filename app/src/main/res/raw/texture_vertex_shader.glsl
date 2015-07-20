uniform mat4 u_Matrix;

attribute vec4 a_Position;
attribute vec2 a_TextureCoordinates;
attribute vec3 a_Normal;

varying vec2 v_TextureCoordinates;
varying vec3 v_Normal;

void main()
{
    v_TextureCoordinates = a_TextureCoordinates;
    v_Normal = a_Normal;
    gl_Position = u_Matrix * a_Position;
    gl_PointSize = 10.0;
}
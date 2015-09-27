uniform mat4 u_VPMatrix;
uniform mat4 u_MMatrix;

attribute vec3 a_Position;
attribute vec2 a_TextureCoords;
attribute vec3 a_Normal;

varying vec2 v_TextureCoords;
varying vec3 v_Normal;
varying vec3 v_WorldPos;

void main()
{



    v_TextureCoords = a_TextureCoords;
    v_Normal = (u_MMatrix * vec4(a_Normal, 0.0)).xyz;
    v_WorldPos = (u_MMatrix * vec4(a_Position, 1.0)).xyz;

    mat4 MVP;
    MVP = u_VPMatrix * u_MMatrix;

    gl_Position = MVP * vec4(a_Position, 1);
}
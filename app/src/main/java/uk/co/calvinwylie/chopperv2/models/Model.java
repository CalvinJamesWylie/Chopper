//package uk.co.calvinwylie.chopperv2.models;
//
//import uk.co.calvinwylie.chopperv2.dataTypes.VertexArray;
//import uk.co.calvinwylie.chopperv2.shaderPrograms.ColorShaderProgram;
//import uk.co.calvinwylie.chopperv2.shaderPrograms.TextureShaderProgram;
//import uk.co.calvinwylie.chopperv2.util.ModelLoader;
//
//import static android.opengl.GLES20.GL_TRIANGLES;
//import static android.opengl.GLES20.GL_TRIANGLE_FAN;
//import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
//import static android.opengl.GLES20.glDrawArrays;
//
///**
// * Created by Calvin on 16/04/2015.
// */
//public class Model {
//
//    //TODO Will hold texture and shader loading ect.
//    private VertexArray m_VertexArray;
//    private
//
//    public Model(){ //TODO this needs to be initiated by model object returned by LoadModel in modelLoader > fillModelList
//
//    }
//    //TODO bindData and draw
//
//    public void bindData(ColorShaderProgram colorProgram){
//        m_VertexArray.setVertexAttribPointer(0, colorProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 0);
//    }
//
//    public void bindData(TextureShaderProgram textureProgram){
//        m_VertexArray.setVertexAttribPointer(
//                0,
//                textureProgram.getPositionAttributeLocation(),
//                POSITION_COMPONENT_COUNT,
//                STRIDE
//        );
//
//        m_VertexArray.setVertexAttribPointer(
//                POSITION_COMPONENT_COUNT,
//                textureProgram.getTextureCoordinatesAttributeLocation(),
//                TEXTURE_COORDINATES_COMPONENT_COUNT,
//                STRIDE
//        );
//    }
//
//    public void draw(){
//        //Log.i("", "GOT TO BEFORE DRAW ARRAYS");
//        glDrawArrays(GL_TRIANGLES, startVertex, numVertices);
//
//    }
//}

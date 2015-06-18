//package uk.co.calvinwylie.chopperv2.util;
//
//import android.content.Context;
//import android.content.res.Resources;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//
//import uk.co.calvinwylie.chopperv2.R;
//import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;
//import uk.co.calvinwylie.chopperv2.dataTypes.VertexArray;
//import uk.co.calvinwylie.chopperv2.models.Model;
//import uk.co.calvinwylie.chopperv2.models.ModelData;
//
///**
// * Created by Calvin on 17/04/2015.
// */
//public class ModelLoader {
//
//    private static Context m_Context;
//    private static final int FLOATS_PER_VERTEX = 3;
//    private int offset = 0;
//
//    static Model loadModel(int modelResourceId){
//        //TODO read in file and save vertex data, normals, uv coords etc.
//        StringBuilder body = new StringBuilder();
//        //float[] vertexData = new float[sizeInVertices * FLOATS_PER_VERTEX];
//        try{
//            InputStream inputStream = m_Context.getResources().openRawResource(modelResourceId);
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//            String nextLine;
//
//            while ((nextLine = bufferedReader.readLine()) != null){
//                if(nextLine.startsWith("v")){
//
//                }
//                //body.append(nextLine);
//                //body.append('\n');
//            }
//        }catch(IOException e){
//            throw new RuntimeException("Could not open resource: " + modelResourceId, e);
//
//        }catch (Resources.NotFoundException nfe){
//            throw new RuntimeException("Resource not found: " + modelResourceId, nfe);
//        }
//
//       // return body.toString();
//
//        return new Model(
//
//        );
//
//    }
//
//    public static void fillModelList(Context context, ArrayList<Model> modelList) {
//        m_Context = context;
//        for(int i = 0; i < R.integer.TOTAL_MODELS; i++){
//            modelList.add(loadModel(i));
//        }
//    }
//}

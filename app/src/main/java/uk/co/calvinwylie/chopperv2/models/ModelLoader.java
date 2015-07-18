package uk.co.calvinwylie.chopperv2.models;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector2;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;


public class ModelLoader {

    private static Context m_Context;
    private static final int FLOATS_PER_VERTEX = 3;
    private int offset = 0;

    public static Mesh loadModel(Context context, String fileName){
        ArrayList<Vector3> vertices  = new ArrayList<>();
        ArrayList<Vector2> texCoords = new ArrayList<>();
        ArrayList<Vector3> normals   = new ArrayList<>();
        ArrayList<OBJIndex> indices  = new ArrayList<>();

        try{
            InputStream inputStream = context.getAssets().open("models/" + fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String nextLine;

            while ((nextLine = bufferedReader.readLine()) != null) {
                String[] tokens = nextLine.split(" ");
                tokens = ModelUtil.removeEmptyStrings(tokens);

                if (tokens.length == 0 || tokens[0].equals("#") || tokens[0].equals("g")) {
                    continue;
                } else if (tokens[0].equals("v")) {
                    vertices.add(new Vector3(
                            Float.valueOf(tokens[1]),
                            Float.valueOf(tokens[2]),
                            Float.valueOf(tokens[3])));
                }else if (tokens[0].equals("vt")) {
                    texCoords.add(new Vector2(
                            Float.valueOf(tokens[1]),
                            Float.valueOf(tokens[2])));
                }else if(tokens[0].equals("vn")){
                    normals.add(new Vector3(
                            Float.valueOf(tokens[1]),
                            Float.valueOf(tokens[2]),
                            Float.valueOf(tokens[3])));
                } else if (tokens[0].equals("f")) {
                    indices.add(parseIndex(tokens[1]));
                    indices.add(parseIndex(tokens[2]));
                    indices.add(parseIndex(tokens[3]));
                }
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

            return toIndexedMesh(vertices, texCoords, normals, indices);

        }catch(IOException e){
            throw new RuntimeException("Could not open resource: " + fileName, e);

        }catch (Resources.NotFoundException nfe){
            throw new RuntimeException("Resource not found: " + fileName, nfe);
        }
    }

    private static OBJIndex parseIndex(String token){
        short vertexIndex;
        short texCoordIndex;
        short normalIndex;

        String[] values = token.split("/");

        vertexIndex   = (short) (Short.parseShort(values[0]) - 1);
        texCoordIndex = (short) (Short.parseShort(values[1]) - 1);
        normalIndex   = (short) (Short.parseShort(values[2]) - 1);

        return new OBJIndex(vertexIndex, texCoordIndex, normalIndex);
    }

    private static Mesh toIndexedMesh(ArrayList<Vector3> vertices, ArrayList<Vector2> texCoords, ArrayList<Vector3> normals, ArrayList<OBJIndex> indices){

        ArrayList<Vector3> newVertices = new ArrayList<>();
        ArrayList<Vector2> newTexCoords = new ArrayList<>();
        ArrayList<Vector3> newNormals = new ArrayList<>();
        ArrayList<Short> newIndices = new ArrayList<>();

        int i = 0;
        for(OBJIndex index: indices){
            i++;
            Vector3 vertex;
            Vector2 texCoord;
            Vector3 normal;

            vertex = vertices.get(index.vertexIndex);
            texCoord = texCoords.get(index.texCoordIndex);
            normal = normals.get(index.normalIndex);

            newVertices.add(vertex);
            newTexCoords.add(texCoord);
            newNormals.add(normal);
            newIndices.add((short)(i - 1));
        }

        Mesh mesh = new Mesh();

        Vector3[] vertexData = new Vector3[newVertices.size()];
        newVertices.toArray(vertexData);

        Vector2[] texCoordData = new Vector2[newTexCoords.size()];
        newTexCoords.toArray(texCoordData);

        Vector3[] normalData = new Vector3[newNormals.size()];
        newNormals.toArray(normalData);

        Short[] indexData = new Short[newIndices.size()];
        newIndices.toArray(indexData);
        mesh.addVertices(vertexData, texCoordData, normalData, ModelUtil.toShortArray(indexData));

        return mesh;
    }

//    public static void fillModelList(Context context, ArrayList<Model> modelList) {
//        m_Context = context;
//        for(int i = 0; i < R.integer.TOTAL_MODELS; i++){
//            modelList.add(loadModel(i));
//        }
//    }
}

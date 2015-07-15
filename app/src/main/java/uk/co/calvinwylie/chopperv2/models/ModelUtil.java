package uk.co.calvinwylie.chopperv2.models;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import uk.co.calvinwylie.chopperv2.dataTypes.Vector2;
import uk.co.calvinwylie.chopperv2.dataTypes.Vector3;

/**
 * Created by Calvin on 07/07/2015.
 */
public class ModelUtil {

    private static FloatBuffer createFloatBuffer(int size){
        return ByteBuffer
                .allocateDirect(size << 2) //divide by 8 to convert to bytes from bits.
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
    }

    private static ShortBuffer createShortBuffer(int size){
        return ByteBuffer
                .allocateDirect(size << 1)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer();
    }

    public static ShortBuffer createFlippedBuffer(short[] values) {
        ShortBuffer buffer = createShortBuffer(values.length);
        buffer.put(values);
        buffer.flip();

        return buffer;
    }

    public static FloatBuffer createFlippedBuffer(Vector3[] positions){
        FloatBuffer buffer = createFloatBuffer(positions.length * Vector3.SIZE);

        for(int i = 0; i < positions.length; i++){
            buffer.put(positions[i].X);
            buffer.put(positions[i].Y);
            buffer.put(positions[i].Z);
        }

        buffer.flip();

        return buffer;
    }

    public static FloatBuffer createFlippedBuffer(Vector2[] texCoords){
        FloatBuffer buffer = createFloatBuffer(texCoords.length * Vector2.SIZE);

        for(int i = 0; i < texCoords.length; i++){
            buffer.put(texCoords[i].X);
            buffer.put(texCoords[i].Y);
        }

        buffer.flip();

        return buffer;
    }

    public static String[] removeEmptyStrings(String[] data){
        ArrayList<String> result = new ArrayList<String>();
        for (int i =0; i < data.length; i++) {
            if (!data[i].equals("")) {
                result.add(data[i]);
            }
        }
        String[] res = new String[result.size()];
        result.toArray(res);

        return res;
    }

    public static short[] toShortArray(Short[] data) {

        short[] result = new short[data.length];

        for(int i = 0; i < data.length; i++){
            result[i] = data[i].shortValue();
        }
        return result;
    }
}


package uk.co.calvinwylie.chopperv2.models;

import android.content.Context;

import uk.co.calvinwylie.chopperv2.R;
import uk.co.calvinwylie.chopperv2.util.TextureHelper;


public class TextureManager{

    private int[] m_TextureHandles = new int[TextureType.values().length];

    public void loadTextures(Context context){

        TextureType textures[] = TextureType.values();

        for(TextureType texture: textures) {
            int id = 0;
            switch (texture) {
                case orange:
                    id = R.drawable.orange;
                    break;
                case analog_stick:
                    id = R.drawable.analog_stick;
                    break;
                case check:
                    id = R.drawable.check;
                    break;
                case helicopter:
                    id = R.drawable.helicopter;
                    break;
                case none:
                    id = R.drawable.none;
                    break;
                case grass:
                    id = R.drawable.grass;
                    break;
                case robot:
                    id = R.drawable.robot;
                    break;
                case base:
                    id = R.drawable.base;
                    break;
                default:
                    id = R.drawable.ic_launcher;
            }
            m_TextureHandles[texture.ordinal()] = TextureHelper.loadTexture(context, id);
        }
    }


    public int getTexture(TextureType texture) {
        return m_TextureHandles[texture.ordinal()];
    }
}

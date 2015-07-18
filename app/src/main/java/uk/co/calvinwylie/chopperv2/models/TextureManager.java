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
                case heli_texture:
                    id = R.drawable.heli_texture;
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

package uk.co.calvinwylie.chopperv2.models;

import android.content.Context;
import android.graphics.Bitmap;

import uk.co.calvinwylie.chopperv2.R;
import uk.co.calvinwylie.chopperv2.util.TextureHelper;


public class TextureManager{

    private Context m_Context;

    public TextureManager(Context context){
        m_Context = context;
    }

    private int[] m_TextureHandles = new int[TextureType.values().length];

    public void loadTextures(){

        TextureType textures[] = TextureType.values();

        for(TextureType texture: textures) {
            int id = 0;
            switch (texture) {
                case orange:
                    id = R.drawable.orange;
                    break;
                case green:
                    id = R.drawable.green;
                    break;
                case blue:
                    id = R.drawable.blue;
                    break;
                case brown:
                    id = R.drawable.brown;
                    break;
                case analog_stick:
                    id = R.drawable.analog;
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
                case robot:
                    id = R.drawable.robot;
                    break;
                case base:
                    id = R.drawable.sprite_sheet;
                    break;
                default:
                    id = R.drawable.ic_launcher;
            }
            m_TextureHandles[texture.ordinal()] = TextureHelper.loadTexture(m_Context, id);
        }
    }

    public void loadTexture(TextureType textureType, Bitmap bitmap){
        m_TextureHandles[textureType.ordinal()] = TextureHelper.loadTexture(bitmap);
    }

    public int getTexture(TextureType texture) {
        return m_TextureHandles[texture.ordinal()];
    }
}

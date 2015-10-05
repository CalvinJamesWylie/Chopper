package uk.co.calvinwylie.chopperv2.level;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import uk.co.calvinwylie.chopperv2.R;
import uk.co.calvinwylie.chopperv2.models.TextureManager;
import uk.co.calvinwylie.chopperv2.models.TextureType;

/**
 * Created by Calvin on 16/09/2015.
 */
public class MapCreator {
    Context m_Context;
    public MapCreator(Context context){
        m_Context = context;
    }

    public void createMapTexture(TextureManager textureManager, int mapResourceId){

        final Bitmap mapData = BitmapFactory.decodeResource(m_Context.getResources(), mapResourceId);
        int mapWidth = mapData.getWidth();
        int mapHeight = mapData.getHeight();


        final Bitmap tileTexture = BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.terrain16);
        int tileTextureWidth = tileTexture.getWidth();
        int tileTextureSize = tileTexture.getHeight();

        final Bitmap terrainTexture = Bitmap.createBitmap(mapWidth*tileTextureSize, mapHeight*tileTextureSize, Bitmap.Config.ARGB_8888);

        for (int i = 0; i < mapHeight*mapWidth; i++) {
            int mapDataPixelX = (i / mapWidth);
            int mapDataPixelY = (i % mapHeight);
            int mapDataPixel = mapData.getPixel(mapDataPixelX, mapDataPixelY);

            int terrainPixelX;
            int terrainPixelY;

            int tileOffset = 0;

            switch(mapDataPixel){
                case Color.GREEN:
                    tileOffset = 0;
                    for(int j = 0; j < tileTextureSize*tileTextureSize; j++){
                        int tileTextureX = j/tileTextureSize;
                        int tileTextureY = j%tileTextureSize;

                        terrainPixelX = mapDataPixelX*tileTextureSize + tileTextureX;
                        terrainPixelY = mapDataPixelY*tileTextureSize + tileTextureY;

                        terrainTexture.setPixel(terrainPixelX, terrainPixelY,
                                tileTexture.getPixel(tileTextureX + tileTextureSize*tileOffset, tileTextureY));
                    }
                    break;

                case Color.BLUE:
                    tileOffset = 1;
                    for(int j = 0; j < tileTextureSize*tileTextureSize; j++){
                        int tileTextureX = j/tileTextureSize;
                        int tileTextureY = j%tileTextureSize;

                        terrainPixelX = mapDataPixelX*tileTextureSize + tileTextureX;
                        terrainPixelY = mapDataPixelY*tileTextureSize + tileTextureY;

                        terrainTexture.setPixel(terrainPixelX, terrainPixelY,
                                tileTexture.getPixel(tileTextureX+tileTextureSize*tileOffset, tileTextureY));
                    }
                    break;

                case Color.RED:
                    tileOffset = 2;
                    for(int j = 0; j < tileTextureSize*tileTextureSize; j++){
                        int tileTextureX = j/tileTextureSize;
                        int tileTextureY = j%tileTextureSize;

                        terrainPixelX = mapDataPixelX*tileTextureSize + tileTextureX;
                        terrainPixelY = mapDataPixelY*tileTextureSize + tileTextureY;

                        terrainTexture.setPixel(terrainPixelX, terrainPixelY,
                                tileTexture.getPixel(tileTextureX+tileTextureSize*tileOffset, tileTextureY));
                    }
                    break;


                default:
                    for(int j = 0; j < tileTextureSize*tileTextureSize; j++){
                        int tileTextureX = j/tileTextureSize;
                        int tileTextureY = j%tileTextureSize;

                        terrainPixelX = mapDataPixelX*tileTextureSize + tileTextureX;
                        terrainPixelY = mapDataPixelY*tileTextureSize + tileTextureY;

                        terrainTexture.setPixel(terrainPixelX, terrainPixelY, Color.CYAN);
                    }
                    break;
            }

        }

        textureManager.loadTexture(TextureType.terrain, terrainTexture);

    }


    //float tileScale =  mapXYScale / mapHeight;


    // Tile tile = new Tile();
//            tile.getScale().set(tileScale, 1, tileScale);
//            tile.setPosition(((i / mapWidth) * tileScale) - mapXYScale / 2 + tileScale / 2, 0, ((i % mapHeight) * tileScale) - mapXYScale / 2 + tileScale / 2);
//tile.updateModelMatrix();
    //terrain.getTileList().add(tile);

}

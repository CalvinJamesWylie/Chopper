package uk.co.calvinwylie.chopperv2.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import uk.co.calvinwylie.chopperv2.R;

/**
 * Created by Calvin on 17/02/2015.
 */
public class TextResourceReader {
    static String tag = "TRR";
    public static String readTextFileFromResource(Context context, int resourceId){
        StringBuilder body = new StringBuilder();

        try{
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String nextLine;

            while ((nextLine = bufferedReader.readLine()) != null){
                body.append(nextLine);
                body.append('\n');
            }
        }catch(IOException e){
            throw new RuntimeException("Could not open resource: " + resourceId, e);

        }catch (Resources.NotFoundException nfe){
            throw new RuntimeException("Resource not found: " + resourceId, nfe);
        }

        return body.toString();
    }
}

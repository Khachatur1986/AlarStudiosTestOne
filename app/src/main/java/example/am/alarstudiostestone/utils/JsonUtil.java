package example.am.alarstudiostestone.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import example.am.alarstudiostestone.models.Data;

/**
 * Created by Khach on 31-Jul-18.
 */

public final class JsonUtil {
    public static JSONObject convertToJson(InputStream responseBody) {
        BufferedReader bR = new BufferedReader(new InputStreamReader(responseBody));
        String line = "";
        JSONObject result = null;

        StringBuilder responseStrBuilder = new StringBuilder();
        try {
            while ((line = bR.readLine()) != null) {
                responseStrBuilder.append(line);
            }
            responseBody.close();
            result = new JSONObject(responseStrBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<Data> convertJsonArrayToList(JSONArray jsonArray) {
        ArrayList<Data> dataArrayList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject dataObj = (JSONObject) jsonArray.get(i);
                Data data = new Data();
                data.setImageResourceId(android.R.drawable.btn_star_big_on);
                data.setId("ID: " + dataObj.getString("id"));
                data.setLon("Lon: " + dataObj.getString("lon"));
                data.setLat("Lat: "+ dataObj.getString("lat"));
                data.setCountry("Country: " + dataObj.getString("country"));
                data.setName("Name: " + dataObj.getString("name"));
                dataArrayList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return dataArrayList;
    }
}

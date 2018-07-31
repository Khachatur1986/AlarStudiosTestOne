package example.am.alarstudiostestone.presenters;

import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import example.am.alarstudiostestone.models.Data;
import example.am.alarstudiostestone.utils.Constants;
import example.am.alarstudiostestone.utils.JsonUtil;
import example.am.alarstudiostestone.utils.Util;
import example.am.alarstudiostestone.views.impl.DataLoaderActivity;

/**
 * Created by Khach on 31-Jul-18.
 */

public class DataLoaderPresenter implements Presenter {
    private DataLoaderActivity dataView;
    private int pageIndex = 1;
    private boolean isRunning;

    public DataLoaderPresenter(DataLoaderActivity dataView) {
        this.dataView = dataView;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    public void loadData(String code) {
        if (isRunning) {
            return;
        }
        isRunning = true;

        Pair<String, String>[] params = new Pair[2];
        params[0] = new Pair<>("code", code);
        params[1] = new Pair<>("p", String.valueOf(pageIndex));

        DataLoaderThread dataLoaderThread = new DataLoaderThread(Constants.API_DATA, params);
        dataLoaderThread.start();
    }

    private class DataLoaderThread extends Thread {
        private String requestUrl;
        private Pair<String, String>[] params;

        public DataLoaderThread(String requestUrl, Pair<String, String>[] params) {
            this.requestUrl = requestUrl;
            this.params = params;
        }

        @Override
        public void run() {
            URL url;
            try {
                url = new URL(Util.buildUrl(requestUrl, params));
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Accept", "application/json");

                if (urlConnection.getResponseCode() == 200) {
                    InputStream responseBody = urlConnection.getInputStream();
                    JSONObject responseJsonObj = JsonUtil.convertToJson(responseBody);

                    if (responseJsonObj != null) {
                        switch (responseJsonObj.getString("status")) {
                            case "ok":
                                if (responseJsonObj.getString("status").equals("ok")) {
                                    pageIndex++;
                                    JSONArray dataArray = responseJsonObj.getJSONArray("data");
                                    if (dataArray != null) {
                                        ArrayList<Data> dataArrayList = JsonUtil.convertJsonArrayToList(dataArray);
                                        dataView.getDataList().addAll(dataArrayList);
                                        dataView.updateData();
                                    }
                                }
                                break;
                            case "error":
                                dataView.showErrorMessage("Cant retrieve data from server");
                                break;
                        }
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                isRunning = false;
            }
        }
    }
}

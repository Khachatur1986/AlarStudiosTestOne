package example.am.alarstudiostestone.presenters;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import example.am.alarstudiostestone.utils.Constants;
import example.am.alarstudiostestone.utils.JsonUtil;
import example.am.alarstudiostestone.utils.NetworkUtil;
import example.am.alarstudiostestone.utils.Util;
import example.am.alarstudiostestone.views.impl.LoginActivity;

/**
 * Created by Khach on 30-Jul-18.
 */

public class LoginPresenter implements Presenter {
    private LoginActivity view;

    public LoginPresenter(LoginActivity view) {
        this.view = view;
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

    public void login(String username, String password) {
        if (!NetworkUtil.isNetworkConnected(view.getApplicationContext())) {
            view.showErrorMessage("Check internet connection");
            return;
        }
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            view.showErrorMessage("Invalid Username or password");
        } else {
            Pair<String, String>[] params = new Pair[2];
            params[0] = new Pair<>("username", username);
            params[1] = new Pair<>("password", password);
            new LoginTask().execute(params);
        }
    }

    private class LoginTask extends AsyncTask<Pair<String, String>, String, String> {

        @Override
        protected String doInBackground(Pair<String, String>[] pairs) {
            URL url;
            try {
                url = new URL(Util.buildUrl(Constants.API_LOGIN, pairs));
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Accept", "application/json");

                if (urlConnection.getResponseCode() == 200) {
                    InputStream responseBody = urlConnection.getInputStream();
                    JSONObject responseJsonObj = JsonUtil.convertToJson(responseBody);
                    if (responseJsonObj != null) {
                        switch (responseJsonObj.getString("status")) {
                            case "ok":
                                view.successLogin(responseJsonObj.getString("code"));
                                break;
                            case "error":
                                view.showErrorMessage("Invalid username or password");
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
            }
            return null;
        }
    }
}

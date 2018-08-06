package example.am.alarstudiostestone.views.impl;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import example.am.alarstudiostestone.R;
import example.am.alarstudiostestone.models.Data;

/**
 * https://developers.google.com/maps/documentation/android-sdk/signup
 * https://www.tutorialspoint.com/android/android_google_maps.htm
 * https://www.youtube.com/watch?v=urLA8z6-l3k
 */
public class DataInfoActivity extends AppCompatActivity {

    private static final String TAG = "DataInfoActivity"; //logt

    private static final int ERROR_DIALOG_REQUEST = 9001;

    private ImageView iv_data_info_image;
    private TextView tv_id;
    private TextView tv_lon;
    private TextView tv_lat;
    private TextView tv_country;
    private TextView tv_name;

    private Data data;

    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        Intent intent = getIntent();
        data = (Data) intent.getSerializableExtra("data_info");

        iv_data_info_image.setImageResource(data.getImageResourceId());
        tv_id.setText("ID: " + data.getId());
        tv_lon.setText("LAT: " + data.getLon());
        tv_lat.setText("LON: " + data.getLat());
        tv_country.setText("COUNTRY: " + data.getCountry());
        tv_name.setText("NAME: " + data.getName());

        //Adding map fragment to activity
        if (isServiceOk()) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    gMap = googleMap;
                    double lat = Double.parseDouble(data.getLat());
                    double lon = Double.parseDouble(data.getLon());
                    LatLng latLng = new LatLng(lat, lon);

                    gMap.addMarker(new MarkerOptions().position(latLng).title("Data info"));
                    moveCamera(latLng, 15f);
                }
            });
        }

    }

    private void initViews() {
        iv_data_info_image = findViewById(R.id.iv_data_info_image);
        tv_id = findViewById(R.id.tv_id);
        tv_lon = findViewById(R.id.tv_lon);
        tv_lat = findViewById(R.id.tv_lat);
        tv_country = findViewById(R.id.tv_country);
        tv_name = findViewById(R.id.tv_name);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * @return true if google api is available else false
     */
    private boolean isServiceOk() {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(DataInfoActivity.this);
        if (available == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(DataInfoActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "Google api is not available", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void moveCamera(LatLng latLng, float zoom) {
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }
}

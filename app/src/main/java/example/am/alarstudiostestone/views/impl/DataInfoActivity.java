package example.am.alarstudiostestone.views.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import example.am.alarstudiostestone.R;
import example.am.alarstudiostestone.models.Data;

public class DataInfoActivity extends AppCompatActivity {
    private ImageView iv_data_info_image;
    private TextView tv_id;
    private TextView tv_lon;
    private TextView tv_lat;
    private TextView tv_country;
    private TextView tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        Intent intent = getIntent();
        Data data = (Data) intent.getSerializableExtra("data_info");

        iv_data_info_image.setImageResource(data.getImageResourceId());
        tv_id.setText(data.getId());
        tv_lon.setText(data.getLon());
        tv_lat.setText(data.getLat());
        tv_country.setText(data.getCountry());
        tv_name.setText(data.getName());

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
}

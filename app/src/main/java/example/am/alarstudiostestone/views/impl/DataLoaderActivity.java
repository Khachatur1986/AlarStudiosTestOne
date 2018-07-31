package example.am.alarstudiostestone.views.impl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import example.am.alarstudiostestone.R;
import example.am.alarstudiostestone.adapters.DataLoaderAdapter;
import example.am.alarstudiostestone.models.Data;
import example.am.alarstudiostestone.presenters.DataLoaderPresenter;
import example.am.alarstudiostestone.views.IDataLoaderView;

public class DataLoaderActivity extends AppCompatActivity implements IDataLoaderView {

    private DataLoaderPresenter presenter = new DataLoaderPresenter(this);

    private ListView lv_data_container;
    private DataLoaderAdapter adapter;
    private ArrayList<Data> dataList;
    private Handler handler;

    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_loader);
        lv_data_container = findViewById(R.id.lv_data_container);


        handler = new Handler(getMainLooper());
        final Intent intent = getIntent();
        code = intent.getStringExtra("code");

        dataList = new ArrayList<>();
        adapter = new DataLoaderAdapter(getApplicationContext(), R.layout.row_item_data_list, dataList);
        lv_data_container.setAdapter(adapter);
        presenter.loadData(code);

        lv_data_container.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1Obj = new Intent(DataLoaderActivity.this, DataInfoActivity.class);
                intent1Obj.putExtra("data_info", dataList.get(position));
                startActivity(intent1Obj);
            }
        });

        lv_data_container.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (view.getLastVisiblePosition() == totalItemCount - 1 && lv_data_container.getCount() >= 10) {
                    presenter.loadData(code);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void updateData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void showErrorMessage(final String s) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public ArrayList<Data> getDataList() {
        return dataList;
    }
}

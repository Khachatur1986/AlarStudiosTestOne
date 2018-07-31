package example.am.alarstudiostestone.views.impl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import example.am.alarstudiostestone.R;
import example.am.alarstudiostestone.presenters.LoginPresenter;
import example.am.alarstudiostestone.views.ILoginView;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    private EditText et_username;
    private EditText et_password;
    private Button btn_login;

    private ProgressDialog progressDialog;

    private LoginPresenter presenter = new LoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog(LoginActivity.this);
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                presenter.login(username, password);
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

    private void initViews() {
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
    }

    @Override
    public void successLogin(final String code) {
        progressDialog.dismiss();
        Intent intent = new Intent(this, DataLoaderActivity.class);
        intent.putExtra("code", code);
        startActivity(intent);
        finish();
    }

    @Override
    public void showErrorMessage(final String message) {
        progressDialog.dismiss();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private ProgressDialog showProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading data...");
        progressDialog.setMessage("Please wait.");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        return progressDialog;
    }

}

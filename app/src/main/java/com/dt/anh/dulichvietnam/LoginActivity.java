package com.dt.anh.dulichvietnam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvSignUp;
    private Button btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        initViews();
    }

    private void initViews() {
        tvSignUp = (TextView) findViewById(R.id.tv_do_not_have_account);
        btnLogIn = (Button) findViewById(R.id.btn_log_in);
        btnLogIn.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_do_not_have_account:
                signUp();
                break;
            case R.id.btn_log_in:
                logIn();
                break;
            default:
                break;
        }
    }

    private void logIn() {
        startActivity(new Intent(LogInActivity.this, MainActivity.class));
    }

    private void signUp() {
        startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
        finish();
    }
}

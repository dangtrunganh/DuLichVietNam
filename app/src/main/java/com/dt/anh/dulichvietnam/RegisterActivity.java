package com.dt.anh.dulichvietnam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
    }

    private void initViews() {
        tvLogIn = (TextView) findViewById(R.id.tv_already_have_account);
        tvLogIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_already_have_account:
                logIn();
                break;
            default:
                break;
        }
    }

    private void logIn() {
        startActivity(new Intent(RegisterActivity.this, LogInActivity.class));
        finish();
    }
}

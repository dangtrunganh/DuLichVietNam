package com.dt.anh.dulichvietnam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener {
    private static final String TAG = LoginActivity.class.getName();
    private EditText edtUsernameLogin, edtPasswordLogin;
    private TextView register;
    private Button signIn;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Animation animation, animationLogo;
    private TextInputLayout txtUsername, txtPassword;
    private de.hdodenhof.circleimageview.CircleImageView imgFirst;
    private RelativeLayout rltvLogin;
    private ProgressDialog dialog;
    private CheckBox mCheckBox;
    private String prefname="my_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Nên đặt trong onCreate() và trước setContentView()
//        mAuth = FirebaseAuth.getInstance();
//        mAuthListener = new FirebaseAuth.AuthStateListener()  {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                //Đăng nhập
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if(user != null) {
//                    Log.d(TAG, "onAuthStateChanged:signed_in" + user.getUid());
//                } else {
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                }
//            }
//        };
        setContentView(R.layout.activity_login);
//        user = FirebaseAuth.getInstance().getCurrentUser();
        initViews();
    }

    private void initViews() {
        edtUsernameLogin = (EditText) findViewById(R.id.edt_user_name_log_in);
        edtPasswordLogin = (EditText) findViewById(R.id.edt_password_log_in);
        register = (TextView) findViewById(R.id.tv_don_t_have_account);
        signIn = (Button) findViewById(R.id.btn_sign_in);
        animation = AnimationUtils.loadAnimation(this, R.anim.animation_rung);
        txtUsername = (TextInputLayout) findViewById(R.id.text_input_layout_username);
        txtPassword = (TextInputLayout) findViewById(R.id.text_input_layout_password);
        mCheckBox = (CheckBox) findViewById(R.id.check_box_remember_me);
        register.setOnClickListener(this);
        signIn.setOnClickListener(this);
        mCheckBox.setOnClickListener(this);

        setAnimationLogo();
    }

    private void setAnimationLogo() {
        imgFirst = (CircleImageView) findViewById(R.id.circle_image_view_log_in_first);
        rltvLogin = (RelativeLayout) findViewById(R.id.rltv_login);
        rltvLogin.setVisibility(View.GONE);

        animationLogo = AnimationUtils.loadAnimation(this, R.anim.move_fb_logo);
        animationLogo.setFillAfter(true);
        imgFirst.setAnimation(animationLogo);
        animationLogo.setAnimationListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mAuth.removeAuthStateListener(mAuthListener);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_don_t_have_account:
                Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.btn_sign_in:
                startActivity(new Intent(this, MainActivity.class));
//                if(checkField()) {
//                    dialog = new ProgressDialog(LoginActivity.this);
//                    dialog.setMessage("Waiting..");
//                    dialog.setCancelable(false);
//                    dialog.show();
//                    login();
//                }
                break;
            case R.id.check_box_remember_me:
//                rememberLogin();

                break;
            default:
                break;
        }
    }

//    private void rememberLogin() {
//        if(mCheckBox.isChecked()) {
//
//        } else {
//
//        }
//    }

//    private void login() {
//        final String email = edtUsernameLogin.getText().toString();
//        String password = edtPasswordLogin.getText().toString();
//        mAuth.signInWithEmailAndPassword(email ,password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
//
//                        //Nếu không đăng nhập được, thì cho hiện Toast thông báo
//                        if(!task.isSuccessful()) {{
//                            dialog.dismiss();
//                            Toast.makeText(LoginActivity.this, "Failed to sign in", Toast.LENGTH_SHORT).show();
////                            Log.w(TAG, "signInWithEmail:failed", task.getException());
//                        }}
//                        else {
//                            dialog.dismiss();
//                            Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
//                        }
//                    }
//                });
//    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }

//    private boolean checkField() {
//        String usernameCheckField = edtUsernameLogin.getText().toString();
//        String passCheckField = edtPasswordLogin.getText().toString();
//        if(usernameCheckField.equals("")) {
//            Toast.makeText(this, "Please fill email", Toast.LENGTH_SHORT).show();
//            txtUsername.startAnimation(animation);
//            return false;
//        } else if(passCheckField.isEmpty()) {
//            Toast.makeText(this, "Please fill password", Toast.LENGTH_SHORT).show();
//            txtPassword.startAnimation(animation);
//            return false;
//        }
//        return true;
//    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        rltvLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        savingPreferences();
    }

    private void savingPreferences() {
        //tạo đối tượng getSharedPreferences
        SharedPreferences pre = getSharedPreferences(prefname, MODE_PRIVATE);
        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor = pre.edit();
        String user = edtUsernameLogin.getText().toString();
        String pwd = edtPasswordLogin.getText().toString();
        boolean bchk = mCheckBox.isChecked();
        if(!bchk)
        {
            //xóa mọi lưu trữ trước đó
            editor.clear();
        }
        else
        {
            //lưu vào editor
            editor.putString("user", user);
            editor.putString("pwd", pwd);
            editor.putBoolean("checked", bchk);
        }
        //chấp nhận lưu xuống file
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoringPreferences();
    }

    private void restoringPreferences() {
        SharedPreferences pre=getSharedPreferences
                (prefname,MODE_PRIVATE);
        //lấy giá trị checked ra, nếu không thấy thì giá trị mặc định là false
        boolean bchk=pre.getBoolean("checked", false);
        if(bchk)
        {
            //lấy user, pwd, nếu không thấy giá trị mặc định là rỗng
            String user=pre.getString("user", "");
            String pwd=pre.getString("pwd", "");
            edtUsernameLogin.setText(user);
            edtPasswordLogin.setText(pwd);
        }
        mCheckBox.setChecked(bchk);
    }
}

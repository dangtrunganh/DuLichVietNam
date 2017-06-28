package com.dt.anh.dulichvietnam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RegisterActivity.class.getName();
    private EditText edtFirstName, edtLastName, edtEmail, edtGrade,
            edtAddress, edtAccount, edtPassword, edtResetPassword;
    private Button createAccount;
    private TextView logInExistAccount;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextInputLayout txtFirstName, txtLastName, txtEmail, txtGrade, txtAddress, txtAccount, txtPassword, txtPasswordAgain;
    private Animation animation;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef;
    private DatabaseReference mData;
    private String idUser;
    private UserClient userClient;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_register);
        initViews();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(!(edtFirstName.getText().toString().equals("") ||
                        edtLastName.getText().toString().equals("")||
                        edtGrade.getText().toString().equals(""))) {
                    String fullName = edtFirstName.getText().toString() + " " +
                            edtLastName.getText().toString() +
                            " - " + "K" + edtGrade.getText().toString();
                    if(user != null){
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(fullName).build();
                        user.updateProfile(profileUpdates);
                    }
                    if(!(edtEmail.getText().toString().equals("") || 
                            edtGrade.getText().toString().equals("") || 
                            edtAddress.getText().toString().equals("") || 
                            edtAccount.getText().toString().equals("") || 
                            edtPassword.getText().toString().equals("") || 
                            edtResetPassword.getText().toString().equals("") || 
                            (!edtResetPassword.getText().toString().equals(edtPassword.getText().toString())))) {
                        idUser = user.getUid();
                        String firstNameFb = edtFirstName.getText().toString();
                        String lastNameFb = edtLastName.getText().toString();
                        String emailFb = edtEmail.getText().toString();
                        String gradeFb = edtGrade.getText().toString();
                        String addressFb = edtAddress.getText().toString();
                        String usernameFb = edtAccount.getText().toString();
                        String passwordFb = edtPassword.getText().toString();
                        String linkCover = "https://firebasestorage.googleapis.com/v0/b/connectfb-8d0a5.appspot.com/o/ic_hl_bay.jpg?alt=media&token=0ddf5d25-7060-4ef5-9454-567aa2401805";
                        String linkAvatar = "https://firebasestorage.googleapis.com/v0/b/connectfb-8d0a5.appspot.com/o/image1494323501053.png?alt=media&token=c3907753-99ba-4fe4-8404-940d31a6023e";
                        userClient = new UserClient(idUser, firstNameFb, lastNameFb, emailFb, gradeFb, addressFb, usernameFb, passwordFb, linkCover, linkAvatar);
                        uploadUser();
                    }
                }
            }
        };
    }



    private void initViews() {
        edtFirstName = (EditText) findViewById(R.id.edt_first_name_register_sign_up);
        edtLastName = (EditText) findViewById(R.id.edt_last_name_register_sign_up);
        edtEmail = (EditText) findViewById(R.id.edt_mail_register_sign_up);
        edtGrade = (EditText) findViewById(R.id.edt_grade_register_sign_up);
        edtAddress = (EditText) findViewById(R.id.edt_address_register_sign_up);
        edtAccount = (EditText) findViewById(R.id.edt_username_register_sign_up);
        edtPassword = (EditText) findViewById(R.id.edt_password_register_sign_up);
        edtResetPassword = (EditText) findViewById(R.id.edt_repeat_password_register_sign_up);
        createAccount = (Button) findViewById(R.id.btn_register_sign_up);
        logInExistAccount = (TextView) findViewById(R.id.btn_exist_account_register_sign_up);

        txtFirstName = (TextInputLayout) findViewById(R.id.txt_first_name);
        txtLastName = (TextInputLayout) findViewById(R.id.txt_last_name);
        txtEmail = (TextInputLayout) findViewById(R.id.txt_email);
        txtGrade = (TextInputLayout) findViewById(R.id.txt_grade);
        txtAddress = (TextInputLayout) findViewById(R.id.txt_address);
        txtAccount = (TextInputLayout) findViewById(R.id.txt_username);
        txtPassword = (TextInputLayout) findViewById(R.id.txt_password);
        txtPasswordAgain = (TextInputLayout) findViewById(R.id.txt_password_again);

        animation = AnimationUtils.loadAnimation(this, R.anim.animation_rung);

        createAccount.setOnClickListener(this);
        logInExistAccount.setOnClickListener(this);
    }

    private void uploadUser() {
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("User").child(userClient.getIdUserClient()).child("UserInfo-" + userClient.getIdUserClient())
                .setValue(userClient, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null) {
//                    Toast.makeText(RegisterActivity.this, "Thêm User thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Lỗi khi lưu dữ liệu!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register_sign_up:
                if(checkField()) {
                    dialog = new ProgressDialog(RegisterActivity.this);
                    dialog.setMessage("Waiting..");
                    dialog.setCancelable(false);
                    dialog.show();
                    register();
                }
                break;
            case R.id.btn_exist_account_register_sign_up:
                Intent intentExist = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intentExist);
                finish();
                break;
            default:
                break;
        }

    }

    private void register() {
        final String email = edtEmail.getText().toString();
        final String password = edtPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if(!task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Failed to sign up", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            dialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                            signin();
                        }
                    }
                });
    }

    private void signin() {
        final String email = edtEmail.getText().toString();
        final String password = edtPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email ,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        //Nếu không đăng nhập được, thì cho hiện Toast thông báo
                        if(!task.isSuccessful()) {{
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(RegisterActivity.this, "Failed to sign in", Toast.LENGTH_SHORT).show();
                        }}
                        else {
                            //doSomething()
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
//        super.onBackPressed();
//        Intent intent = new Intent(this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("Exit me", true);
//        startActivity(intent);
//        finish();
    }

    private boolean checkField() {
        String firstNameCheckField = edtFirstName.getText().toString();
        String lastNameCheckField = edtLastName.getText().toString();
        String emailCheckField = edtEmail.getText().toString();
        String gradeCheckField = edtGrade.getText().toString();
        String addressCheckField = edtAddress.getText().toString();
        String accountCheckField = edtAccount.getText().toString();
        String passwordCheckField = edtPassword.getText().toString();
        String passwordAgainCheckField = edtResetPassword.getText().toString();
        if(!passwordAgainCheckField.equals(passwordCheckField)) {
            Toast.makeText(this, "Don't match password, please do again", Toast.LENGTH_SHORT).show();
            txtPasswordAgain.startAnimation(animation);
            return false;
        }
        if(firstNameCheckField.equals("")) {
            Toast.makeText(this, "Please fill first name", Toast.LENGTH_SHORT).show();
            txtFirstName.startAnimation(animation);
            return false;
        } 
        else if(lastNameCheckField.equals("")) {
            Toast.makeText(this, "Please fill last name", Toast.LENGTH_SHORT).show();
            txtLastName.startAnimation(animation);
            return false;
        } else if(emailCheckField.equals("")) {
            Toast.makeText(this, "Please fill email", Toast.LENGTH_SHORT).show();
            txtEmail.startAnimation(animation);
            return false;
        } else if(gradeCheckField.equals("")) {
            Toast.makeText(this, "Please fill grade", Toast.LENGTH_SHORT).show();
            txtGrade.startAnimation(animation);
            return false;
        } else if(addressCheckField.equals("")) {
            Toast.makeText(this, "Please fill address", Toast.LENGTH_SHORT).show();
            txtAddress.startAnimation(animation);
            return false;
        } else if(accountCheckField.equals("")) {
            Toast.makeText(this, "Please fill account", Toast.LENGTH_SHORT).show();
            txtAccount.startAnimation(animation);
            return false;
        } else if(passwordCheckField.isEmpty()) {
            Toast.makeText(this, "Please fill password", Toast.LENGTH_SHORT).show();
            txtPassword.startAnimation(animation);
            return false;
        } else if(passwordAgainCheckField.isEmpty()) {
            Toast.makeText(this, "Please fill repeat password", Toast.LENGTH_SHORT).show();
            txtPasswordAgain.startAnimation(animation);
            return false;
        }
        return true;
    }
}

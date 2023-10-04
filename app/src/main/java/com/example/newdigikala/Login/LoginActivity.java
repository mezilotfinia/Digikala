package com.example.newdigikala.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newdigikala.R;
import com.example.newdigikala.SignupActivity.SignupActivity;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    TextView txtSignup;
    CheckBox showPass;
    ImageView imgClose;
    EditText edtEmail, edtPass;
    LoginViewModel loginViewModel;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupViews();
    }

    private void login() {
        loginViewModel.login(edtEmail.getText().toString(), edtPass.getText().toString()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Message>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Message message) {
                        if (!message.getMessage().equals("not found")) {
                            loginViewModel.saveUserEmail(message.getMessage());
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "ایمیل یا کلمه عبور اشتباه است", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("LOG", "onError: " + e.toString());
                    }
                });
    }

    private void setupViews() {
        loginViewModel=new LoginViewModel(this);
        btnLogin = findViewById(R.id.btn_login_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtEmail.getText().toString().equals("")){
                    edtEmail.setError("ایمیل نمیتواند خالی باشد");
                }else if (edtPass.getText().toString().equals("")){
                    edtPass.setError("رمزعبور نمیتواند خالی باشد");
                }else {
                    login();
                }
            }
        });
        edtEmail = findViewById(R.id.edt_login_email);
        edtPass = findViewById(R.id.edt_login_pass);
        showPass = findViewById(R.id.ch_login_showPass);
        imgClose = findViewById(R.id.img_login_toolbarClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    edtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        txtSignup = findViewById(R.id.btn_login_signup);
        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}
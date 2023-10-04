package com.example.newdigikala.SignupActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.R;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class SignupActivity extends AppCompatActivity {
    EditText edtEmail,edtPass;
    SignupViewModel signupViewModel;
    CompositeDisposable compositeDisposable=new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setupViews();
    }

    private void signupObserve() {
        signupViewModel.signup(edtEmail.getText().toString(),edtPass.getText().toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Message>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Message message) {
                        signupViewModel.saveUserInfo(message.getMessage());
                        Toast.makeText(SignupActivity.this, "ثبت نام با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private void setupViews() {
        signupViewModel=new SignupViewModel(SignupActivity.this);
        edtEmail=findViewById(R.id.edt_signup_email);
        edtPass=findViewById(R.id.edt_signup_pass);
        Button btnSignup=findViewById(R.id.btn_signup_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtEmail.getText().toString().equals("")){
                    edtEmail.setError("ایمیل نمیتواند خالی باشد");
                }else if (edtPass.getText().toString().equals("")){
                    edtPass.setError("رمزعبور نمیتواند خالی باشد");
                }else {
                    signupObserve();
                }
            }
        });
        ImageView imgClose=findViewById(R.id.img_signup_toolbarClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
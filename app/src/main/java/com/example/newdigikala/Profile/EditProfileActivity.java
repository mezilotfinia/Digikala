package com.example.newdigikala.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.R;

import org.json.JSONObject;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class EditProfileActivity extends AppCompatActivity {

    EditText edtName,edtFamily,edtCode,edtHome,edtPhone,edtEmail;
    AppCompatCheckBox checkBox;
    RadioButton radioMan, radioWoman;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    EditProfileViewModel viewModel=new EditProfileViewModel();
    RadioGroup radioGroup;
    Button btnSubmit;
    String daySelected, monthSelected, yearSelected;
    AppCompatSpinner daySpinner, monthSpinner, yearSpinner;
    String[] years, months, days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setupViews();
    }

    private void setupViews() {
        years=getResources().getStringArray(R.array.year);
        months=getResources().getStringArray(R.array.month);
        days=getResources().getStringArray(R.array.days);
        daySpinner = findViewById(R.id.spinner_editProfile_day);
        monthSpinner = findViewById(R.id.spinner_editProfile_month);
        yearSpinner = findViewById(R.id.spinner_editProfile_year);
        ArrayAdapter dayAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item,days);
        ArrayAdapter monthAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item,months);
        ArrayAdapter yearAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item,years);
        daySpinner.setAdapter(dayAdapter);
        monthSpinner.setAdapter(monthAdapter);
        yearSpinner.setAdapter(yearAdapter);
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                daySelected=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monthSelected=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yearSelected=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        edtName = findViewById(R.id.edt_editProfile_name);
        edtFamily = findViewById(R.id.edt_editProfile_family);
        edtCode = findViewById(R.id.edt_editProfile_code);
        edtHome = findViewById(R.id.edt_editProfile_home);
        edtPhone = findViewById(R.id.edt_editProfile_mobile);
        edtEmail = findViewById(R.id.edt_editProfile_email);
        edtEmail.setText(getIntent().getExtras().getString("email"));
        checkBox = findViewById(R.id.chb_editProfile_emailReceive);
        radioGroup = findViewById(R.id.radioGroup_editProfile);
        radioMan = findViewById(R.id.radio_editProfile_man);
        radioWoman = findViewById(R.id.radio_editProfile_woman);
        btnSubmit = findViewById(R.id.btn_editProfile_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedItem=radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton=findViewById(selectedItem);
                viewModel.updateProfile(edtEmail.getText().toString(),edtName.getText().toString(),edtFamily.getText().toString()
                ,edtCode.getText().toString(),edtHome.getText().toString(),edtPhone.getText().toString(),
                        daySelected+"/"+monthSelected+"/"+yearSelected,selectedRadioButton.getText().toString().equals("مرد")?1:0,
                        checkBox.isChecked()?1:0,0
                        ).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Message>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onSuccess(@NonNull Message message) {
                                Log.i("LOG", "onSuccess: "+message.getStatus());
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.i("LOG", "onError: "+e.toString());
                            }
                        });
            }
        });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}
package com.example.newdigikala.Comments.AddComment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.newdigikala.R;

public class WriteCommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment);
        setupViews();
    }

    private void setupViews() {
        final EditText edtTitle=findViewById(R.id.edt_writeComment_title);
        final EditText edtPositive=findViewById(R.id.edt_writeComment_positive);
        final EditText edtNegative=findViewById(R.id.edt_writeComment_negative);
        final EditText edtPassage=findViewById(R.id.edt_writeComment_passage);
        ImageView imgBack=findViewById(R.id.img_writeComment_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button btnSubmit=findViewById(R.id.btn_writeComment_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("title",edtTitle.getText().toString());
                intent.putExtra("positive",edtPositive.getText().toString());
                intent.putExtra("negative",edtNegative.getText().toString());
                intent.putExtra("passage",edtPassage.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
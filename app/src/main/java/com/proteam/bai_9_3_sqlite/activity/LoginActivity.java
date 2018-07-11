package com.proteam.bai_9_3_sqlite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.proteam.bai_9_3_sqlite.R;
import com.proteam.bai_9_3_sqlite.database.MyDatabase;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "TAGG";
    private Button mBtnCreate, mBtnCreateNew;
    private EditText mEdtName, mEdtPass;
    private MyDatabase database = new MyDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mBtnCreate = (Button) findViewById(R.id.btnCreate);
        mBtnCreateNew = (Button) findViewById(R.id.btnRegistry);
        mEdtName = (EditText) findViewById(R.id.edtName);
        mEdtPass = (EditText) findViewById(R.id.edtPass);

        mBtnCreate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                database.open();
                Boolean ketQuaDangNhap = database.kiemTraLogin(mEdtName.getText().toString(), mEdtPass.getText().toString());
                database.close();
                if (ketQuaDangNhap) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("user", mEdtName.getText().toString());
                    intent.putExtra("pass", mEdtPass.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBtnCreateNew.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistryActivity.class);
                startActivity(intent);
            }
        });
    }
}

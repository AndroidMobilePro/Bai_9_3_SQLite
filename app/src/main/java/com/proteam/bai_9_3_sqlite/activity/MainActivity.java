package com.proteam.bai_9_3_sqlite.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.proteam.bai_9_3_sqlite.R;
import com.proteam.bai_9_3_sqlite.database.MyDatabase;

public class MainActivity extends AppCompatActivity {
    private Button mBtnList, mBtnDeleteAll, mBtnUpdate;
    private TextView mTvList;
    private TextView mTvPass;
    private TextView mTvName;

    private MyDatabase database = new MyDatabase(this);

    String userCurrent;
    String passCurrent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent() != null) {
            userCurrent = getIntent().getStringExtra("user");
            passCurrent = getIntent().getStringExtra("pass");
        }

        mBtnList = (Button) findViewById(R.id.btnList);
        mBtnDeleteAll = (Button) findViewById(R.id.btnDeleteAll);
        mBtnUpdate = (Button) findViewById(R.id.btnUpdate);

        mTvList = (TextView) findViewById(R.id.tvListDs);
        mTvName = (TextView) findViewById(R.id.tvName);
        mTvPass = (TextView) findViewById(R.id.tvPass);

        mTvName.setText(userCurrent);
        mTvPass.setText(passCurrent);

        mBtnList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                database.open();
                String ds = database.getData();
                database.close();
                mTvList.setText(ds);
            }
        });

        mBtnUpdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                database.open();
                Boolean ketQuaDangNhap = database.kiemTraLogin(mTvName.getText().toString(), mTvPass.getText().toString());
                database.close();
                if (ketQuaDangNhap) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Change password");
                    builder.setMessage("New Password:");
                    final EditText input = new EditText(MainActivity.this);
                    builder.setView(input);
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    database.open();
                                    Boolean kq = database.changePass(mTvName.getText().toString(), input.getText().toString());
                                    database.close();
                                    if (kq) {
                                        Toast.makeText(MainActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                        passCurrent = input.getText().toString();
                                        mTvPass.setText(passCurrent);
                                    } else {
                                        Toast.makeText(MainActivity.this, "Cập nhật tên thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    builder.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        mBtnDeleteAll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                database.open();
                database.deleteAccountAll();
                database.close();
            }
        });

    }
}
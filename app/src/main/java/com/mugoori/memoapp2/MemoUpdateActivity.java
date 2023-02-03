package com.mugoori.memoapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mugoori.memoapp2.data.DatabaseHandler;
import com.mugoori.memoapp2.model.Memo;

public class MemoUpdateActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editContent;
    Button btnSave;

    int id;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_update);

        editContent = findViewById(R.id.editContent);
        editTitle = findViewById(R.id.editTitle);
        btnSave = findViewById(R.id.btnSave);

        Memo memo = (Memo) getIntent().getSerializableExtra("memo");

        id = memo.id;
        editTitle.setText(memo.title);
        editContent.setText(memo.content);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString().trim();
                String content= editContent.getText().toString().trim();

                DatabaseHandler db = new DatabaseHandler(MemoUpdateActivity.this);

                Memo memo = new Memo(id, title, content);

                db.updateMemo(memo);

                finish();




            }
        });


    }
}
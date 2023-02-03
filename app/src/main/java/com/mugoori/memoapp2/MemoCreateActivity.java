package com.mugoori.memoapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mugoori.memoapp2.data.DatabaseHandler;
import com.mugoori.memoapp2.model.Memo;

public class MemoCreateActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editContent;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_create);

        editContent = findViewById(R.id.editContent);
        editTitle = findViewById(R.id.editTitle);
        btnSave = findViewById(R.id.btnSave);



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 에디트 텍스트 받기
                String title= editTitle.getText().toString().trim();
                String content = editContent.getText().toString().trim();

                if (title.isEmpty() ){
                    Toast.makeText(MemoCreateActivity.this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 2. 객체 생성
                Memo memo = new Memo(title, content);

                // 3. 디비에 저장
                DatabaseHandler db = new DatabaseHandler(MemoCreateActivity.this);
                db.AddMemo(memo);

                // 4. 메인 액티비티로 돌아가기
                finish();





            }
        });
    }
}
package com.mugoori.memoapp2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.mugoori.memoapp2.model.Memo;
import com.mugoori.memoapp2.util.Util;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    ArrayList<Memo> memoArrayList = new ArrayList<>();


    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DB_NAME, null, Util.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_MEMO_TABLE= "create table memo ( id integer primary key, title text, content text )";
        sqLiteDatabase.execSQL(CREATE_MEMO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE = "drop table memo";
//        sqLiteDatabase.execSQL(DROP_TABLE);

        sqLiteDatabase.execSQL(DROP_TABLE, new String[]{Util.DB_NAME});

        onCreate(sqLiteDatabase);

    }

    public void AddMemo(Memo memo){
        // 1. 데이터베이스를 가져온다
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. 저장가능한 형식으로 만들어준다
        ContentValues values = new ContentValues();
        values.put(Util.KEY_TITLE,memo.title);
        values.put(Util.KEY_CONTENT,memo.content);
        // 3. insert 한다
        db.insert(Util.TABLE_NAME, null, values);
        // 4. DB 사용이 끝나면 닫아준다
        db.close();
    }


    public void deleteMemo(Memo memo) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " delete from memo where id = ?";
        db.execSQL(query,new String[] {memo.id+""});
        db.close();
    }

    public ArrayList<Memo> getAllMemos() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from memo";
        Cursor cursor = db.rawQuery(query,null);


        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);

                Memo memo = new Memo(id, title, content);
                memoArrayList.add(0,memo);


            }while (cursor.moveToNext());

        }

        db.close();

        return memoArrayList;

    }

    public void updateMemo(Memo memo) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " update memo " + " set title = ?, content = ?" + " where id = ?";
        db.execSQL(query,new String[] {memo.title,memo.content,memo.id+""});
        db.close();

    }

    public ArrayList<Memo> SearchMemo(String keyword) {
        //1. 데이터베이스를 가져온다.
        SQLiteDatabase db = this.getReadableDatabase();

        //2. 쿼리문 만든다.
        String query = " select * from memo where content like '%" + keyword + "%' or title like '%" + keyword + "%' " ;

        //3. 쿼리문을 실행하여, 커서로 받는다.
        Cursor cursor = db.rawQuery(query,null);

        //3-1.여러 데이터를 저장할 어레이리스트를 만든다.
        ArrayList<Memo> memoArrayList = new ArrayList<>();
        //4. 커서에서 데이터를 뽑아낸다.
        if(cursor.moveToFirst()){
            do {

                int id =cursor.getInt(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);


                // 이 데이터를,화면에 표시하기 위해서는
                // 메모리에 전부 다 남아있어야 한다.

                Memo memo = new Memo(id,title,content);
                memoArrayList.add(0,memo);

            }while(cursor.moveToNext());
        }

        db.close();

        return memoArrayList;

    }


}

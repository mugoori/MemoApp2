package com.mugoori.memoapp2.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mugoori.memoapp2.MemoUpdateActivity;
import com.mugoori.memoapp2.R;
import com.mugoori.memoapp2.data.DatabaseHandler;
import com.mugoori.memoapp2.model.Memo;

import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.viewHolder> {

    Context context;
    List<Memo> memoList;

    int deleteIndex;

    public MemoAdapter(Context context, List<Memo> contactList) {
        this.context = context;
        this.memoList = contactList;
    }

    @NonNull
    @Override
    public MemoAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_row,parent,false);
        return new MemoAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoAdapter.viewHolder holder, int position) {
        Memo memo = memoList.get(position);

        holder.txtTitle.setText( memo.title );
        holder.txtContent.setText( memo.content );


    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtContent;
        ImageView imgDelete;
        CardView cardView;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtContent = itemView.findViewById(R.id.txtContent);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 1. 인텐트에 유저가 누른 이름과 전화번호를 담는다
                    int index = getAdapterPosition();

                    Memo memo = memoList.get(index);

                    // 2. 수정 액티비티를 띄운다
                    // 어떤 액티비티가 어떤 액티비티를 띄운다
                    Intent intent = new Intent(context, MemoUpdateActivity.class);

                    intent.putExtra("memo", memo);

                    context.startActivity(intent);

                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteIndex = getAdapterPosition();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("메모 삭제");
                    builder.setMessage("정말 삭제 하시겠습니까 ?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DatabaseHandler db = new DatabaseHandler(context);
                            Memo memo = memoList.get(deleteIndex);
                            db.deleteMemo(memo);
                            memoList.remove(deleteIndex);
                            notifyDataSetChanged();



                        }
                    });
                    builder.setNegativeButton("No", null);
                    builder.show();

                }
            });


        }
    }


}

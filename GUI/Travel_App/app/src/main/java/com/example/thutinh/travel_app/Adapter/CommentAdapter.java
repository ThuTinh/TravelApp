package com.example.thutinh.travel_app.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thutinh.travel_app.DTO.Comment_class;
import com.example.thutinh.travel_app.R;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends  RecyclerView.Adapter<CommentAdapter.ViewHolder> {


    private List<Comment_class> listComment = new ArrayList<>();
    public CommentAdapter(List<Comment_class> listComment) {
        this.listComment = listComment;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listCommnetView = inflater.inflate(R.layout.item_comment,viewGroup, false);
        CommentAdapter.ViewHolder viewHolder = new CommentAdapter.ViewHolder(listCommnetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Comment_class item = listComment.get(i);
        TextView lbName = viewHolder.lbName;
        lbName.setText(item.getName());
        TextView lbContentCmt = viewHolder.lbContentCmt;
        lbContentCmt.setText(item.getCmt());
        TextView lbTime = viewHolder.lbTime;
        lbTime.setText(item.getTimeCmt());
    }

    @Override
    public int getItemCount() {
        return listComment.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView lbName, lbContentCmt, lbTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lbName = (TextView)itemView.findViewById(R.id.lbName);
            lbContentCmt = (TextView)itemView.findViewById(R.id.lbContentCmt);
            lbTime = (TextView)itemView.findViewById(R.id.lbTime);


        }
    }
}

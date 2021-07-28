package com.example.dailymemes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MemeAdapter extends RecyclerView.Adapter<MemeAdapter.ViewHolder> {
    private  List<Meme> memes;

    public MemeAdapter(Context applicationContext,List<Meme> memes) {
        this.memes = memes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meme_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MemeAdapter.ViewHolder holder, int position) {
        String memeimage = memes.get(position).getMeme();
        holder.setData(memeimage);
    }

    @Override
    public int getItemCount() {
        return memes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView currentmeme;
        public ViewHolder(View itemView) {
            super(itemView);
            currentmeme = itemView.findViewById(R.id.meme);
        }
        public void setData(String memeImage) {
            Picasso.get().load(memeImage).into(currentmeme);

        }
    }
}

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

    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
        void downloadImage(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

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
        String shareurl = memeimage;
        holder.setData(memeimage);
    }

    @Override
    public int getItemCount() {
        return memes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView currentmeme;
        ImageView shareIcon;
        ImageView download_image;
        public ViewHolder(View itemView) {
            super(itemView);
            currentmeme = itemView.findViewById(R.id.meme);
            shareIcon = itemView.findViewById(R.id.share);
            download_image = itemView.findViewById(R.id.download);
            shareIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
            download_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.downloadImage(position);
                        }
                    }
                }
            });


        }
        public void setData(String memeImage) {
            Picasso.get().load(memeImage).into(currentmeme);

        }
    }
}

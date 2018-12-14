package com.example.jesulonimi.anyimage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.exampleViewHolder> {
    List<model> mList;
    Context c;

    public myAdapter() {
    }

    public myAdapter(List<model> mList, Context c) {
        this.mList = mList;
        this.c = c;
    }

    @NonNull
    @Override
    public exampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.format,parent,false);
        return new exampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final exampleViewHolder holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.mCardView);
model m=mList.get(position);
final String image=m.getImageUrl();
        Picasso.with(c).load(image).networkPolicy(NetworkPolicy.OFFLINE).fit().into(holder.imv, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

Picasso.with(c).load(image).fit().into(holder.imv);
            }
        });
        String nam=m.getcName();
        int like=m.getLikes();
        holder.likes.setText(Integer.toString(like));
        holder.name.setText(nam);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

   private static OnClickListener listener;

    public interface OnClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnClickListener listener){
        this.listener=listener;
    }

    public static class exampleViewHolder extends RecyclerView.ViewHolder{
ImageView imv;
TextView likes;
TextView name;
    CardView mCardView;
        public exampleViewHolder(View itemView) {
            super(itemView);
            imv=(ImageView) itemView.findViewById(R.id.formatImage);
            likes=(TextView) itemView.findViewById(R.id.likes);
            name=(TextView) itemView.findViewById(R.id.cName);
            mCardView=(CardView) itemView.findViewById(R.id.Mcardview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }

                    }
                }
            });
        }
    }
}

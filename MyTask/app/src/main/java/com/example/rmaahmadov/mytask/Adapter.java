package com.example.rmaahmadov.mytask;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.rmaahmadov.mytask.Interfaces.MyInterface;
import com.example.rmaahmadov.mytask.models.Article;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Article> articles;
    private Context mContex;
    private MyInterface myInterface;



    public Adapter(List<Article> articles, Context mContex, MyInterface myInterface2) {
        this.articles = articles;
        this.mContex = mContex;
        this.myInterface = myInterface2;
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContex).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }





    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int i) {
        final MyViewHolder holder = holders;
        Article model = articles.get(i);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();
        Glide.with(mContex)
                .load(model.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);

        holder.title.setText(model.getTitle());
        holder.desc.setText(model.getDescription());
        holder.source.setText(model.getSource().getName());
        holder.time.setText("\u2022" + Utils.DateToTimeFormat(model.getPublishedAt()));
        holder.published_ad.setText(Utils.DateFormat(model.getPublishedAt()));
        holder.author.setText(model.getAuthor());

    }





    @Override
    public int getItemCount() {
        return articles.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, desc, author, published_ad, source, time;
        ImageView imageView;
        ProgressBar progressBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            published_ad = itemView.findViewById(R.id.publishedAt);
            source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.img);
            progressBar = itemView.findViewById(R.id.progress_load_photo);
            itemView.setOnClickListener(this);
            //this.onItemClickListener=onItemClickListener;
        }
        @Override
        @Nullable
        public void onClick(View v) {
            myInterface.setOnclick(getAdapterPosition());
        }
    }
}

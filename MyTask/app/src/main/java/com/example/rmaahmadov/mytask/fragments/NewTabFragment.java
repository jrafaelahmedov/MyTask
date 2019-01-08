package com.example.rmaahmadov.mytask.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.rmaahmadov.mytask.R;
import com.example.rmaahmadov.mytask.Utils;
import com.example.rmaahmadov.mytask.models.Article;

public class NewTabFragment extends Fragment {

    private String author;
    private String content;
    private String title;
    private String urlToImage;
    private String publishedAt;
    TextView titleNewsTab, authorNewsTab, publishedAtNewsTab, timeNewsTab, contentNewTabs;
    ImageView imageViewNewTab;
    ProgressBar progressBar;
    Toolbar toolbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newtab, container, false);
        getDataParcelable();
        timeNewsTab = view.findViewById(R.id.timeHomeNews);
        toolbar = view.findViewById(R.id.toolbarNewTab);
        titleNewsTab = view.findViewById(R.id.titleHomeNews);
        authorNewsTab = view.findViewById(R.id.authorNewTab);
        publishedAtNewsTab = view.findViewById(R.id.publishedAtHomeNews);
        contentNewTabs = view.findViewById(R.id.contentNewTabs);
        imageViewNewTab = view.findViewById(R.id.imgHomeNews);
        progressBar = view.findViewById(R.id.progress_load_photo);
        setAllContent();
        createMenuSlider();
        return view;
    }






    public void getDataParcelable() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Article article = bundle.getParcelable("HomeNews");
            if (article != null) {
                author = article.getAuthor();
                title = article.getTitle();
                urlToImage = article.getUrlToImage();
                publishedAt = article.getPublishedAt();
                content = article.getContent();
            } else {
                Toast.makeText(getActivity(), "No result,Article null ", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(), "No result,bundle null", Toast.LENGTH_LONG).show();
        }
    }





    public void setAllContent() {
        authorNewsTab.setText(author);
        titleNewsTab.setText(title);
        publishedAtNewsTab.setText(Utils.DateFormat(publishedAt));
        timeNewsTab.setText("\u2022" + Utils.DateToTimeFormat(publishedAt));
        if (content == null) {
            contentNewTabs.setText("No Content");
        } else {
            contentNewTabs.setText(content);
        }
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();
        Glide.with(getActivity())
                .load(urlToImage)
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageViewNewTab);
    }





    //appbar set back button
    @SuppressLint("RestrictedApi")
    private void createMenuSlider() {
        //appbar back button
        setHasOptionsMenu(true);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .remove(NewTabFragment.this)
                .commit();
        return true;
    }



}

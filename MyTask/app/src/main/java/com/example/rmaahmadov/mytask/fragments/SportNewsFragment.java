package com.example.rmaahmadov.mytask.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rmaahmadov.mytask.Adapter;
import com.example.rmaahmadov.mytask.Interfaces.MyInterface;
import com.example.rmaahmadov.mytask.R;
import com.example.rmaahmadov.mytask.api.ApiClient;
import com.example.rmaahmadov.mytask.api.ApiInterface;
import com.example.rmaahmadov.mytask.models.Article;
import com.example.rmaahmadov.mytask.models.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportNewsFragment extends Fragment implements MyInterface ,SwipeRefreshLayout.OnRefreshListener{
    public static final String API_KEY ="92273f4b31d247f8a4798c6bfc9f7713";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private Adapter adapter;
    Call<News> call;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sportnews, container, false);
        swipeRefreshLayout=view.findViewById(R.id.swipeSportNews);
        swipeRefreshSetting();
        recyclerView = view.findViewById(R.id.recyclerViewSportNews);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        progressBar=view.findViewById(R.id.progressSportNews);
        loadJson();
        return view;
    }





    @Override
    public void onRefresh() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                recyclerView.setVisibility(View.VISIBLE);
                loadJson();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }





    public void swipeRefreshSetting(){
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }





    public void loadJson(){
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface =ApiClient.getApiClient().create(ApiInterface.class);
        call =apiInterface.getSportNews("bbc-sport",API_KEY);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful() && response.body().getArticle()!=null){
                    if(!articles.isEmpty()){
                        articles.clear();
                    }
                    articles=response.body().getArticle();
                    adapter=new Adapter(articles,getActivity(), SportNewsFragment.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }else {
                }
            }
            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }




    @Override
    public void setOnclick(int position) {
        Fragment fragment = new NewTabFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("HomeNews", articles.get(position));
        fragment.setArguments(bundle);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction()
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(R.id.activitymaincontainer, fragment, "newTab").commit();
    }
}

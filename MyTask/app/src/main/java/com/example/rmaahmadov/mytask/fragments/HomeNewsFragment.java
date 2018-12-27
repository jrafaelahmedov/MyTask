package com.example.rmaahmadov.mytask.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.example.rmaahmadov.mytask.Utils;
import com.example.rmaahmadov.mytask.api.ApiClient;
import com.example.rmaahmadov.mytask.api.ApiInterface;
import com.example.rmaahmadov.mytask.models.Article;
import com.example.rmaahmadov.mytask.models.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeNewsFragment extends Fragment implements MyInterface {

    public static final String API_KEY = "92273f4b31d247f8a4798c6bfc9f7713";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private Adapter adapter;
    Call<News> call;
    ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homenews, container, false);
        recyclerView = view.findViewById(R.id.recylerViewHomeNews);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        progressBar=view.findViewById(R.id.progressHomeNews);
        loadJson();


        return view;
    }


    public void loadJson() {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String country = Utils.getCountry();


        call = apiInterface.getNews(country, API_KEY);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {

                if (response.isSuccessful() && response.body().getArticle() != null) {
                    if (!articles.isEmpty()) {
                        articles.clear();
                    }
                    articles = response.body().getArticle();
                    adapter = new Adapter(articles, getActivity(), HomeNewsFragment.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                } else {
//                    Toast.makeText(getActivity(), "No Result", Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
//                Toast.makeText(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();
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

        manager.beginTransaction().addToBackStack(null)
                .add(R.id.activityhomelayout, fragment,"newTab").commit();
    }
}

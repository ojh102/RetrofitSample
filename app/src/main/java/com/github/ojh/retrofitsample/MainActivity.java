package com.github.ojh.retrofitsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.ojh.retrofitsample.data.SearchItem;
import com.github.ojh.retrofitsample.data.SearchResult;
import com.github.ojh.retrofitsample.network.MyApi;
import com.github.ojh.retrofitsample.network.NetWorkManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "636df8b114f0cc206273eacf348eb45a";
    public static final String API_OUTPUT = "json";

    MyAdapter mAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.edtSearch)
    EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mAdapter = new MyAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.btnSearch)
    public void onClickSearch() {
        getData(edtSearch.getText().toString());
    }


    private void getData(String keyword) {
        mAdapter.clear();

        MyApi myApi = NetWorkManager.getInstance().getApi(MyApi.class);
        Call<SearchResult> call = myApi.getImageList(API_KEY, keyword, 20, 1, API_OUTPUT);
        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                for(SearchItem item : response.body().channel.item) {
                    mAdapter.add(item);
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Network Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<SearchItem> items = new ArrayList<>();

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            ((MyViewHolder)holder).textView.setText(items.get(position).title);

            Glide.with(MyApplication.getContext())
                    .load(items.get(position).thumbnail)
                    .into(((MyViewHolder)holder).imageView);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void add(SearchItem item) {
            items.add(item);
            notifyDataSetChanged();
        }

        public void clear() {
            items.clear();
            notifyDataSetChanged();
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.textView)
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

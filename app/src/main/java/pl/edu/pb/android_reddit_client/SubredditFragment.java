package pl.edu.pb.android_reddit_client;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pl.edu.pb.android_reddit_client.databinding.FragmentSubredditListBinding;
import pl.edu.pb.android_reddit_client.placeholder.PlaceholderContent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A fragment representing a list of Items.
 */
public class SubredditFragment extends Fragment {

    private RecyclerView recyclerView;
    private MySubredditRecyclerViewAdapter subredditAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FragmentSubredditListBinding binding;
    private final String TAG = "SubredditFragment";

    private String subreddit;
    private List<Subreddit> subredditList;
    private RedditAPI redditAPI;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SubredditFragment() {
    }

    public static SubredditFragment newInstance() {
        SubredditFragment fragment = new SubredditFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            subreddit = SubredditFragmentArgs.fromBundle(getArguments()).getSubreddit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSubredditListBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.8:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        redditAPI = retrofit.create(RedditAPI.class);
        getSubreddits(subreddit);

    }

    public void getSubreddits(String subreddit) {
        Call<List<Subreddit>> call = redditAPI.searchSubreddit(subreddit);
        call.enqueue(new Callback<List<Subreddit>>() {
            @Override
            public void onResponse(Call<List<Subreddit>> call, Response<List<Subreddit>> response) {
                List<Subreddit> subreddits = response.body();
                subredditList = subreddits;
                initRecyclerView();
            }

            @Override
            public void onFailure(Call<List<Subreddit>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    public void initRecyclerView() {
        recyclerView = binding.list;
        subredditAdapter = new MySubredditRecyclerViewAdapter(subredditList, subreddit);
        recyclerView.setAdapter(subredditAdapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }
}
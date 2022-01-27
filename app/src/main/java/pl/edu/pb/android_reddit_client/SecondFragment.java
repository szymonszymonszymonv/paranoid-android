package pl.edu.pb.android_reddit_client;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;
import java.util.TreeMap;

import de.blox.treeview.BaseTreeAdapter;
import de.blox.treeview.TreeNode;
import de.blox.treeview.TreeView;
import pl.edu.pb.android_reddit_client.databinding.FragmentSecondBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.14:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        displayComments(retrofit);
        TreeView treeView= binding.treeViewComments;

        BaseTreeAdapter<ViewHolder> adapter= new BaseTreeAdapter<ViewHolder>(this.getActivity(), R.layout.node){

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(View view) {
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, Object data, int position) {
                viewHolder.textView.setText(data.toString());
            }
        };
        treeView.setAdapter(adapter);
        TreeNode rootNode = new TreeNode("Root");
        TreeNode child1 = new TreeNode("child1");
        TreeNode child2 = new TreeNode("child2");
        TreeNode child3 = new TreeNode("child3");
        TreeNode child4 = new TreeNode("child4");
        TreeNode child5 = new TreeNode("child5");

        child1.addChild(child2);
        child2.addChild(child3);
        child3.addChild(child4);
        child3.addChild(child5);




//        child1.addChildren(child2, child3, child4, child5);

//        child1.addChild(child3);


        rootNode.addChild(child1);


        adapter.setRootNode(rootNode);




        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    public void displayComments(Retrofit retrofit) {
        RedditAPI redditAPI = retrofit.create(RedditAPI.class);

        String postId = requireArguments().getString("postId");

        Call<List<Comment>> call = redditAPI.getComments(postId);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                List<Comment> comments = response.body();

                for (Comment comment : comments){
                    String str = String.format("author: %s%nbody: %s%nscore: %s %n %n",
                            comment.getAuthor(),
                            comment.getBody(),
                            comment.getScore());
//                    binding.comments.append(str);

                    binding.ScrollViewComments.append(str);

                    Log.d("getComments", comment.getBody());
                }


//                Comment firstComment = comments.get(0);
//                String str = String.format("author: %s%nbody: %s%nscore: %s",
//                        firstComment.getAuthor(),
//                        firstComment.getBody(),
//                        firstComment.getScore());
//                binding.comments.setText(str);
//                Log.d("getComments", firstComment.getBody());
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.d("getComments", t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
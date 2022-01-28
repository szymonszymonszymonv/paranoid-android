package pl.edu.pb.android_reddit_client;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pl.edu.pb.android_reddit_client.databinding.CommentsListBinding;



import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private Activity activity;
    List<Comment> parentCommentArrayList;
    List<Comment> childCommentArrayList;
    private CommentsListBinding binding;

    public CommentsAdapter(List<Comment> parentCommentArrayList, Activity activity, List<Comment> childCommentArrayList) {
        this.activity = activity;
        this.parentCommentArrayList = parentCommentArrayList;
        this.childCommentArrayList = childCommentArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CommentsListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(parentCommentArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return parentCommentArrayList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

//        TextView  author, body, score;
//        RecyclerView nested_comments;

        public ViewHolder(@NonNull CommentsListBinding binding) {
//            super(itemView);
            super(binding.getRoot());
//            author= binding.parentAuthor
        }
        public void bind(Comment comment) {
            binding.parentAuthor.setText(comment.getAuthor());
            binding.parentBody.setText(comment.getBody());
            binding.parentTime.setText(comment.getTimeInHours());
            binding.parentScore.submissionScore.setText(comment.getScore()+"");

            ReplyAdapter replyAdapter = new ReplyAdapter(childCommentArrayList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
            binding.nestedReply.setLayoutManager(linearLayoutManager);
            binding.nestedReply.setAdapter(replyAdapter);

        }


    }

}

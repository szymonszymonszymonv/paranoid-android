package pl.edu.pb.android_reddit_client;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import pl.edu.pb.android_reddit_client.databinding.ReplyListBinding;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> {

    private ReplyListBinding binding;
    List<Comment> childCommentArrayList;

    public ReplyAdapter(List<Comment> childCommentArrayList) {
        this.childCommentArrayList = childCommentArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ReplyListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ReplyAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(childCommentArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return childCommentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{



        public ViewHolder(@NonNull ReplyListBinding  binding) {
//            super(itemView);
            super(binding.getRoot());
//
        }
        public void bind(Comment comment) {
            binding.childAuthor.setText(comment.getAuthor());
            binding.childBody.setText(comment.getBody());
            binding.childTime.setText(comment.getTimeInHours());
            binding.childScore.submissionScore.setText(comment.getScore()+"");

//            ReplyAdapter replyAdapter = new ReplyAdapter(childCommentArrayList);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
//            binding.nestedReply.setLayoutManager(linearLayoutManager);
//            binding.nestedReply.setAdapter(replyAdapter);

        }
    }

}

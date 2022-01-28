package pl.edu.pb.android_reddit_client;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import pl.edu.pb.android_reddit_client.databinding.CommentsListBinding;
import pl.edu.pb.android_reddit_client.databinding.ReplyListBinding;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> {

    private ReplyListBinding binding;
    private CommentsListBinding binding1;
    List<Comment> childCommentArrayList;
    private Activity activity;
    private OnReplyClickListener OnReplyClickListener;

    public ReplyAdapter(List<Comment> childCommentArrayList, Activity activity) {
        this.activity = activity;
        this.childCommentArrayList = childCommentArrayList;

    }

    public interface OnReplyClickListener {
        void onReplyClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("create reply", "create reply");
        binding = ReplyListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ReplyAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = childCommentArrayList.get(position);
        holder.bind(comment);

    }

    @Override
    public int getItemCount() {
        return childCommentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        public ViewHolder(@NonNull ReplyListBinding  binding) {
//            super(itemView);
            super(binding.getRoot());


        }




        public void bind(Comment comment) {



            try{
                binding.childAuthor.setText(comment.getAuthor());
                binding.childBody.setText(comment.getBody());
                binding.childTime.setText(comment.getTimeInHours());
                binding.childScore.submissionScore.setText(comment.getScore()+"");
                Log.d("test", comment.getAuthor());


            }catch (IndexOutOfBoundsException e) {
                System.out.println("brak replies: ");
            }


//            ReplyAdapter replyAdapter = new ReplyAdapter(comment.getReplies(), activity);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);

//            linearLayoutManager.addView(binding1.nestedReply);
//            notifydatasetchanged
//            binding1.nestedReply.setLayoutManager(linearLayoutManager);
//            binding1.nestedReply.setAdapter(replyAdapter);
        }

    }



}

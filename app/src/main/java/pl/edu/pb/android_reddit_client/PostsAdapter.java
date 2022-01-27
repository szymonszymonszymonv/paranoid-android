package pl.edu.pb.android_reddit_client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.edu.pb.android_reddit_client.databinding.PostItemBinding;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {
    private List<Post> posts;
    private PostItemBinding binding;
    private OnPostClickListener onPostClickListener;

    public PostsAdapter(List<Post> p, OnPostClickListener listener){
        posts = p;
        onPostClickListener = listener;
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private OnPostClickListener onPostClickListener;

        public PostsViewHolder(@NonNull PostItemBinding binding, OnPostClickListener listener) {
            super(binding.getRoot());
            onPostClickListener = listener;
            binding.getRoot().setOnClickListener(this);
        }
        public void bind(Post post) {
            binding.authorText.setText(post.getAuthor());
            binding.subredditText.setText(post.getSubreddit());
            binding.titleText.setText(post.getTitle());
            binding.timeText.setText(post.getTimeInHours());
        }

        @Override
        public void onClick(View v) {
            onPostClickListener.onPostClick(getBindingAdapterPosition());
        }
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = PostItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostsViewHolder(binding, onPostClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        holder.bind(posts.get(position));

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public interface OnPostClickListener {
        void onPostClick(int position);
    }

}

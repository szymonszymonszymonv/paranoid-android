package pl.edu.pb.android_reddit_client;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.edu.pb.android_reddit_client.placeholder.PlaceholderContent.PlaceholderItem;
import pl.edu.pb.android_reddit_client.databinding.SubredditItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MySubredditRecyclerViewAdapter extends RecyclerView.Adapter<MySubredditRecyclerViewAdapter.ViewHolder> {

    private final List<Subreddit> mValues;

    public MySubredditRecyclerViewAdapter(List<Subreddit> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(SubredditItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.subredditText.setText(mValues.get(position).getUrl());
        holder.descriptionText.setText(mValues.get(position).getDescription());
        holder.membersText.setText((mValues.get(position).getSubscribers()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView subredditText;
        public final TextView membersText;
        public final TextView descriptionText;
        public Subreddit mItem;

        public ViewHolder(SubredditItemBinding binding) {
            super(binding.getRoot());
            subredditText = binding.subredditText;
            membersText = binding.membersText;
            descriptionText = binding.subredditDescriptionText;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + subredditText.getText() + "'";
        }
    }
}
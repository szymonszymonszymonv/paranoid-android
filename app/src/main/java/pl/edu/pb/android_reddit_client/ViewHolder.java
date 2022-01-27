package pl.edu.pb.android_reddit_client;

import android.view.View;
import android.widget.TextView;

import pl.edu.pb.android_reddit_client.databinding.FragmentSecondBinding;

public class ViewHolder {

    TextView textView;
    ViewHolder(View view){
        textView = view.findViewById(R.id.nodeTextView);
    }

}

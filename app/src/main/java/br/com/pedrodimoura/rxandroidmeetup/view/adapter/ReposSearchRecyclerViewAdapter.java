package br.com.pedrodimoura.rxandroidmeetup.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.pedrodimoura.rxandroidmeetup.R;
import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.ReposPayload;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by pedrodimoura on 21/07/16.
 */
public class ReposSearchRecyclerViewAdapter extends RecyclerView.Adapter<ReposSearchRecyclerViewAdapter.ReposSearchViewHolder> {

    private Context mContext;
    private ReposPayload mReposPayload;
    private View mView;

    public ReposSearchRecyclerViewAdapter(Context context) {
        this.mContext = context;
        this.mReposPayload = new ReposPayload();
    }

    public void updateList(ReposPayload reposPayload) {
        this.mReposPayload = reposPayload;
        notifyDataSetChanged();
    }

    public void clearList(ReposPayload reposPayload) {
        this.mReposPayload = null;
    }

    @Override
    public ReposSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.repos_item, parent, false);
        return new ReposSearchViewHolder(this.mView);
    }

    @Override
    public void onBindViewHolder(ReposSearchViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        Picasso
                .with(this.mContext)
                .load(this.mReposPayload.getItems().get(position).getOwner().getAvatarUrl())
                .into(holder.circleImageViewReposOwnerAvatar);

        holder.textViewReposName.setText(this.mReposPayload.getItems().get(position).getName());
        holder.textViewReposOwnerLogin.setText(this.mReposPayload.getItems().get(position).getOwner().getLogin());
        holder.textViewReposDescription.setText(this.mReposPayload.getItems().get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return (null != this.mReposPayload.getItems() && this.mReposPayload.getItems().size() > 0 ? this.mReposPayload.getItems().size() : 0);
    }

    public class ReposSearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.circleImageViewReposOwnerAvatar) CircleImageView circleImageViewReposOwnerAvatar;
        @BindView(R.id.textViewReposName) TextView textViewReposName;
        @BindView(R.id.textViewReposOwnerLogin) TextView textViewReposOwnerLogin;
        @BindView(R.id.textViewReposDescription) TextView textViewReposDescription;

        public ReposSearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(ReposSearchViewHolder.this, itemView);
        }
    }

}

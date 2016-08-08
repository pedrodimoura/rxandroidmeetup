package br.com.pedrodimoura.rxandroidmeetup.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.pedrodimoura.rxandroidmeetup.R;
import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.ReposPayload;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pedrodimoura on 08/08/16.
 */
public class UserReposRecyclerViewAdapter extends RecyclerView.Adapter<UserReposRecyclerViewAdapter.UserReposViewHolder> {

    private Context mContext;
    private ReposPayload mReposPayload;
    private View mView;

    public UserReposRecyclerViewAdapter(Context context) {
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
    public UserReposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_repos_item, parent, false);
        return new UserReposViewHolder(this.mView);
    }

    @Override
    public void onBindViewHolder(UserReposViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        holder.textViewReposName.setText(this.mReposPayload.getItems().get(position).getName());

    }

    @Override
    public int getItemCount() {
        return (null != this.mReposPayload.getItems() && this.mReposPayload.getItems().size() > 0 ? this.mReposPayload.getItems().size() : 0);
    }

    public class UserReposViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewReposName)
        TextView textViewReposName;

        public UserReposViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(UserReposViewHolder.this, itemView);
        }
    }

}

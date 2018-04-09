package br.com.pedrodimoura.rxandroidmeetup.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import br.com.pedrodimoura.rxandroidmeetup.databinding.ReposItemBinding;
import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.ReposPayload;
import br.com.pedrodimoura.rxandroidmeetup.util.Constants;
import br.com.pedrodimoura.rxandroidmeetup.view.activity.impl.ReposDetailsActivity;

/**
 * Created by pedrodimoura on 21/07/16.
 */
public class ReposSearchRecyclerViewAdapter extends RecyclerView.Adapter<ReposSearchRecyclerViewAdapter.ReposSearchViewHolder> {

    private Context mContext;
    private ReposPayload mReposPayload;

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

    @NonNull
    @Override
    public ReposSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ReposItemBinding reposItemBinding =
                ReposItemBinding.inflate(layoutInflater, parent, false);
        return new ReposSearchViewHolder(reposItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReposSearchViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        Picasso
                .with(this.mContext)
                .load(this.mReposPayload.getItems().get(position).getOwner().getAvatarUrl())
                .into(holder.reposItemBinding.circleImageViewReposOwnerAvatar);

        holder.reposItemBinding.textViewReposName.setText(this.mReposPayload.getItems().get(position).getName());
        holder.reposItemBinding.textViewReposOwnerLogin.setText(this.mReposPayload.getItems().get(position).getOwner().getLogin());
        holder.reposItemBinding.textViewReposDescription.setText(this.mReposPayload.getItems().get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return (null != this.mReposPayload.getItems() && this.mReposPayload.getItems().size() > 0 ? this.mReposPayload.getItems().size() : 0);
    }

    public class ReposSearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ReposItemBinding reposItemBinding;

        public ReposSearchViewHolder(ReposItemBinding reposItemBinding) {
            super(reposItemBinding.getRoot());
            this.reposItemBinding = reposItemBinding;
            this.reposItemBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mContext.startActivity(
                    new Intent(
                            mContext,
                            ReposDetailsActivity.class)
                            .putExtra(
                                    Constants.REPOS,
                                    new Gson()
                                            .toJson(
                                                    mReposPayload
                                                            .getItems()
                                                            .get(getLayoutPosition())
                                            )
                            )
            );
        }
    }

}

package br.com.pedrodimoura.rxandroidmeetup.view.activity.impl;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import br.com.pedrodimoura.rxandroidmeetup.R;
import br.com.pedrodimoura.rxandroidmeetup.databinding.ActivityReposDetailsBinding;
import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.Repos;
import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.ReposPayload;
import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.User;
import br.com.pedrodimoura.rxandroidmeetup.presenter.impl.ReposPresenter;
import br.com.pedrodimoura.rxandroidmeetup.util.Constants;
import br.com.pedrodimoura.rxandroidmeetup.view.activity.IActivity;
import br.com.pedrodimoura.rxandroidmeetup.view.adapter.UserReposRecyclerViewAdapter;

public class ReposDetailsActivity extends AppCompatActivity implements IActivity {

    private ActivityReposDetailsBinding mActivityReposDetailsBinding;

    private ReposPresenter mReposPresenter;
    private UserReposRecyclerViewAdapter mUserReposRecyclerViewAdapter;

    private Repos mRepos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos_details);

        this.mActivityReposDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_repos_details);
        setSupportActionBar(this.mActivityReposDetailsBinding.toolbarReposDetailsActivity.defaultToolbar);
        setInit();
    }

    private void setInit() {
        this.mActivityReposDetailsBinding.toolbarReposDetailsActivity.defaultToolbar.setTitle(getString(R.string.title_activity_repos_details));
        this.mUserReposRecyclerViewAdapter = new UserReposRecyclerViewAdapter(ReposDetailsActivity.this);
        this.mActivityReposDetailsBinding.contentReposDetails.recyclerViewUserRepos.setLayoutManager(new LinearLayoutManager(ReposDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
        this.mActivityReposDetailsBinding.contentReposDetails.recyclerViewUserRepos.setAdapter(this.mUserReposRecyclerViewAdapter);

        String reposJSON = getIntent().getStringExtra(Constants.REPOS);
        this.mRepos = new Gson().fromJson(reposJSON, Repos.class);

        this.mReposPresenter = new ReposPresenter(ReposDetailsActivity.this);
        this.mReposPresenter.getUser(this.mRepos.getOwner().getLogin());
    }

    @Override
    public void showReposOnUI(ReposPayload reposPayload) {
        this.mUserReposRecyclerViewAdapter.updateList(reposPayload);
    }

    @Override
    public void showUserOnUI(User user) {

        Picasso
                .with(ReposDetailsActivity.this)
                .load(user.getAvatarUrl())
                .into(this.mActivityReposDetailsBinding.contentReposDetails.circleImageViewUserAvatar);

        this.mActivityReposDetailsBinding.contentReposDetails.textViewUserName.setText(user.getName());

    }

    @Override
    public void showErrorOnUI(Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorOnUI(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
    }

    @Override
    public Activity getActivityContext() {
        return ReposDetailsActivity.this;
    }}

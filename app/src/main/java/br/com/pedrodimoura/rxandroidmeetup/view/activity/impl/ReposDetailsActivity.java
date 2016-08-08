package br.com.pedrodimoura.rxandroidmeetup.view.activity.impl;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import br.com.pedrodimoura.rxandroidmeetup.R;
import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.Repos;
import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.ReposPayload;
import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.User;
import br.com.pedrodimoura.rxandroidmeetup.presenter.impl.ReposPresenter;
import br.com.pedrodimoura.rxandroidmeetup.util.Constants;
import br.com.pedrodimoura.rxandroidmeetup.view.activity.IActivity;
import br.com.pedrodimoura.rxandroidmeetup.view.adapter.UserReposRecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ReposDetailsActivity extends AppCompatActivity implements IActivity {

    @BindView(R.id.toolbarReposDetailsActivity) Toolbar toolbar;

    @BindView(R.id.circleImageViewUserAvatar) CircleImageView circleImageViewUserAvatar;
    @BindView(R.id.textViewUserName) TextView textViewUserName;
    @BindView(R.id.recyclerViewUserRepos) RecyclerView recyclerViewUserRepos;

    private ReposPresenter mReposPresenter;
    private UserReposRecyclerViewAdapter mUserReposRecyclerViewAdapter;
    private Toast mToast;

    private Repos mRepos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos_details);
        setSupportActionBar(toolbar);
        ButterKnife.bind(ReposDetailsActivity.this);
        setInit();
    }

    private void setInit() {
        this.toolbar.setTitle(getString(R.string.title_activity_repos_details));
        this.mUserReposRecyclerViewAdapter = new UserReposRecyclerViewAdapter(ReposDetailsActivity.this);
        this.recyclerViewUserRepos.setLayoutManager(new LinearLayoutManager(ReposDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
        this.recyclerViewUserRepos.setAdapter(this.mUserReposRecyclerViewAdapter);

        this.mToast = Toast.makeText(getActivityContext(), "", Toast.LENGTH_LONG);
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
                .into(circleImageViewUserAvatar);

        this.textViewUserName.setText(user.getName());

    }

    @Override
    public void showErrorOnUI(Throwable t) {
        this.mToast.setText(t.getMessage());
        this.mToast.setDuration(Toast.LENGTH_LONG);
        this.mToast.show();
    }

    @Override
    public void showErrorOnUI(int resId) {
        this.mToast.setText(resId);
        this.mToast.setDuration(Toast.LENGTH_LONG);
        this.mToast.show();
    }

    @Override
    public Activity getActivityContext() {
        return ReposDetailsActivity.this;
    }}

package br.com.pedrodimoura.rxandroidmeetup.view.activity.impl;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;

import java.util.concurrent.TimeUnit;

import br.com.pedrodimoura.rxandroidmeetup.R;
import br.com.pedrodimoura.rxandroidmeetup.databinding.ActivityMainBinding;
import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.ReposPayload;
import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.User;
import br.com.pedrodimoura.rxandroidmeetup.presenter.impl.ReposPresenter;
import br.com.pedrodimoura.rxandroidmeetup.view.activity.IActivity;
import br.com.pedrodimoura.rxandroidmeetup.view.adapter.ReposSearchRecyclerViewAdapter;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity implements IActivity, View.OnClickListener {

    private ActivityMainBinding mActivityMainBinding;

    private ReposPresenter mReposPresenter;
    private CompositeSubscription mCompositeSubscription;
    private ReposSearchRecyclerViewAdapter mReposSearchRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(this.mActivityMainBinding.toolbarMainActivity.defaultToolbar);
        this.mCompositeSubscription = new CompositeSubscription();
        this.mReposPresenter = new ReposPresenter(MainActivity.this);

        initSet();
        this.mReposPresenter.loadDefaultRepos();
    }

    private void initSet() {
        this.mReposSearchRecyclerViewAdapter = new ReposSearchRecyclerViewAdapter(MainActivity.this);
        this.mActivityMainBinding.contentMain.recyclerViewReposResultSearch.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        this.mActivityMainBinding.contentMain.recyclerViewReposResultSearch.setAdapter(this.mReposSearchRecyclerViewAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!this.mCompositeSubscription.isUnsubscribed()) this.mCompositeSubscription.unsubscribe();

        mReposPresenter.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView;

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        if (searchManager != null) {
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));

            registerToSearchViewEvents(searchView);
        }

        return true;
    }

    public void registerToSearchViewEvents(SearchView searchView) {
        this.mCompositeSubscription.add(
                RxSearchView
                        .queryTextChanges(searchView)
                        .throttleLast(100, TimeUnit.MILLISECONDS)
                        .debounce(200, TimeUnit.MILLISECONDS)
                        .onBackpressureLatest()
                        .filter(charSequence -> {
                            if (TextUtils.isEmpty(charSequence)) {
                                this.mReposPresenter.loadDefaultRepos();
                            }
                            return !TextUtils.isEmpty(charSequence);
                        })
                        .subscribe(charSequence -> mReposPresenter.searchRepos(charSequence.toString()))
        );
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void showReposOnUI(ReposPayload reposPayload) {
        this.mReposSearchRecyclerViewAdapter.updateList(reposPayload);
    }

    @Override
    public void showUserOnUI(User user) {

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
        return MainActivity.this;
    }

}

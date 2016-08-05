package br.com.pedrodimoura.rxandroidmeetup.view.activity.impl;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;

import java.util.concurrent.TimeUnit;

import br.com.pedrodimoura.rxandroidmeetup.R;
import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.ReposPayload;
import br.com.pedrodimoura.rxandroidmeetup.presenter.impl.ReposPresenter;
import br.com.pedrodimoura.rxandroidmeetup.view.activity.IActivity;
import br.com.pedrodimoura.rxandroidmeetup.view.adapter.ReposSearchRecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity implements IActivity, View.OnClickListener {

    @BindView(R.id.toolbarMainActivity) Toolbar toolbarMainActivity;
    @BindView(R.id.recyclerViewReposResultSearch) RecyclerView recyclerViewReposSearch;

    private SearchView mSearchView;
    private ReposPresenter mReposPresenter;
    private CompositeSubscription mCompositeSubscription;
    private ReposSearchRecyclerViewAdapter mReposSearchRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        setSupportActionBar(toolbarMainActivity);
        this.mCompositeSubscription = new CompositeSubscription();
        this.mReposPresenter = new ReposPresenter(MainActivity.this);

        initRecyclerView();
        this.mReposPresenter.loadDefaultRepos();
    }

    private void initRecyclerView() {
        this.mReposSearchRecyclerViewAdapter = new ReposSearchRecyclerViewAdapter(MainActivity.this);
        this.recyclerViewReposSearch.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        this.recyclerViewReposSearch.setAdapter(this.mReposSearchRecyclerViewAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!this.mCompositeSubscription.isUnsubscribed()) this.mCompositeSubscription.unsubscribe();

        mReposPresenter.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        } else {
            mSearchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        }

        mSearchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        registerToSearchViewEvents(mSearchView);

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
    public void showErrorOnUI(Throwable t) {
        Toast.makeText(getActivityContext(), t.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public Activity getActivityContext() {
        return MainActivity.this;
    }

}

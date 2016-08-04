package br.com.pedrodimoura.rxandroidmeetup.view.activity.impl;

import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;

import java.util.concurrent.TimeUnit;

import br.com.pedrodimoura.rxandroidmeetup.R;
import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.ReposPayload;
import br.com.pedrodimoura.rxandroidmeetup.presenter.impl.ReposPresenter;
import br.com.pedrodimoura.rxandroidmeetup.util.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbarMainActivity) Toolbar toolbarMainActivity;

    private SearchManager mSearchManager;
    private SearchView mSearchView;
    private MenuItem mMenuItem;
    private ReposPresenter mReposPresenter;
    private CompositeSubscription mCompositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        setSupportActionBar(toolbarMainActivity);
        this.mCompositeSubscription = new CompositeSubscription();
        this.mReposPresenter = new ReposPresenter();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!this.mCompositeSubscription.isUnsubscribed()) this.mCompositeSubscription.unsubscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
        MenuItem menuItem = menu.findItem(R.id.action_search);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        } else {
            searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        }

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        this.mCompositeSubscription.add(
                RxSearchView
                        .queryTextChanges(searchView)
                        .filter(charSequence -> !TextUtils.isEmpty(charSequence))
                        .throttleLast(100, TimeUnit.MILLISECONDS)
                        .debounce(200, TimeUnit.MILLISECONDS)
                        .onBackpressureLatest()
                        .flatMap(charSequence ->
                                mReposPresenter.searchRepos(charSequence.toString())
                                        .subscribeOn(Schedulers.io())
                        )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ReposPayload>() {
                            @Override
                            public void onCompleted() {
                                Log.d(Constants.DEBUG_KEY, "onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(Constants.DEBUG_KEY, "onError -> " + e.getMessage(), e);
                            }

                            @Override
                            public void onNext(ReposPayload reposPayload) {
                                Log.d(Constants.DEBUG_KEY, "onNext -> " + reposPayload);
                            }
                        })
        );



        return true;
    }

    @Override
    public void onClick(View view) {

    }
}

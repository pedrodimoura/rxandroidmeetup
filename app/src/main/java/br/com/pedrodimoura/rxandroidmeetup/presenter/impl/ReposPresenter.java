package br.com.pedrodimoura.rxandroidmeetup.presenter.impl;

import br.com.pedrodimoura.rxandroidmeetup.model.dao.remote.impl.ReposAPI;
import br.com.pedrodimoura.rxandroidmeetup.presenter.IReposPresenter;
import br.com.pedrodimoura.rxandroidmeetup.view.activity.IActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by pedrodimoura on 22/07/16.
 */
public class ReposPresenter implements IReposPresenter {

    private ReposAPI mReposAPI;
    private IActivity mIActivity;
    private CompositeSubscription mCompositeSubscription;

    public ReposPresenter(IActivity iActivity) {
        this.mIActivity = iActivity;
        this.mCompositeSubscription = new CompositeSubscription();
        this.mReposAPI = ReposAPI.getInstance();
    }

    @Override
    public void searchRepos(String q) {
        this.mCompositeSubscription
                .add(
                        this.mReposAPI
                                .getAPI()
                                .searchRepos(q)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        mIActivity::showReposOnUI,
                                        mIActivity::showErrorOnUI
                                )
                );
    }

    @Override
    public void onStop() {
        this.mCompositeSubscription.unsubscribe();
    }
}

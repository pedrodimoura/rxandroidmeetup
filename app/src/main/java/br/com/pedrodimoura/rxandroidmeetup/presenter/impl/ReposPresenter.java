package br.com.pedrodimoura.rxandroidmeetup.presenter.impl;

import br.com.pedrodimoura.rxandroidmeetup.R;
import br.com.pedrodimoura.rxandroidmeetup.model.dao.remote.impl.ReposAPI;
import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.ReposPayload;
import br.com.pedrodimoura.rxandroidmeetup.presenter.IReposPresenter;
import br.com.pedrodimoura.rxandroidmeetup.view.activity.IActivity;
import rx.Observable;
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
    public void loadDefaultRepos() {
        this.mCompositeSubscription
                .add(
                        this.mReposAPI
                                .getAPI()
                                .getRepos()
                                .subscribeOn(Schedulers.io())
                                .map(repos -> {
                                    ReposPayload reposPayload = new ReposPayload();
                                    reposPayload.setItems(repos);
                                    return reposPayload;
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        mIActivity::showReposOnUI
                                )
                );
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
                                        throwable -> mIActivity.showErrorOnUI(R.string.error_rate_limit)
                                )
                );
    }

    @Override
    public void getUser(String login) {
        this.mCompositeSubscription
                .add(
                        this.mReposAPI
                                .getAPI()
                                .getOwner(login)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnNext(mIActivity::showUserOnUI)
                                .subscribeOn(Schedulers.io())
                                .flatMap(user -> Observable.just(user.getLogin()))
                                .flatMap(s -> this.mReposAPI
                                            .getAPI()
                                            .getRepos(s)
                                            .subscribeOn(Schedulers.io())
                                )
                                .map(repos -> {
                                    ReposPayload reposPayload = new ReposPayload();
                                    reposPayload.setItems(repos);
                                    return reposPayload;
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        mIActivity::showReposOnUI,
                                        throwable -> mIActivity.showErrorOnUI(R.string.error_rate_limit)
                                )
                );
    }

    @Override
    public void onStop() {
        this.mCompositeSubscription.unsubscribe();
    }
}

package br.com.pedrodimoura.rxandroidmeetup.presenter.impl;

import br.com.pedrodimoura.rxandroidmeetup.model.dao.remote.impl.ReposAPI;
import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.ReposPayload;
import br.com.pedrodimoura.rxandroidmeetup.presenter.IReposPresenter;
import rx.Observable;

/**
 * Created by pedrodimoura on 22/07/16.
 */
public class ReposPresenter implements IReposPresenter {

    private ReposAPI mReposAPI;

    public ReposPresenter() {
        this.mReposAPI = ReposAPI.getInstance();
    }

    @Override
    public Observable<ReposPayload> searchRepos(String q) {
        return this.mReposAPI
                .getAPI()
                .searchRepos(q);
    }
}

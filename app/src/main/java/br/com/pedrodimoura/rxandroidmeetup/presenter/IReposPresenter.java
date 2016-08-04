package br.com.pedrodimoura.rxandroidmeetup.presenter;

import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.ReposPayload;
import rx.Observable;

/**
 * Created by pedrodimoura on 22/07/16.
 */
public interface IReposPresenter {

    Observable<ReposPayload> searchRepos(String q);

}

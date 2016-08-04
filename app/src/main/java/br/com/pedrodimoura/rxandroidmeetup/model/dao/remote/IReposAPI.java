package br.com.pedrodimoura.rxandroidmeetup.model.dao.remote;

import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.ReposPayload;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pedrodimoura on 03/08/16.
 */
public interface IReposAPI {

    @GET("search/repositories")
    Observable<ReposPayload> searchRepos(@Query("q") String query);

}

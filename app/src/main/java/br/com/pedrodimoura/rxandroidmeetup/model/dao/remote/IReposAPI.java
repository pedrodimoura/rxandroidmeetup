package br.com.pedrodimoura.rxandroidmeetup.model.dao.remote;

import java.util.List;

import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.Repos;
import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.ReposPayload;
import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.User;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pedrodimoura on 03/08/16.
 */
public interface IReposAPI {

    @GET("search/repositories")
    Observable<ReposPayload> searchRepos(
            @Query("q") String query
    );

    @GET("users/{user}")
    Observable<User> getOwner(
            @Path("user") String login
    );

    @GET("users/{login}/repos")
    Observable<List<Repos>> getRepos(
            @Path("login") String login
    );

    @GET("repositories")
    Observable<List<Repos>> getRepos();

}


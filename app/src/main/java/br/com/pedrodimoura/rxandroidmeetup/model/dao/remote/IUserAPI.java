package br.com.pedrodimoura.rxandroidmeetup.model.dao.remote;

import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.User;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by pedrodimoura on 22/07/16.
 */
public interface IUserAPI {

    @GET("user/{login}")
    Observable<User> getUser(@Path("login") String login);

}

package br.com.pedrodimoura.rxandroidmeetup.model.dao.remote.impl;

import br.com.pedrodimoura.rxandroidmeetup.model.dao.remote.IUserAPI;
import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.User;
import br.com.pedrodimoura.rxandroidmeetup.util.Constants;
import br.com.pedrodimoura.rxandroidmeetup.util.GsonUtil;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by pedrodimoura on 22/07/16.
 */
public class UserAPI implements IUserAPI {

    private static UserAPI sInstance;
    private IUserAPI mIUserAPI;

    private UserAPI() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.DEBUG_KEY)
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.buildCustomGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        this.mIUserAPI = retrofit.create(IUserAPI.class);

    }

    public static UserAPI getInstance() {
        if (sInstance == null) {
            synchronized (UserAPI.class) {
                sInstance = new UserAPI();
            }
        }
        return sInstance;
    }

    @Override
    public Observable<User> getUser(String login) {
        return mIUserAPI.getUser(login);
    }
}

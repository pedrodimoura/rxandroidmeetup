package br.com.pedrodimoura.rxandroidmeetup.model.dao.remote.impl;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import br.com.pedrodimoura.rxandroidmeetup.model.dao.remote.IReposAPI;
import br.com.pedrodimoura.rxandroidmeetup.util.Constants;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pedrodimoura on 03/08/16.
 */
public class ReposAPI {

    private static ReposAPI sInstance;
    private IReposAPI mIReposAPI;

    private ReposAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(
                        GsonConverterFactory
                                .create(
                                        new GsonBuilder()
                                                .setFieldNamingStrategy(
                                                        FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                                .create()
                                ))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        this.mIReposAPI = retrofit.create(IReposAPI.class);
    }

    public static ReposAPI getInstance() {
        if (sInstance == null) {
            synchronized (ReposAPI.class) {
                if (sInstance == null) sInstance = new ReposAPI();
            }
        }
        return sInstance;
    }

    public IReposAPI getAPI() {
        return mIReposAPI;
    }

}

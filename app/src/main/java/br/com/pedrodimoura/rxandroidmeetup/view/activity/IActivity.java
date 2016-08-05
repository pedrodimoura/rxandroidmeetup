package br.com.pedrodimoura.rxandroidmeetup.view.activity;

import android.app.Activity;

import br.com.pedrodimoura.rxandroidmeetup.model.entity.impl.ReposPayload;

/**
 * Created by pedrodimoura on 21/07/16.
 */
public interface IActivity {

    void showReposOnUI(ReposPayload reposPayload);

    void showErrorOnUI(Throwable t);

    void showErrorOnUI(int resId);

    Activity getActivityContext();

}

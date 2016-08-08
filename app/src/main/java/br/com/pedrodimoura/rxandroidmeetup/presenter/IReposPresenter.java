package br.com.pedrodimoura.rxandroidmeetup.presenter;

/**
 * Created by pedrodimoura on 22/07/16.
 */
public interface IReposPresenter {

    void loadDefaultRepos();

    void searchRepos(String q);

    void getUser(String login);

    void onStop();

}

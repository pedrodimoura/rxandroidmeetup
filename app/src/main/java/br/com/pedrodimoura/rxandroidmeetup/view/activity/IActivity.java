package br.com.pedrodimoura.rxandroidmeetup.view.activity;

/**
 * Created by pedrodimoura on 21/07/16.
 */
public interface IActivity {

    void showFragment(int idFragment);

    void setCheckedItemNavigationView(int idFragment);

    boolean isFragmentOnUI(int idFragment);

}

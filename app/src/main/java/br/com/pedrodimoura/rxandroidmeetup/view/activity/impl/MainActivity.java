package br.com.pedrodimoura.rxandroidmeetup.view.activity.impl;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import br.com.pedrodimoura.rxandroidmeetup.R;
import br.com.pedrodimoura.rxandroidmeetup.util.Constants;
import br.com.pedrodimoura.rxandroidmeetup.util.SystemServices;
import br.com.pedrodimoura.rxandroidmeetup.view.activity.IActivity;
import br.com.pedrodimoura.rxandroidmeetup.view.fragment.impl.FragmentEmpty;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IActivity, View.OnClickListener {

    @BindView(R.id.toolbarMainActivity) Toolbar toolbarMainActivity;
    @BindView(R.id.fab) FloatingActionButton floatingActionButton;

    private int mCurrentFragment = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        setSupportActionBar(toolbarMainActivity);
        showFragment(Constants.FRAGMENT_EMPTY);
    }

    @Override
    public void showFragment(int idFragment) {
        switch (idFragment) {
            case Constants.FRAGMENT_EMPTY:
                if (!this.isFragmentOnUI(Constants.FRAGMENT_EMPTY)) {
                    this.mCurrentFragment = Constants.FRAGMENT_EMPTY;

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, new FragmentEmpty())
                            .commit();
                    SystemServices.changeToolbarTitle(MainActivity.this, getResources().getString(R.string.title_fragment_empty));
                }
                break;
        }
    }

    @Override
    public void setCheckedItemNavigationView(int idFragment) {
        switch (idFragment) {
            case Constants.FRAGMENT_EMPTY:
                break;
        }
    }

    @Override
    public boolean isFragmentOnUI(int idFragment) {
        return (this.mCurrentFragment == idFragment);
    }

    @OnClick({R.id.fab})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Snackbar
                        .make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
                break;
        }
    }
}

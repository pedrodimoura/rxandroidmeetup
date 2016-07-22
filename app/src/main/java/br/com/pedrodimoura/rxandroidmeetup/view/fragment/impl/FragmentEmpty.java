package br.com.pedrodimoura.rxandroidmeetup.view.fragment.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.pedrodimoura.rxandroidmeetup.R;
import butterknife.ButterKnife;

/**
 * Created by pedrodimoura on 21/07/16.
 */
public class FragmentEmpty extends Fragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.fragment_empty, container, false);
        ButterKnife.bind(FragmentEmpty.this, this.mView);
        return this.mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

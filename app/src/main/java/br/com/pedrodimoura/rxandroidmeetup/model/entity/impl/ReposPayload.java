package br.com.pedrodimoura.rxandroidmeetup.model.entity.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pedrodimoura on 03/08/16.
 */
public class ReposPayload {

    private List<Repos> items = new ArrayList<>();

    public List<Repos> getItems() {
        return items;
    }

    public void setItems(List<Repos> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ReposPayload{" +
                "items=" + items +
                '}';
    }
}

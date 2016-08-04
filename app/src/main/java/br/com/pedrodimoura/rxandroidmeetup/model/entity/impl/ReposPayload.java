package br.com.pedrodimoura.rxandroidmeetup.model.entity.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pedrodimoura on 03/08/16.
 */
public class ReposPayload {

    private Integer totalCount;
    private Boolean incompleteResults;
    private List<Repos> items = new ArrayList<>();

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<Repos> getItems() {
        return items;
    }

    public void setItems(List<Repos> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ReposPayload{" +
                "totalCount=" + totalCount +
                ", incompleteResults=" + incompleteResults +
                ", items=" + items +
                '}';
    }
}

package com.jcsamples.installkit.models;

public class LoadingDialogModel {
    private String name;
    private int count;

    public LoadingDialogModel(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}

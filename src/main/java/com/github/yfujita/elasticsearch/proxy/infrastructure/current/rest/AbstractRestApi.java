package com.github.yfujita.elasticsearch.proxy.infrastructure.current.rest;

import okhttp3.OkHttpClient;

import java.io.IOException;

public abstract class AbstractRestApi {
    protected final OkHttpClient client;

    protected AbstractRestApi(final OkHttpClient client) {
        this.client = client;
    }

    abstract public void routing();
}

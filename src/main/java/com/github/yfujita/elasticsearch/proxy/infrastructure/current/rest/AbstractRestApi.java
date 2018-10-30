package com.github.yfujita.elasticsearch.proxy.infrastructure.current.rest;

import com.github.yfujita.elasticsearch.proxy.settings.ProxySettings;
import okhttp3.OkHttpClient;

public abstract class AbstractRestApi {
    protected final OkHttpClient client;
    protected final ProxySettings settings;

    protected AbstractRestApi(final OkHttpClient client, final ProxySettings settings) {
        this.client = client;
        this.settings = settings;
    }

    abstract public void routing();
}

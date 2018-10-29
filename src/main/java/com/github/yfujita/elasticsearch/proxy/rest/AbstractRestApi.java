package com.github.yfujita.elasticsearch.proxy.rest;

import org.elasticsearch.client.Client;

public class AbstractRestApi {
    protected final Client client;

    protected AbstractRestApi(final Client client) {
        this.client = client;
    }
}

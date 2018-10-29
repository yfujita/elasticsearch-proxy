package com.github.yfujita.elasticsearch.proxy.router;

import spark.Route;

public class RoutingTable {
    protected final HttpMethod method;
    protected final String path;
    protected final Route route;

    public RoutingTable(final HttpMethod method, final String path, final Route route) {
        this.method = method;
        this.path = path;
        this.route = route;
    }

    public HttpMethod method() {
        return method;
    }

    public String path() {
        return path;
    }

    public Route route() {
        return route;
    }
}

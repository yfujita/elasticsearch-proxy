package com.github.yfujita.elasticsearch.proxy.router;

import com.github.yfujita.elasticsearch.proxy.infrastructure.current.rest.AbstractRestApi;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class ApiRouter {
    public String baseUrl = "/";

    public List<AbstractRestApi> apis = new ArrayList<>();

    public void addApi(final AbstractRestApi api) {
        apis.add(api);
    }

    public void routing() {
        path(baseUrl, () -> {
            path("/es2/", () ->
                apis.forEach(AbstractRestApi::routing)
            );
            apis.forEach(AbstractRestApi::routing);
        });
    }
}

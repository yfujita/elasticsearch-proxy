package com.github.yfujita.elasticsearch.proxy.infrastructure.current.rest;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;

import static spark.Spark.*;

public class MainProxyApi extends AbstractRestApi {

    public MainProxyApi(final OkHttpClient client) {
        super(client);
    }

    @Override
    public void routing() {
        get("/*", (request, response) -> route(HttpMethod.get, request, response));
        post("/*", (request, response) -> route(HttpMethod.get, request, response));
        put("/*", (request, response) -> route(HttpMethod.get, request, response));
        delete("/*", (request, response) -> route(HttpMethod.get, request, response));
        head("/*", (request, response) -> route(HttpMethod.get, request, response));
    }

    protected Object route(HttpMethod method, Request request, Response response) throws Exception {
        final String[] splat = request.splat();
        final String path = splat.length > 0 ? splat[0] : "";
        final MediaType MIMEType= MediaType.parse("application/json; charset=utf-8");
        final String body = request.body();
        final RequestBody esRequestBody = RequestBody.create (MIMEType,body);

        HttpUrl.Builder httpUrlBuilder = new HttpUrl.Builder()
            .host("localhost")
            .port(9200)
            .scheme("http")
            .addPathSegments(path);
        request.queryParams().stream().forEach(key ->
            httpUrlBuilder.addQueryParameter(key, request.queryParams(key)));
        final okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(
            httpUrlBuilder.build()
        );
        switch (method){
            case get:
                if (body.length() > 0) {
                    builder.post(esRequestBody);
                } else {
                    builder.get();
                }
                break;
            case post:
                builder.post(esRequestBody);
                break;
            case put:
                builder.put(esRequestBody);
                break;
            case delete:
                builder.delete(esRequestBody);
                break;
            case head:
                builder.head();
                break;
            default:
                if (body.length() > 0) {
                    builder.post(esRequestBody);
                } else {
                    builder.get();
                }
                break;
        }

        try {
            okhttp3.Request esRequest = builder.build();
            okhttp3.Response esResponse = client.newCall(esRequest).execute();
            response.status(esResponse.code());
            esResponse.headers().names().forEach(name -> response.header(name, esResponse.header(name)));
            return esResponse.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

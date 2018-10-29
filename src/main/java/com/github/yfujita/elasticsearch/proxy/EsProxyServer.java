package com.github.yfujita.elasticsearch.proxy;

import static spark.Spark.*;

public class EsProxyServer
{
    public static void main( String[] args )
    {
        // ★ポイント2
        // initialize
        initialize();
        // ★ポイント3
        // define api
        SampleApi.api();
        // omitted
    }

    // ★ポイント2
    private static void initialize() {
        // server port
        port(8090);
        // static files
        staticFiles.location("/public");
        // connection pool
        // maxThreads, minThreads, timeOutMillis
        threadPool(8, 2, 30000);
    }
}

package com.github.yfujita.elasticsearch.proxy;

import com.github.yfujita.elasticsearch.proxy.infrastructure.current.rest.MainProxyApi;
import com.github.yfujita.elasticsearch.proxy.router.ApiRouter;
import com.github.yfujita.elasticsearch.proxy.settings.ProxySettings;
import okhttp3.OkHttpClient;

import java.io.File;

import static spark.Spark.*;

public class EsProxyServer {
    private final ProxySettings settings;

    public EsProxyServer(ProxySettings settings) {
        this.settings = settings;
    }

    public static void main( String[] args )
    {
        try {
            ProxySettings settings = ProxySettings.load(new File(EsProxyServer.class.getResource("/settings.json").getPath()).getAbsolutePath());
            EsProxyServer server = new EsProxyServer(settings);
            server.initialize();
            server.routing();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    private void initialize() {
        port(8090);
        threadPool(8, 2, 300000);
    }

    private void routing() {
        final OkHttpClient client = new OkHttpClient();
        final ApiRouter router = new ApiRouter();
        router.addApi(new MainProxyApi(client));
        router.routing();
    }
}

package com.github.yfujita.elasticsearch.proxy.rest.bulk;

import com.github.yfujita.elasticsearch.proxy.rest.AbstractRestApi;
import com.github.yfujita.elasticsearch.proxy.util.BytesReferenceUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.*;

public class BulkApi extends AbstractRestApi {

    public BulkApi(final Client client) {
        super(client);
    }

    public void routing() {
        final Route route = new BulkRoute();
        post("/_bulk", route);
        post("/*/_bulk", route);
        post("/*/*/_bulk", route);
        put("/_bulk", route);
        put("/*/_bulk", route);
        put("/*/*/_bulk", route);
    }

    protected class BulkRoute implements Route {

        @Override
        public Object handle(Request request, Response response) throws Exception {
            BulkRequest bulkRequest = Requests.bulkRequest();

            final String[] splat = request.splat();
            String index = splat.length >= 1 ? splat[0] : null;
            String type = splat.length >= 2 ? splat[1] : null;
            String defaultRouting = request.params("routing");
            FetchSourceContext defaultFetchSourceContext = FetchSourceContext.parseFromRestRequest(request);
            String fieldsParam = request.params("fields");

            String[] defaultFields = fieldsParam != null? Strings.commaDelimitedListToStringArray(fieldsParam):null;
            String defaultPipeline = request.params("pipeline");
            String waitForActiveShards = request.params("wait_for_active_shards");
            if(waitForActiveShards != null) {
                bulkRequest.waitForActiveShards(ActiveShardCount.parseString(waitForActiveShards));
            }

            bulkRequest.timeout(request.queryParamOrDefault("timeout", "10m"));
            bulkRequest.setRefreshPolicy(request.params("refresh"));
            bulkRequest.add(request.bodyAsBytes(), 0, request.bodyAsBytes().length, index, type, XContentType.JSON);
            final BulkResponse bulkResponse = client.bulk(bulkRequest).actionGet();
            final String responseBody = BytesReferenceUtils.toString(BytesReference.bytes(bulkResponse.toXContent(XContentBuilder.builder(JsonXContent.jsonXContent), null)), false);
            response.body(responseBody);
            response.status(200);
            return null;
        }
    }
}

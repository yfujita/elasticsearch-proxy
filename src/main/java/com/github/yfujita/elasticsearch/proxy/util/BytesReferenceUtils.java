package com.github.yfujita.elasticsearch.proxy.util;

import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.XContentHelper;

import java.io.IOException;

public class BytesReferenceUtils {
    public static String toString(BytesReference bytes, boolean reformatJson) throws IOException {
        return XContentHelper.convertToJson(bytes, reformatJson);
    }
}

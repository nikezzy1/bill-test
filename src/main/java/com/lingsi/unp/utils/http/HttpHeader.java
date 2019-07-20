
package com.lingsi.unp.utils.http;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpHeader {
    private Map<String, Header> headerMaps = new HashMap();

    private HttpHeader() {
    }

    public static HttpHeader custom() {
        return new HttpHeader();
    }

    public HttpHeader other(String key, String value) {
        this.headerMaps.put(key, new BasicHeader(key, value));
        return this;
    }

    public HttpHeader other(Map<String, String> otherMap){
        Iterator otherIt = otherMap.keySet().iterator();
        while (otherIt.hasNext())
        {
            String key = (String)otherIt.next();
            this.headerMaps.put(key, new BasicHeader(key, otherMap.get(key)));
        }
        return this;
    }

    public HttpHeader accept(String accept) {
        this.headerMaps.put("Accept", new BasicHeader("Accept", accept));
        return this;
    }

    public HttpHeader acceptCharset(String acceptCharset) {
        this.headerMaps.put("Accept-Charset", new BasicHeader("Accept-Charset", acceptCharset));
        return this;
    }

    public HttpHeader acceptEncoding(String acceptEncoding) {
        this.headerMaps.put("Accept-Encoding", new BasicHeader("Accept-Encoding", acceptEncoding));
        return this;
    }

    public HttpHeader acceptLanguage(String acceptLanguage) {
        this.headerMaps.put("Accept-Language", new BasicHeader("Accept-Language", acceptLanguage));
        return this;
    }

    public HttpHeader acceptRanges(String acceptRanges) {
        this.headerMaps.put("Accept-Ranges", new BasicHeader("Accept-Ranges", acceptRanges));
        return this;
    }

    public HttpHeader authorization(String authorization) {
        this.headerMaps.put("Authorization", new BasicHeader("Authorization", authorization));
        return this;
    }

    public HttpHeader cacheControl(String cacheControl) {
        this.headerMaps.put("Cache-Control", new BasicHeader("Cache-Control", cacheControl));
        return this;
    }

    public HttpHeader connection(String connection) {
        this.headerMaps.put("Connection", new BasicHeader("Connection", connection));
        return this;
    }

    public HttpHeader cookie(String cookie) {
        this.headerMaps.put("Cookie", new BasicHeader("Cookie", cookie));
        return this;
    }

    public HttpHeader contentLength(String contentLength) {
        this.headerMaps.put("Content-Length", new BasicHeader("Content-Length", contentLength));
        return this;
    }

    public HttpHeader contentType(String contentType) {
        this.headerMaps.put("Content-Type", new BasicHeader("Content-Type", contentType));
        return this;
    }

    public HttpHeader date(String date) {
        this.headerMaps.put("Date", new BasicHeader("Date", date));
        return this;
    }

    public HttpHeader expect(String expect) {
        this.headerMaps.put("Expect", new BasicHeader("Expect", expect));
        return this;
    }

    public HttpHeader from(String from) {
        this.headerMaps.put("From", new BasicHeader("From", from));
        return this;
    }

    public HttpHeader host(String host) {
        this.headerMaps.put("Host", new BasicHeader("Host", host));
        return this;
    }

    public HttpHeader ifMatch(String ifMatch) {
        this.headerMaps.put("If-Match ", new BasicHeader("If-Match ", ifMatch));
        return this;
    }

    public HttpHeader ifModifiedSince(String ifModifiedSince) {
        this.headerMaps.put("If-Modified-Since", new BasicHeader("If-Modified-Since", ifModifiedSince));
        return this;
    }

    public HttpHeader ifNoneMatch(String ifNoneMatch) {
        this.headerMaps.put("If-None-Match", new BasicHeader("If-None-Match", ifNoneMatch));
        return this;
    }

    public HttpHeader ifRange(String ifRange) {
        this.headerMaps.put("If-Range", new BasicHeader("If-Range", ifRange));
        return this;
    }

    public HttpHeader ifUnmodifiedSince(String ifUnmodifiedSince) {
        this.headerMaps.put("If-Unmodified-Since", new BasicHeader("If-Unmodified-Since", ifUnmodifiedSince));
        return this;
    }

    public HttpHeader maxForwards(String maxForwards) {
        this.headerMaps.put("Max-Forwards", new BasicHeader("Max-Forwards", maxForwards));
        return this;
    }

    public HttpHeader pragma(String pragma) {
        this.headerMaps.put("Pragma", new BasicHeader("Pragma", pragma));
        return this;
    }

    public HttpHeader proxyAuthorization(String proxyAuthorization) {
        this.headerMaps.put("Proxy-Authorization", new BasicHeader("Proxy-Authorization", proxyAuthorization));
        return this;
    }

    public HttpHeader range(String range) {
        this.headerMaps.put("Range", new BasicHeader("Range", range));
        return this;
    }

    public HttpHeader referer(String referer) {
        this.headerMaps.put("Referer", new BasicHeader("Referer", referer));
        return this;
    }

    public HttpHeader te(String te) {
        this.headerMaps.put("TE", new BasicHeader("TE", te));
        return this;
    }

    public HttpHeader upgrade(String upgrade) {
        this.headerMaps.put("Upgrade", new BasicHeader("Upgrade", upgrade));
        return this;
    }

    public HttpHeader userAgent(String userAgent) {
        this.headerMaps.put("User-Agent", new BasicHeader("User-Agent", userAgent));
        return this;
    }

    public HttpHeader warning(String warning) {
        this.headerMaps.put("Warning", new BasicHeader("Warning", warning));
        return this;
    }

    public HttpHeader via(String via) {
        this.headerMaps.put("Via", new BasicHeader("Via", via));
        return this;
    }

    public HttpHeader keepAlive(String keepAlive) {
        this.headerMaps.put("Keep-Alive", new BasicHeader("Keep-Alive", keepAlive));
        return this;
    }

    public String accept() {
        return this.get("Accept");
    }

    public String acceptCharset() {
        return this.get("Accept-Charset");
    }

    public String acceptEncoding() {
        return this.get("Accept-Encoding");
    }

    public String acceptLanguage() {
        return this.get("Accept-Language");
    }

    public String acceptRanges() {
        return this.get("Accept-Ranges");
    }

    public String authorization() {
        return this.get("Authorization");
    }

    public String cacheControl() {
        return this.get("Cache-Control");
    }

    public String connection() {
        return this.get("Connection");
    }

    public String cookie() {
        return this.get("Cookie");
    }

    public String contentLength() {
        return this.get("Content-Length");
    }

    public String contentType() {
        return this.get("Content-Type");
    }

    public String date() {
        return this.get("Date");
    }

    public String expect() {
        return this.get("Expect");
    }

    public String from() {
        return this.get("From");
    }

    public String host() {
        return this.get("Host");
    }

    public String ifMatch() {
        return this.get("If-Match ");
    }

    public String ifModifiedSince() {
        return this.get("If-Modified-Since");
    }

    public String ifNoneMatch() {
        return this.get("If-None-Match");
    }

    public String ifRange() {
        return this.get("If-Range");
    }

    public String ifUnmodifiedSince() {
        return this.get("If-Unmodified-Since");
    }

    public String maxForwards() {
        return this.get("Max-Forwards");
    }

    public String pragma() {
        return this.get("Pragma");
    }

    public String proxyAuthorization() {
        return this.get("Proxy-Authorization");
    }

    public String referer() {
        return this.get("Referer");
    }

    public String te() {
        return this.get("TE");
    }

    public String upgrade() {
        return this.get("Upgrade");
    }

    public String userAgent() {
        return this.get("User-Agent");
    }

    public String via() {
        return this.get("Via");
    }

    public String warning() {
        return this.get("Warning");
    }

    public String keepAlive() {
        return this.get("Keep-Alive");
    }

    private String get(String headName) {
        return this.headerMaps.containsKey(headName) ? ((Header)this.headerMaps.get(headName)).getValue() : null;
    }

    public Header[] build() {
        Header[] headers = new Header[this.headerMaps.size()];
        int i = 0;

        for(Iterator var3 = this.headerMaps.values().iterator(); var3.hasNext(); ++i) {
            Header header = (Header)var3.next();
            headers[i] = header;
        }

        this.headerMaps.clear();
        this.headerMaps = null;
        return headers;
    }

    public static class Headers {
        public static final String APP_FORM_URLENCODED = "application/x-www-form-urlencoded";
        public static final String TEXT_PLAIN = "text/plain";
        public static final String TEXT_HTML = "text/html";
        public static final String TEXT_XML = "text/xml";
        public static final String TEXT_JSON = "text/json";
        public static final String APPLICATION_JSON = "application/json";
        public static final String CONTENT_CHARSET_ISO_8859_1;
        public static final String CONTENT_CHARSET_UTF8;
        public static final String DEF_PROTOCOL_CHARSET;
        public static final String CONN_CLOSE = "close";
        public static final String KEEP_ALIVE = "keep-alive";
        public static final String EXPECT_CONTINUE = "100-continue";

        public Headers() {
        }

        static {
            CONTENT_CHARSET_ISO_8859_1 = Consts.ISO_8859_1.name();
            CONTENT_CHARSET_UTF8 = Consts.UTF_8.name();
            DEF_PROTOCOL_CHARSET = Consts.ASCII.name();
        }
    }

    private static class HttpReqHead {
        public static final String ACCEPT = "Accept";
        public static final String ACCEPT_CHARSET = "Accept-Charset";
        public static final String ACCEPT_ENCODING = "Accept-Encoding";
        public static final String ACCEPT_LANGUAGE = "Accept-Language";
        public static final String ACCEPT_RANGES = "Accept-Ranges";
        public static final String AUTHORIZATION = "Authorization";
        public static final String CACHE_CONTROL = "Cache-Control";
        public static final String CONNECTION = "Connection";
        public static final String COOKIE = "Cookie";
        public static final String CONTENT_LENGTH = "Content-Length";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String DATE = "Date";
        public static final String EXPECT = "Expect";
        public static final String FROM = "From";
        public static final String HOST = "Host";
        public static final String IF_MATCH = "If-Match ";
        public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
        public static final String IF_NONE_MATCH = "If-None-Match";
        public static final String IF_RANGE = "If-Range";
        public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
        public static final String KEEP_ALIVE = "Keep-Alive";
        public static final String MAX_FORWARDS = "Max-Forwards";
        public static final String PRAGMA = "Pragma";
        public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
        public static final String RANGE = "Range";
        public static final String REFERER = "Referer";
        public static final String TE = "TE";
        public static final String UPGRADE = "Upgrade";
        public static final String USER_AGENT = "User-Agent";
        public static final String VIA = "Via";
        public static final String WARNING = "Warning";

        private HttpReqHead() {
        }
    }
}

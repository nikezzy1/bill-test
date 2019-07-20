package com.lingsi.unp.utils.http;

import com.lingsi.unp.exception.HttpProcessException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    private static HttpClient client4HTTP;
    private static HttpClient client4HTTPS;
    private static final Logger logger = LogManager.getLogger();

    public HttpUtil() {
    }

    private static void create(HttpConfig config) throws HttpProcessException {
        if (config.client() == null) {
            if (config.url().toLowerCase().startsWith("https://")) {
                config.client(client4HTTPS);
            } else {
                config.client(client4HTTP);
            }
        }

    }

    public static String get(HttpClient client, String url, Header[] headers, HttpContext context, String encoding) throws HttpProcessException {
        return get(HttpConfig.custom().client(client).url(url).headers(headers).context(context).encoding(encoding));
    }

    public static String get(HttpConfig config) throws HttpProcessException {
        return send(config.method(HttpMethods.GET));
    }

    public static String post(HttpClient client, String url, Header[] headers, Map<String, Object> parasMap, HttpContext context, String encoding) throws HttpProcessException {
        return post(HttpConfig.custom().client(client).url(url).headers(headers).map(parasMap).context(context).encoding(encoding));
    }

    public static String post(HttpConfig config) throws HttpProcessException {
        return send(config.method(HttpMethods.POST));
    }

    public static String put(HttpClient client, String url, Map<String, Object> parasMap, Header[] headers, HttpContext context, String encoding) throws HttpProcessException {
        return put(HttpConfig.custom().client(client).url(url).headers(headers).map(parasMap).context(context).encoding(encoding));
    }

    public static String put(HttpConfig config) throws HttpProcessException {
        return send(config.method(HttpMethods.PUT));
    }

    public static String delete(HttpClient client, String url, Header[] headers, HttpContext context, String encoding) throws HttpProcessException {
        return delete(HttpConfig.custom().client(client).url(url).headers(headers).context(context).encoding(encoding));
    }

    public static String delete(HttpConfig config) throws HttpProcessException {
        return send(config.method(HttpMethods.DELETE));
    }

    public static String patch(HttpClient client, String url, Map<String, Object> parasMap, Header[] headers, HttpContext context, String encoding) throws HttpProcessException {
        return patch(HttpConfig.custom().client(client).url(url).headers(headers).map(parasMap).context(context).encoding(encoding));
    }

    public static String patch(HttpConfig config) throws HttpProcessException {
        return send(config.method(HttpMethods.PATCH));
    }

    public static String head(HttpClient client, String url, Header[] headers, HttpContext context, String encoding) throws HttpProcessException {
        return head(HttpConfig.custom().client(client).url(url).headers(headers).context(context).encoding(encoding));
    }

    public static String head(HttpConfig config) throws HttpProcessException {
        return send(config.method(HttpMethods.HEAD));
    }

    public static String options(HttpClient client, String url, Header[] headers, HttpContext context, String encoding) throws HttpProcessException {
        return options(HttpConfig.custom().client(client).url(url).headers(headers).context(context).encoding(encoding));
    }

    public static String options(HttpConfig config) throws HttpProcessException {
        return send(config.method(HttpMethods.OPTIONS));
    }

    public static String trace(HttpClient client, String url, Header[] headers, HttpContext context, String encoding) throws HttpProcessException {
        return trace(HttpConfig.custom().client(client).url(url).headers(headers).context(context).encoding(encoding));
    }

    public static String trace(HttpConfig config) throws HttpProcessException {
        return send(config.method(HttpMethods.TRACE));
    }

    public static OutputStream down(HttpClient client, String url, Header[] headers, HttpContext context, OutputStream out) throws HttpProcessException {
        return downOut(HttpConfig.custom().client(client).url(url).headers(headers).context(context).out(out));
    }

    public static OutputStream downOut(HttpConfig config) throws HttpProcessException {
        return fmt2Stream(execute(config.method(HttpMethods.GET)), config.out());
    }

    public static HttpResponse down(HttpConfig config) throws HttpProcessException{
        return execute(config.method(HttpMethods.GET));
    }

    public static InputStream downIn(HttpConfig config, InputStream in) throws HttpProcessException {
        return fmt2Stream(execute(config.method(HttpMethods.GET)), in);
    }

    public static String upload(HttpClient client, String url, Header[] headers, HttpContext context) throws HttpProcessException {
        return upload(HttpConfig.custom().client(client).url(url).headers(headers).context(context));
    }

    public static String upload(HttpConfig config) throws HttpProcessException {
        if (config.method() != HttpMethods.POST && config.method() != HttpMethods.PUT) {
            config.method(HttpMethods.POST);
        }

        return send(config);
    }

    public static int status(HttpClient client, String url, Header[] headers, HttpContext context, HttpMethods method) throws HttpProcessException {
        return status(HttpConfig.custom().client(client).url(url).headers(headers).context(context).method(method));
    }

    public static int status(HttpConfig config) throws HttpProcessException {
        return fmt2Int(execute(config));
    }

    public static String send(HttpConfig config) throws HttpProcessException {
        return fmt2String(execute(config), config.outenc());
    }

    private static HttpResponse execute(HttpConfig config) throws HttpProcessException {
        create(config);
        HttpResponse resp = null;

        try {
            HttpRequestBase request = getRequest(config.url(), config.method());
            request.setHeaders(config.headers());
            if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass())) {
                List<NameValuePair> nvps = new ArrayList();
                config.url(Utils.checkHasParas(config.url(), nvps, config.inenc()));
                HttpEntity entity = Utils.map2HttpEntity(nvps, config.map(), config.inenc());
                ((HttpEntityEnclosingRequestBase)request).setEntity(entity);
                logger.info("请求地址：" + config.url());
                if (nvps.size() > 0) {
                    logger.info("请求参数：" + nvps.toString());
                }

                if (config.json() != null) {
                    logger.info("请求参数：" + config.json());
                }
            } else {
                int idx = config.url().indexOf("?");
                logger.info("请求地址：" + config.url().substring(0, idx > 0 ? idx : config.url().length()));
                if (idx > 0) {
                    logger.info("请求参数：" + config.url().substring(idx + 1));
                }
            }

            resp = config.context() == null ? config.client().execute(request) : config.client().execute(request, config.context());
            if (config.isReturnRespHeaders()) {
                config.headers(resp.getAllHeaders());
            }

            return resp;
        } catch (IOException var5) {
            throw new HttpProcessException(var5);
        }
    }

    private static String fmt2String(HttpResponse resp, String encoding) throws HttpProcessException {
        String body = "";

        try {
            if (resp.getEntity() != null) {
                body = EntityUtils.toString(resp.getEntity(), encoding);
                logger.info(body);
            } else {
                body = resp.getStatusLine().toString();
            }

            EntityUtils.consume(resp.getEntity());
        } catch (IOException var7) {
            throw new HttpProcessException(var7);
        } finally {
            close(resp);
        }

        return body;
    }

    private static int fmt2Int(HttpResponse resp) throws HttpProcessException {
        int statusCode;
        try {
            statusCode = resp.getStatusLine().getStatusCode();
            EntityUtils.consume(resp.getEntity());
        } catch (IOException var6) {
            throw new HttpProcessException(var6);
        } finally {
            close(resp);
        }

        return statusCode;
    }

    public static OutputStream fmt2Stream(HttpResponse resp, OutputStream out) throws HttpProcessException {
        try {
            resp.getEntity().writeTo(out);
            EntityUtils.consume(resp.getEntity());
        } catch (IOException var6) {
            throw new HttpProcessException(var6);
        } finally {
            close(resp);
        }

        return out;
    }

    public static InputStream fmt2Stream(HttpResponse resp, InputStream in) throws HttpProcessException {
        try {
            in = resp.getEntity().getContent();
        } catch (IOException var6) {
            throw new HttpProcessException(var6);
        } finally {
            close(resp);
        }

        return in;
    }

    private static HttpRequestBase getRequest(String url, HttpMethods method) {
        HttpRequestBase request = null;
        switch(method.getCode()) {
        case 0:
            request = new HttpGet(url);
            break;
        case 1:
            request = new HttpPost(url);
            break;
        case 2:
            request = new HttpHead(url);
            break;
        case 3:
            request = new HttpPut(url);
            break;
        case 4:
            request = new HttpDelete(url);
            break;
        case 5:
            request = new HttpTrace(url);
            break;
        case 6:
            request = new HttpPatch(url);
            break;
        case 7:
            request = new HttpOptions(url);
            break;
        default:
            request = new HttpPost(url);
        }

        return (HttpRequestBase)request;
    }

    private static void close(HttpResponse resp) {
        try {
            if (resp == null) {
                return;
            }

            if (CloseableHttpResponse.class.isAssignableFrom(resp.getClass())) {
                ((CloseableHttpResponse)resp).close();
            }
        } catch (IOException var2) {
            Utils.exception(var2);
        }

    }

    static {
        try {
            client4HTTP = HCB.custom().build();
            client4HTTPS = HCB.custom().ssl().build();
        } catch (HttpProcessException var1) {
            Utils.errorException("创建https协议的HttpClient对象出错：", var1);
        }

    }
}

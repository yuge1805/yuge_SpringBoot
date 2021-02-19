package com.yuge.demo.springboot.interaction.longpolling;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 数据交互：长轮询客户端
 *
 * @author: yuge
 * @date: 2021-02-19
 **/
@Slf4j
public class LongPollingClient {

    private CloseableHttpClient httpClient;
    private RequestConfig requestConfig;

    public LongPollingClient() {
        this.httpClient = HttpClientBuilder.create().build();
        // httpClient 客户端超时时间要大于服务器长轮询约定的超时时间
        this.requestConfig = RequestConfig.custom().setSocketTimeout(40 * 1000).build();
    }

    @SneakyThrows
    public void longPolling(String url, String dataId) {
        String endpoint = url + "?dataId=" + dataId;
        HttpGet request = new HttpGet(endpoint);
        CloseableHttpResponse response = httpClient.execute(request);
        switch (response.getStatusLine().getStatusCode()) {
            case 200: {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                response.close();
                String configInfo = result.toString();
                log.info("dataId: [{}] changed, receive configInfo: {}", dataId, configInfo);
                break;
            }
            case 304: {
                log.info("longPolling dataId: [{}] once finished, configInfo is unchanged, longPolling again", dataId);
                break;
            }
            default: {
                throw new RuntimeException("unExcepted HTTP status code");
            }
        }
        // 异步运行，下一次长轮询
        new Thread(() -> longPolling(url, dataId)).start();
    }

    public static void main(String[] args) {
        // httpClient 会打印很多 debug 日志，关闭掉
        Logger logger = (Logger) LoggerFactory.getLogger("org.apache.http");
        logger.setLevel(Level.INFO);
        logger.setAdditive(false);

        LongPollingClient client = new LongPollingClient();
        client.longPolling("http://localhost:38081/yuge/listener", "hello");
    }
}

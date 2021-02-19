package com.yuge.demo.springboot.interaction.longpolling;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 数据交互：长轮询服务端
 *
 * @author: yuge
 * @date: 2021-02-19
 * 参考： https://github.com/lexburner/longPolling-demo/tree/fcc14d9c40f5fff14a789d756ed6cc78b326723d
 **/
@Slf4j
@RestController
public class LongPollingServer {

    // guava 提供的多值 Map，一个 key 可以对应多个 value
    private volatile Multimap<String, AsyncTask> dataIdContext = Multimaps.synchronizedSetMultimap(HashMultimap.create());
    private ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("longPolling-timeout-checker-%d").build();
    private ScheduledExecutorService timeoutChecker = new ScheduledThreadPoolExecutor(1, threadFactory);

    /**
     * 异步任务
     */
    @Data
    private static class AsyncTask {
        // 长轮询请求的上下文，包含请求和响应体
        private AsyncContext asyncContext;
        // 超时标记
        private boolean timeout;

        public AsyncTask(AsyncContext asyncContext, boolean timeout) {
            this.asyncContext = asyncContext;
            this.timeout = timeout;
        }
    }

    /**
     * 监听接入点
     *
     * @param request
     * @param response
     */
    @RequestMapping("/listener")
    public void addListener(HttpServletRequest request, HttpServletResponse response) {
        String dataId = request.getParameter("dataId");
        // 开启异步
        AsyncContext asyncContext = request.startAsync(request, response);
        // 自定义异步任务
        AsyncTask asyncTask = new AsyncTask(asyncContext, false);
        dataIdContext.put(dataId, asyncTask);

        // 启动定时器，服务器30s超时后响应304（未修改）
        timeoutChecker.schedule(() -> {
            asyncTask.setTimeout(true);
            dataIdContext.remove(dataId, asyncTask);
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            asyncContext.complete();
        }, 30 * 1000, TimeUnit.MILLISECONDS);
    }

    /**
     * 配置发布接入点
     *
     * @param dataId
     * @param configInfo
     * @return
     */
    @RequestMapping("/publishConfig")
    public String publishConfig(String dataId, String configInfo) throws IOException {
        log.info("publish configInfo dataId: [{}], configInfo: {}", dataId, configInfo);
        Collection<AsyncTask> asyncTasks = dataIdContext.removeAll(dataId);
        for (AsyncTask asyncTask : asyncTasks) {
            if (asyncTask.isTimeout()) {
                continue;
            }
            HttpServletResponse response = (HttpServletResponse) asyncTask.getAsyncContext().getResponse();
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(configInfo);
            asyncTask.getAsyncContext().complete();
        }
        return "success";
    }



}

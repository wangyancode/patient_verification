package com.dincher.framework.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;

/**
 * 线程池配置
 *
 * @author zhanglj
 * @date 2022-12-28 09:23:29
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);

    @Bean
    public ExecutorService asyncServiceExecutor() {
        logger.info("start asyncServiceExecutor");
        //cpu 核心线程数
        int cpuProcessors = Runtime.getRuntime().availableProcessors();
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("async-service-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, cpuProcessors, 30, TimeUnit.SECONDS, new LinkedBlockingDeque<>(80), threadFactory);
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    /**
         @Autowired
         private ExecutorService asyncServiceExecutor;

         // 异步调用(业务)接口，防止（业务代码）响应时间过长，从而导致重复回调
         asyncServiceExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
            //业务代码
            return "成功";
            }
          });
     */

}

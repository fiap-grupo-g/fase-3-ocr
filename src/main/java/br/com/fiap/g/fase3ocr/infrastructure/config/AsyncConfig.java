package br.com.fiap.g.fase3ocr.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Value("${async.threadpool.maxsize}")
    private int maxSize;

    @Value("${async.threadpool.queuesize}")
    private int queueSize;

    @Bean(name = "OCRExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(maxSize);
        taskExecutor.setQueueCapacity(queueSize);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }
}

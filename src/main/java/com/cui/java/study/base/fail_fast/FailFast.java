package com.cui.java.study.base.fail_fast;

import com.github.rholder.retry.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 *  http://blog.rickiyang.cn/posts/73e3dbd9.html
 *
 * @Author cuihanze
 */
@Slf4j
public class FailFast {
    public static void main(String[] args) throws TimeoutException {
        Result result = guavaRetry(100, 3);
        System.out.println(result);
    }

    /**
     * guava 失败重试
     * @param interval
     * @param retryTime
     * @return
     */
    public static Result guavaRetry(int interval, int retryTime) {
        Retryer<Result> retryer = RetryerBuilder.<Result>newBuilder()
            .retryIfExceptionOfType(TimeoutException.class) // 报错接入
            .retryIfResult(result -> result == null) // 如果返回为 null，仍会重试
            .withWaitStrategy(WaitStrategies.fixedWait(interval, TimeUnit.MICROSECONDS))
            .withStopStrategy(StopStrategies.stopAfterAttempt(3)).withRetryListener(new RetryListener() {
                @Override public <V> void onRetry(Attempt<V> attempt) {
                    long attemptNum = attempt.getAttemptNumber();
                    if (attemptNum > 1) {
                        log.info("<=====重试次数:{},params:{}", attemptNum - 1, retryTime);
                    }
                }
            }).build();
        try {
            return retryer.call(() -> updateMsg());
        } catch (ExecutionException e) {
            log.error("<<<<<=====重试失败====参数：{}", retryTime, e);
        } catch (RetryException e) {
            log.error("<<<<<=====重试失败====参数：{}", retryTime, e);
        }
        return null;
    }

    /**
     * spring retry
     * 1.3 之后带来
     * RetryTemplate.builder()
     *       .maxAttempts(10)
     *       .exponentialBackoff(100, 2, 10000)
     *       .retryOn(IOException.class)
     *       .traversingCauses()
     *       .build();
     * @param interval
     * @param retryTime
     * @return
     * @throws TimeoutException
     */
    public static Result springRetry(int interval, int retryTime) throws TimeoutException {

        // 重试模板
        RetryTemplate template = new RetryTemplate();
        // 重试策略：1）重试 3 次；2）
        SimpleRetryPolicy retryPolicy =
            new SimpleRetryPolicy(retryTime, Collections.singletonMap(TimeoutException.class, true));

        // 设置重试间隔策略
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(interval);

        // 给模板设置策略
        template.setRetryPolicy(retryPolicy);
        template.setBackOffPolicy(backOffPolicy);

        // 重试回调
        RetryCallback<Result, TimeoutException> retryCallback = context -> {
            System.out.println(context.getRetryCount());
            Result result;
            try {
                result = updateMsg();
                if (null == result) {
                    // 不满足要求，想要重试，需手动抛出异常
                     throw new TimeoutException();
                }
            } catch (TimeoutException e) {
                throw e;
            }
            return result;
        };

        // 获取结果失败回调
        RecoveryCallback<Result> recoveryCallback = retryContext -> null;

        return template.execute(retryCallback, recoveryCallback);
    }

    /**
     * 可设置指定重试次数和间隔时间
     * @param interval 间隔时间
     * @param retryTime    重试次数
     * @return
     * @throws InterruptedException
     */
    public static Result retryTimes(int interval, int retryTime) throws InterruptedException {
        Result result = null;
        while (retryTime-- > 0) {
            try {
                Thread.sleep(interval);
                result = updateMsg();
                if (result != null) {
                    return result;
                }
            } catch (TimeoutException e) {
                log.error("updateLoad error", e);
            }
        }
        return result;
    }

    /**
     * try-catch-redo 简单重试模式
     * 仅重试 1 次
     * @return
     * @throws InterruptedException
     */
    public static Result tryOneTime() throws InterruptedException, TimeoutException {
        Result result = null;
        try {
            result = updateMsg();
            if (null == result) {
                // 休眠 1s，重试 1 次
                Thread.sleep(1000);
                updateMsg();
            }
        } catch (TimeoutException e) {
            log.error("updateLoad error", e);
            // 休眠 1s，重试 1 次
            Thread.sleep(1000);
            updateMsg();
        }
        return result;
    }

    private static Result updateMsg() throws TimeoutException {
        throw new TimeoutException();
//         return new Result();
    }
}

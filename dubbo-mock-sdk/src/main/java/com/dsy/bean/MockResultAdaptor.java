package com.dsy.bean;

import org.apache.dubbo.rpc.Result;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @Author: dongshuaiyin
 * @Description: 统一返回结果抓取
 * @Date: 2023/4/21 16:07
 * @Modified by:
 */
public abstract class MockResultAdaptor implements Result {
    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(Object value) {

    }

    @Override
    public Throwable getException() {
        return null;
    }

    @Override
    public void setException(Throwable t) {

    }

    @Override
    public boolean hasException() {
        return false;
    }

    @Override
    public Object recreate() throws Throwable {
        return null;
    }

    @Override
    public Map<String, String> getAttachments() {
        return null;
    }

    @Override
    public Map<String, Object> getObjectAttachments() {
        return null;
    }

    @Override
    public void addAttachments(Map<String, String> map) {

    }

    @Override
    public void addObjectAttachments(Map<String, Object> map) {

    }

    @Override
    public void setAttachments(Map<String, String> map) {

    }

    @Override
    public void setObjectAttachments(Map<String, Object> map) {

    }

    @Override
    public String getAttachment(String key) {
        return null;
    }

    @Override
    public Object getObjectAttachment(String key) {
        return null;
    }

    @Override
    public String getAttachment(String key, String defaultValue) {
        return null;
    }

    @Override
    public Object getObjectAttachment(String key, Object defaultValue) {
        return null;
    }

    @Override
    public void setAttachment(String key, String value) {

    }

    @Override
    public void setAttachment(String key, Object value) {

    }

    @Override
    public void setObjectAttachment(String key, Object value) {

    }

    @Override
    public Result whenCompleteWithContext(BiConsumer<Result, Throwable> fn) {
        return null;
    }

    @Override
    public <U> CompletableFuture<U> thenApply(Function<Result, ? extends U> fn) {
        return null;
    }

    @Override
    public Result get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public Result get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}

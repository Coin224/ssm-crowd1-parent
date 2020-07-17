package com.lch.ssm.util;

/**
 * @author lch
 * @create 2020-07-17 11:35
 *
 * 用来统一处理ajax请求的数据
 */
public class ResultEntity<T> {


    private static final String SUCCESS = "SUCCESS";
    private static final String FAILD = "FAILD";
    private static final String NO_MESSAGE = "NO_MESSAGE";

    private String operationResult;//结果成功或者失败
    private String operationMessage;//失败携带的信息
    private T data;//携带的数据

    /**
     * 成功有返回  但是无数局
     * @param <E>
     * @return
     */
    public static <E> ResultEntity<E> successWithOutData() {
        return new ResultEntity<E>(SUCCESS,NO_MESSAGE,null);
    }


    /**
     * 成功，有返回数据
     */
    public static <E> ResultEntity<E> successWithData(E data) {
        return new ResultEntity<E>(SUCCESS,NO_MESSAGE,data);
    }

    /**
     * 失败并携带信息
     * @param message
     * @param <E>
     * @return
     */
    public static <E> ResultEntity<E> faildWithMessage(String message) {
        return new ResultEntity<E>(FAILD,message,null);
    }

    public ResultEntity(String operationResult, String operationMessage, T data) {
        this.operationResult = operationResult;
        this.operationMessage = operationMessage;
        this.data = data;
    }

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public String getOperationMessage() {
        return operationMessage;
    }

    public void setOperationMessage(String operationMessage) {
        this.operationMessage = operationMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

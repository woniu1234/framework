package com.common.frame.common;

public class MessageEvent<T> {

    private int type;
    private T data;

    public MessageEvent() {
    }

    public MessageEvent(T data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

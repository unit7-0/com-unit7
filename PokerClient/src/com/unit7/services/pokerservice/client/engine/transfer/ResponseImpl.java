package com.unit7.services.pokerservice.client.engine.transfer;

public class ResponseImpl implements Response {

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }

    private Object data;
}

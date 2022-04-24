package com.example.infernoinfosec.recycle_view;

public class service_model {
    String head,body;

    public service_model() {
    }

    public service_model(String head, String body) {
        this.head = head;
        this.body = body;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

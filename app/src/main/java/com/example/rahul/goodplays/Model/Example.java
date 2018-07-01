package com.example.rahul.goodplays.Model;

import java.util.HashMap;
import java.util.Map;

public class Example {

    private Message message;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
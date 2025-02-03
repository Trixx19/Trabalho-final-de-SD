package com.aluguelcarros;

public class Message {
    public int messageType;
    public int id;
    public String objectReference;
    public String methodId;
    public String arguments;

    public Message(Integer messageType, Integer id, String objectReference, String methodId, String arguments){
        this.messageType = messageType;
        this.id = id;
        this.objectReference = objectReference;
        this.methodId = methodId;
        this.arguments = arguments;
    }
}

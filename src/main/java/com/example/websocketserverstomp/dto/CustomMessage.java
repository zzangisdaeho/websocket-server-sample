package com.example.websocketserverstomp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomMessage {

    private ConnectType type;
    private String sender;
    private String channelId;
    private Object data;

    public void setSender(String sender){this.sender = sender;}

    public void newConnect(){
        this.type = ConnectType.NEW;
    }

    public void closeConnect(){
        this.type = ConnectType.CLOSE;
    }

    enum ConnectType{
        NEW, CLOSE
    }
}

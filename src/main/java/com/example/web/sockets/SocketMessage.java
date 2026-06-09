package com.example.web.sockets;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * soket消息传输模型
 */
@Data
@NoArgsConstructor
public class SocketMessage{

    /**
     * 消息内容
     */
    @JsonProperty("Data")
    private String Data;

    /**
     * 消息类型
     */
    @JsonProperty("Type")
    private String Type;

}

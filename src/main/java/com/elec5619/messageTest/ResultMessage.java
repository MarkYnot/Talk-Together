package com.elec5619.messageTest;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务端发送给客户端的消息.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultMessage {
    /**
     * Is it a system message
     */
    private boolean systemMsgFlag;
    /**
     * Sender Name
     */
    private Integer fromUser_id;
    /**
     * data
     */
    private Object message;

}

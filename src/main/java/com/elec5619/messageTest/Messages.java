package com.elec5619.messageTest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * websocket Data from client to Server.
 */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class Messages {
        /** receiver*/
        private Integer toName;
        /** sent data */
        private String message;
}

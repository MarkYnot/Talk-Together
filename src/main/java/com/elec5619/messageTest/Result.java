package com.elec5619.messageTest;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {

    /**
     * Login flag, success/failure
     */
    private boolean flag;
    /**
     * Response message for login
     */
    private String message;

}

package com.elec5619.utils;
import com.elec5619.messageTest.ResultMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageUtils {

    public static String getMessage(boolean systemMsgFlag, Integer fromName, Object message) {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setSystemMsgFlag(systemMsgFlag);
        resultMessage.setMessage(message);
        //If not a system message, pass in fromName
        if (!systemMsgFlag) {
            resultMessage.setFromUser_id(fromName);
        }

        //Use the “writeValueAsString” method of the objectmapper object in jackson to turn the "resultMessage" object into json format.
        ObjectMapper objectMapper = new ObjectMapper();
        String repStr = null;
        try {

            //Return the result message in json format.
            repStr = objectMapper.writeValueAsString(resultMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return repStr;
    }

}


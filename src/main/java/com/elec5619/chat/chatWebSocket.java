package com.elec5619.chat;

import com.alibaba.fastjson.JSON;
import com.elec5619.messageTest.Messages;
import com.elec5619.utils.MessageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket server side
 *
 * @see ServerEndpoint WebSocket server Need to specify the access path to the endpoint
 * @see Session   WebSocket session object Sending messages to the client through it
 */

@Component
@ServerEndpoint(value = "/chatTest", configurator = GetHttpSessionConfig.class)
public class chatWebSocket {

    /**
     * All online sessions PS: Based on the scenario, a thread-safe Map is used to store the session objects.
     */
    private static Map<Integer,chatWebSocket> onlineUsers = new ConcurrentHashMap<>();


    /**
     * Declare the session object through which messages can be sent to specified users
     */
    private Session session;

    /**
     * Declare an HttpSession object, in which we previously stored the username
     */
    private HttpSession httpSession;

    /**
     * When the client opens a connection: 1. Add a session object 2. Update the number of people online
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {

        //Assigning a local session object to a member session
        this.session = session;
        //get httpSession object
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
         System.out.println(httpSession.getId());
        //Get the username from the httpSession object
        Integer user_id = (Integer)httpSession.getAttribute("user_id");
        //Storing the current object in a container
        onlineUsers.put(user_id,this);

        // push the current online user name to the client of all online users
        // wrap system push messages, front-end onmessage received data
        // Get the message
       String message =  MessageUtils.getMessage(true, null, getAllOnlineUsername());

        //2. Call method for pushing system messages
        broadcastAllUsers(message);
    }

    private void broadcastAllUsers(String message){
        try {
            // All logged-in user names (all usernames)
         Set<Integer> names = onlineUsers.keySet();
         for(Integer name: names) {
             chatWebSocket ChatwebSocket = onlineUsers.get(name);
             // Get the push object
             if(session.isOpen()==true){
                 ChatwebSocket.session.getBasicRemote().sendText(message);
             }

         }
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }




    private Set<Integer> getAllOnlineUsername(){
        return onlineUsers.keySet();

    }

    /**
     * When the client sends a message: 1. Get its username and message 2. Send the message to everyone
     * <p>
     * PS: The convention here is to pass the message as a JSON string to facilitate passing more parameters!
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        try {
            //Converting a message to a message object
            ObjectMapper mapper = new ObjectMapper();
            Messages mess = mapper.readValue(jsonStr, Messages.class);
            //Get the user who will receive the message
            Integer toName = mess.getToName();

            //Get the message data
            String data = mess.getMessage();

            //Get the currently logged in user
            Integer username = (Integer) httpSession.getAttribute("user_id");
            //Get the data in the format of the message pushed to the specified user
            String resultMessage = MessageUtils.getMessage(false, username, data);

            //send message
            onlineUsers.get(toName).session.getBasicRemote().sendText(resultMessage);

        }catch(Exception e){
            e.printStackTrace();

        }



    }

    /**
     * When closing a connection: 1. Remove the session object 2. Update the number of people online
     */
    @OnClose
    public void onClose(Session session) {
//        onlineSessions.remove(session.getId());
//        sendMessageToAll(Message.jsonStr(Message.QUIT, "", "", onlineSessions.size()));
    Integer username = (Integer) httpSession.getAttribute("user_id");
    chatWebSocket remove = onlineUsers.remove(username);
    String message = MessageUtils.getMessage(true, null, getAllOnlineUsername());
    broadcastAllUsers(message);
    }

    /**
     * When an exception occurs in communication: print error log
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }



}





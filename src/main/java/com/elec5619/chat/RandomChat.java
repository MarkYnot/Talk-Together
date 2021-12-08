package com.elec5619.chat;


import com.elec5619.Entity.Tag;
import com.elec5619.Entity.User;

import com.elec5619.Repository.UserRepository;
import com.elec5619.messageTest.Messages;
import com.elec5619.utils.MessageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@Component
@ServerEndpoint(value = "/randomChat", configurator = GetHttpSessionConfig.class)
public class RandomChat {
    //use map to store randomly matched users
    private static Map<Integer, RandomChat> onlineUsers = new ConcurrentHashMap<>();
    //use map to store tag
    private static Map<Tag, Integer> userInfoList = new HashMap<>();
    //Set up a session to isolate messages that occur between users
    private Session session;
    //Save logged in users
    private HttpSession httpSession;

    private static UserRepository userRepository;

    @Autowired
    public void setUserService (UserRepository userRepository) {
        RandomChat.userRepository = userRepository;
    }

    //Establishing a connection
    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        Integer user_id = (Integer) httpSession.getAttribute("user_id");
        onlineUsers.put(user_id,this);
        User currentUser = RandomChat.userRepository.getById(user_id);
        List<User> allUsers =  userRepository.findAll();

        for(Tag tag: currentUser.getTagList()){
             userInfoList.put(tag,1);
        }

        for(User user: allUsers){
            if(user.getState()=="true" && user.getUser_id() != currentUser.getUser_id()){

                for(Tag tag: user.getTagList()){
                     Integer currentUserTag = userInfoList.get(tag);
                     if(currentUserTag != null){
                         onlineUsers.put(user.getUser_id(),this);
                     }
                }

            }
        }

        String message = MessageUtils.getMessage(true, null, getAllOnlineUserName());
        broadcastAllUsers(message);
    }

    private Set<Integer> getAllOnlineUserName() {
        return onlineUsers.keySet();
    }


    private void broadcastAllUsers(String message) {
        try{
            Set<Integer> user_id = onlineUsers.keySet();
            for (Integer name: user_id) {
                RandomChat randomChat = onlineUsers.get(name);
                randomChat.session.getBasicRemote().sendText(message);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @OnMessage
    public void OnMessage(String message, Session session) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Messages mess = mapper.readValue(message,Messages.class);
            Integer toName = mess.getToName();
            System.out.println(toName);
            String data = mess.getMessage();
            Integer username = (Integer) httpSession.getAttribute("user_id");
            String resultMessage = MessageUtils.getMessage(false, username, data);
            onlineUsers.get(toName).session.getBasicRemote().sendText(resultMessage);
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    @OnClose
    public void OnClose(Session session) {
        Integer username = (Integer) httpSession.getAttribute("user_id");
        RandomChat remove = onlineUsers.remove(username);
        String message = MessageUtils.getMessage(true, null, getAllOnlineUserName());
        broadcastAllUsers(message);
    }

    @OnError
    public void onError(Session session, Throwable error){
        error.printStackTrace();
    }
}

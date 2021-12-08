package com.elec5619.chat;

import com.alibaba.fastjson.JSON;
import com.elec5619.Entity.GroupChat;
import com.elec5619.Entity.Message;
import com.elec5619.Entity.Tag;
import com.elec5619.Entity.User;
import com.elec5619.Repository.GroupChatRepository;
import com.elec5619.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This is a WebSocket server
 * SeverEndpoint is the url you can access the server
 */

@Component
@ServerEndpoint(value = "/chat/{roomName}/{userName}")
public class WebSocketChatServer {

    private static GroupChatRepository groupChatRepository;
    private static UserRepository userRepository;

    @Autowired
    public void setGroupChat(GroupChatRepository groupChatRepository)
    {
        WebSocketChatServer.groupChatRepository=groupChatRepository;
    }

    @Autowired
    public void setUser(UserRepository userRepository)
    {
        WebSocketChatServer.userRepository=userRepository;
    }

    //store rooms (key=room name, value=users in the room)
    private static final Map<String, Set<Session>> rooms=new ConcurrentHashMap<>();
    //store users in this session
    private static final Map<String,String> users=new ConcurrentHashMap<>();

    /**
     *
     * @param roomName
     * @param userName
     * @param session
     * When users open server, 1. put user into a room 2. broadcast a message
     */
    @OnOpen
    public void onOpen(@PathParam("roomName") String roomName,@PathParam("userName") String userName, Session session){
        System.out.println(UserCheck(roomName,userName));

        if(!rooms.containsKey(roomName)){
            Set<Session> room=new HashSet<>();
            room.add(session);
            rooms.put(roomName,room);
        }
        else{
            rooms.get(roomName).add(session);
        }
        users.put(session.getId(),userName);
    }

    /**
     *
     * @param roomName
     * @param
     * @param jsonStr
     * When user send a message, broadcast message in his room
     */
    @OnMessage
    public void onMessage(@PathParam("roomName") String roomName, @PathParam("userName") String userName, String jsonStr) {
        Message message = JSON.parseObject(jsonStr, Message.class);
        broadcast(roomName,Message.jsonStr(Message.SPEAK, userName, message.getMsg(), users.size()));
    }

    /**
     *
     * @param roomName
     * @param session
     * When close the server, remove user from the room and broadcast a message
     */
    @OnClose
    public void onClose(@PathParam("roomName") String roomName, Session session) {
        rooms.get(roomName).remove(session);
        users.remove(session.getId());
        //broadcast(roomName,Message.jsonStr(Message.QUIT, "", "", users.size()));
    }

    /**
     *
     * @param session
     * @param error
     * When error occurs, print the report
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     *
     * @param roomName
     * @param userName
     * @return flag: ture: user can access this room
     * Check if the user can access this room
     */
    public boolean UserCheck(String roomName, String userName){
        boolean flag=false;
        User usercheck=WebSocketChatServer.userRepository.getById(1);
        //System.out.println(usercheck.getUsername());
        List<Tag> usertags=usercheck.getTagList();
        GroupChat groupcheck=WebSocketChatServer.groupChatRepository.getById(Integer.parseInt(roomName));

        for(Tag t:usertags){
            if(t.getTag_id()==groupcheck.getTag_id().getTag_id())
            {
                flag=true;
            }
        }
        return flag;
    }

    /**
     *
     * @param roomName
     * @param msg
     * broadcast message to all users in this room
     */
    private static void broadcast(String roomName, String msg){
        System.out.println("broadcast");
        System.out.println(msg);
        rooms.get(roomName).forEach(session -> {
                    try {
                        session.getBasicRemote().sendText(msg);
                        System.out.println(1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

}

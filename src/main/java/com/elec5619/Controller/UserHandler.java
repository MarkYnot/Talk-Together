package com.elec5619.Controller;

import com.elec5619.Entity.ChatRoom;
import com.elec5619.Entity.Friends;
import com.elec5619.Entity.User;
import com.elec5619.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserHandler {

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/test")
    public List<?> getAllUser(){
        User user = userRepository.getById(1);
        List<Map<String,Object>> list=new ArrayList<>();

        List<ChatRoom> chatRoomList = user.getChatRoomList1();
        List<Friends> friendsList = user.getFriendsList1();


        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("email", user.getEmail());
        userMap.put("password", user.getPassword());
        userMap.put("firstname", user.getFirstname());
        userMap.put("lastname", user.getLastname());
        userMap.put("state", user.getState());
        userMap.put("photo", user.getPhoto());
        userMap.put("background", user.getBackground());
        userMap.put("username", user.getUsername());
        userMap.put("ChatRoom", chatRoomList);
        userMap.put("Friends", friendsList);
        userMap.put("Tag", user.getTagList());
        userMap.put("GroupRoom",user.getGroupChatList());
        list.add(userMap);

        return list;
    }

    @RequestMapping("/test1")
    public void getUser(){
        User user = userRepository.getById(1);
        System.out.println(user.getChatRoomList1());
        System.out.println(user.getFriendsList1());
        System.out.println(user.getTagList());
        System.out.println(user.getGroupChatList());

    }
}

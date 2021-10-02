package com.elec5619.Controller;

import com.elec5619.Entity.ChatRoom;
import com.elec5619.Entity.User;
import com.elec5619.Repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CharRoomHandler {

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @RequestMapping("/charroom")
    public List<ChatRoom> findAll() {
        return chatRoomRepository.findAll();
    }
}

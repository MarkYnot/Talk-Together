package com.elec5619.Controller;


import com.alibaba.fastjson.JSONObject;
import com.elec5619.Entity.Tag;
import com.elec5619.Repository.FriendsRepository;
import com.elec5619.Repository.TagRepository;
import org.json.JSONArray;
import org.json.JSONException;
import com.elec5619.Entity.User;
import com.elec5619.Repository.UserRepository;
import com.elec5619.WebSocketChatApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest (classes= WebSocketChatApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FriendHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FriendController friendController;

    @Autowired
    private FriendsRepository friendsRepository;


    @Test
    public void TestAddFriend(){
        String result = friendController.addFriend(1, 3);
        assertEquals("200", result);
    }

    @Test
    public void TestDeleteFriend(){
      String deleteResult = friendController.deleteFriend(2);
      assertEquals("200",deleteResult);

    }
}

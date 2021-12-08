package com.elec5619.Controller;
import com.alibaba.fastjson.JSONObject;
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
public class UserHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserHandler userHandler;

    @Autowired
    private UserRepository userRepository;


    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(new UserHandler()).build();
    }


    @Test
    public void getUserInfo(){
       List<Map<String, Object>> response = userHandler.getInfo(1);
       assertEquals(1,response.size());
   }

    @Test
    public void updateInfo() throws  JSONException {

        User user = userRepository.getById(1);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(user);
        Boolean result = userHandler.updateInfo(jsonObject);
        assertEquals(true, result);

    }

    @Test
    public void testLogout(){
          Boolean logoutStatus = userHandler.logout(1);
          assertEquals(true, logoutStatus);
    }

    @Test
    public void testGetAllLanguage(){
       List<String> allLanguageList = userHandler.getAllLanguage();
       assertEquals(5, allLanguageList.size());
   }

   @Test
   public void testFindByEmail(){
        User user = userRepository.findByEmail("mark727221029@gmail.com");
        assertEquals(10, user.getUser_id());
   }

    /*Test Jpa getById method*/
    @Test
    public void testGetById() {
        User user = userRepository.getById(1);
        assertEquals(1,user.getUser_id());
        assertEquals("mark",user.getUsername());
        assertEquals("zihan1",user.getFirstname());
        assertEquals("wang1",user.getLastname());
        assertEquals("123@qq.com",user.getEmail());
        assertEquals("male",user.getGender());
        assertEquals("zh",user.getNative_language());
        assertEquals("en",user.getTranslate());
    }

    @Test
    public void vaildLoginInformation(){
        HttpSession httpSession = new MockHttpSession();
        User user = userHandler.login("888@888.com","202cb962ac59075b964b07152d234b70",httpSession);

        System.out.println(user);
        assertEquals(38, user.getUser_id());
        assertEquals("181881",user.getFirstname());
        assertEquals("241241",user.getLastname());
        assertEquals("9999",user.getUsername());
    }







}

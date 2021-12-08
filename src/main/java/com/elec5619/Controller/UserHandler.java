package com.elec5619.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.elec5619.Entity.*;
import com.elec5619.Repository.TagRepository;
import com.elec5619.Repository.UserRepository;
import com.elec5619.messageTest.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserHandler {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TagRepository tagRepository;


    /**
     * use used_id to get all user infomation
     * @param user_id from frontend
     * @return
     */
    @RequestMapping("/get_info/{user_id}")
    public List<Map<String, Object>> getInfo(@PathVariable int user_id){

        User user =  userRepository.getById(user_id);
        List<Map<String,Object>> list = new ArrayList<>();
        List<ChatRoom> chatRoomList = user.getChatRoomList1();
        List<Friends> friendsList = user.getFriendsList2();
        HashMap<String, Object> userMap = new HashMap<>();

        //User currentUser =  userRepository.getById(user.getUser_id());
        if (user !=null){

            userMap.put("email", user.getEmail());
            userMap.put("user_id", user.getUser_id());
//            userMap.put("password", user.getPassword());
            userMap.put("firstname", user.getFirstname());
            userMap.put("lastname", user.getLastname());
            userMap.put("state", user.getState());
            userMap.put("photo", user.getPhoto());
            userMap.put("background", user.getBackground());
            userMap.put("username", user.getUsername());
            userMap.put("gender",user.getGender());
            userMap.put("native_language", user.getNative_language());
            userMap.put("translate", user.getTranslate());
            userMap.put("ChatRoom", chatRoomList);
            userMap.put("Friends", friendsList);
            userMap.put("Tag", user.getTagList());
            userMap.put("GroupRoom",user.getGroupChatList());
            list.add(userMap);

            return list;
        }
        return null;
    }

    // update all user info when user modify their personal information in profile page
    @RequestMapping("/update_info")
    public boolean updateInfo(@RequestBody JSONObject UserInfo){
        User oldUser =  userRepository.getById(UserInfo.getInteger("user_id"));
        oldUser.setUsername(UserInfo.getString("username"));
        oldUser.setFirstname(UserInfo.getString("firstname"));
        oldUser.setLastname(UserInfo.getString("lastname"));
        oldUser.setEmail(UserInfo.getString("email"));
        oldUser.setGender(UserInfo.getString("gender"));
        oldUser.setNative_language(UserInfo.getString("native_language"));
        oldUser.setTranslate(UserInfo.getString("translate"));
        JSONArray tags = UserInfo.getJSONArray("Tag");
        List<Tag> tagList = JSONObject.parseArray(tags.toJSONString(), Tag.class);
        List<Tag> updateTag = new ArrayList<>();
        List<GroupChat> updateGroupChat = new ArrayList<>();
        for(int i = 0; i < tagList.toArray().length; i++){
             updateTag.add(tagList.get(i));
             updateGroupChat.add(tagRepository.getById(tagList.get(i).getTag_id()).getGroupChat());
        }
        oldUser.setTagList(updateTag);
        oldUser.setGroupChatList(updateGroupChat);

       if (oldUser.getEmail().contains("@")){
        userRepository.saveAndFlush(oldUser);
        return true;
       }else{
           return false;
       }
    }

    // change the background image when users upload their background image in profile page
    @PostMapping("/update_background")
    public boolean updateBackground(@RequestBody JSONObject UserInfo){
        User oldUser =  userRepository.getById(UserInfo.getInteger("user_id"));
        if (!oldUser.getBackground().equals(UserInfo.getString("imgUrl"))){
            oldUser.setBackground(UserInfo.getString("imgUrl"));
            userRepository.saveAndFlush(oldUser);
        }
        return true;
    }

    // modify Avatar when users upload their image in profile page
    @PostMapping("/update_portrait")
    public boolean updatePortrait(@RequestBody JSONObject UserInfo){

        User oldUser =  userRepository.getById(UserInfo.getInteger("user_id"));
        if (!oldUser.getPhoto().equals(UserInfo.getString("imgUrl"))){
            oldUser.setPhoto(UserInfo.getString("imgUrl"));
            userRepository.saveAndFlush(oldUser);
        }
        return true;
    }

    //when users modify their password in profile page
    @PostMapping("/change_pwd")
    public String changePwd(@RequestBody JSONObject UserInfo){

        User oldUser =  userRepository.getById(UserInfo.getInteger("user_id"));
        //User oldUser = userRepository.getById(user.getUser_id());
        if(oldUser.getPassword().equals( UserInfo.getString("currentPassword"))){
            oldUser.setPassword(UserInfo.getString("newPassword"));
            userRepository.saveAndFlush(oldUser);
            return "Successfully changed";
        }else{
            return "Old password is not correct, please try again";

        }

    }

    @GetMapping(value="/getUserDetail")
    @ResponseBody
    public String getUsername(HttpSession session){
        System.out.println(session.getId());
        return (String) session.getAttribute("username");

    }


    // when user log out, set status to false
    @PostMapping(value="/logout/{userid}")
    public Boolean logout(@PathVariable int userid){
        User user = userRepository.getById(userid);
        user.setState("false");
        userRepository.saveAndFlush(user);
        return true;
    }


    @GetMapping(value = "/login/{username}/{password}")
    public User login(@PathVariable ("username") String username,
                         @PathVariable("password") String password,
                         HttpSession session) {
        System.out.println(username);
        User returnUser = userRepository.findByEmail(username);
        System.out.println(returnUser);
        if(returnUser == null){
             return null;
        }else if(returnUser.getPassword().equals(password)){
            System.out.println(session.getId());
            session.setAttribute("user_id",returnUser.getUser_id());
            returnUser.setState("true");
            userRepository.saveAndFlush(returnUser);
            return  returnUser;
        }else{
            return  null;
        }
    }


    @GetMapping(value="/getAllLanguage")
    public List<String> getAllLanguage(){
        List<User> allUsers = userRepository.findAll();
        List<String> list = new ArrayList<>();
        for(int i = 0; i < allUsers.size(); i++){
             if(!list.contains(allUsers.get(i).getTranslate())){
                  list.add(allUsers.get(i).getTranslate());
             }
        }

        return list;
    }


    /**
     * store all user info to database
     * @param Userinfo
     * @return new user entity
     */

    @PostMapping("/UserSignin")
    public List<?> Signin(@RequestBody JSONObject Userinfo)
    {
        List<Map<String,Object>> list=new ArrayList<>();
        HashMap<String, Object> userMap = new HashMap<>();
        if(userRepository.findByEmail(Userinfo.getString("email"))!=null){
            userMap.put("check",false);
            userMap.put("User",null);
            userMap.put("message","The email is already existed");
            list.add(userMap);
            return list;
        }else{
            User Newuser = new User();
            JSONArray tags = Userinfo.getJSONArray("tag");
            List<Integer> tagList = JSONObject.parseArray(tags.toJSONString(), Integer.class);
            List<Tag> userTag = new ArrayList<>();
            List<GroupChat> groupChatList = new ArrayList<>();
            for(int i = 0; i < tagList.toArray().length; i++){
                userTag.add(tagRepository.getById(tagList.get(i)));
                groupChatList.add(tagRepository.getById(tagList.get(i)).getGroupChat());
            }
            Newuser.setPassword(Userinfo.getString("password"));
            Newuser.setFirstname(Userinfo.getString("firstname"));
            Newuser.setLastname(Userinfo.getString("lastname"));
            Newuser.setEmail(Userinfo.getString("email"));
            Newuser.setState("");
            Newuser.setPhoto("");
            Newuser.setBackground("");
            Newuser.setUsername(Userinfo.getString("username"));
            Newuser.setTagList(userTag);
            Newuser.setNative_language(Userinfo.getString("native"));
            Newuser.setTranslate(Userinfo.getString("translate"));
            Newuser.setGroupChatList(groupChatList);
            userRepository.save(Newuser);
            

            userMap.put("check",true);
            userMap.put("User",Newuser);
            userMap.put("message","Login successfully");
            list.add(userMap);
            return list;
        }
    }



}

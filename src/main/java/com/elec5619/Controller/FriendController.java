package com.elec5619.Controller;

import com.elec5619.Entity.Friends;
import com.elec5619.Entity.User;
import com.elec5619.Repository.FriendsRepository;

import com.elec5619.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FriendController {
    @Autowired
    FriendsRepository firendRepository;

    @Autowired
    UserRepository userRepository;

    /**
     *  Deleting a user's friends
     * @param friendid from frontend
     * @return
     */
    @PostMapping( "/deleteFriend/{friendid}")
    public String deleteFriend(@PathVariable ("friendid") Integer friendid){

        if(friendid != null){
            firendRepository.deleteById(friendid);
             return "200";
        }else{

            return "201";
        }

    }

    /**
     * add friend
     * @param userid  user id from
     * @param Touserid  user friend id
     * @return
     */
    @PostMapping("/addFriend/{userid}/{Touserid}")
    public String addFriend(@PathVariable("userid") Integer userid,
                            @PathVariable Integer Touserid) {

        List<Friends> friends = firendRepository.findAll();
        if (userid != null && Touserid != null) {

            Friends friend = new Friends();
            Friends addRelateFriend = new Friends();
            User fromUser = userRepository.getById(userid);
            User toUser = userRepository.getById(Touserid);
            friend.setUser(fromUser);
            friend.setUser1(toUser);

            addRelateFriend.setUser(toUser);
            addRelateFriend.setUser1(fromUser);

            List<Integer> allId = new ArrayList<>();
            for(Friends eachFri: friends){
                  if(!allId.contains(eachFri.getFriend_id())){
                      allId.add(eachFri.getFriend_id());
                  }
            }

            List<Friends> fromUserList = fromUser.getFriendsList2();
            List<Friends> toUserList = toUser.getFriendsList2();

            List<Integer> fromUserListId = new ArrayList<>();
            for(Friends fromFriend: fromUserList){
                if(!allId.contains(fromFriend.getFriend_id())){
                    fromUserListId.add(fromFriend.getFriend_id());
                }
            }

            List<Integer> toUserListId = new ArrayList<>();
            for(Friends toFriend: toUserList){
                if(!allId.contains(toFriend.getFriend_id())){
                    toUserListId.add(toFriend.getFriend_id());
                }
            }



            if(!allId.contains(toUserListId) && !allId.contains(fromUserListId)){

                firendRepository.save(friend);
                firendRepository.save(addRelateFriend);
                return "200";
            }else return "202";
        }else{
            return "201";

        }




    }


}

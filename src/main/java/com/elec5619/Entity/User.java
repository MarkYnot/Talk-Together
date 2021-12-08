package com.elec5619.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    @Column
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String state;
    private String photo;
    private String gender;
    private String background;
    private String username;
    private String native_language;
    private String translate;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<ChatRoom> chatRoomList1;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<ChatRoom> chatRoomList2;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Friends> friendsList1;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Friends> friendsList2;

    @JsonIgnore
    @ManyToMany(targetEntity = Tag.class,cascade = CascadeType.ALL)
    @JoinTable(name = "User_To_Tag",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id",referencedColumnName = "tag_id")})
    private List<Tag> tagList;

    @JsonIgnore
    @ManyToMany(targetEntity = GroupChat.class,cascade = CascadeType.ALL)
    @JoinTable(name = "User_To_Group",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "room_id",referencedColumnName = "room_id")})
    private List<GroupChat> groupChatList;

    public boolean authentication(String email, String password){ return this.email.equals(email) && password == this.password; }
}

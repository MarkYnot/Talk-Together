package com.elec5619.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    @GeneratedValue
    private Integer user_id;

    @Column
    private String email;
    private Integer password;
    private String firstname;
    private String lastname;
    private String state;
    private String photo;
    private String background;
    private String username;

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




}

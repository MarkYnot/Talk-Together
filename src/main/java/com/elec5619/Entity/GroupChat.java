package com.elec5619.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Group_Chat")
public class GroupChat {

    @Id
    @GeneratedValue
    private Integer room_id;

    @Column
    private Integer tag_id;

    @JsonIgnore
    @ManyToMany(mappedBy = "groupChatList")
    private List<User> userList;
}

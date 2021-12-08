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
@Table(name = "Tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tag_id;

    @Column
    private String tag_name;

    @JsonIgnore
    @ManyToMany(mappedBy = "tagList")
    private List<User> userList;

    @JsonIgnore
    @OneToOne(mappedBy = "Tag_id")
    private GroupChat groupChat;
}

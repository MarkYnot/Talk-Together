package com.elec5619.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="Friends")
public class Friends {

    @Id
    @GeneratedValue
    private int friend_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;
}

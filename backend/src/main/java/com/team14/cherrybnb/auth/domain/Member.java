package com.team14.cherrybnb.auth.domain;

import com.team14.cherrybnb.room.domain.Room;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String oauthAccessToken;

    private String oauthRefreshToken;

    private String resourceServer;

    @OneToMany(mappedBy = "member")
    private List<Room> rooms;
}

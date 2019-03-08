package com.mj.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by mj on 2018-05-02.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "subscribe")
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Integer id;

    @Column(name = "nick_name")
    @JsonProperty("nickName")
    private String nickName;

    @Column(name = "avatar_url", length = 200)
    @JsonProperty("avatarUrl")
    private String avatarUrl;

    @Column(name = "gender", length = 10)
    @JsonProperty("gender")
    private String gender;

}

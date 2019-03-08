package com.mj.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * Created by mj on 2018-03-20.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Integer id;

    @NotNull
    @Column(name = "content", length = 500)
    @JsonProperty("content")
    private String content;

    @NotNull
    @JsonProperty("goodsId")
    @JoinColumn(name = "goods_id")
    private Integer goodsId;


//    @ManyToOne(targetEntity = User.class)
    @JsonProperty(value = "commentUser")
//    @JoinColumn(name = "comment_user_id")
    @Column(name = "comment_user_id")
    private Integer userId;

    @Transient
    private User user;

    @Column(name = "comment_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "commentDate")
    private Date commentDate;

//    @OneToOne(targetEntity = User.class)
    @JsonProperty(value = "replyUser")
//    @JoinColumn(name="reply_id")
    @Transient
    private User replyUser;

    @Column(name="reply_id")
    private Integer replyId;

    @JsonProperty(value = "replyCommentId")
    @Column(name = "reply_comment_id")
    private Integer replyCommentId;

    @JsonProperty(value = "read")
    @Column(name = "comment_read")
    private Integer read;

}

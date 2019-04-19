package com.mj.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mj.enums.ErrorCode;
import com.mj.enums.ErrorMessage;
import com.mj.enums.Read;
import com.mj.exceptions.UnAuthorizedException;
import com.mj.model.Comment;
import com.mj.model.User;
import com.mj.service.Impl.UserServiceImpl;
import com.mj.service.Impl.CommentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by mj on 2018-05-05.
 */
@Slf4j
@ResponseBody
@RestController
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/comments/{goodsId}")
    public ResponseEntity<?> getComments(@PathVariable Integer goodsId) {
        List list = commentService.getComments(goodsId);
        return ResponseEntity.ok(list);
    }

    /**
     * @param userId
     * @param replyId
     * @param jsonNode {
    "content" : "12121111111111sdwd",
    "goodsId" : 1,
    "reply_comment_id" : 2
    }
     * @return
     */
    @PostMapping("/comment/add/{userId}/reply/{replyId}")
    public ResponseEntity<?> addComment(@PathVariable(value = "userId") Integer userId,
                                        @PathVariable(value = "replyId") Integer replyId,
                                        @RequestBody JsonNode jsonNode) {
        Comment comment = new Comment();
        User commentUser = userService.getUser(userId);
        User replyUser = userService.getUser(replyId);
        try {
            comment = new ObjectMapper().readValue(jsonNode.traverse(), Comment.class);
//            String content = jsonNode.path("content").textValue();
//            Integer goodsId = Integer.valueOf(jsonNode.path("goodsId").textValue());
//            comment.setContent(content);
//            comment.setGoodsId(goodsId);
//            comment.setReplyCommentId(replyCommentId);
            comment.setCommentDate(new Date());
            comment.setUser(commentUser);
            comment.setUserId(userId);
            comment.setReplyUser(replyUser);
            comment.setReplyId(replyId);
            comment.setRead(Read.UNREAD.getRead());
        } catch (Exception e) {
            throw new UnAuthorizedException(ErrorCode.JSON_TO_OBJECT_ERROR, ErrorMessage.ERROR_CHANGE_TYPE);
        }
        commentService.addComment(comment);
        return ResponseEntity.ok(null);
    }


    /**
     * 删除评论
     * @param commentId
     * @return
     */
    @PostMapping("/comment/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "commentId") Integer commentId) {

        Comment comment = commentService.getOneComment(commentId);
        commentService.deleteComment(comment);
        return ResponseEntity.ok(null);
    }

    /**
     * 获取用户未读信息数量
     * @param userId
     * @return
     */
    @GetMapping("/comments/unread/count/{userId}")
    public ResponseEntity<?> getUnreadCount(@PathVariable Integer userId) {
        Integer count = commentService.getUnreadCount(userId);
        return ResponseEntity.ok(count);
    }

    /**
     * 获取用户未读信息
     * @param userId
     * @return
     */
    @GetMapping("/comments/unread/{userId}")
    public ResponseEntity<?> getUnreadComment(@PathVariable Integer userId) {
        List list = commentService.getUnreadComment(userId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/comments/isRead/{commentId}")
    public ResponseEntity<?> commentIsRead(@PathVariable Integer commentId) {
        log.info(commentId.toString());
        commentService.commentIsRead(commentId);
        return ResponseEntity.ok(null);
    }


    /**
     * 用户所有评论
     * @param userId
     * @return
     */
    @GetMapping("/comments/user/{userId}")
    public ResponseEntity<?> getUserComment(@PathVariable Integer userId) {
        List list = commentService.getUserComment(userId);
        return ResponseEntity.ok(list);
    }


}

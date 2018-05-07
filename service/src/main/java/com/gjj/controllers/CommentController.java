package com.gjj.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gjj.enums.ErrorCode;
import com.gjj.enums.ErrorMessage;
import com.gjj.exceptions.UnAuthorizedException;
import com.gjj.models.Comment;
import com.gjj.models.Goods;
import com.gjj.models.User;
import com.gjj.services.AuthenticationUserService;
import com.gjj.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by gjj on 2018-05-05.
 */
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AuthenticationUserService authenticationUserService;

    @ResponseBody
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
    @ResponseBody
    @PostMapping("/comment/add/{userId}/reply/{replyId}")
    public ResponseEntity<?> addComment(@PathVariable(value = "userId") Integer userId,
                                        @PathVariable(value = "replyId") Integer replyId,
                                        @RequestBody JsonNode jsonNode) {
        Comment comment;
        User commentUser = authenticationUserService.getUser(userId);
        User replyUser = authenticationUserService.getUser(replyId);
        try {
            comment = new ObjectMapper().readValue(jsonNode.traverse(), Comment.class);
            comment.setCommentDate(new Date());
            comment.setUser(commentUser);
            comment.setReplyUser(replyUser);
        } catch (IOException e) {
            throw new UnAuthorizedException(ErrorCode.JSON_TO_OBJECT_ERROR, ErrorMessage.ERROR_CHANGE_TYPE);
        }
        commentService.addComment(comment);
        return ResponseEntity.ok(null);
    }


    @ResponseBody
    @PostMapping("/comment/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "commentId") Integer commentId) {

        Comment comment = commentService.getOneComment(commentId);
        commentService.deleteComment(comment);
        return ResponseEntity.ok(null);
    }
}

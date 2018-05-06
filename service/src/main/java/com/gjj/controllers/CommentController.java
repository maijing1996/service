package com.gjj.controllers;

import com.gjj.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by gjj on 2018-05-05.
 */
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @GetMapping("/comments/{goodsId}")
    public ResponseEntity<?> getComments(@PathVariable Integer goodsId) {
        List list = commentService.getComments(goodsId);
        return ResponseEntity.ok(list);


    }
}

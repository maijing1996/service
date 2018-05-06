package com.gjj.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gjj.models.Comment;
import com.gjj.models.SecondComment;
import com.gjj.qModels.QComment;
import com.gjj.repositories.CommentRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gjj on 2018-05-05.
 */
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List getComments(Integer goodsId) {


        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanBuilder secondBooleanBuilder = new BooleanBuilder();
        QComment qComment = QComment.comment;
        QComment secondQComment = QComment.comment;
        if (goodsId != null) {
            booleanBuilder.and(qComment.goodsId.eq(goodsId));
            booleanBuilder.and(qComment.type.eq(1));
            secondBooleanBuilder.and(secondQComment.goodsId.eq(goodsId));
            secondBooleanBuilder.and(secondQComment.type.eq(2));
        }
        Sort sort = new Sort(Sort.Direction.ASC, "commentDate");
        List<Comment> list = (List) commentRepository.findAll(booleanBuilder,sort);
        List<Comment> list2 = (List) commentRepository.findAll(secondBooleanBuilder,sort);
        /**
         * 组装评论
         */
//        ObjectMapper objectMapper = new ObjectMapper();
//        for (Comment comment : list) {
//            try {
//                Map map = objectMapper.readValue(comment.toString(), Map.class);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        List commentList = new ArrayList();
        for (Comment comment: list) {
            for (Comment comment2 : list2) {
                SecondComment secondComment = new SecondComment();
                secondComment.setId(comment.getId());
                secondComment.setContent(comment.getContent());
                secondComment.setGoodsId(comment.getGoodsId());
                secondComment.setUser(comment.getUser());
                secondComment.setCommentDate(comment.getCommentDate());
                secondComment.setReplyUser(comment.getReplyUser());
                secondComment.setType(comment.getType());
                if (secondComment.getId().equals(comment2.getReplyUser().getId())) {
                    if (secondComment.getList() == null) {
                        secondComment.setList(new ArrayList());
                    }
                    secondComment.getList().add(comment2);
                }
                commentList.add(secondComment);
            }
        }




        return commentList;

    }
}

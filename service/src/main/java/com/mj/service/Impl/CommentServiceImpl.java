package com.mj.service.Impl;

import com.mj.enums.ErrorCode;
import com.mj.enums.ErrorMessage;
import com.mj.enums.Read;
import com.mj.exceptions.BusinessException;
import com.mj.exceptions.UnAuthorizedException;
import com.mj.mapper.CommentMapper;
import com.mj.model.Comment;
import com.mj.model.SecondComment;
import com.mj.model.User;
import com.mj.qModels.QComment;
import com.mj.service.CommentService;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mj on 2018-05-05.
 */
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
    /*@Autowired
    private CommentRepository commentRepository;*/

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserServiceImpl userService;

    public Comment getOneComment(Integer commentId) {
        Comment comment2;
        try {
            Comment comment = new Comment();
            comment.setId(commentId);
            comment2 = commentMapper.selectOne(comment);
//            comment = commentRepository.getOne(commentId);
        } catch (BusinessException e) {
            throw new UnAuthorizedException(ErrorCode.COMMENT_NOT_EXIST, ErrorMessage.COMMENT_NOT_EXIST);
        }
        return comment2;
    }

    public List getComments(Integer goodsId) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanBuilder secondBooleanBuilder = new BooleanBuilder();
        QComment qComment = QComment.comment;
        QComment secondQComment = QComment.comment;
        if (goodsId != null) {
            booleanBuilder.and(qComment.goodsId.eq(goodsId));
            booleanBuilder.and(qComment.replyCommentId.isNull());
            secondBooleanBuilder.and(secondQComment.goodsId.eq(goodsId));
            secondBooleanBuilder.and(secondQComment.replyCommentId.isNotNull());
        }

        List<Comment> list = commentMapper.findCommentByGoodId(goodsId);
        List<Comment> list2 = commentMapper.findCommentByGoodIdAndReplyCommentIdIsNotNull(goodsId);
        for (Comment comment:list2) {
            Integer replyId = comment.getReplyId();
            Integer userId2 = comment.getUserId();
            User user = userService.getUser(replyId);
            User user1 = userService.getUser(userId2);
            comment.setReplyUser(user);
            comment.setUser(user1);
        }

//        Sort sort = new Sort(Sort.Direction.ASC, "id");
//        List<Comment> list = (List) commentRepository.findAll(booleanBuilder,sort);
//        List<Comment> list2 = (List) commentRepository.findAll(secondBooleanBuilder,sort);
        /**
         * 组装评论
         */
        List commentList = new ArrayList();
        for (Comment comment: list) {
            User user = userService.getUser(comment.getUserId());
            SecondComment secondComment = new SecondComment();
            secondComment.setId(comment.getId());
            secondComment.setContent(comment.getContent());
            secondComment.setGoodsId(comment.getGoodsId());
            secondComment.setUser(user);
            secondComment.setUserId(comment.getUserId());
            secondComment.setCommentDate(comment.getCommentDate());
            secondComment.setReplyUser(comment.getReplyUser());
            secondComment.setReplyCommentId(comment.getReplyCommentId());
            secondComment.setRead(comment.getRead());
            for (Comment comment2 : list2) {
                if (secondComment.getList() == null) {
                    secondComment.setList(new ArrayList<Comment>());
                }
                for (Comment comment3 : secondComment.getList()) {
                    if (comment3.getId().equals(comment2.getReplyCommentId())) {
                        comment2.setReplyCommentId(secondComment.getId());
                        continue;
                    }
                }
                if (secondComment.getId().equals(comment2.getReplyCommentId())) {
                    secondComment.getList().add(comment2);
                }

            }
            commentList.add(secondComment);
        }
        return commentList;
    }

    /**
     * 保存评论
     * @param comment
     */
    public void addComment(Comment comment) {
        commentMapper.insertSelective(comment);
//        commentRepository.save(comment);
    }

    /**
     *
     * @param comment
     */
    public void deleteComment(Comment comment) {
        if (comment.getReplyCommentId() == null) {
            /**
             * 删除一级评论
             */
            /*BooleanBuilder booleanBuilder = new BooleanBuilder();
            QComment qComment = QComment.comment;
            if (true) {
                booleanBuilder.and(qComment.replyCommentId.eq(comment.getId()));
            }
            List<Comment> list =  (List) commentRepository.findAll(booleanBuilder);
            if (list != null) {
                commentRepository.delete(list);
            }*/
        }
        commentMapper.delete(comment);
//        commentRepository.delete(comment);
    }

    /**
     * 获取用户未读信息数量
     * @param userId
     * @return
     */
    public Integer getUnreadCount(Integer userId) {
//        BooleanBuilder booleanBuilder = booleanBuilder(userId,"");
        Comment comment = new Comment();
        comment.setRead(0);
        comment.setUserId(userId);
        int count = commentMapper.selectCount(comment);
//        Long count = commentRepository.count(booleanBuilder);
        return count;
    }

    /**
     * 获取用户未读信息
     * @param userId
     * @return
     */
    public List<Comment> getUnreadComment(Integer userId) {
        /*BooleanBuilder booleanBuilder = booleanBuilder(userId,"");
        Sort sort = new Sort(Sort.Direction.DESC, "commentDate");
        List<Comment> list =  (List) commentRepository.findAll(booleanBuilder,sort);*/
        List<Comment> list = commentMapper.findCommentByUserId(userId);
        for (Comment comment:list) {
            Integer replyId = comment.getReplyId();
            Integer userId2 = comment.getUserId();
            User user = userService.getUser(replyId);
            User user1 = userService.getUser(userId2);
            comment.setReplyUser(user);
            comment.setCommentUser(user);
            comment.setUser(user1);
        }
        log.info("Unread"+list.size()+list.toString());
        return list;
    }

    public BooleanBuilder booleanBuilder(Integer userId,String type) {
        User user = userService.getUser(userId);
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QComment qComment = QComment.comment;
        if (userId != null) {
            booleanBuilder.and(qComment.replyUser.eq(user));
            if (!type.equals("all")) {
                booleanBuilder.and(qComment.read.eq(Read.UNREAD.getRead()));
            }
        }
        return booleanBuilder;
    }

    /**
     * 评论设置已读
     * @param commentId
     */
    public void commentIsRead(Integer commentId) {
//        List<Comment> list = getUnreadComment(userId);
//        for (Comment comment : list) {
//            comment.setRead(Read.ALREADYREAD.getRead());
//            commentRepository.save(comment);
//        }

            /*Comment comment = this.getOneComment(commentId);
            comment.setRead(Read.ALREADYREAD.getRead());
            commentRepository.save(comment);*/
            Comment comment = new Comment();
            comment.setId(commentId);
            commentMapper.select(comment);
            comment.setRead(Read.ALREADYREAD.getRead());
            commentMapper.updateByPrimaryKeySelective(comment);
    }

    /**
     * 获取用户评论
     * @param userId
     * @return
     */
    public List getUserComment(Integer userId) {
        /*BooleanBuilder booleanBuilder = booleanBuilder(userId,"all");
        Sort sort = new Sort(Sort.Direction.DESC, "commentDate");
        List<Comment> list =  (List) commentRepository.findAll(booleanBuilder,sort);*/
        Comment com = new Comment();
        com.setUserId(userId);
        List<Comment> list = commentMapper.select(com);
//        List<Comment> list = commentMapper.findAllCommentByUserId(userId);
        for (Comment comment:list) {
            Integer replyId = comment.getReplyId();
            Integer userId2 = comment.getUserId();
            User user = userService.getUser(replyId);
            User user1 = userService.getUser(userId2);
            comment.setReplyUser(user);
            comment.setCommentUser(user);
            comment.setUser(user1);
        }
        log.info("Comment:"+list.toString()+list.size());
        return list;
    }

}

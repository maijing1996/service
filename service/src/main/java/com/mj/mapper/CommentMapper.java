package com.mj.mapper;


import com.mj.model.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CommentMapper extends Mapper<Comment> {

    List<Comment> findCommentByGoodId(@Param("goodsId") Integer goodsId);

    List<Comment> findCommentByUserId(@Param("userId") Integer userId);

    List<Comment> findAllCommentByUserId(@Param("userId") Integer userId);

    /**
     *
     * @param goodsId
     * @return
     */
    List<Comment> findCommentByGoodIdAndReplyCommentIdIsNotNull(@Param("goodsId") Integer goodsId);
}

package com.mj.repositories;

import com.mj.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by mj on 2018-05-05.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer>, QueryDslPredicateExecutor<Comment> {
}

package com.gjj.repositories;

import com.gjj.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by gjj on 2018-05-05.
 */
public interface CommentRepository extends JpaRepository<Comment,Integer>, QueryDslPredicateExecutor<Comment> {
}

package com.mj.repositories;

import com.mj.model.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by mj on 2018-03-09.
 */
@Repository
public interface GoodsRepository extends JpaRepository<Goods, Integer>, QueryDslPredicateExecutor<Goods> {

}

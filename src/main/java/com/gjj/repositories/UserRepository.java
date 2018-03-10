package com.gjj.repositories;

import com.gjj.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gjj on 2018-03-04
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
    User findByUsername(String username);
    List<User> findAll();

}

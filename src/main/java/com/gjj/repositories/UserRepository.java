package com.gjj.repositories;

import com.gjj.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Created by gjj on 2018-03-04
 */
public interface UserRepository extends JpaRepository<User,Integer>{
    User findByUsername(String username);
    List<User> findAll();

}

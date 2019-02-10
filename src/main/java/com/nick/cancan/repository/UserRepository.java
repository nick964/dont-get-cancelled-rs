package com.nick.cancan.repository;

import com.nick.cancan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    @Query("SELECT usr FROM User usr WHERE usr.token = :token")
    User findByToken(@Param("token") String token);

    @Query("SELECT usr FROM User usr WHERE usr.id = :id")
    User findByUserId(@Param("id") Long id);

    @Query("SELECT usr FROM User usr WHERE upper(usr.screenName) = upper(:name)")
    User findByUsername(@Param("name") String screenName);




}

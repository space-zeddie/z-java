package com.zakharuk.java;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by citizenzer0 on 12/4/16.
 */
@Transactional
public interface UserDao extends CrudRepository<User, Long> {

    public User findByName(String name);
}

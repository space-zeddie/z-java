package com.zakharuk.java;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by citizenzer0 on 12/4/16.
 */
public interface UserRepository extends JpaRepository<User, Long> {

}

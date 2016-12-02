package com.zakharuk.java;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by citizenzer0 on 12/2/16.
 */
@Transactional
public interface SubjectDao extends CrudRepository<Subject, Long> {

    public Subject findByName(String name);

}

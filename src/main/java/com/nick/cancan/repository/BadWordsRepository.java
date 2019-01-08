package com.nick.cancan.repository;

import com.nick.cancan.entity.BadWord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadWordsRepository extends CrudRepository<BadWord, Long> {

    List<BadWord> findAll();

}

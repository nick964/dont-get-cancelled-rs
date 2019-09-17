package com.nick.cancan.repository;

import com.nick.cancan.entity.Word;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WordsRepository extends CrudRepository<Word, Long> {

    List<Word> findAll();

    @Query("SELECT bw FROM Word bw WHERE id < 1010")
    List<Word> findAllWithLimit();

}

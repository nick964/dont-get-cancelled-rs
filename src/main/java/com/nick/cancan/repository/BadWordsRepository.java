package com.nick.cancan.repository;

import com.nick.cancan.entity.BadWords;
import org.springframework.data.repository.CrudRepository;

public interface BadWordsRepository extends CrudRepository<BadWords, Long> {

}

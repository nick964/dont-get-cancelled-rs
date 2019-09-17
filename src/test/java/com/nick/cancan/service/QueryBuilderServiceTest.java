package com.nick.cancan.service;

import com.nick.cancan.repository.WordsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class QueryBuilderServiceTest {

    @InjectMocks
    private QueryBuilderServiceImpl queryBuilderService;

    @Mock
    WordsRepository wordsRepository;

    @Test
    public void createQueryTest() {

    }




}

package com.nick.cancan.service;

import com.nick.cancan.entity.BadWord;
import com.nick.cancan.model.MyQueryRequest;
import com.nick.cancan.repository.BadWordsRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QueryBuilderServiceTest {

    @InjectMocks
    private QueryBuilderServiceImpl queryBuilderService;

    @Mock
    BadWordsRepository badWordsRepository;

    @Test
    public void createQueryTest() {

    }




}

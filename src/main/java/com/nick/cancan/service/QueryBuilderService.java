package com.nick.cancan.service;

import com.nick.cancan.model.MyQueryRequest;

public interface QueryBuilderService {

    MyQueryRequest buildQuery(String screenname);

}

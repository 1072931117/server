package com.lhx.manager.service.impl;

import com.lhx.manager.service.TestService;
import com.lhx.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lhx on 2017/3/24.
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper testMapper;

    @Override
    public Object test() {
        return testMapper.testSelect();
    }
}

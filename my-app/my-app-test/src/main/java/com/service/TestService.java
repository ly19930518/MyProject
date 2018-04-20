package com.service;

import com.dao.dao.DictionaryMapper;
import com.dao.model.Dictionary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("testService")
public class TestService {
//    @Resource
//    public DictionaryMapper dictionaryMapper;

    public Dictionary test(){
        Dictionary di = null;
//        Dictionary di = dictionaryMapper.selectByPrimaryKey(10);
        return di;
    }
}

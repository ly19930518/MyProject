package com.test.proxy.logger.service;

public class UserServiceImpl implements UserService{
    @Override
    public void getUser() {
        System.out.println("get User......");
    }
}

package com.example.apm.eq.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.example.apm.eq.table.User;

import io.reactivex.rxjava3.core.Observable;

@Repository 
public class UserDao {
    
    @Resource 
    EasyEntityQuery easyEntityQuery;

    public Observable<List<User>> query(String name){
        return Observable.fromCallable(() -> {
            return easyEntityQuery.queryable(User.class).where(u -> u.name().eq(name)).toList();
        });
    }
}

package com.example.apm.service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.apm.eq.dao.UserDao;
import com.example.apm.eq.table.User;
// import com.example.apm.utils.RxJavaUtils;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApmServiceImpl implements ApmService {

    @Resource
    UserDao userDao;

    public Observable<List<User>> findUsersByUsername(String username) {
        return Observable.defer(() -> userDao.query(username))
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::fromIterable)
                .filter(u -> u.name != null && !u.name.isEmpty())
                .doOnNext(u -> log.info("命中用户: id={}, username={}", u.id, u.name))
                .toList() // Single<List<User>>
                .toObservable() // ★ 转回 Observable<List<User>>
                .doOnNext(list -> log.info("username={} 查询到 {} 个用户", username, list.size()))
                .doOnError(e -> log.error("按用户名查询失败, username={}", username, e))
                .onErrorReturn(e -> Collections.emptyList());
    }

    public Maybe<User> single(String name) {
        return Observable.defer(() -> userDao.query(name))
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::fromIterable)
                .filter(u -> u.name != null && !u.name.isEmpty())
                .doOnNext(u -> log.info("命中用户: id={}, username={}", u.id, u.name))
                .skip(2)
                .take(1)
                .switchIfEmpty(Observable.error(() -> new RuntimeException("匹配用户不足 3 个: " + name)))
                .doOnNext(u -> log.info("task() username={} 命中单个用户, id={}", name, u.id))
                .doOnError(e -> log.error("按用户名查询单个用户失败, username={}", name, e))
                .firstElement();
    }
}

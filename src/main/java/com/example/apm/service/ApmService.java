package com.example.apm.service;

import java.util.List;

import com.example.apm.eq.table.User;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;

public interface ApmService {

    Observable<List<User>> findUsersByUsername(String username);

    // Observable<User> single(String name);

    Maybe<User> single(String name);
}

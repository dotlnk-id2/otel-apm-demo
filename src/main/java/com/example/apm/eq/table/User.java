package com.example.apm.eq.table;

import java.time.LocalDateTime;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EasyAssertMessage;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.example.apm.eq.table.proxy.UserProxy;

import lombok.Data;

@Data
@Table("t_user")
@EntityProxy
@EasyAlias("t_user")
@EasyAssertMessage("未找到对应的用户信息")
public class User implements ProxyEntityAvailable<User, UserProxy> {
    @Column(primaryKey = true, comment = "用户id")
    private Long id;
    @Column(comment = "用户姓名")
    private String name;
    @Column(comment = "用户手机")
    private String phone;
    @Column(value = "create_at" ,comment = "创建时间")
    private LocalDateTime createAt;
}

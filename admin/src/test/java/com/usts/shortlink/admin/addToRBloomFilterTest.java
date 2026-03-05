package com.usts.shortlink.admin;

import com.usts.shortlink.admin.dao.entity.UserDO;
import com.usts.shortlink.admin.dao.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class addToRBloomFilterTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testAdd() {
        List<UserDO> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }

}

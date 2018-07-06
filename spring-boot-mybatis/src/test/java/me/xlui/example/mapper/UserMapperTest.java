package me.xlui.example.mapper;

import me.xlui.example.entity.User;
import me.xlui.example.enums.SexEnum;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    private String testUsername = "aa";

    @Before
    public void init() {
        userMapper.insert(new User(testUsername, "aa12345", SexEnum.MAN));
        userMapper.insert(new User("BB", "BB12345", SexEnum.WOMAN));
        userMapper.insert(new User("cc", "cc12345", SexEnum.WOMAN));
    }

    @After
    public void clean() {
        userMapper.deleteAll();
        Assert.assertEquals(0, userMapper.getAll().size());
    }

    @Test
    public void testInsert() throws Exception {
        Assert.assertEquals(3, userMapper.getAll().size());
    }

    @Test
    public void testQuery() throws Exception {
        Assert.assertEquals(testUsername, userMapper.getByUsername(testUsername).getUsername());
    }

    @Test
    public void testUpdate() throws Exception {
        User user = userMapper.getByUsername(testUsername);
        String newNickname = "xlui";
        user.setNickName(newNickname);
        userMapper.update(user);
        Assert.assertEquals(newNickname, userMapper.getByUsername(testUsername).getNickName());
    }

    @Test
    public void testDelete() throws Exception {
        userMapper.deleteByUsername(testUsername);
        Assert.assertNull(userMapper.getByUsername(testUsername));
    }
}

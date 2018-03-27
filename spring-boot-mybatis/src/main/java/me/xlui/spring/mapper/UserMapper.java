package me.xlui.spring.mapper;

import me.xlui.spring.entity.User;
import me.xlui.spring.enums.SexEnum;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {
	@Select("SELECT * FROM user")
	@Results({
			// 如果数据库中字段与类中相同，则不需要写 @Result；
			// 如果不同，则需要指明数据库表列中字段名
			// 如果不是常规类型，还需要指明 javaType
			@Result(property = "userSex", column = "user_sex", javaType = SexEnum.class),
			@Result(property = "nickName", column = "nick_name")
	})
	List<User> getAll();

	@Select("SELECT * FROM user WHERE id = #{id}")
	@Results({
			@Result(property = "userSex", column = "user_sex", javaType = SexEnum.class),
			@Result(property = "nickName", column = "nick_name")
	})
	User getById(Long id);

	@Select("SELECT * FROM user WHERE username = #{username}")
	@Results({
			@Result(property = "userSex", column = "user_sex", javaType = SexEnum.class),
			@Result(property = "nickName", column = "nick_name")
	})
	User getByUsername(String username);

	@Insert("INSERT INTO user(username, password, user_sex) VALUES(#{username}, #{password}, #{userSex})")
	void insert(User user);

	@Update("UPDATE user SET username=#{username}, nick_name=#{nickName} WHERE id = #{id}")
	void update(User user);

	@Delete("DELETE FROM user WHERE id = #{id}")
	void deleteById(Long id);

	@Delete("DELETE FROM user WHERE username = #{username}")
	void deleteByUsername(String username);

	@Delete("TRUNCATE TABLE user")
	void deleteAll();
}


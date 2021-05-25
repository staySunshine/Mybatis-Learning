package cn.xie.test;


import cn.xie.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {
    SqlSession sqlSession;
    @Before
    public void setUp() throws IOException {
        //1.Resources工具类，配置文件的加载，把配置文件加载成字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        //2.解析了配置文件，并创建了sqlSessionFactory工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //3.生产sqlSession
        sqlSession = sqlSessionFactory.openSession();// 默认开启一个事务，但是该事务不会自动提交
        //在进行增删改操作时，要手动提交事务
    }
    @Test
    public void testAdd() throws IOException {
        User user = new User();
        user.setId(4);
        user.setUsername("nancy");
        //插入 insert
        sqlSession.insert("userMapper.addUser",user);
        sqlSession.commit();
        //查询selectList selectOne
        List<User> userList = sqlSession.selectList("userMapper.findAllIncludeSql");
        System.out.println(userList);

    }

    @Test
    public void testUpdate() throws IOException {
        User user = new User();
        user.setId(3);
        user.setUsername("zhangsan");
        //更新：update
        sqlSession.update("userMapper.updateUser",user);
        sqlSession.commit();

        //s查询selectList selectOne 修改：update
        List<User> userList = sqlSession.selectList("userMapper.findAllIncludeSql");
        System.out.println(userList);

    }

    @Test
    public void testDelete(){
        //删除：delete
        sqlSession.delete("userMapper.deleteUser",4);
        sqlSession.commit();

        //查询selectList selectOne
        List<User> userList = sqlSession.selectList("userMapper.findAllIncludeSql");
        System.out.println(userList);
    }

    @After
    public void closeUp(){
        sqlSession.close();
    }
}

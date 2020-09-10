package cn.smbms.service.user;

import cn.smbms.dao.user.UserMapper;
import cn.smbms.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * service层捕获异常，进行事务处理
 * 事务处理：调用不同dao的多个方法，必须使用同一个connection（connection作为参数传递）
 * 事务完成之后，需要在service层进行connection的关闭，在dao层关闭（PreparedStatement和ResultSet对象）
 *
 * @author Administrator
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    @Transactional
    public boolean add(User user) {
        // TODO Auto-generated method stub
        boolean flag = false;
        try {
            int add = userMapper.add(user);
            if (add > 0) {
                flag = true;
                System.out.println("add success!");
            } else {
                System.out.println("add failed!");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
        return flag;
    }

    @Override
    public User login(String userCode) {
        // TODO Auto-generated method stub
        User user = null;
        try {
            user = userMapper.getLoginUser(userCode);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        // TODO Auto-generated method stub
        List<User> userList = null;
        System.out.println("queryUserName ---- > " + queryUserName);
        System.out.println("queryUserRole ---- > " + queryUserRole);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        try {
            userList = userMapper.getUserList(queryUserName, queryUserRole, (currentPageNo - 1) * pageSize, pageSize);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
        return userList;
    }

    @Override
    public User selectUserCodeExist(String userCode) {
        // TODO Auto-generated method stub
        User user = null;
        try {
            user = userMapper.getLoginUser(userCode);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
        return user;
    }

    @Override
    public boolean deleteUserById(Integer delId) {
        // TODO Auto-generated method stub
        boolean flag = false;
        User user = userMapper.getUserById(delId.toString());
        if (user.getIdPicPath() != null) {
            flag = false;
        } else {
            try {

                int count = userMapper.deleteUserById(delId);

                if (count > 0) {
                    flag = true;
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw e;
            }
        }
        return flag;
    }

    @Override
    public User getUserById(String id) {
        // TODO Auto-generated method stub
        User user = null;
        try {
            user = userMapper.getUserById(id);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            throw e;
        }
        return user;
    }

    @Override
    public boolean modify(User user) {
        // TODO Auto-generated method stub
        boolean flag = false;
        try {
            int modify = userMapper.modify(user);
            if (modify > 0) {
                flag = true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
        return flag;
    }

    @Override
    public boolean updatePwd(int id, String pwd) {
        // TODO Auto-generated method stub
        boolean flag = false;
        try {
            int pwd1 = userMapper.updatePwd(id, pwd);
            if (pwd1 > 0) {
                flag = true;
            }
        } catch (Exception e) {

            // TODO: handle exception
            e.printStackTrace();
            throw e;
        }
        return flag;
    }

    @Override
    public int getUserCount(String queryUserName, int queryUserRole) {
        // TODO Auto-generated method stub
        int count = 0;
        System.out.println("queryUserName ---- > " + queryUserName);
        System.out.println("queryUserRole ---- > " + queryUserRole);
        try {
            count = userMapper.getUserCount(queryUserName, queryUserRole);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
        return count;
    }

}

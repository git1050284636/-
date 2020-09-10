package cn.smbms.service.role;

import cn.smbms.dao.role.RoleMapper;
import cn.smbms.pojo.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRoleList() {
        // TODO Auto-generated method stub
        List<Role> roleList = null;
        try {
            roleList = roleMapper.getRoleList();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
        return roleList;
    }

    @Override
    public boolean modify(Role role) {
        // TODO Auto-generated method stub
        boolean flag = false;
        try {
            int modify = roleMapper.modify(role);
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
    public Role getRoleById(String id) {
        // TODO Auto-generated method stub
        Role role = null;
        try {
            role = roleMapper.getRoleById(id);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            throw e;
        }
        return role;
    }

    @Override
    public boolean add(Role role) {
        // TODO Auto-generated method stub
        boolean flag = false;
        try {
            int add = roleMapper.add(role);
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
    @Transactional
    public boolean deleteRoleById(Integer delId) {
        // TODO Auto-generated method stub
        boolean flag = false;
        Role role = roleMapper.getRoleById(delId.toString());
        try {
            int count = roleMapper.deleteRoleById(delId);
            if (count > 0) {
                flag = true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
        return flag;

    }
}

package cn.smbms.dao.role;

import cn.smbms.pojo.Role;

import java.util.List;

public interface RoleMapper {

    public List<Role> getRoleList();/**
     * 修改角色信息
     * @param role
     */
    public int modify(Role role);
    public Role getRoleById(String id);
    public int add(Role role);
    /**
     * 通过userId删除role
     *
     * @param delId
     */
    public int deleteRoleById(Integer delId);

}

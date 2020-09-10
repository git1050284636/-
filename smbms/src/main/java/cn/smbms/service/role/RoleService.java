package cn.smbms.service.role;

import cn.smbms.pojo.Role;

import java.util.List;

public interface RoleService {
	
	public List<Role> getRoleList();
	public boolean modify(Role role);
	public Role getRoleById(String id);
	/**
	 * 增加用户信息
	 * @param role
	 * @return
	 */
	public boolean add(Role role);
	/**
	 * 根据ID删除role
	 * @param delId
	 * @return
	 */
	public boolean deleteRoleById(Integer delId);
}

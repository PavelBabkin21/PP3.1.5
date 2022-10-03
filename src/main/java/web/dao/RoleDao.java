package web.dao;

import web.model.Role;

import java.util.Set;

public interface RoleDao {

    Set<Role> setRoles();

    Set<Role> getByName(String name);

    void saveRole(Role role);

}

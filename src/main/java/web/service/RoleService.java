package web.service;

import web.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    Set<Role> setRoles();
    Set<Role> getByName(String name);
    void saveRole(Role role);
}

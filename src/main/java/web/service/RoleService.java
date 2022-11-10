package web.service;

import web.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> listRoles();
    List<Role> getByName(String name);
}

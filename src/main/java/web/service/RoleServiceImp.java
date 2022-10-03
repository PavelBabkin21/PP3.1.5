package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.RoleDao;
import web.model.Role;

import java.util.LinkedList;
import java.util.List;

@Service
public class RoleServiceImp implements RoleService {

    private final RoleDao roleDao;
    @Autowired
    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> listRoles() {
        return roleDao.listRoles();
    }

    @Override
    public List<Role> getByName(String name) {
        List<Role> roles = new LinkedList<>();
        for (Role role : listRoles()) {
            if (name.contains(role.getName()))
                roles.add(role);
        }
        return roles;
    }

}

package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.RoleDao;
import web.model.Role;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    private final RoleDao roleDao;

    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Set<Role> setRoles() {
        return roleDao.setRoles();
    }

    @Override
    public Set<Role> getByName(String name) {
        Set<Role> roles = new HashSet<>();
        for (Role role : setRoles()) {
            if (name.contains(role.getRole()))
                roles.add(role);
        }
        return roles;
    }
    @Override
    @Transactional
    public void saveRole(Role role) {
        roleDao.saveRole(role);
    }

}

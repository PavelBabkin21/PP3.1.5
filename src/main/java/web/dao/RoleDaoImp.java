package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional
public class RoleDaoImp implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Role> setRoles() {
        return entityManager.createQuery("select role from Role role", Role.class).getResultStream().collect(Collectors.toSet());
    }

    @Override
    public Set<Role> getByName(String name) {
        return entityManager.createQuery("SELECT role FROM Role role WHERE role.role = :name", Role.class).
                setParameter("name", name).getResultStream().collect(Collectors.toSet());
    }
    @Override
    public void saveRole(Role role) {
        entityManager.persist(role);
    }
}

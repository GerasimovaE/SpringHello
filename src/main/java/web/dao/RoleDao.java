package web.dao;

import web.model.Role;
import web.model.User;

import java.util.List;

public interface RoleDao {

    void saveRole(Role role);

    List<Role> getAllRoles();

    Role getRoleById(long id);

    void update(Role role);

    void removeRole(Role role);

}

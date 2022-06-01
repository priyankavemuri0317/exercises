package com.revature.developercorner.service;

import com.revature.developercorner.data.RoleRepository;
import com.revature.developercorner.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// RoleService Class
// This class will handle the business logic for the Role objects in the application.
@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    // AddRole method
    // This method will take the supplied Role object and pass that into the RoleRepository object's save method,
    //  inserting it into the database:
    public Role addRole(Role role) {
        roleRepository.save(role);
        return role;
    }

    // GetRoleById method
    // This method will find the Role by the specified id from the database:
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).get();
    }

    // GetAllRoles method
    // This method will call the RoleRepository to query for a List of Roles to return:
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // UpdateRole method
    // This method will update the specified Role in the database:
    public void updateRole(Long id, Role role) {
        // Retrieve the Role by the specified id and save it into a Role object:
        Role dbRole = roleRepository.findById(id).get();

        // Set the database Role's name to the supplied Role's name:
        dbRole.setRole_name(role.getRole_name());

        // Call the RoleRepository to update the record with the newly updated database Role object:
        roleRepository.save(dbRole);
    }

    // DeleteRole method
    // This method will call the RoleRepository to delete the record with the specified id:
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}

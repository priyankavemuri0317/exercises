package com.revature.developercorner.data;

import com.revature.developercorner.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// RoleRepository Interface Class
// This interface class will handle the data logic of the Role objects in the database for the application.
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}

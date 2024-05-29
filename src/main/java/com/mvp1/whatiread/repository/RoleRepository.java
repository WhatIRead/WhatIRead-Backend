package com.mvp1.whatiread.repository;

import com.mvp1.whatiread.entity.role.Role;
import com.mvp1.whatiread.entity.role.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(RoleName name);
}
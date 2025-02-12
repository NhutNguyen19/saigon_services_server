package com.iuh.edu.fit.config;

import com.iuh.edu.fit.constant.PredefinePermission;
import com.iuh.edu.fit.constant.PredefinedRole;
import com.iuh.edu.fit.model.Permission;
import com.iuh.edu.fit.model.Role;
import com.iuh.edu.fit.model.User;
import com.iuh.edu.fit.repository.PermissionRepository;
import com.iuh.edu.fit.repository.RoleRepository;
import com.iuh.edu.fit.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring.datasource",
            name = "driver-class-name",
            havingValue = "org.postgresql.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        log.info("Initializing application");
        return args -> {
            if (userRepository.findByUsername(ADMIN_NAME).isEmpty()) {
                Permission readPermission = Permission.builder()
                        .name(PredefinePermission.READ_PRIVILEGES)
                        .description("Read privileges")
                        .build();
                Permission createPermission = Permission.builder()
                        .name(PredefinePermission.CREATE_PRIVILEGES)
                        .description("Create privileges")
                        .build();
                Permission updatePermission = Permission.builder()
                        .name(PredefinePermission.UPDATE_PRIVILEGES)
                        .description("Update privileges")
                        .build();
                Permission deletePermission = Permission.builder()
                        .name(PredefinePermission.DELETE_PRIVILEGES)
                        .description("Delete privileges")
                        .build();

                permissionRepository.save(readPermission);
                permissionRepository.save(createPermission);
                permissionRepository.save(updatePermission);
                permissionRepository.save(deletePermission);

                Set<Permission> userPermissions = new HashSet<>();
                userPermissions.add(readPermission);

                Set<Permission> adminPermissions = new HashSet<>();
                adminPermissions.add(readPermission);
                adminPermissions.add(createPermission);
                adminPermissions.add(updatePermission);
                adminPermissions.add(deletePermission);

                Role userRole = Role.builder()
                        .name(PredefinedRole.USER_ROLE)
                        .description("User role")
                        .permissions(userPermissions)
                        .build();
                Role adminRole = Role.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .description("Admin role")
                        .permissions(adminPermissions)
                        .build();
                roleRepository.save(userRole);
                adminRole = roleRepository.save(adminRole);

                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                User user = User.builder()
                        .username(ADMIN_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .build();
                userRepository.save(user);

                log.warn("Admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}

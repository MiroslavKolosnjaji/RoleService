package com.example.roleservice.bootstrap;

import com.example.roleservice.model.Role;
import com.example.roleservice.model.RoleName;
import com.example.roleservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Miroslav Kološnjaji
 */
@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        roleRepository.deleteAll().doOnSuccess(success -> loadRoleData()).subscribe();
        roleRepository.count().subscribe(System.out::println);
    }

    private void loadRoleData(){
        roleRepository.count().subscribe(count -> {
            Role role1 = Role.builder().roleName(RoleName.ADMIN).build();
            Role role2 = Role.builder().roleName(RoleName.USER).build();

            roleRepository.save(role1).subscribe();
            roleRepository.save(role2).subscribe();
        });
    }
}

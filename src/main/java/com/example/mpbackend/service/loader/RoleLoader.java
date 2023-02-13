package com.example.mpbackend.service.loader;

import com.example.mpbackend.service.DefaultRoles;
import com.example.mpbackend.service.RoleService;
import com.example.mpbackend.web.data.transfer.RoleAddPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RoleLoader implements CommandLineRunner {
    private final RoleService roleService;

    @Autowired
    public RoleLoader(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {
        roleService.create(new RoleAddPayload(DefaultRoles.USER, 3));
        roleService.create(new RoleAddPayload(DefaultRoles.ADMIN, 2));
        roleService.create(new RoleAddPayload(DefaultRoles.SUPER_ADMIN, 1));
    }
}

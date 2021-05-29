package com.heroku.labshare.model;

import com.heroku.labshare.util.Sets;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum Role {

    MODER(Sets.of(Permission.MODERATE)),
    APPROVED_USER(Sets.of(Permission.DEFAULT, Permission.DOWNLOAD_LAB)),
    NON_APPROVED_USER(Sets.of(Permission.DEFAULT));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}

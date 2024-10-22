package com.quantus.backend.config.auth;

import com.quantus.backend.models.system.UserRole;
import com.quantus.backend.models.system.User;
import com.quantus.backend.repositories.system.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-06-04
 */

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUserName(username);
        if (user == null || user.getIsDeleted()==true) {
            throw new UsernameNotFoundException("User not found");
        }
        if (user.getIsDisabled()==true) {
            throw new UsernameNotFoundException("The user is disabled");
        }
        return new UserPrincipal(user);
    }

    public static class UserAuthority implements GrantedAuthority {

        private final UserRole userRole;

        public UserAuthority(UserRole userRole) {
            this.userRole = userRole;
        }

        @Override
        public String getAuthority() {
            return userRole.getRoleName();
        }
    }

    public static class UserPrincipal implements UserDetails {

        private final User user;

        public UserPrincipal(User user) {
            this.user = user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return user.getUserRoles().stream()
                    .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.getRoleName()))
                    .collect(Collectors.toList());
        }

        public Integer getUserId() { return user.getId(); }

        @Override
        public String getPassword() {
            return user.getUserPassword();
        }

        @Override
        public String getUsername() {
            return user.getUserName();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}

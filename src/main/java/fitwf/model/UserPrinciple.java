package fitwf.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrinciple implements UserDetails {
    private int id;
    private String username;
    private String email;
    private String password;
    private boolean enable;
    private Collection<? extends GrantedAuthority> authorities;
    public UserPrinciple(int id, String username, String email, String password, boolean enable, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enable = enable;
        this.authorities = authorities;
    }

    public static UserPrinciple create(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles()
                .forEach(role ->
                        authorities.add(new SimpleGrantedAuthority(role.getName().name())));
        return new UserPrinciple(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.isEnable(),
                authorities
        );
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEnable() {
        return enable;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return false;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }
}

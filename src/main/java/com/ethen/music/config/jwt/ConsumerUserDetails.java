package com.ethen.music.config.jwt;

import com.ethen.music.entity.ConsumerEntity;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * SpringSecurity需要的用户详情
 * Created by macro on 2018/4/26.
 */
public class ConsumerUserDetails implements UserDetails {

    private final ConsumerEntity consumer;

    public ConsumerUserDetails(ConsumerEntity consumer) {
        this.consumer = consumer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return null;
    }

    @Override
    public String getPassword() {
        return consumer.getPassword();
    }

    @Override
    public String getUsername() {
        return consumer.getUsername();
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


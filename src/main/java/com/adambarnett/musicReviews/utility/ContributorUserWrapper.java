package com.adambarnett.musicReviews.utility;

import com.adambarnett.musicReviews.model.Contributor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


/** Wraps Contributor class in order to implement UserDetails*/
public class ContributorUserWrapper implements UserDetails {
    private final Contributor contributor;

    public ContributorUserWrapper(Contributor contributor) {
        this.contributor = contributor;
    }

    /** Returns the full list of permissions of the user*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(contributor.getRole().name()));
    }

    @Override
    public String getPassword() {
        return contributor.getPassword();
    }

    @Override
    public String getUsername() {
        return contributor.getUsername();
    }

    @Override public boolean isAccountNonExpired() {
        return true;
    }

    @Override public boolean isAccountNonLocked() {
        return true;
    }

    @Override public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override public boolean isEnabled() {
        return true;
    }
}

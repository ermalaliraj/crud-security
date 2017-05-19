package com.ea.crud.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDto implements Serializable, UserDetails {

	private static final long serialVersionUID = -8472447620112441018L;

	private String username;
	private String password;
	private List<RoleDto> roles;

	public UserDto() {}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}
	public List<RoleDto> getRoles() {
		List<RoleDto> r = new ArrayList<RoleDto>();
		r.addAll(roles);
		return r;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
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
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	@Override
	public String getPassword() {
		return password;
	}

	public boolean equals(final Object other) {
        if (!(other instanceof UserDto))
            return false;
        UserDto credit = (UserDto) other;
      
        return new EqualsBuilder()
        		.append(username, credit.username)
        		.isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(username)
        		.toHashCode();
    }
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("username", username)
        	.append("password", password)
        	.append("roles ", roles)
        	.toString();
    }

}
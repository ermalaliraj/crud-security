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

	private Long id;
	private String username;
	private String password;
	private List<RoleDto> roles;
	private boolean enabled;

	public UserDto() {
		enabled = true;
		roles = new ArrayList<RoleDto>();
	}
	public UserDto(String u, String p) {
		this();
		username = u;
		password = p;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
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
		return roles;
	}
	public void addRole(RoleDto r) {
		roles.add(r);
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}
	public List<String> getRoleNames() {
		List<String> rv = new ArrayList<String>();
		for (RoleDto r : roles) {
			rv.add(r.getName());
		}
		return rv;
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
		return enabled;
	}
	@Override
	public String getPassword() {
		return password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean equals(final Object other) {
        if (!(other instanceof UserDto))
            return false;
        UserDto credit = (UserDto) other;
      
        return new EqualsBuilder()
        		.append(id, credit.id)
        		.append(username, credit.username)
        		.isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(id)
        		.append(username)
        		.toHashCode();
    }
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("id", id)
        	.append("username", username)
        	.append("password", password)
        	.append("roles ", roles)
        	.append("enabled", enabled)
        	.toString();
    }

}

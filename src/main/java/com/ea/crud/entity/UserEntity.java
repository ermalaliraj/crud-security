package com.ea.crud.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
public class UserEntity{

	@Id
	@Column(name="USERNAME")
	private String username;
	private String password;
	
	@OneToMany(mappedBy="user")
	List<RoleEntity> roles;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<RoleEntity> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleEntity> roles) {
		this.roles = roles;
	}
	public boolean equals(final Object other) {
        if (!(other instanceof UserEntity))
            return false;
        UserEntity o = (UserEntity) other;
      
        return new EqualsBuilder()
        		.append(username, o.username)
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
        	.append("roles", roles)
        	.toString();
    }

}

package com.ea.crud.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class RoleEntity implements Serializable, GrantedAuthority {

	private static final long serialVersionUID = 2754769147166111640L;
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_READ = "ROLE_USER";
	
	@Id
	private String name;
    private String description;
    
    @ManyToMany
    private List<UserEntity> users = new LinkedList<UserEntity>();
    
    public RoleEntity(){
    }
    
	public RoleEntity(String name){
    	this.name=name;
    }
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

	public String getAuthority() {
        return name;
    }

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public boolean equals(final Object other) {
        if (!(other instanceof RoleEntity))
            return false;
        RoleEntity castOther = (RoleEntity) other;
        return new EqualsBuilder().append(name, castOther.name).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(name).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
    		.append("name", name)
        	.append("description", description)
        	.toString();
    }

}
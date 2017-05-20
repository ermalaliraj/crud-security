package com.ea.crud.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;

public class RoleDto implements Serializable, GrantedAuthority {

	private static final long serialVersionUID = 2754769147166111640L;
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_READONLY = "ROLE_READONLY";
	
    private String name;
    private String description;
    
    public RoleDto(){
    }
    
    public RoleDto(String name){
    	this.name=name;
    }
    
    public String getAuthority() {
        return name;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public boolean equals(final Object other) {
        if (!(other instanceof RoleDto))
            return false;
        RoleDto castOther = (RoleDto) other;
        return new EqualsBuilder()
        		.append(name, castOther.name).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
        		.append(name).toHashCode();
    }

    public String toStringFull() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
        	.append("name", name)
        	.append("description", description)
        	.toString();
    }
    
    public String toString() {
        return name;
    }

}
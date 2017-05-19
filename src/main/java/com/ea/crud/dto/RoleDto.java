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
	public static final String ROLE_READ = "ROLE_USER";
	
    private String id;
    private String description;
    
    public RoleDto(){
    }
    
    public RoleDto(String id){
    	this.id=id;
    }
    
    public String getAuthority() {
        return id;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
        		.append(id, castOther.id).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
        		.append(id).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
        	.append("id", id)
        	.append("description", description)
        	.toString();
    }

}
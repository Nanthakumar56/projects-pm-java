package com.manage_projects.projects.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "projectmembers")
public class Members {
    @EmbeddedId
    private MembersId id; 

    public Members() {}

    public Members(MembersId id) {
        this.id = id;
    }

    public MembersId getId() {
        return id;
    }

    public void setId(MembersId id) {
        this.id = id;
    }
}

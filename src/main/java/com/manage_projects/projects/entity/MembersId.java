package com.manage_projects.projects.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class MembersId implements Serializable {
    private String projectid;
    private String userid;

    public MembersId() {}

    public MembersId(String projectid, String userid) {
        this.projectid = projectid;
        this.userid = userid;
    }

    // Getters and Setters

    public String getprojectid() {
        return projectid;
    }

    public void setprojectid(String projectid) {
        this.projectid = projectid;
    }

    public String getuserid() {
        return userid;
    }

    public void setuserid(String userid) {
        this.userid = userid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MembersId)) return false;
        MembersId that = (MembersId) o;
        return Objects.equals(projectid, that.projectid) &&
               Objects.equals(userid, that.userid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectid, userid);
    }
}

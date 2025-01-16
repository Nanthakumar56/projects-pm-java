package com.manage_projects.projects.dto;

import java.util.List;

public class ProjectNotesDto {
    private String notesid;
    private String name;
    private List<KeyPointsDTO> keypoints;
    private String tag;

    public ProjectNotesDto() {
        super();
    }

    public ProjectNotesDto(String notesid, String name, List<KeyPointsDTO> keypoints, String tag) {
        this.notesid = notesid;
        this.name = name;
        this.keypoints = keypoints;
        this.tag = tag;
    }

    public String getNotesid() {
        return notesid;
    }

    public void setNotesid(String notesid) {
        this.notesid = notesid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<KeyPointsDTO> getKeypoints() {
        return keypoints;
    }

    public void setKeypoints(List<KeyPointsDTO> keypoints) {
        this.keypoints = keypoints;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "ProjectNotesDTO [notesid=" + notesid + ", name=" + name + ", keypoints=" + keypoints + ", tag=" + tag + "]";
    }

    // KeyPointsDTO class
    public static class KeyPointsDTO {

        private String id;
        private String points;

        public KeyPointsDTO() {
            super();
        }

        public KeyPointsDTO(String id, String points) {
            this.id = id;
            this.points = points;
        }

        public String getPointsid() {
            return id;
        }

        public void setPointsid(String id) {
            this.id = id;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        @Override
        public String toString() {
            return "KeyPointsDTO [pointsid=" + id + ", points=" + points + "]";
        }
    }
}

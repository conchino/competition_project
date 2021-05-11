package com.competition.project.dto;

import com.competition.project.entity.Position;
import lombok.Data;

import java.io.Serializable;

@Data
public class PositionDTO implements Serializable {

    private static final long serialVersionUID=1L;

    private String postId;
    private String postName;

    public PositionDTO(Position position){
        this.postId = position.getPostId();
        this.postName = position.getPostName();
    }
}

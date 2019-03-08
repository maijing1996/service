package com.mj.model;

import lombok.Data;

import java.util.List;

/**
 * Created by mj on 2018-05-06.
 */
@Data
public class SecondComment extends Comment {
    public SecondComment() {
        super();
    }

    private List<Comment> list;
}

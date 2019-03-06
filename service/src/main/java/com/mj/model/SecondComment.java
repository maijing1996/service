package com.mj.model;

import java.util.List;

/**
 * Created by mj on 2018-05-06.
 */
public class SecondComment extends Comment {
    public SecondComment() {
        super();
    }

    private List<Comment> list;

    public List<Comment> getList() {
        return list;
    }

    public void setList(List<Comment> list) {
        this.list = list;
    }
}

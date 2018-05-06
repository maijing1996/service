package com.gjj.models;

import java.util.List;

/**
 * Created by gjj on 2018-05-06.
 */
public class SecondComment extends Comment {
    public SecondComment() {
        super();
    }

    private List list;

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}

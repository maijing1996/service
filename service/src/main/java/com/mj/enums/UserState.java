package com.mj.enums;

/**
 * Created by mj on 2018-03-30.
 */
public enum UserState {
    /*正常*/
    NORMAL(0),
    /*锁定*/
    LOCK(1);
    private Integer state;

    public Integer getState() {
        return state;
    }

    UserState(Integer state) {

        this.state = state;
    }
}

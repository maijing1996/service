package com.mj.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Builder
@Table(name = "user_relation")
@NoArgsConstructor
@AllArgsConstructor
public class UserRelation {

    @Column(name = "active_id")
    private Integer activeId;//关注者

    @Column(name = "passive_id")
    private Integer passiveId;//被关注

}

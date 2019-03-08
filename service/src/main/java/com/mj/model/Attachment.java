package com.mj.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by mj on 2018-03-08.
 */
@Entity
@Data
@Builder
@Table(name = "attachment")
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Integer id;

    @Column(name = "attachment_name")
    @JsonProperty("attachmentName")
    private String attachmentName;

    @NotNull
    @Column(name = "attachment_url")
    @JsonProperty("attachmentUrl")
    private String attachmentUrl;

    @NotNull
    @JsonIgnore
//    @ManyToOne(targetEntity = Goods.class)
//    @JoinColumn(name = "goods_id",insertable = false, updatable = false)
    @Column(name = "goods_id")
    @JsonProperty("goodsId")
    private Integer goodsId;

}

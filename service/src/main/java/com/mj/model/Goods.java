package com.mj.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by mj on 2018-03-08.
 */
@Entity
@Data
@Table(name = "goods")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Goods {
    @Id
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "goods_name", length = 200)
    private String goodsName;

    @NotNull
    @Column(name = "spec", length = 500)
    private String spec;

    @NotNull
    @Column(name = "price")
    private Double price;

    @Column(name = "original_price")
    private Double originalPrice;

    @NotNull
    @Column(name = "type")
    private String type;

    @NotNull
    @Column(name = "bulletin_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bulletinDate;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "good_attachments")
    private String goodAttachments;

    @Column(name = "customer_id")
    private Integer customerId;

    @Transient
    private User user;

    @Transient
    private List<Attachment> attachments;
}

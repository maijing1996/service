package com.gjj.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gjj on 2018-03-08.
 */
@Entity
@Table(name = "goods")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Integer id;

    @NotNull
    @Column(name = "goods_name", length = 200)
    @JsonProperty("goodsName")
    private String goodsName;

    @NotNull
    @Column(name = "spec", length = 500)
    @JsonProperty("spec")
    private String spec;

    @NotNull
    @Column(name = "price")
    @JsonProperty("price")
    private Double price;

    @Column(name = "original_price")
    @JsonProperty("originalPrice")
    private Double originalPrice;

    @NotNull
    @Column(name = "type")
    @JsonProperty("type")
    private String type;

    @NotNull
    @Column(name = "bulletin_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "bulletinDate")
    private Date bulletinDate;

    @NotNull
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    @JsonProperty("user")
    private User user;

    @OneToMany(targetEntity = Attachment.class, mappedBy = "goods", cascade = CascadeType.REMOVE)
    @JsonProperty("good_attachments")
    private Set<Attachment> attachments = new HashSet<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getBulletinDate() {
        return bulletinDate;
    }

    public void setBulletinDate(Date bulletinDate) {
        this.bulletinDate = bulletinDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", goodsName='" + goodsName + '\'' +
                ", spec='" + spec + '\'' +
                ", price=" + price +
                ", originalPrice=" + originalPrice +
                ", type='" + type + '\'' +
                ", bulletinDate=" + bulletinDate +
                ", user=" + user +
                ", attachments=" + attachments +
                '}';
    }
}

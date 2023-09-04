package com.gm.wj.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Shop entity.
 *
 * @author yunshiyu
 * @date 2023/8
 */
@Data
@Entity
@Table(name = "shop")
@ToString
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * 店铺名称
     */
    private String name;

    /**
     * 店长
     */
    private String manager;

    /**
     * 店铺地址
     */
    private String address;

    /**
     * 店铺经度
     */
    private String lng;

    /**
     * 店铺维度
     */
    private String lat;

    /**
     * 店铺类型名称（1，售卖店铺；2，仓库）
     */
    @Column(name = "shoptype")
    private String shopType;

    /**
     * 店铺类型（1，售卖店铺；2，仓库）
     */
    private String type;

}

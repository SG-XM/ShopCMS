package com.tju.zq.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class ItemOrder extends BaseObject {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tju_item_order.id
     *
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    private Integer id;
    private String sdes;

    public String getSdes() {
        return sdes;
    }

    public void setSdes(String sdes) {
        this.sdes = sdes;
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tju_item_order.name
     *
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tju_item_order.count
     *
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    private Integer count;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tju_item_order.price
     *
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    private BigDecimal price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tju_item_order.total
     *
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    private String total;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tju_item_order.customer
     *
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    private String customer;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tju_item_order.time
     *
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date time;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date mintime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maxtime;

    public Date getMintime() {
        return mintime;
    }

    public void setMintime(Date mintime) {
        this.mintime = mintime;
    }

    public Date getMaxtime() {
        return maxtime;
    }

    public void setMaxtime(Date maxtime) {
        this.maxtime = maxtime;
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tju_item_order.provider
     *
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    private String provider;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tju_item_order.phone
     *
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tju_item_order.address
     *
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tju_item_order.info
     *
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    private String info;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tju_item_order.id
     *
     * @return the value of tju_item_order.id
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tju_item_order.id
     *
     * @param id the value for tju_item_order.id
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tju_item_order.name
     *
     * @return the value of tju_item_order.name
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tju_item_order.name
     *
     * @param name the value for tju_item_order.name
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tju_item_order.count
     *
     * @return the value of tju_item_order.count
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public Integer getCount() {
        return count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tju_item_order.count
     *
     * @param count the value for tju_item_order.count
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tju_item_order.price
     *
     * @return the value of tju_item_order.price
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tju_item_order.price
     *
     * @param price the value for tju_item_order.price
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tju_item_order.total
     *
     * @return the value of tju_item_order.total
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public String getTotal() {
        return total;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tju_item_order.total
     *
     * @param total the value for tju_item_order.total
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tju_item_order.customer
     *
     * @return the value of tju_item_order.customer
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tju_item_order.customer
     *
     * @param customer the value for tju_item_order.customer
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tju_item_order.time
     *
     * @return the value of tju_item_order.time
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public Date getTime() {
        return time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tju_item_order.time
     *
     * @param time the value for tju_item_order.time
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tju_item_order.provider
     *
     * @return the value of tju_item_order.provider
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public String getProvider() {
        return provider;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tju_item_order.provider
     *
     * @param provider the value for tju_item_order.provider
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tju_item_order.phone
     *
     * @return the value of tju_item_order.phone
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tju_item_order.phone
     *
     * @param phone the value for tju_item_order.phone
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tju_item_order.address
     *
     * @return the value of tju_item_order.address
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tju_item_order.address
     *
     * @param address the value for tju_item_order.address
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tju_item_order.info
     *
     * @return the value of tju_item_order.info
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public String getInfo() {
        return info;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tju_item_order.info
     *
     * @param info the value for tju_item_order.info
     * @mbggenerated Sun Jun 21 14:52:19 CST 2020
     */
    public void setInfo(String info) {
        this.info = info;
    }
}
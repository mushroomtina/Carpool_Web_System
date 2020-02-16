package com.example.demo.domain;

import java.util.Date;

public class Order {
    private Integer id;
    private String origin;
    private String destination;
    private Date time;
    private Integer person;
    private Integer ur_id;
    private Integer cp_id;

    public Order() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getPerson() {
        return person;
    }

    public void setPerson(Integer person) {
        this.person = person;
    }

    public Integer getUr_id() {
        return ur_id;
    }

    public void setUr_id(Integer ur_id) {
        this.ur_id = ur_id;
    }

    public Integer getCp_id() {
        return cp_id;
    }

    public void setCp_id(Integer cp_id) {
        this.cp_id = cp_id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", time=" + time +
                ", person=" + person +
                ", ur_id=" + ur_id +
                ", cp_id=" + cp_id +
                '}';
    }
}

package com.ashokit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDto {

    private Integer totalEnqs;

    private Integer openEnqs;

    private Integer enrolledEnqs;

    private Integer lostEnqs;

    public Integer getTotalEnqs() {
        return totalEnqs;
    }

    public void setTotalEnqs(Integer totalEnqs) {
        this.totalEnqs = totalEnqs;
    }

    public Integer getOpenEnqs() {
        return openEnqs;
    }

    public void setOpenEnqs(Integer openEnqs) {
        this.openEnqs = openEnqs;
    }

    public Integer getEnrolledEnqs() {
        return enrolledEnqs;
    }

    public void setEnrolledEnqs(Integer enrolledEnqs) {
        this.enrolledEnqs = enrolledEnqs;
    }

    public Integer getLostEnqs() {
        return lostEnqs;
    }

    public void setLostEnqs(Integer lostEnqs) {
        this.lostEnqs = lostEnqs;
    }
}

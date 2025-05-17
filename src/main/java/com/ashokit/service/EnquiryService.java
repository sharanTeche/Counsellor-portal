package com.ashokit.service;

import com.ashokit.dto.DashboardDto;
import com.ashokit.dto.EnquiryDto;

import java.util.List;

public interface EnquiryService {

    boolean upsertEnquiry(EnquiryDto enquiryDto, Integer counsellorId);

    DashboardDto getDashboardInfo(Integer counsellorId);

    List<EnquiryDto> getEnquiries(Integer counsellorId);

    //update enquiry
    EnquiryDto getEnquiry(Integer enqId, EnquiryDto enquiryDto);

    //delete enuiry
    void deleteEnquiry(Integer enqId);
    List<EnquiryDto> filterEnqs(EnquiryDto filterDto, Integer counsellorId);



}

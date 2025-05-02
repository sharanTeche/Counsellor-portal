package com.ashokit.service;

import com.ashokit.dto.CounsellorsDto;
import com.ashokit.entity.Counsellors;
import org.springframework.stereotype.Service;


public interface CounsellorService {

    boolean register(CounsellorsDto counsellorsDto);

    public CounsellorsDto login(String email, String pwd);

    public boolean isEmailUnique(String email);


}

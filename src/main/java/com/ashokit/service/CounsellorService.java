package com.ashokit.service;

import com.ashokit.dto.CounsellorsDto;


public interface CounsellorService {

    boolean register(CounsellorsDto counsellorsDto);

    public CounsellorsDto login(String email, String pwd);

    public boolean isEmailUnique(String email);


}

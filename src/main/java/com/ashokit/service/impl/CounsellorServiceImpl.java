package com.ashokit.service.impl;

import com.ashokit.dto.CounsellorsDto;
import com.ashokit.entity.Counsellors;
import com.ashokit.repository.CounsellorsRepository;
import com.ashokit.service.CounsellorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CounsellorServiceImpl implements CounsellorService {

    @Autowired
    private CounsellorsRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public CounsellorServiceImpl(CounsellorsRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }
    @Override
    public boolean register(CounsellorsDto counsellorsDto) {

        Counsellors counsellors = mapToEntity(counsellorsDto);

        Counsellors savedEntity = repository.save(counsellors);
        return savedEntity.getCounsellorId()!=null;
    }

    @Override
    public CounsellorsDto login(String email, String pwd) {
       Counsellors counsellors =  repository.findByEmailAndPwd(email, pwd);
       if (counsellors != null) {
           return mapToDTO(counsellors);
       }
       return null;
    }

    @Override
    public boolean isEmailUnique(String email) {
        Optional<Counsellors> byEmail = repository.findByEmail(email);
       return !byEmail.isPresent();
    }


    private CounsellorsDto mapToDTO(Counsellors counsellors) {
       return modelMapper.map(counsellors, CounsellorsDto.class);
    }

    private Counsellors mapToEntity(CounsellorsDto counsellorsDto) {
       return modelMapper.map(counsellorsDto, Counsellors.class);
    }
}

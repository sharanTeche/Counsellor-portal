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
       // return savedEntity.getCounsellorId()!=null;
        return true;
    }

    @Override
    public CounsellorsDto login(String email, String pwd) {
       Counsellors counsellors =  repository.findByEmailAndPwd(email, pwd);
       if (counsellors != null) {
           return mapToDTO(counsellors);
       }
     //   throw new RuntimeException("Invalid credenatils");
       return null;
    }

    @Override
    public boolean isEmailUnique(String email) {
        Optional<Counsellors> byEmail = repository.findByEmail(email);
        if(byEmail.isPresent()) {
            return false;
        }
        return true;

    }


    private CounsellorsDto mapToDTO(Counsellors counsellors) {
        CounsellorsDto counsellorsDto = modelMapper.map(counsellors, CounsellorsDto.class);
        return counsellorsDto;
    }

    private Counsellors mapToEntity(CounsellorsDto counsellorsDto) {
        Counsellors counsellors = modelMapper.map(counsellorsDto, Counsellors.class);
       /* Counsellors counsellors = new Counsellors();
        counsellors.setName(counsellorsDto.getName());
        counsellors.setEmail(counsellorsDto.getEmail());
        counsellors.setPwd(counsellorsDto.getPwd());
        counsellors.setPhno(counsellorsDto.getPhno());*/
        return counsellors;
    }
}

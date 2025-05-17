package com.ashokit.service.impl;

import com.ashokit.dto.DashboardDto;
import com.ashokit.dto.EnquiryDto;
import com.ashokit.entity.Counsellors;
import com.ashokit.entity.Enquiry;
import com.ashokit.repository.CounsellorsRepository;
import com.ashokit.repository.EnquiryRepository;
import com.ashokit.service.EnquiryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnquiryServiceImpl implements EnquiryService {

    @Autowired
    private EnquiryRepository repository;

    @Autowired
    private CounsellorsRepository counsellorsRepository;

    @Autowired
    private ModelMapper modelMapper;

    public EnquiryServiceImpl(EnquiryRepository repository, CounsellorsRepository counsellorsRepository,ModelMapper modelMapper) {
        this.repository =repository;
        this.counsellorsRepository= counsellorsRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public boolean upsertEnquiry(EnquiryDto enquiryDto, Integer  counsellorId) {
        Enquiry enquiry = mapToEntity(enquiryDto);

        Counsellors counsellor = counsellorsRepository.findById(counsellorId)
                .orElseThrow(() -> new RuntimeException("Counsellor not found"));

        enquiry.setCounsellors(counsellor);
        repository.save(enquiry);
        return true;
    }

    @Override
    public DashboardDto getDashboardInfo(Integer counsellorId) {

       List<Enquiry> allEnquiries = repository.findByCounsellorsCounsellorId(counsellorId);
       int total = allEnquiries.size();

       int open =  allEnquiries.stream().filter(e -> e.getEnqStatus().equals("Open")).toList().size();

        int enrolled =  allEnquiries.stream().filter(e -> e.getEnqStatus().equals("Enrolled")).toList().size();

        int lost =  allEnquiries.stream().filter(e -> e.getEnqStatus().equals("Lost")).toList().size();

        //Older approche
        DashboardDto dto = new DashboardDto();
        dto.setTotalEnqs(total);
        dto.setOpenEnqs(open);
        dto.setEnrolledEnqs(enrolled);
        dto.setLostEnqs(lost);
        return dto;

    }

    @Override
    public List<EnquiryDto> getEnquiries(Integer counsellorId) {

       List<Enquiry> enquiry = repository.findByCounsellorsCounsellorId(counsellorId);
        return enquiry.stream().map(this::mapToDTO).toList();
    }

    //edit method
    @Override
    public EnquiryDto getEnquiry(Integer enqId, EnquiryDto enquiryDto) {
        Enquiry enquiry = repository.findById(enqId).orElseThrow(()-> new RuntimeException("enqId is not found"));
        return mapToDTO(enquiry);
    }

    @Override
    public List<EnquiryDto> filterEnqs(EnquiryDto filterDto, Integer counsellorId) {


        Enquiry enquiry = new Enquiry();

        if (filterDto.getCourceName() != null && !filterDto.getCourceName().equals("")) {
            enquiry.setCourceName(filterDto.getCourceName());
        }

        if (filterDto.getClassMode() != null && !filterDto.getClassMode().equals("")) {
            enquiry.setClassMode(filterDto.getClassMode());
        }

        if (filterDto.getEnqStatus() != null && !filterDto.getEnqStatus().equals("")) {
            enquiry.setEnqStatus(filterDto.getEnqStatus());
        }

        Counsellors counsellor = counsellorsRepository.findById(counsellorId)
                .orElseThrow(()->new RuntimeException("counsellor id is found"));
        enquiry.setCounsellors(counsellor);

        List<Enquiry> allenq =  repository.findAll(Example.of(enquiry));
        return allenq.stream().map(this::mapToDTO).toList();

    }

    public void deleteEnquiry(Integer enqId) {
        Enquiry existingDto = repository.findById(enqId).orElseThrow();
        repository.delete(existingDto);

    }


    private EnquiryDto mapToDTO(Enquiry enquiry) {
       return modelMapper.map(enquiry, EnquiryDto.class);
    }
    private Enquiry mapToEntity(EnquiryDto enquiryDto) {
        return modelMapper.map(enquiryDto, Enquiry.class);
    }
}

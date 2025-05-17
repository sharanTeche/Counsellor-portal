package com.ashokit.repository;

import com.ashokit.entity.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnquiryRepository extends JpaRepository<Enquiry, Integer> {
    List<Enquiry> findByCounsellorsCounsellorId(Integer counsellorId);


}

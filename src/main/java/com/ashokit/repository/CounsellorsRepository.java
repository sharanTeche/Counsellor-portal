package com.ashokit.repository;

import com.ashokit.entity.Counsellors;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CounsellorsRepository extends JpaRepository<Counsellors, Integer> {

     Counsellors findByEmailAndPwd(String email, String pwd);

     Optional<Counsellors> findByEmail(String email);
}

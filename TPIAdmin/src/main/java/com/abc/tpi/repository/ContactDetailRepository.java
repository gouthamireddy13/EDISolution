package com.abc.tpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.tpi.model.partner.ContactDetail;
@Repository("contactDetailRepository")
public interface ContactDetailRepository extends JpaRepository<ContactDetail, Long> {

}

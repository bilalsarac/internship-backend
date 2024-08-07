package com.mobile.repository;

import com.mobile.entities.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {

}

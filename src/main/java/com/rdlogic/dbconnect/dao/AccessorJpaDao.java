package com.rdlogic.dbconnect.dao;

import com.rdlogic.dbconnect.db.entities.Accessor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessorJpaDao extends JpaRepository<Accessor, String> {

    Accessor getById(String id);
}

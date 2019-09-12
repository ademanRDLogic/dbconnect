package com.rdlogic.dbconnect.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

@Named
public class JDBCTestDAO {

    DataSource dataSource;
    JdbcTemplate template;

    public String getSUPemail() {
        return template.queryForObject("select email from accessor where id = 'SUP'", String.class);
    }

    @Inject
    public void setdataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.template = new JdbcTemplate(this.dataSource);
        this.template.setResultsMapCaseInsensitive(true);
    }
}

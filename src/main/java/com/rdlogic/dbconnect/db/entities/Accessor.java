package com.rdlogic.dbconnect.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Accessor {

    @Id @Column(name = "ID")
    private String id;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;

    protected Accessor() {}

    public Accessor(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Accessor@%s[id=%s, firstName=%s, lastName=%s, email=%s]"
                , String.valueOf(this.hashCode()),id,firstName,lastName,email);
    }
}

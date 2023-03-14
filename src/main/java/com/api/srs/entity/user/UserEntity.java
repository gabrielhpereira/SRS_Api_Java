package com.api.srs.entity.user;

import jakarta.persistence.*;

@Entity
@Table
public class UserEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String email;

    public UserEntity() {

    }

    private UserEntity(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.address = builder.address;
        this.email = builder.email;
    }

    public static class Builder {
        private Integer id;
        private String name;
        private String address;
        private String email;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(this);
        }
    }

    public Integer getId() { return this.id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

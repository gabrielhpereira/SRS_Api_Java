package com.api.srs.entity.user;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class LogUserEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private Integer userId;

    @Column
    private String description;

    @Column
    private Date date;

    public LogUserEntity() {

    }

    private LogUserEntity(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.description = builder.description;
        this.date = builder.date;
    }

    public static class Builder {
        private Integer id;
        private Integer userId;
        private String description;
        private Date date;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }

        public LogUserEntity build() {
            return new LogUserEntity(this);
        }
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getDate() {
        return this.date;
    }
}

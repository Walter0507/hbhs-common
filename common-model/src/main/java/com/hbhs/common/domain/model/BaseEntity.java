package com.hbhs.common.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter@Setter@ToString
@NoArgsConstructor
public class BaseEntity {
    private Boolean isDeleted;
    private Date createTime;
    private Date updateTime;
}

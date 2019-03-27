package com.olasharing.footstone.repository.domain;

import lombok.Data;

import java.util.Date;

/**
 * AdminTag
 *
 * @author GW00168835
 */
@Data
public class AdminTag {

    private Long id;

    private String tagType;

    private String tagRef;

    private String tagCode;

    private String tagValue;

    private Date gmtModified;
}

package com.olasharing.footstone.server.service;

import com.olasharing.footstone.repository.domain.AdminTag;

import java.util.List;

/**
 * AdminTagService
 *
 * @author GW00168835
 */
public interface AdminTagService {

    /**
     * 插入
     *
     * @param adminTag
     * @return
     */
    void addAdminTag(AdminTag adminTag);

    /**
     * 查询
     *
     * @param tagType
     * @param tagRef
     * @param tagCode
     * @return
     */
    AdminTag getAdminTag(String tagType, String tagRef, String tagCode);


    /**
     * 查询
     *
     * @param tagType
     * @param tagRef
     * @return
     */
    List<AdminTag> getAdminTagList(String tagType, String tagRef);
}

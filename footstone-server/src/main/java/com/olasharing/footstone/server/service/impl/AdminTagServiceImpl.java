package com.olasharing.footstone.server.service.impl;

import com.olasharing.footstone.repository.domain.AdminTag;
import com.olasharing.footstone.repository.mapper.AdminTagMapper;
import com.olasharing.footstone.server.service.AdminTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * AdminTagServiceImpl
 *
 * @author GW00168835
 */
@Service
public class AdminTagServiceImpl implements AdminTagService {

    @Autowired
    private AdminTagMapper adminTagMapper;

    @Override
    public void addAdminTag(AdminTag adminTag) {
        adminTagMapper.insert(Collections.singletonList(adminTag));
    }

    @Override
    public AdminTag getAdminTag(String tagType, String tagRef, String tagCode) {
        return adminTagMapper.selectOneByCode(tagType, tagRef, tagCode);
    }

    @Override
    public List<AdminTag> getAdminTagList(String tagType, String tagRef) {
        return adminTagMapper.selectListByRef(tagType, tagRef);
    }
}

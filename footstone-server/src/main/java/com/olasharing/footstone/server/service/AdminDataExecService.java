package com.olasharing.footstone.server.service;

import com.olasharing.footstone.repository.domain.AdminDataExec;

import java.util.List;

/**
 * AdminDataExecService
 *
 * @author GW00168835
 */
public interface AdminDataExecService {

    /**
     * 插入
     *
     * @param adminDataExec
     */
    void insert(AdminDataExec adminDataExec);

    /**
     * 查询
     *
     * @param id
     * @param username
     * @return
     */
    AdminDataExec getAdminDataExec(Integer id, String username);


    /**
     * 查询
     *
     * @param username
     * @param execState
     * @param auditList
     * @return
     */
    List<AdminDataExec> getListByCondition(String username, Integer execState, boolean auditList);

    /**
     * 审核通过
     *
     * @param adminDataExec
     */
    void passDataExec(AdminDataExec adminDataExec);

    /**
     * 审核驳回
     *
     * @param adminDataExec
     */
    void rejectDataExec(AdminDataExec adminDataExec);

    /**
     * 执行脚本
     *
     * @param adminDataExec
     */
    void executeDataExec(AdminDataExec adminDataExec);
}

package com.olasharing.footstone.deploy.git;

import org.gitlab.api.models.GitlabMergeRequest;
import org.gitlab.api.models.GitlabUser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GitLabApiServiceTest {
    private String namespace;
    private String projectName;

    private GitLabApiService apiService;

    @Before
    public void setUp() {
        GitLabProperties properties = new GitLabProperties();
        properties.setHostUrl("https://gitlab.olafuwu.com");
        properties.setPrivateAccessToken("5xxWqyzzNdCPxGnCYCXY");
        apiService = new GitLabApiService();
        apiService.setProperties(properties);
        namespace = "ola-sharing-ms/ola-sharing-apps";
        projectName = "ola-sharing-app";
    }

    @Test
    public void testListBranch() throws IOException {
        for (String branchName : apiService.listBranch(namespace, projectName)) {
            System.out.println(branchName);
        }
    }

    @Test
    public void testCreateBranch() throws IOException {
        String newBranchName = "test/20190322-junit";
        apiService.createBranch(namespace, projectName, newBranchName);
        apiService.deleteBranch(namespace, projectName, newBranchName);
    }

    @Test
    public void testGetGroups() throws IOException {
        List<String> groupList = apiService.getGroups();
        System.out.println(Arrays.toString(groupList.toArray()));
    }

    @Test
    public void testCreateProject() throws IOException {
        apiService.createProject(namespace, projectName + "2", "测试gitlab-api");
        apiService.deleteProject(namespace, projectName + "2");
    }

    @Test
    public void testCreateMergeDevelopRequest() {
        String namespace = "liuyan";
        String projectName = "footstone-sample";
        String branchName = "feature/test-merge";
        // 选择审批人
        GitlabUser user = apiService.getMasterUser(namespace, projectName, "liuyan");
        // 提交合并申请
        GitlabMergeRequest gitlabMergeRequest = apiService.createMergeDevelopRequest(namespace, projectName, branchName, user);

        System.out.println("merge request: " + gitlabMergeRequest.getIid());
        apiService.acceptMergeDevelopRequest(namespace, projectName, gitlabMergeRequest.getIid(), "审查通过");
    }
}


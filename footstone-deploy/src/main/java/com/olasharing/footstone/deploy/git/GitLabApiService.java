package com.olasharing.footstone.deploy.git;

import com.google.common.collect.Lists;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.TokenType;
import org.gitlab.api.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * GitApiService
 *
 * @author GW00168835
 */
@Service
public class GitLabApiService {

    @Autowired
    private GitLabProperties properties;

    public void setProperties(GitLabProperties properties) {
        this.properties = properties;
    }

    public List<String> getGroups() throws IOException {
        GitlabAPI gitlabAPI = createGitlabAPI();

        List<GitlabGroup> groupList = gitlabAPI.getGroups();
        return groupList.stream().map((group) ->
                StringUtils.trimAllWhitespace(group.getFullName())
        ).collect(Collectors.toList());
    }

    public void createProject(String namespace, String projectName, String desc) throws IOException {
        GitlabAPI gitlabAPI = createGitlabAPI();
        GitlabGroup gitlabGroup = gitlabAPI.getGroup(namespace);
        GitlabProject project = gitlabAPI.createProjectForGroup(projectName, gitlabGroup, desc);
        // 创建develop分支
        gitlabAPI.createBranch(project, GitlabConstants.DEVELOP_BRANCH_NAME, GitlabConstants.MASTER_BRANCH_NAME);

        // master开发不能推送和合并
        gitlabAPI.protectBranchWithDeveloperOptions(project, GitlabConstants.MASTER_BRANCH_NAME, false, false);
        // develop开发能推送、但不能合并
        gitlabAPI.protectBranchWithDeveloperOptions(project, GitlabConstants.DEVELOP_BRANCH_NAME, true, false);
    }

    /**
     * 危险操作仅供测试使用
     *
     * @param namespace
     * @param projectName
     * @throws IOException
     */
    void deleteProject(String namespace, String projectName) throws IOException {
        GitlabAPI gitlabAPI = createGitlabAPI();
        GitlabProject gitlabProject = gitlabAPI.getProject(namespace, projectName);
        gitlabAPI.deleteProject(gitlabProject.getId());
    }

    public List<String> listBranch(String namespace, String projectName) {
        GitlabAPI gitlabAPI = createGitlabAPI();
        GitlabProject project = getProject(gitlabAPI, namespace, projectName);

        List<GitlabBranch> branchList = gitlabAPI.getBranches(project);
        return branchList.stream().map(GitlabBranch::getName).collect(Collectors.toList());
    }

    public void createBranch(String namespace, String projectName, String branchName) {
        GitlabAPI gitlabAPI = createGitlabAPI();
        GitlabProject project = getProject(gitlabAPI, namespace, projectName);

        try {
            gitlabAPI.createBranch(project, branchName, GitlabConstants.MASTER_BRANCH_NAME);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void deleteBranch(String namespace, String projectName, String branchName) {
        GitlabAPI gitlabAPI = createGitlabAPI();
        GitlabProject project = getProject(gitlabAPI, namespace, projectName);

        try {
            gitlabAPI.deleteBranch(project.getId(), branchName);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public List<GitlabUser> getMasterUsers(String namespace, String projectName) {
        GitlabAPI gitlabAPI = createGitlabAPI();
        List<GitlabUser> userList = Lists.newArrayList();
        GitlabProject project = getProject(gitlabAPI, namespace, projectName);
        List<GitlabProjectMember> memberList = null;

        try {
            memberList = gitlabAPI.getProjectMembers(project);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        if (memberList != null) {
            for (GitlabProjectMember member : memberList) {
                if (GitlabAccessLevel.Owner.equals(member.getAccessLevel())
                        || GitlabAccessLevel.Master.equals(member.getAccessLevel())) {
                    userList.add(member);
                }
            }
        }
        return userList;
    }

    public GitlabUser getMasterUser(String namespace, String projectName, String username) {
        GitlabAPI gitlabAPI = createGitlabAPI();
        List<GitlabUser> userList = getMasterUsers(namespace, projectName);
        if (userList != null) {
            for (GitlabUser user : userList) {
                if (Objects.equals(user.getUsername(), username)) {
                    return user;
                }
            }
        }
        return null;
    }

    public GitlabMergeRequest createMergeDevelopRequest(String namespace, String projectName, String branchName, GitlabUser assigneeUser) {
        String mergeTitle = "[pm] merge from " + branchName + " into develop";
        GitlabAPI gitlabAPI = createGitlabAPI();
        GitlabProject project = getProject(gitlabAPI, namespace, projectName);
        try {
            return gitlabAPI.createMergeRequest(project.getId(), branchName, GitlabConstants.DEVELOP_BRANCH_NAME,
                    assigneeUser.getId(), mergeTitle);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public GitlabMergeRequest acceptMergeDevelopRequest(String namespace, String projectName,
                                                        Integer mergeRequestIid, String mergeCommitMessage) {
        GitlabAPI gitlabAPI = createGitlabAPI();
        GitlabProject project = getProject(gitlabAPI, namespace, projectName);
        GitlabMergeRequest mergeRequest = null;
        try {
            mergeRequest = gitlabAPI.getMergeRequest(project.getId(), mergeRequestIid);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        try {
            return gitlabAPI.acceptMergeRequest(project.getId(), mergeRequestIid, mergeCommitMessage);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private GitlabAPI createGitlabAPI() {
        return GitlabAPI.connect(properties.getHostUrl(), properties.getPrivateAccessToken(), TokenType.PRIVATE_TOKEN);
    }

    private GitlabProject getProject(GitlabAPI gitlabAPI, String namespace, String projectName) {
        try {
            return gitlabAPI.getProject(namespace, projectName);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

package com.olasharing.footstone.deploy.maven;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class MavenProjectUtilsTest {

    MavenCommand command;

    @Before
    public void testUp() {
        command = new MavenCommand("D:\\apache-maven-3.5.4",
                "E:\\demo_projects\\trc-leaf",
                "clean");
    }

    @Test
    public void testGetProjects() throws IOException, InterruptedException {
        List<MavenProject> projectList = MavenProjectUtils.getProjects(command);
        for (MavenProject mavenProject : projectList) {
            System.out.println(mavenProject);
        }
    }
}

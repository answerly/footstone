package com.olasharing.footstone.deploy.maven;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class MavenCommandTest {

    MavenCommand command;

    @Before
    public void testUp() {
        command = new MavenCommand("D:\\apache-maven-3.5.4",
                "E:\\demo_projects\\trc-leaf",
                "clean");
    }

    @Test
    public void testExecute() throws IOException, InterruptedException {
        MavenResult mavenResult = command.execute();
        for (String buffer : mavenResult.getBuildLines()) {
            System.out.println(buffer);
        }
    }
}

package com.olasharing.footstone.deploy.maven;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author GW00168835
 */
public class MavenProjectUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(MavenProjectUtils.class);

    public static final int STATE_OTHER = 0;
    public static final int STATE_GROUP = 1;
    public static final int STATE_ARTIFACT_VERSION = 2;
    public static final int STATE_PACKAGING = 3;
    public static final String PATTERN_GROUP = "(-*)< (.*):(.*) >(-*)";
    public static final String PATTERN_PACKAGING = "(-*)\\[ ([a-z]*) \\](-*)";

    private MavenProjectUtils() {

    }

    public static String getDefaultM2Home() {
        String m2Home = System.getProperty("M2_HOME");
        if (m2Home == null) {
            m2Home = System.getProperty("MAVEN_HOME");
        }
        if (m2Home == null) {
            m2Home = "D:\\apache-maven-3.5.4";
        }
        return m2Home;
    }

    public static List<MavenProject> getProjects(MavenCommand command) throws IOException, InterruptedException {
        List<MavenProject> mavenProjects = Lists.newArrayList();
        MavenResult result = command.execute();
        if (!result.getSuccess()) {
            return Collections.emptyList();
        }
        int reactorState = STATE_OTHER;
        MavenProject mavenProject = null;
        for (String buffer : result.getBuildLines()) {
            buffer = buffer.substring(7).trim();
            if (buffer.matches(PATTERN_GROUP)) {
                mavenProject = new MavenProject();
                appendGroup(mavenProject, buffer);
                reactorState = STATE_GROUP;
                continue;
            }
            if (reactorState == STATE_GROUP) {
                appendArtifactVersion(mavenProject, buffer);
                reactorState = STATE_ARTIFACT_VERSION;
                continue;
            }
            if (reactorState == STATE_ARTIFACT_VERSION) {
                appendPackaging(mavenProject, buffer);
                mavenProjects.add(mavenProject);
                reactorState = STATE_PACKAGING;
                continue;
            }
        }
        return mavenProjects;
    }

    private static void appendGroup(MavenProject mavenProject, String buffer) {
        int group = 2;
        Matcher matcher = Pattern.compile(PATTERN_GROUP).matcher(buffer);
        if (matcher.find(group)) {
            mavenProject.setGroupId(matcher.group(group));
        }
    }

    private static void appendArtifactVersion(MavenProject mavenProject, String buffer) {
        String[] strList = buffer.split(" ");
        mavenProject.setArtifactId(strList[1]);
        mavenProject.setVersion(strList[2]);
    }

    private static void appendPackaging(MavenProject mavenProject, String buffer) {
        int group = 2;
        Matcher matcher = Pattern.compile(PATTERN_PACKAGING).matcher(buffer);
        if (matcher.find(group)) {
            mavenProject.setPackaging(matcher.group(group));
        }
    }
}

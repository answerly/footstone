package com.olasharing.footstone.deploy.maven;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * MavenClient
 *
 * @author GW00168835
 */
public class MavenCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(MavenCommand.class);

    public static final String SCRIPT_FORMAT;

    public static final Charset SCRIPT_CHARSET;

    static {
        String osName = System.getProperty("os.name");
        if (osName.contains("Window")) {
            SCRIPT_CHARSET = Charset.forName("GBK");
            SCRIPT_FORMAT = "cmd /c {0}/bin/mvn.cmd -f {1}/pom.xml {2}";
        } else {
            SCRIPT_CHARSET = Charset.forName("UTF-8");
            SCRIPT_FORMAT = "/bin/sh -c {0}/bin/mvn -f {1}/pom.xml {2}";
        }
    }

    private final String mavenHome;

    private final String projectDir;

    private final String args;

    public MavenCommand(String mavenHome, String projectDir, String args) {
        this.mavenHome = mavenHome;
        this.projectDir = projectDir;
        this.args = args;
    }

    public MavenResult execute() throws IOException, InterruptedException {
        MavenResult mavenResult = new MavenResult();
        String scriptText = MessageFormat.format(SCRIPT_FORMAT, mavenHome, projectDir, args);
        LOGGER.info("exec:{}", scriptText);
        Process process = Runtime.getRuntime().exec(scriptText);
        process.waitFor();

        if (process.getErrorStream().available() > 0) {
            mavenResult.setSuccess(false);
            mavenResult.setErrorLines(getExecuteMessage(process.getErrorStream()));
            return mavenResult;
        }
        mavenResult.setSuccess(true);
        mavenResult.setBuildLines(getExecuteMessage(process.getInputStream()));
        return mavenResult;
    }

    private static List<String> getExecuteMessage(InputStream is) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, SCRIPT_CHARSET))) {
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                lines.add(buffer);
            }
        }
        return lines;
    }

    public String getMavenHome() {
        return mavenHome;
    }

    public String getProjectDir() {
        return projectDir;
    }

    public String getArgs() {
        return args;
    }
}

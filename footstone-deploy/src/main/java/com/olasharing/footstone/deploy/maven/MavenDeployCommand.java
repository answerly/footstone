package com.olasharing.footstone.deploy.maven;

/**
 * @author GW00168835
 */
public class MavenDeployCommand extends MavenCommand {

    public MavenDeployCommand(String projectDir) {
        super(MavenProjectUtils.getDefaultM2Home(), projectDir, "clean deploy");
    }
}

package com.olasharing.footstone.deploy.maven;

/**
 * @author GW00168835
 */
public class MavenCleanCommand extends MavenCommand {

    public MavenCleanCommand(String projectDir) {
        super(MavenProjectUtils.getDefaultM2Home(), projectDir, "clean");
    }


}

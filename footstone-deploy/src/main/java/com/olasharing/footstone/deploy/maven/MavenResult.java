package com.olasharing.footstone.deploy.maven;

import java.util.List;

/**
 * MavenResult
 *
 * @author GW00168835
 */
public class MavenResult {

    private Boolean success;

    private List<String> errorLines;

    private List<String> buildLines;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<String> getErrorLines() {
        return errorLines;
    }

    public void setErrorLines(List<String> errorLines) {
        this.errorLines = errorLines;
    }

    public List<String> getBuildLines() {
        return buildLines;
    }

    public void setBuildLines(List<String> buildLines) {
        this.buildLines = buildLines;
    }

    public String getResult() {
        StringBuilder sb = new StringBuilder();
        List<String> lineList = success ? buildLines : errorLines;
        for (String line : lineList) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}

package com.csu.ecbackend.vo;

import java.util.Map;

public class AiMetricReviewRequest {

    private String projectName;
    private String metricSystem;
    private String context;
    private Map<String, Double> metrics;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getMetricSystem() {
        return metricSystem;
    }

    public void setMetricSystem(String metricSystem) {
        this.metricSystem = metricSystem;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Map<String, Double> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Double> metrics) {
        this.metrics = metrics;
    }
}

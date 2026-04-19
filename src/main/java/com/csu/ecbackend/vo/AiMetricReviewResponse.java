package com.csu.ecbackend.vo;

import java.util.List;

public class AiMetricReviewResponse {

    private String overallAssessment;
    private String riskLevel;
    private List<String> keyFindings;
    private List<String> suggestions;
    private String rawAnswer;

    public String getOverallAssessment() {
        return overallAssessment;
    }

    public void setOverallAssessment(String overallAssessment) {
        this.overallAssessment = overallAssessment;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public List<String> getKeyFindings() {
        return keyFindings;
    }

    public void setKeyFindings(List<String> keyFindings) {
        this.keyFindings = keyFindings;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public String getRawAnswer() {
        return rawAnswer;
    }

    public void setRawAnswer(String rawAnswer) {
        this.rawAnswer = rawAnswer;
    }
}

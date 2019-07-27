
package com.fataelislami.clola.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisionModels {

    private List<ResultVision> results = null;
    private Boolean has_more;
    private String rank_token;
    private String status;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<ResultVision> getResults() {
        return results;
    }

    public void setResults(List<ResultVision> results) {
        this.results = results;
    }

    public Boolean getHas_more() {
        return has_more;
    }

    public void setHas_more(Boolean has_more) {
        this.has_more = has_more;
    }

    public String getRank_token() {
        return rank_token;
    }

    public void setRank_token(String rank_token) {
        this.rank_token = rank_token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

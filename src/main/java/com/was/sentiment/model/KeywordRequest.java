package com.was.sentiment.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class KeywordRequest {
    private final int keywordId;
    private final String startDate;
    private final String endDate;

    @JsonCreator
    public KeywordRequest(@JsonProperty("keywordId") int keywordId, @JsonProperty("startDate") String startDate, @JsonProperty("endDate") String endDate){
        this.keywordId = keywordId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}

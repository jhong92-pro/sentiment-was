package com.was.sentiment.model;

import com.was.sentiment.entity.KeywordTag;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class HistoryDetailListResponse {
    private List<String> keywordTagList;
    private String keyword;
    private int keywordId;
}

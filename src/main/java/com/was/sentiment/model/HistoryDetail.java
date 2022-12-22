package com.was.sentiment.model;

import com.was.sentiment.entity.KeywordTag;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class HistoryDetail {
    private List<String> keywordTagList;
    private String keyword;
    private int keywordId;
}
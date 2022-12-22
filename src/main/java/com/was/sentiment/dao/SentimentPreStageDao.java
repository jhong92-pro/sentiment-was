package com.was.sentiment.dao;

import com.was.sentiment.code.SentimentCode;
import com.was.sentiment.entity.CrawlResult;
import com.was.sentiment.entity.SentimentPreStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SentimentPreStageDao extends JpaRepository<SentimentPreStage, Integer> {
    List<SentimentPreStage> findBySentimentPreStageIdIn(List<Integer> sentimentPreStageId);
    Optional<SentimentPreStage> findByKeywordIdAndSentimentCodeAndCrawlResult(Integer keywordId, SentimentCode sentimentCode, CrawlResult crawlResult);
    List<SentimentPreStage> findAllByKeywordId(Integer keywordId);
}


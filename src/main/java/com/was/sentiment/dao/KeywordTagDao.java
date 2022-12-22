package com.was.sentiment.dao;

import com.was.sentiment.entity.KeywordTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordTagDao extends JpaRepository<KeywordTag, Integer> {
    List<KeywordTag> findAllByKeywordId(int keywordId);
}
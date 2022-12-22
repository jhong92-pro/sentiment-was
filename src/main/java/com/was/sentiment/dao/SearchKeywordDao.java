package com.was.sentiment.dao;

import com.was.sentiment.entity.SearchKeyword;
import com.was.sentiment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface SearchKeywordDao extends JpaRepository<SearchKeyword, Integer>{
//    SearchKeyword save(SearchKeyword searchKeyword);
        Optional<SearchKeyword> findByKeywordId(int keywordId);
}

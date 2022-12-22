package com.was.sentiment.dao;

import com.was.sentiment.entity.UserChartHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserChartHistoryDao extends JpaRepository<UserChartHistory, Integer> {
    List<UserChartHistory> findAllByUserId(Integer userId);
//    void saveAll(List<UserChartHistory> userChartHistoryList);
}

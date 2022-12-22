package com.was.sentiment.service;

import com.was.sentiment.dao.*;
import com.was.sentiment.entity.*;
import com.was.sentiment.model.HistoryDetail;
import com.was.sentiment.model.KeywordRequest;
import com.was.sentiment.producer.ChartProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlService {
    private final UserDao userDao;
    private final UserChartHistoryDao userChartHistoryDao;
    private final SentimentPreStageDao sentimentPreStageDao;
    private final KeywordTagDao keywordTagDao;
    private final SearchKeywordDao searchKeywordDao;
    private final ChartProducer chartProducer;
    public List<HistoryDetail> loadChart(String email){
        User user = userDao.findByEmail(email).orElseThrow(RuntimeException::new);
        int userId = user.getUserId();
        List<UserChartHistory> userChartHistoryList = userChartHistoryDao.findAllByUserId(userId);
        List<HistoryDetail> historyDetailList = new ArrayList<>();
        for(UserChartHistory userChartHistory:userChartHistoryList){
            int keywordId = userChartHistory.getKeywordId();
            String keyword = searchKeywordDao.findByKeywordId(keywordId)
                    .orElseThrow(RuntimeException::new).getKeyword(); // TODO: Foreign Key 묶기
            List<String> keywordTagList = keywordTagDao.findAllByKeywordId(keywordId)
                    .stream()
                    .map(KeywordTag::getTagName)
                    .toList();
            historyDetailList.add(
                    HistoryDetail.builder()
                            .keyword(keyword)
                            .keywordId(keywordId)
                            .keywordTagList(keywordTagList)
                            .build()
            );

        }
        return historyDetailList;
    }

    public HashMap<String, Integer> loadChartDetail(Integer keywordId) {
        List<SentimentPreStage> sentimentPreStageList = sentimentPreStageDao.findAllByKeywordId(keywordId);
        HashMap<String, Integer> finishCntMap = new HashMap<>();
        finishCntMap.put("finishCnt",0);
        finishCntMap.put("notFinishCnt",0);
        sentimentPreStageList.forEach(
                sentimentPreStage -> {
                    if (sentimentPreStage.isFinished()){
                        finishCntMap.put("finishCnt", finishCntMap.get("finishCnt")+1);
                    }else{
                        finishCntMap.put("notFinishCnt", finishCntMap.get("notFinishCnt")+1);
                    }
                }
        );
        return finishCntMap;
    }

    @Transactional
    public void addChart(String keyword, List<String> tagList) {
        SearchKeyword searchKeyword = SearchKeyword.builder()
                .keyword(keyword)
                .build();
        searchKeywordDao.save(searchKeyword);
        List<KeywordTag> keywordTagList = tagList.stream().map(
                tag -> KeywordTag.builder()
                        .keywordId(searchKeyword.getKeywordId())
                        .tagName(tag)
                        .build()
        ).toList();
        keywordTagDao.saveAll(keywordTagList);
        UserChartHistory userChartHistory = UserChartHistory.builder()
                .userId(6)
                .startDate("19000101")
                .endDate("99991212")
                .keywordId(searchKeyword.getKeywordId())
                .build();
        userChartHistoryDao.save(userChartHistory);
        KeywordRequest keywordRequest = KeywordRequest.builder()
                .keywordId(searchKeyword.getKeywordId())
                .startDate("19000101")
                .endDate("99991212")
                .build();
        chartProducer.sync("ilbe", keywordRequest);
    }
}

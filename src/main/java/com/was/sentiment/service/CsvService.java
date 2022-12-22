package com.was.sentiment.service;

import com.was.sentiment.code.SentimentCode;
import com.was.sentiment.dao.*;
import com.was.sentiment.entity.*;
import com.was.sentiment.producer.ChartProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvService {
    private final UserDao userDao;
    private final UserChartHistoryDao userChartHistoryDao;
    private final SentimentPreStageDao sentimentPreStageDao;
    private final KeywordTagDao keywordTagDao;
    private final SearchKeywordDao searchKeywordDao;
    private final ChartProducer chartProducer;

    public void writeCsv(HttpServletResponse response, Writer writer, int keywordId) throws IOException {
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        writeHead(keywordId, csvPrinter, response);
        HashMap<String,HashMap<SentimentCode,Integer>> sentimentConcat = new HashMap<>();

        List<SentimentPreStage> sentimentPreStageDaoList = sentimentPreStageDao.findAllByKeywordId(keywordId);
        sentimentPreStageDaoList.forEach(
                sentimentPreStage -> {
                    SentimentCode sentimentCode = sentimentPreStage.getSentimentCode();
                    int cnt = sentimentPreStage.getTotal();
                    String ymd;
                    if(sentimentPreStage.getCrawlResult()==null){
                        ymd = "99991212";
                    }else{
                        ymd = sentimentPreStage.getCrawlResult().getYmd();
                    }
                    if(!sentimentConcat.containsKey(ymd)){
                        HashMap<SentimentCode,Integer> totalByYmd = new HashMap<>();
                        totalByYmd.put(SentimentCode.NEUTRAL,0);
                        totalByYmd.put(SentimentCode.POSITIVE,0);
                        totalByYmd.put(SentimentCode.NEGATIVE,0);
                        sentimentConcat.put(ymd, totalByYmd);
                    }
                    HashMap<SentimentCode,Integer> totalByYmd = sentimentConcat.get(ymd);
                    totalByYmd.put(sentimentCode, totalByYmd.get(sentimentCode)+cnt);
                }
        );
        ArrayList<String> sortedkeySet = new ArrayList<>(sentimentConcat.keySet());
        Collections.sort(sortedkeySet);
        sortedkeySet.forEach(
                ymd -> {
                    HashMap<SentimentCode,Integer> totalByYmd = sentimentConcat.get(ymd);
                    int neutral = totalByYmd.get(SentimentCode.NEUTRAL);
                    int negative = totalByYmd.get(SentimentCode.NEGATIVE);
                    int positive = totalByYmd.get(SentimentCode.POSITIVE);
                    try {
                        csvPrinter.printRecord(ymd, positive, neutral, negative);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        
        //TODO: prestage 삭제하고 파일 서버 로컬 경로에 저장

    }

    private void writeHead(int keywordId, CSVPrinter csvPrinter,HttpServletResponse response) throws IOException {
        String keyword = searchKeywordDao.findByKeywordId(keywordId)
                .orElseThrow(RuntimeException::new).getKeyword();
        List<String> keywordTagList = keywordTagDao.findAllByKeywordId(keywordId)
                        .stream()
                        .map(KeywordTag::getTagName)
                        .toList();
        response.setHeader("Content-disposition", "attachment;filename=" + keyword+".csv");
        csvPrinter.printRecord("키워드 : "+keyword);
        csvPrinter.printRecord("동의어 : "+String.join(", ", keywordTagList));
        csvPrinter.printRecord();
        csvPrinter.printRecord("Date", "Positive", "Neutral", "Negative");
    }
}

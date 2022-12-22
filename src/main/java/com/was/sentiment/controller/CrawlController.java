package com.was.sentiment.controller;
import com.was.sentiment.entity.UserChartHistory;
import com.was.sentiment.model.HistoryDetail;
import com.was.sentiment.service.CrawlService;
import com.was.sentiment.service.CsvService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class CrawlController {

    private final CrawlService crawlService;
    private final CsvService csvService;

    @GetMapping("/search/history/{email}")
        public List<HistoryDetail> loadHistory(@PathVariable String email){
            return crawlService.loadChart(email);
        }

    @GetMapping("/search/history/detail/{keywordId}")
    public HashMap<String, Integer> loadHistoryDetail(@PathVariable Integer keywordId){
        return crawlService.loadChartDetail(keywordId);
    }

    @GetMapping("/make/loop")
    public String addChart(@RequestParam List<String> tagList,
                           @RequestParam String keyword){
        log.info(Arrays.toString(tagList.toArray()));
        log.info(keyword);
        crawlService.addChart(keyword, tagList);
        return "요청을 성공했습니다";
    }

    @GetMapping("/download/csv/{keywordId}")
    public void addChart(@PathVariable int keywordId,
                           HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/csv; charset=UTF-8");

        csvService.writeCsv(response, response.getWriter(), keywordId);
    }
}

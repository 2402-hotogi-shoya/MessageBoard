package com.example.forum.service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.ReportRepository;
import com.example.forum.repository.entity.Comment;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    /*
     * レコード全件取得処理
     */
    public List<ReportForm> findAllReport() {
        List<Report> results = reportRepository.findAll();
        List<ReportForm> reports = setReportForm(results);
        return reports;
    }
    /*
     * DBから取得したデータをFormに設定
     */
    private List<ReportForm> setReportForm(List<Report> results) {
        List<ReportForm> reports = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            ReportForm report = new ReportForm();
            Report result = results.get(i);
            report.setId(result.getId());
            report.setContent(result.getContent());
            reports.add(report);
        }
        return reports;
    }

    /*
     * レコード追加
     */
    public void saveReport(ReportForm reqReport) {
        Report saveReport = setReportEntity(reqReport);
        reportRepository.save(saveReport);
    }

    /*
     * レコード削除
     */
    public void deleteReport(int id) {
        reportRepository.deleteById(id);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Report setReportEntity(ReportForm reqReport) {
        // 現在日時を取得
        LocalDateTime nowDate = LocalDateTime.now();

        // 表示形式を指定
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS"); // ①
        String formatNowDate = dtf1.format(nowDate); // ②

        Report report = new Report();
        report.setId(reqReport.getId());
        report.setContent(reqReport.getContent());
        report.setUpdated_date(LocalDateTime.parse(formatNowDate, dtf1));
        report.setCreated_date(LocalDateTime.parse(formatNowDate, dtf1));
        return report;
    }

    /*
     * レコード1件取得処理
     */
    public Report selectReport(Integer id) {
        Report result = reportRepository.findById(id).orElse(null);
        return result;
    }

    /*
     * 投稿編集処理
     */
    public void updateReportEntity(ReportForm reqReport) {

        Report saveReport = setReportEntity(reqReport);
        reportRepository.save(saveReport);
    }
}

package com.example.forum.service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    /*
     * レコード全件取得処理
     */
    public List<CommentForm> findAllReport() {
        List<Comment> results = commentRepository.findAllByOrderByUpdatedDateDesc();
        List<CommentForm> reports = setCommentForm(results);
        return reports;
    }
    /*
     * DBから取得したデータをFormに設定
     */
    private List<CommentForm> setCommentForm(List<Comment> results) {
        List<CommentForm> comments = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            CommentForm comment = new CommentForm();
            Comment result = results.get(i);
            comment.setId(result.getId());
            comment.setMessage_id(result.getMessage_id());
            comment.setText(result.getText());
            comments.add(comment);
        }
        return comments;
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Comment setCommentEntity(CommentForm reqReport) {
        // 現在日時を取得
        LocalDateTime nowDate = LocalDateTime.now();

        // 表示形式を指定
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS"); // ①
        String formatNowDate = dtf1.format(nowDate); // ②

        Comment comment = new Comment();
        comment.setText(reqReport.getText());
        comment.setUpdatedDate(LocalDateTime.parse(formatNowDate, dtf1));
        comment.setCreatedDate(LocalDateTime.parse(formatNowDate, dtf1));
        return comment;
    }

    /*
     * コメント追加処理
     */
    public void addCommentEntity(int id, CommentForm reqReport) {
        Comment saveReport = setCommentEntity(reqReport);
        saveReport.setMessage_id(id);
        commentRepository.save(saveReport);
    }

    /*
     * レコード削除
     */
    public void deleteComment(int id) {
        commentRepository.deleteById(id);
    }

    /*
     * レコード1件取得処理
     */
    public Comment selectReport(Integer id) {
        Comment result = commentRepository.findById(id).orElse(null);
        return result;
    }

    /*
     * 投稿編集処理
     */
    public void updateReportEntity(CommentForm reqReport) {
        Comment saveReport = setCommentEntity(reqReport);
        saveReport.setId(reqReport.getId());
        saveReport.setMessage_id(reqReport.getMessage_id());
        commentRepository.save(saveReport);
    }

    public Comment findById(int id) {
        Comment result = commentRepository.findById(id).orElse(null);
        return result;
    }
}

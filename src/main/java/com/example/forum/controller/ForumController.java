package com.example.forum.controller;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.entity.Comment;
import com.example.forum.repository.entity.Report;
import com.example.forum.service.CommentService;
import com.example.forum.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ForumController {
    @Autowired
    ReportService reportService;

    @Autowired
    CommentService commentService;
    /*
     * 投稿内容表示処理
     */
    @GetMapping
    public ModelAndView top() {
        ModelAndView mav = new ModelAndView();
        CommentForm commentForm = new CommentForm();
        // 投稿を全件取得
        List<ReportForm> contentData = reportService.findAllReport();
        List<CommentForm> commentData = commentService.findAllReport();
        // 画面遷移先を指定
        mav.setViewName("/top");
        // 投稿データオブジェクトを保管
        mav.addObject("contents", contentData);
        mav.addObject("commentForm", commentForm);
        mav.addObject("comments", commentData);
        return mav;
    }

    /*
     * 新規投稿画面表示
     */
    @GetMapping("/new")
    public ModelAndView newContent() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        ReportForm reportForm = new ReportForm();
        // 画面遷移先を指定
        mav.setViewName("/new");
        // 準備した空のFormを保管
        mav.addObject("formModel", reportForm);
        return mav;
    }

    /*
     * 新規投稿処理
     */
    @PostMapping("/add")
    public ModelAndView addContent(@ModelAttribute("formModel") ReportForm reportForm){
        // 投稿をテーブルに格納
        reportService.saveReport(reportForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * コメント新規投稿処理
     */
    @PostMapping("/comment/{id}")
    public ModelAndView addCommentContent(@PathVariable int id,
                                          @ModelAttribute("commentForm") CommentForm commentForm){

        // 投稿をテーブルに格納
        commentService.addCommentEntity(id, commentForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * 投稿削除処理
     */
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteContent(@PathVariable int id){
        // 投稿をテーブルで削除
        reportService.deleteReport(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * 編集表示処理
     */
    @GetMapping("/edit/{id}")
    public ModelAndView editPage(@PathVariable Integer id) {

        ModelAndView mav = new ModelAndView();
        //編集するコメントを取得
        Report comment = reportService.selectReport(id);
        //編集するコメントをセット
        mav.addObject("formModel", comment);
        //画面遷移先を指定
        mav.setViewName("/edit");

        return mav;
    }

    /*
     * 投稿編集処理
     */
    @PutMapping("/update/{id}")
    public ModelAndView editContent(@PathVariable Integer id,
                                    @ModelAttribute("formModel") ReportForm reportForm) {

        reportForm.setId(id);
        //編集するコメントをセット
        reportService.updateReportEntity(reportForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * コメント新規投稿処理
     */
    @GetMapping("/comment/edit/{id}")
    public ModelAndView editCommentContent(@PathVariable int id,
                                          @ModelAttribute("commentForm") CommentForm commentForm){

        ModelAndView mav = new ModelAndView();
        //編集するコメントを取得
        Comment comment = commentService.selectReport(id);
        //編集するコメントをセット
        mav.addObject("commentForm", comment);
        //画面遷移先を指定
        mav.setViewName("/commentEdit");
        return mav;
    }

    /*
     * コメント新規投稿処理
     */
    @DeleteMapping("/comment/delete/{id}")
    public ModelAndView deleteCommentContent(@PathVariable int id) {

        // 投稿をテーブルに格納
        commentService.deleteComment(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * コメント新規投稿処理
     */
    @PutMapping("/comment/update/{id}")
    public ModelAndView updateCommentContent(@PathVariable int id,
                                             @ModelAttribute("commentForm") CommentForm reportForm) {

        reportForm.setId(id);
        //編集するコメントをセット
        commentService.updateReportEntity(reportForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }
}

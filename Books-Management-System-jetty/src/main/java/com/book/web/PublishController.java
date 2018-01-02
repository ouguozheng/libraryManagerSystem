package com.book.web;

import com.book.domain.Publish;
import com.book.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 10394 on 2017/12/20.
 */
@Controller
public class PublishController {

    private PublishService publishService;

    @Autowired
    public void setPublishService(PublishService publishService) {
        this.publishService = publishService;
    }

    @RequestMapping(value = "admin_publish_add.html")
    public ModelAndView toAdminPublishAdd() {
        ModelAndView modelAndView = new ModelAndView("admin_publish_add");
        int maxId = publishService.getMaxPublishId();
        modelAndView.addObject("maxPublishId", maxId + 1);
        return modelAndView;
    }

    @RequestMapping(value = "admin_publish_add_do.html")
    public String toAdminPublishAddDo(Publish publish, RedirectAttributes redirectAttributes) {
        if (publish == null) {
            System.out.println("publish null");
            redirectAttributes.addAttribute("succ", "添加出版社信息失败！");
            return "redirect:/admin_publish_list.html";
        }
        System.out.println("publishinfo " + publish.getId() + " name" + publish.getName());
        publishService.addPublish(publish);
        return "redirect:/admin_publish_list.html";
    }

    @RequestMapping("admin_publish_list.html")
    public ModelAndView toAdminPublishList() {
        ModelAndView modelAndView = new ModelAndView("admin_publish_list");
        List<Publish> list = publishService.selectAllPublish();
        modelAndView.addObject("list", list);
        return modelAndView;
    }

    @RequestMapping("publish_detail")
    public ModelAndView getPublishDetail(HttpServletRequest httpServletRequest) {
        ModelAndView modelAndView = new ModelAndView("admin_publish_detail");
        int id = Integer.parseInt(httpServletRequest.getParameter("id"));
        Publish publish = publishService.getPublishById(id);
        modelAndView.addObject("detail", publish);
        return modelAndView;
    }

    @RequestMapping("admin_publish_update")
    public ModelAndView adminUpdatePublish() {
        ModelAndView modelAndView = new ModelAndView("admin_publish_edit");
        return modelAndView;
    }

    @RequestMapping(value = "admin_publish_del.html")
    public String toAdminPublishdDelDo(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        Integer id = Integer.parseInt(httpServletRequest.getParameter("publishId"));
        if (id == null) {
            System.out.println("id null");
            redirectAttributes.addAttribute("succ", "删除出版社信息失败！");
            return "redirect:/admin_publish_list.html";
        }
        Publish publish = publishService.getPublishById(id);
        if (publish == null) {
            System.out.println("publish null");
            redirectAttributes.addAttribute("succ", "删除出版社信息失败！");
            return "redirect:/admin_publish_list.html";
        }
        System.out.println("publishinfo " + publish.getId() + " name" + publish.getName());
        int res = publishService.deletePublish(publish.getId());
        if(res > 0){
            System.out.println(" mapper publish res success");
            redirectAttributes.addAttribute("succ", "删除出版社信息成功！");
            return "redirect:/admin_publish_list.html";
        }else{
            System.out.println(" mapper publish res failed");
            redirectAttributes.addAttribute("succ", "删除出版社信息失败！");
            return "redirect:/admin_publish_list.html";
        }
    }
}
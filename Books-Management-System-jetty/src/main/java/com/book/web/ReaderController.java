package com.book.web;

import com.book.domain.ReaderAllInfo;
import com.book.domain.ReaderCard;
import com.book.domain.ReaderInfo;
import com.book.domain.TimeFm;
import com.book.service.LoginService;
import com.book.service.ReaderCardService;
import com.book.service.ReaderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ReaderController {

    private ReaderInfoService readerInfoService;

    @Autowired
    public void setReaderInfoService(ReaderInfoService readerInfoService) {
        this.readerInfoService = readerInfoService;
    }

    private LoginService loginService;


    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    private ReaderCardService readerCardService;

    @Autowired
    public void setReaderCardService(ReaderCardService readerCardService) {
        this.readerCardService = readerCardService;
    }

    @RequestMapping("allreaders.html")
    public ModelAndView allBooks() {
        List<ReaderInfo> readers = readerInfoService.getAllReaderInfos();
        ModelAndView modelAndView = new ModelAndView("admin_readers");
        List<ReaderCard> cards = readerCardService.getAllReaderCard();
        modelAndView.addObject("cards",cards);
        modelAndView.addObject("readers", readers);
        return modelAndView;
    }

    @RequestMapping("reader_delete.html")
    public String readerDelete(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        int readerId = Integer.parseInt(request.getParameter("readerId"));
        boolean success = readerInfoService.deleteReaderInfo(readerId);

        if (success) {
            redirectAttributes.addFlashAttribute("succ", "删除成功！");
            return "redirect:/allreaders.html";
        } else {
            redirectAttributes.addFlashAttribute("error", "删除失败！");
            return "redirect:/allreaders.html";
        }
    }

    @RequestMapping("/reader_info.html")
    public ModelAndView toReaderInfo(HttpServletRequest request) {
        ReaderCard readerCard = (ReaderCard) request.getSession().getAttribute("readerCard");
        ReaderInfo readerInfo = readerInfoService.getReaderInfo(readerCard.getReaderId());
        ModelAndView modelAndView = new ModelAndView("reader_info");
        modelAndView.addObject("readerinfo", readerInfo);
        System.out.println(readerInfo.toString());
        return modelAndView;
    }

    @RequestMapping("reader_edit.html")
    public ModelAndView readerInfoEdit(HttpServletRequest request) {
        int readerId = Integer.parseInt(request.getParameter("readerId"));
        ReaderInfo readerInfo = readerInfoService.getReaderInfo(readerId);
        ModelAndView modelAndView = new ModelAndView("admin_reader_edit");
        modelAndView.addObject("readerInfo", readerInfo);
        return modelAndView;
    }

    @RequestMapping("reader_edit_do.html")
    public String readerInfoEditDo(HttpServletRequest request,ReaderAllInfo readerAllInfo,RedirectAttributes redirectAttributes) {
        Integer readerId = Integer.parseInt(request.getParameter("id"));
        if(readerId != readerAllInfo.getReaderId()||readerId == null){
            System.out.println("error");
            redirectAttributes.addFlashAttribute("error", "读者id有误！");
            return "redirect:/allreaders.html";
        }
        ReaderCard readerCard = loginService.findReaderCardByUserId(readerId);
        if (readerCard != null) {
                readerCard.setName(readerAllInfo.getName());
                readerCard.setCardState(readerAllInfo.getCardState());
                readerCard.setCardType(readerAllInfo.getCardType());
                boolean succo = readerCardService.updateReaderCard(readerCard);
                ReaderInfo readerInfo = new ReaderInfo();
                readerInfo.setAddress(readerAllInfo.getAddress());
                try{
                    TimeFm.dateFormat.format(readerAllInfo.getBirth());
                }catch (Exception e){
                    System.out.println("format birth failed");
                    redirectAttributes.addFlashAttribute("error", "读者生日输入有误！");
                    return "redirect:/allreaders.html";
                }
                readerInfo.setBirth(readerAllInfo.getBirth());
                readerInfo.setName(readerAllInfo.getName());
                readerInfo.setReaderId(readerId);
                readerInfo.setTelcode(readerAllInfo.getTelcode());
                readerInfo.setSex(readerAllInfo.getSex());
                boolean succ = readerInfoService.updateReaderInfo(readerInfo);
                if (succo && succ) {
                    System.out.println("读者信息修改成功！");
                    redirectAttributes.addFlashAttribute("succ", "读者信息修改成功！");
                    return "redirect:/allreaders.html";
                } else {
                    System.out.println("读者信息修改失败！");
                    redirectAttributes.addFlashAttribute("error", "读者信息修改失败！");
                    return "redirect:/allreaders.html";
                }
            }
            else{
            System.out.println("读者信息修改成功！ 读者不存在！");
            redirectAttributes.addFlashAttribute("error", "读者信息修改失败！");
            return "redirect:/allreaders.html";
        }
    }
    @RequestMapping("reader_add.html")
    public ModelAndView readerInfoAdd() {
        ModelAndView modelAndView = new ModelAndView("admin_reader_add");
        int maxId = readerCardService.getReaderCardMaxId();
        modelAndView.addObject("maxID",maxId + 1);
        return modelAndView;

    }

    //用户功能--进入修改密码页面
    @RequestMapping("reader_repasswd.html")
    public ModelAndView readerRePasswd() {
        ModelAndView modelAndView = new ModelAndView("reader_repasswd");
        return modelAndView;
    }

    //用户功能--修改密码执行
    @RequestMapping("reader_repasswd_do.html")
    public String readerRePasswdDo(HttpServletRequest request, String oldPasswd, String newPasswd, String reNewPasswd, RedirectAttributesModelMap map) {
        ReaderCard readerCard = (ReaderCard) request.getSession().getAttribute("readerCard");
        int readerId = readerCard.getReaderId();
        String passwd = readerCard.getPasswd();
        System.out.println(readerCard.toString());
        if (readerCard != null) {
            if (newPasswd.equals(reNewPasswd)) {
                if (passwd.equals(oldPasswd)) {
                    readerCard.setPasswd(newPasswd);
                    boolean succ = readerCardService.updateReaderCard(readerCard);
                    if (succ) {
                        ReaderCard readerCardNew = loginService.findReaderCardByUserId(readerId);
                        request.getSession().setAttribute("readercard", readerCardNew);
                        map.addFlashAttribute("succ", "密码修改成功！请重新登陆！");
                        return "redirect:/login.html";
                    } else {
                        map.addFlashAttribute("succ", "密码修改失败！");
                        return "redirect:/reader_repasswd.html";
                    }

                } else {
                    map.addFlashAttribute("error", "修改失败,原密码错误");
                    return "redirect:/reader_repasswd.html";
                }
            } else {
                map.addFlashAttribute("error", "修改失败,两次输入的新密码不相同");
                return "redirect:/reader_repasswd.html";
            }
        }
        map.addFlashAttribute("succ", "密码修改失败！");
        return "redirect:/reader_repasswd.html";
    }

    //管理员功能--读者信息添加
    @RequestMapping("reader_add_do.html")
    public String readerInfoAddDo(ReaderCommed readerCommed, RedirectAttributes redirectAttributes) {
        if(readerCommed == null){
            System.out.println("reader add 插入 null");
            redirectAttributes.addFlashAttribute("succ", "添加读者信息失败！");
            return "redirect:/allreaders.html";
        }
        System.out.println("type" + readerCommed.getType() + "state" + readerCommed.getState());
        ReaderInfo readerInfo = new ReaderInfo();
        readerInfo.setAddress(readerCommed.getAddress());
        try{
            readerInfo.setBirth(TimeFm.dateFormat.format(readerCommed.getBirth()));
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("succ", "生日填写出错!");
            System.out.println("date fromat error");
            return "redirect:/allreaders.html";
        }
        readerInfo.setName(readerCommed.getName());
        readerInfo.setReaderId(readerCommed.getReaderId());
        readerInfo.setTelcode(readerCommed.getTelcode());
        readerInfo.setSex(readerCommed.getSex());
        ReaderCard readerCard = new ReaderCard();
        readerCard.setReaderId(readerCommed.getReaderId());
        readerCard.setPasswd("111111");
        readerCard.setCardType(readerCommed.getType());
        readerCard.setCardState(readerCommed.getState());
        readerCard.setName(readerCommed.getName());
        boolean succ = readerInfoService.addReaderInfo(readerInfo);
        boolean succc = readerCardService.addReaderCard(readerCard);
        if (succ && succc) {
            redirectAttributes.addFlashAttribute("succ", "添加读者信息成功！");
            return "redirect:/allreaders.html";
        } else {
            redirectAttributes.addFlashAttribute("succ", "添加读者信息失败！");
            return "redirect:/allreaders.html";
        }
    }

    //读者功能--读者信息修改
    @RequestMapping("reader_info_edit.html")
    public ModelAndView readerInfoEditReader(HttpServletRequest request) {
        ReaderCard readerCard = (ReaderCard) request.getSession().getAttribute("readerCard");
        ReaderInfo readerInfo = readerInfoService.getReaderInfo(readerCard.getReaderId());
        ModelAndView modelAndView = new ModelAndView("reader_info_edit");
        modelAndView.addObject("readerinfo", readerInfo);
        return modelAndView;

    }

    @RequestMapping("reader_edit_do_r.html")
    public String readerInfoEditDoReader(HttpServletRequest request, String name, String sex, String birth, String address, String telcode, RedirectAttributes redirectAttributes) {
        ReaderCard readerCard = (ReaderCard) request.getSession().getAttribute("readerCard");
        if (!readerCard.getName().equals(name)) {
            boolean succo = readerCardService.updateReaderCard(readerCard);
            ReaderInfo readerInfo = new ReaderInfo();
            readerInfo.setAddress(address);
            readerInfo.setBirth(birth);
            readerInfo.setName(name);
            readerInfo.setReaderId(readerCard.getReaderId());
            readerInfo.setTelcode(telcode);
            readerInfo.setSex(sex);

            boolean succ = readerInfoService.updateReaderInfo(readerInfo);
            if (succ && succo) {
                ReaderCard readerCardNew = loginService.findReaderCardByUserId(readerCard.getReaderId());
                request.getSession().setAttribute("readercard", readerCardNew);
                redirectAttributes.addFlashAttribute("succ", "信息修改成功！");
                return "redirect:/reader_info.html";
            } else {
                redirectAttributes.addFlashAttribute("error", "信息修改失败！");
                return "redirect:/reader_info.html";
            }


        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date nbirth = new Date();
            try {
                java.util.Date date = sdf.parse(birth);
                nbirth = date;
            } catch (ParseException e) {
                e.printStackTrace();
            }

            ReaderInfo readerInfo = new ReaderInfo();
            readerInfo.setAddress(address);
            readerInfo.setBirth(TimeFm.dateFormat.format(nbirth));
            readerInfo.setName(name);
            readerInfo.setReaderId(readerCard.getReaderId());
            readerInfo.setTelcode(telcode);
            readerInfo.setSex(sex);

            boolean succ = readerInfoService.updateReaderInfo(readerInfo);
            if (succ) {
                ReaderCard readerCardNew = loginService.findReaderCardByUserId(readerCard.getReaderId());
                request.getSession().setAttribute("readercard", readerCardNew);
                redirectAttributes.addFlashAttribute("succ", "信息修改成功！");
                return "redirect:/reader_info.html";
            } else {
                redirectAttributes.addFlashAttribute("error", "信息修改失败！");
                return "redirect:/reader_info.html";
            }
        }


    }
}

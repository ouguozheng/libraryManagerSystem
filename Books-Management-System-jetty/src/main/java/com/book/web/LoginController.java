package com.book.web;

import com.book.dao.*;
import com.book.domain.*;
import com.book.service.LoginService;
import com.book.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//标注为一个Spring mvc的Controller
@Controller
public class LoginController {

    @Autowired
    private org.mybatis.spring.SqlSessionTemplate sqlSession;
    private LoginService loginService;
    @Autowired
    private ReserveService reserveService;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private PublishMapper publishMapper;
    @Autowired
    private ReserveMapper reserveMapper;
    @Autowired
    private ReaderInfoMapper readerInfoMapper;
    @Autowired
    private ReaderCardMapper readerCardMapper;
    @Autowired
    private LendMapper lenMapper;


    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    //负责处理login.html请求
    @RequestMapping(value = {"/", "/login.html"})
    public String toLogin(HttpServletRequest request) {
        request.getSession().invalidate();
        return "index";
    }

    @RequestMapping("/logout.html")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login.html";
    }


    //负责处理loginCheck.html请求
    //请求参数会根据参数名称默认契约自动绑定到相应方法的入参中
    @RequestMapping(value = "/api/loginCheck", method = RequestMethod.POST)
    public @ResponseBody
    Object loginCheck(HttpServletRequest request) {
      /*  List<Book> list1 = bookMapper.selectAllBook();
        List<Publish> list2 = publishMapper.selectAllPublish();
        List<ReaderCard> list3 = readerCardMapper.selectAllReaderCard();
        List<ReaderInfo> list4 = readerInfoMapper.selectAllReaderInfo();
        List<Reserve> list5 = reserveMapper.selectAllReserve();
        List<Lend> list6 = lenMapper.selectAllLend();
        System.out.println(list1.size() + "|" + list2.size() + "|" + list3.size() + "|" + list4.size() + "|" + list5.size() + "|" + list6.size());*/
      /*Long a = Long.parseLong("10000001");
      Book book = bookMapper.selectBookByBookId(a);
        System.out.println(book.toString());
        Long maxId = bookMapper.selectMaxBookId();
        System.out.println("maxid " + maxId);
        book.setName("下雨天的故事");
        Integer c = bookMapper.updateBooK(book);
        System.out.println("update" + (c > 0));
        book.setBookId(maxId + 1);
        Integer b = bookMapper.insertBook(book);
        System.out.println("insert" + (b >0));

        System.out.println("readerId:" + readerId);
        Reserve reserve = new Reserve();
        System.out.println("test");
        reserve.setReader_id(readerId);
        reserve.setVaild(1);
        reserve.setBook_id(10000002);
        reserve.setReserve_time(TimeFm.dateFormat.format(new Date()));
        System.out.println(reserve.toString());
        boolean r = reserveService.addNewReserve(reserve);
        System.out.println(r);*/
       /* System.out.println("get allReader");
        List<ReaderCard> list = loginService.getAllReaderCard();
        for(int i = 0;i <list.size();i++){
            System.out.println("id:" + list.get(i).getReaderId() + "pass:" + list.get(i).getPasswd());
        }
        System.out.println("pass:" + passwd);*/
       /* List<ReaderCard> li = loginService.getAllReaderCard();
        for (int i = 0; i < li.size();i++ ){
            System.out.println(li.get(i).toString());
        }
        List<Book> li2 = bookMapper.selectAllBook();
        for(int j= 0;j < li2.size();j++){
            System.out.println("book id:" + li2.get(j).getBookId());
        }
        List<ReaderInfo> li3 = readerInfoMapper.selectAllReaderInfo();
        for(int i = 0;i < li3.size(); i++){
            System.out.println("readInfo" + li3.get(i).getReaderId() + "  " + li3.get(i).getName());
        }
        List<Publish> li4 = publishMapper.selectAllPublish();
        for(int i = 0;i < li4.size();i++){
            System.out.println("publish id" + li4.get(i).getId());
        }
        List<Reserve> li5 = reserveMapper.selectAllReserve();
        for(int i = 0;i < li5.size();i++){
            System.out.println("res" + li5.get(i).getReserveId());
        }*/

        int readerId = Integer.parseInt(request.getParameter("readerId"));
        System.out.println("get readerid" + readerId);
        String passwd = request.getParameter("passwd");
        System.out.println("get pass" + passwd);
        ReaderCard readerCard = loginService.findReaderCardByUserId(readerId);
        Map<String, Object> res = new HashMap<String, Object>();
        // System.out.println(readerCard);
        /*System.out.println(readerCard.getReaderId());
        System.out.println(readerCard.getPasswd());*/

            if (readerCard != null) {
                System.out.println("readermes:" + readerCard.toString());
                // System.out.println(readerCard.getPasswd() + readerCard.getReaderId());
                // System.out.println(readerCard.getPasswd().trim().equals(passwd.trim()));
                if (passwd.trim().equals(readerCard.getPasswd().trim())) {
                    System.out.println("type:" + readerCard.getCardType());
                    System.out.println("state" + readerCard.getCardState());
                    if (readerCard.getCardState() == 0) {
                        res.put("stateCode", "-2");
                        res.put("msg", "禁止登陆！");
                        return res;
                    }
                    if (readerCard.getCardType() == 3) {
                        request.getSession().setAttribute("readerCard", readerCard);
                        res.put("stateCode", "3");
                        res.put("msg", "管理员登陆成功！");
                        request.getSession().setAttribute("login", "1");
                        return res;
                    } else if (readerCard.getCardType() == 1) {
                        request.getSession().setAttribute("readerCard", readerCard);
                        res.put("stateCode", "1");
                        res.put("msg", "教师登陆成功！");
                        return res;
                    } else if (readerCard.getCardType() == 0) {
                        request.getSession().setAttribute("readerCard", readerCard);
                        res.put("stateCode", "0");
                        res.put("msg", "学生登陆成功！");
                        return res;
                    }
                }else {
                    res.put("stateCode", "-2");
                    res.put("msg", "账号或密码错误！");
                    return res;
                }
            } else if (readerCard == null) {
                System.out.println("该用户不存在！");
                request.getSession().setAttribute("readerCard", null);
                res.put("stateCode", "-1");
                res.put("msg", "该用户不存在！");
                return res;
            } else {
                res.put("stateCode", "-2");
                res.put("msg", "账号或密码错误！");
                return res;
            }
        System.out.println("last return");
        return res;
    }

    @RequestMapping("/admin_main.html")
    public ModelAndView toAdminMain(HttpServletResponse response) {
        return new ModelAndView("admin_main");
    }


    @RequestMapping("/reader_main.html")
    public ModelAndView toReaderMain(HttpServletResponse response) {

        return new ModelAndView("reader_main");
    }


    @RequestMapping("/admin_repasswd.html")
    public ModelAndView reAdminPasswd() {

        return new ModelAndView("admin_repasswd");
    }

    @RequestMapping("/admin_repasswd_do")
    public String reAdminPasswdDo(HttpServletRequest request, String oldPasswd, String newPasswd, String reNewPasswd, RedirectAttributes redirectAttributes) {
        ReaderCard readerCard = (ReaderCard) request.getSession().getAttribute("readerCard");
        if(readerCard == null){
            System.out.println("会话失效");
        }
        int id = readerCard.getReaderId();
        ReaderCard ReaderCard2 = loginService.findReaderCardByUserId(id);
        if (ReaderCard2.getPasswd().equals(oldPasswd) && newPasswd.equals(reNewPasswd)) {
            readerCard.setPasswd(newPasswd);
            int succ = loginService.updateReaderCard(readerCard);
            if (succ > 0) {
                redirectAttributes.addFlashAttribute("succ", "密码修改成功！請重新登陸!");
                return "redirect:/login.html";
            } else if (succ < 0) {
                redirectAttributes.addFlashAttribute("error", "密码修改失败！");
                return "redirect:/admin_repasswd.html";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "旧密码错误！");
            return "redirect:/admin_repasswd.html";
        }
        return "404";
    }

    //配置404页面
    @RequestMapping("*")
    public String notFind() {
        return "404";
    }
}
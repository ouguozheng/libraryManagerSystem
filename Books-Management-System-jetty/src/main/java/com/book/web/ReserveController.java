package com.book.web;

import com.book.domain.Book;
import com.book.domain.ReaderCard;
import com.book.domain.Reserve;
import com.book.domain.TimeFm;
import com.book.service.BookService;
import com.book.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

/**
 * Created by 10394 on 2017/12/20.
 */
@Controller
public class ReserveController {

    @Autowired
    private ReserveService reserveService;
    @Autowired
    private BookService bookService;


    @RequestMapping("admin_reserve_list.html")
    public ModelAndView getReserveList(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes){
        ModelAndView map = new ModelAndView("admin_reserve_list");
        List<Reserve> list = reserveService.getAllReserves();
        map.addObject("list",list);
        return map;
    }

    @RequestMapping(value = "/reader/addReserve", method = RequestMethod.POST)
    public @ResponseBody
    Object addNewReserve(HttpServletRequest httpServletRequest){
        HashMap<String,Object> map = new HashMap<String, Object>();
        ReaderCard readerCard = (ReaderCard)httpServletRequest.getSession().getAttribute("readerCard");
        Long bookID = Long.parseLong(httpServletRequest.getParameter("bookId"));
        System.out.println("bookId" + bookID);
        System.out.println("预定书籍");
        if(bookID == null){
            map.put("msg","读者或书籍不存在！");
            map.put("code",-1);
            return map;
        }
        Book book = bookService.getBook(bookID);
        if(readerCard == null){
            if(book == null){
                map.put("msg","读者或书籍不存在！");
                map.put("code",-1);
            }else {
                map.put("msg","读者不存在");
                map.put("code",-1);
            }
        }else{
            if(book == null){
                map.put("msg","书籍不存在");
                map.put("code",-1);
            }else{
                Reserve reserve = new Reserve();
                reserve.setReaderId(readerCard.getReaderId());
                reserve.setBookId(bookID);
                reserve.setReserveTime(TimeFm.dateFormat.format(new Date()));
                System.out.println(TimeFm.dateFormat.format(new Date()));
                boolean sc = false;
                StringBuffer stringBuffer = new StringBuffer();
                if(book.getType() == 1){//只能教师借
                    if(readerCard.getCardType() == 0){
                        reserve.setVaild(0);//预定失败
                        stringBuffer.append("您不是教师不能预定该图书！");
                    }else{
                        if(book.getState() == 0) {
                            reserve.setVaild(0);//书籍已经被借出
                            stringBuffer.append("书籍已经被借出");
                        }else{
                            reserve.setVaild(1);//预定成功
                            sc = true;
                        }
                    }
                }else if(book.getType() == 0){
                    if(book.getState() == 0) {
                        reserve.setVaild(0);//书籍已经被借出
                        stringBuffer.append("书籍已经被借出");
                    }else{
                        reserve.setVaild(1);//预定成功
                        sc = true;
                    }
                }
                if(sc){
                    book.setState(3);//被预定
                    sc = bookService.updateBook(book);
                }
                boolean succ = reserveService.addNewReserve(reserve);
                if(succ && sc){
                    map.put("msg","预定成功!");
                    map.put("code",1);
                }else{
                    stringBuffer.append(" 预定失败!");
                    map.put("msg",stringBuffer.toString());
                    map.put("code",-2);
                }
            }
        }
        System.out.println("return map");
        return map;
    }
    //管理员查询所有的预定
    @RequestMapping("all_reserve_list.html")
    public ModelAndView getReaderReserve(HttpServletRequest httpServletRequest,RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("admin_reserve_list");
        ReaderCard readerCard = (ReaderCard)httpServletRequest.getSession().getAttribute("readerCard");
        List<Reserve> list = new ArrayList<Reserve>();
        if(readerCard != null){
            list = reserveService.getAllReserves();
            modelAndView.addObject("code",1);
            modelAndView.addObject("list",list);
            return modelAndView;
        }
        modelAndView.addObject("code",-1);
        return modelAndView;
    }
    //读者自己的
    @RequestMapping("reader_reserve_list.html")
    public ModelAndView getAllReaderReserves(HttpServletRequest httpServletRequest,RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("reader_reserve_list");
        ReaderCard readerCard = (ReaderCard)httpServletRequest.getSession().getAttribute("readerCard");
        if(readerCard != null){
            List<Reserve> list = reserveService.getReservesByReaderId(readerCard.getReaderId());
            System.out.println("reserve list size" + list.size());
            modelAndView.addObject("list",list);
            modelAndView.addObject("code",1);
        }else{
            System.out.println("card null");
            modelAndView.addObject("code",-1);
        }
        return modelAndView;
    }
    /* 每天的 00：00：00更新预定的表*/
    @Scheduled(cron = "00 00 00 * * ?")
    public void updateReservePerDay() {
        System.out.println("更新读者预定------1");
        List<Reserve> reserveList = reserveService.getResveBySuccessVaild(1);
        int overTime = 3 * 24 * 60 * 60 * 1000;
        for(int i = 0;i < reserveList.size();i++){
            if(reserveList.get(i).getVaild() == 1){
                try{
                    Date date = TimeFm.dateFormat.parse(reserveList.get(i).getReserveTime());
                    if(System.currentTimeMillis() - date.getTime() > overTime){
                        reserveList.get(i).setVaild(3);
                        boolean a = reserveService.updateReserve(reserveList.get(i));
                        System.out.println("a1" + a);
                        Book book = bookService.getBook(reserveList.get(i).getBookId());
                        if(book != null){
                            book.setState(1);
                            boolean b = bookService.updateBook(book);
                            System.out.println("b2" + b);
                        }
                    }
                }catch (Exception e){
                    System.out.println("updateReservePerDay error");
                    continue;
                }
            }
        }
        System.out.println("更新读者预定------2");
    }

}

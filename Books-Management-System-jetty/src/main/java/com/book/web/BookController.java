package com.book.web;

import com.book.dao.PublishMapper;
import com.book.domain.Book;
import com.book.domain.Publish;
import com.book.domain.Sort;
import com.book.domain.TimeFm;
import com.book.service.BookService;
import com.book.service.PublishService;
import com.book.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class BookController {
    private BookService bookService;
    @Autowired
    private PublishService publishService;
    @Autowired
    private PublishMapper publishMapper;
    @Autowired
    private SortService sortService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/querybook.html")
    public ModelAndView queryBookDo(HttpServletRequest request, String searchWord) {
        boolean exist = bookService.matchBook(searchWord);
        if (exist) {
            List<Book> books = bookService.queryBook(searchWord);
            ModelAndView modelAndView = new ModelAndView("admin_books");
            modelAndView.addObject("books", books);
            return modelAndView;
        } else {
            return new ModelAndView("admin_books", "error", "没有匹配的图书");
        }
    }

    @RequestMapping("/reader_querybook.html")
    public ModelAndView readerQueryBook() {
        return new ModelAndView("reader_book_query");
    }

    @RequestMapping("/reader_querybook_do.html")
    public String readerQueryBookDo(HttpServletRequest request, String searchWord, RedirectAttributes redirectAttributes) {
        boolean exist = bookService.matchBook(searchWord);
        if (exist) {
            List<Book> books = bookService.queryBook(searchWord);
            redirectAttributes.addFlashAttribute("books", books);
            return "redirect:/reader_querybook.html";
        } else {
            redirectAttributes.addFlashAttribute("error", "没有匹配的图书！");
            return "redirect:/reader_querybook.html";
        }

    }

    @RequestMapping("/allbooks.html")
    public ModelAndView allBook() {
        List<Book> books = bookService.getAllBooks();
        ModelAndView modelAndView = new ModelAndView("admin_books");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @RequestMapping("/deletebook.html")
    public String deleteBook(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        long bookId = Integer.parseInt(request.getParameter("bookId"));
        int res = bookService.deleteBook(bookId);

        if (res == 1) {
            redirectAttributes.addFlashAttribute("succ", "图书删除成功！");
            return "redirect:/allbooks.html";
        } else {
            redirectAttributes.addFlashAttribute("error", "图书删除失败！");
            return "redirect:/allbooks.html";
        }
    }

    @RequestMapping("/book_add.html")
    public ModelAndView addBook(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("admin_book_add");
        List<Publish> list = publishService.selectAllPublish();
        List<Sort> sortList = sortService.getAllSort();
        modelAndView.addObject("publishList",list);
        modelAndView.addObject("sortList",sortList);
        Long maxId = bookService.getMaxBookId() + 1;
        modelAndView.addObject("maxId",maxId);
        return modelAndView;

    }

    @RequestMapping("/book_add_do.html")
    public String addBookDo(BookAddCommand bookAddCommand, RedirectAttributes redirectAttributes) {
        Book book = new Book();
        Long bookId = bookService.getMaxBookId();
        book.setBookId(bookId + 1);
        book.setPrice(bookAddCommand.getPrice());
        book.setState(bookAddCommand.getState());
        book.setPublish(bookAddCommand.getPublish());
        book.setPubdate(bookAddCommand.getPubdate());
        book.setName(bookAddCommand.getName());
        book.setIsbn(bookAddCommand.getIsbn());
        book.setClassId(bookAddCommand.getClassId());
        book.setAuthor(bookAddCommand.getAuthor());
        book.setIntroduction(bookAddCommand.getIntroduction());
        book.setPressmark(bookAddCommand.getPressmark());
        book.setLan(bookAddCommand.getLan());

        boolean succ = bookService.addBook(book);
        List<Book> books = bookService.getAllBooks();
        if (succ) {
            redirectAttributes.addFlashAttribute("succ", "图书添加成功！");
            return "redirect:/allbooks.html";
        } else {
            redirectAttributes.addFlashAttribute("succ", "图书添加失败！");
            return "redirect:/allbooks.html";
        }
    }

    @RequestMapping("/updatebook.html")
    public ModelAndView bookEdit(HttpServletRequest request) {
        long bookId = Integer.parseInt(request.getParameter("bookId"));
        Book book = bookService.getBook(bookId);
        List<Publish> list = publishMapper.selectAllPublish();
        ModelAndView modelAndView = new ModelAndView("admin_book_edit");
        modelAndView.addObject("publishs",list);
        modelAndView.addObject("detail", book);
        return modelAndView;
    }

    @RequestMapping("/book_edit_do.html")
    public String bookEditDo(HttpServletRequest request, String name, String author, String publish, String isbn,
                             String introduction, String lan, BigDecimal price, String pubdate, Integer classId, Integer pressmark,
                             Integer state, Integer type, RedirectAttributes redirectAttributes) {
        Long bookId = Long.parseLong(request.getParameter("id"));
        System.out.println("get id:" + bookId);
        Book book = bookService.getBook(bookId);
        if(book == null){
            redirectAttributes.addFlashAttribute("error", "图书修改失败！");
            return "redirect:/allbooks.html";
        }
        book.setBookId(bookId);
        book.setName(name);
        book.setState(state);
        book.setAuthor(author);
        book.setClassId(classId);
        book.setIntroduction(introduction);
        book.setIsbn(isbn);
        book.setLan(lan);
        book.setPressmark(pressmark);
        book.setPrice(price);
//        book.setPubdate(TimeFm.dateFormat.format(pubdate));
        book.setPubdate(pubdate);
        book.setType(type);
        book.setPublish(publish);
        System.out.println(book.toString());
        boolean succ = bookService.updateBook(book);
        if (succ) {
            redirectAttributes.addFlashAttribute("succ", "图书修改成功！");
            return "redirect:/allbooks.html";
        } else {
            redirectAttributes.addFlashAttribute("error", "图书修改失败！");
            return "redirect:/allbooks.html";
        }
    }


    @RequestMapping("/bookdetail.html")
    public ModelAndView bookDetail(HttpServletRequest request) {
        long bookId = Integer.parseInt(request.getParameter("bookId"));
        Book book = bookService.getBook(bookId);
        ModelAndView modelAndView = new ModelAndView("admin_book_detail");
        modelAndView.addObject("detail", book);
        return modelAndView;
    }


    @RequestMapping("/readerbookdetail.html")
    public ModelAndView readerBookDetail(HttpServletRequest request) {
        long bookId = Integer.parseInt(request.getParameter("bookId"));
        Book book = bookService.getBook(bookId);
        ModelAndView modelAndView = new ModelAndView("reader_book_detail");
        modelAndView.addObject("detail", book);
        return modelAndView;
    }


}

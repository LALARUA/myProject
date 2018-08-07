package com.dx.Booker.service;

import com.dx.Booker.generator.extendPojo.allTags;
import com.dx.Booker.generator.extendPojo.findBooks;
import com.dx.Booker.generator.extendPojo.selectBooksOrderBy;
import com.dx.Booker.generator.mapper.BooksMapper;


import com.dx.Booker.generator.mapper.LabelAndTagMapper;
import com.dx.Booker.generator.mytool.MyTool;
import com.dx.Booker.generator.po.Books;
import com.dx.Booker.generator.po.BooksExample;
import com.dx.Booker.generator.po.tag;
import com.dx.Booker.serviceinterface.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
/**
 * @description 关于book书籍的操作
 * @author xiangXX
 * @date 2018/6/08 0012 9:59
  * l
 *
 */
@Service
public class BookServiceImp implements BookService {
    private final int PAGESIZE = 15;

    @Autowired
    private BooksMapper booksMapper;
    @Autowired
    private LabelAndTagMapper labelAndTagMapper;
    //实现根据标签的分页查询


    //分页查询
    /**
     * @description 根据条件得到Book数据
     * @author xiangXX
     * @date 2018/6/12 0012 10:04
     * @param booksExample 数据库条件
     * @param bookNum      Book总数量
     * @param pageNow         需要查找第几页
     */
    private HashMap getPageData(BooksExample booksExample, int bookNum, int pageNow) {
        int pageNum = (int) (bookNum % PAGESIZE == 0 ? bookNum / PAGESIZE : bookNum / PAGESIZE + 1);

        int startRow = (int) (PAGESIZE * (pageNow - 1) + 0);

        //如果只有1页或者要查询最后一页
        if (pageNum == 1 || pageNow == pageNum) {
            booksExample.setStartRow(startRow);
            booksExample.setPageSize(bookNum - (pageNow - 1) * PAGESIZE);
        } else {
            booksExample.setStartRow(startRow);
            booksExample.setPageSize(PAGESIZE);
        }
        HashMap map = new HashMap();
        map.put("books", booksMapper.selectByExample(booksExample));
        map.put("pageNum", pageNum);
        return map;
    }
    @Override
    public Map BooksInfo(findBooks findBooks){
        String method = findBooks.getMethod();
        HashMap map = new HashMap();
        if (method.equals("ByTag"))

            map = selectBookPageQueryByTag(findBooks);


        else if (method.equals("BySearch"))
            map = searchBook(findBooks);


        else if (method.equals("ByAuthor"))

            map = selectBookPageQueryByAuthor(findBooks);

        return map;
    }

    @Override
    /**
     * String tag:要查询的图书标签
     * int page:要查询的页码数
     */
    public HashMap selectBookPageQueryByTag(findBooks findBooks) {
        String tag = findBooks.getKeyWord();
        Integer pageNow = findBooks.getPageNow();
        //1、查询出该标签的图书量
        BooksExample booksExample = new BooksExample();
        BooksExample.Criteria criteria = booksExample.createCriteria();
        criteria.andTagsLike('%'+tag+'%');
        booksExample.setOrderByClause(findBooks.getOrderBy()+" desc");
        int bookNum = (int) booksMapper.countByExample(booksExample);
        return getPageData(booksExample, bookNum, pageNow);
    }

    @Override
/**
 * @description 通过作者查询书籍
 * @author xiangXX
 * @date 2018/6/5 0005 9:08
  * @param author  需要查询的作者名字
 * @param page    需要查询的页数
 *
 */
    public HashMap selectBookPageQueryByAuthor(findBooks findBooks) {
        String author = findBooks.getKeyWord();
        Integer pageNow = findBooks.getPageNow();
        BooksExample booksExample = new BooksExample();
        booksExample.setOrderByClause(findBooks.getOrderBy()+" desc");
        BooksExample.Criteria criteria = booksExample.createCriteria();
        criteria.andAuthorLike('%'+author+'%');
        int bookNum = (int) booksMapper.countByExample(booksExample);
        return getPageData(booksExample, bookNum, pageNow);
    }


    @Override
    /**
     * @description 通过搜索框查询书籍
     * @author xiangXX
     * @date 2018/6/12 0012 10:00
      * @param condition 查询关键字
     * @param page 要查询的页数
     *
     */
    public HashMap searchBook(findBooks findBooks) {

        String condition = findBooks.getKeyWord();
        Integer pageNow = findBooks.getPageNow();
        HashMap map = new HashMap();
        BooksExample booksExample = new BooksExample();
        booksExample.setOrderByClause(findBooks.getOrderBy()+" desc");
        //将条件字符串换成一个字一个字的字符串数组
        String[] strings = condition.split("");
        //如果查询条件是13位的isbn数字则直接按isbn查询图书
        if (condition.length() == 13 && MyTool.isInteger(condition)) {
            List bookByISBN = findBookByISBN(condition);
//            BooksExample.Criteria criteria_Isbn = booksExample.createCriteria();
//            criteria_Isbn.andIsbnEqualTo((long) MyTool.toInteger(condition));
//            List<Books> allBook = booksMapper.selectByExample(booksExample);
            //按isbn查询仅能查出一条数据
            map.put("books", bookByISBN);
            map.put("pageNum", 1);
            return map;
        }
        BooksExample.Criteria criteria_Author = booksExample.createCriteria();
        BooksExample.Criteria criteria_Title = booksExample.createCriteria();
        BooksExample.Criteria criteria_Tags = booksExample.createCriteria();
//        criteria_Author.andTagsLike("%小说%");
        for (int i = 0; i < strings.length; i++) {
            criteria_Author.andAuthorLike("%" + strings[i] + "%");
            criteria_Title.andTitleLike("%" + strings[i] + "%");
            criteria_Tags.andTagsLike("%" + strings[i] + "%");
            booksExample.or(criteria_Title);
            booksExample.or(criteria_Tags);
        }
//        List<Books> allBook = booksMapper.selectByExample(booksExample);
        int bookNum =(int) booksMapper.countByExample(booksExample);
//        int bookNum = allBook.size();
        return getPageData(booksExample, bookNum, pageNow);
    }

    @Override
    /**
     * @description 查询Book详情
     * @author xiangXX
     * @date 2018/6/12 0012 10:01
      * @param id BookI
     *
     */
    public Books findBooksDetail(Integer id) {
        Books books = booksMapper.selectByPrimaryKey(id);
        return books;
    }

    /**
     * @description 在主页显示的Book
     * @author xiangXX
     * @date 2018/6/08 0012 10:01

     *
     */
    public HashMap BooksInHomepage() {
//          最新上架的Book
        List<Books> homepagePutaway = booksMapper.selectBooksOrderBy(new selectBooksOrderBy("putaway",0));

//          销量最高的Books
        List<Books> homepageSales = booksMapper.selectBooksOrderBy(new selectBooksOrderBy("sales",0));

        List<allTags> labels = labelAndTagMapper.selectAllTags();
        List<tag> tags = labelAndTagMapper.selectTags();
        List<tag> hotTags = labelAndTagMapper.selectHotTags(1);
        HashMap<Object, Object> booksMap = new HashMap<>();
        booksMap.put("homepagePutaway", homepagePutaway);
        booksMap.put("homepageSales", homepageSales);
        booksMap.put("labels",labels);
        booksMap.put("tags",tags);
        booksMap.put("hotTags",hotTags);
        return booksMap;
    }

    @Override
    /**
     * @description 通过标题查找book
     * @author xiangXX
     * @date 2018/6/12 0012 10:03
      * @param title Book标题
     * @param page  要查询的页数
     *
     */
    public HashMap findBooksByTitle(findBooks findBooks) {
        String title = findBooks.getKeyWord();
        Integer pageNow = findBooks.getPageNow();
        BooksExample booksExample = new BooksExample();
        booksExample.setOrderByClause(findBooks.getOrderBy()+" desc");
        BooksExample.Criteria criteria = booksExample.createCriteria();
        criteria.andTitleLike('%'+title+'%');
        int bookNum = (int)booksMapper.countByExample(booksExample);
        return getPageData(booksExample,bookNum,pageNow);
    }

    @Override
    /**
     * @description 通过ISBN号查找Book
     * @author xiangXX
     * @date 2018/6/08 0012 10:03
      * @param ISBN ISBN号
     *
     */
    public List<Books> findBookByISBN(String ISBN) {
        BooksExample booksExample = new BooksExample();
        BooksExample.Criteria criteria = booksExample.createCriteria();
        criteria.andIsbnEqualTo(Long.valueOf(ISBN));
        List<Books> books = booksMapper.selectByExample(booksExample);
        return books;
        }
}

package com.dx.Booker.generator.mapper;

import com.dx.Booker.generator.extendPojo.*;
import com.dx.Booker.generator.po.Books;
import com.dx.Booker.generator.po.BooksExample;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

public interface BooksMapper {

    @Select("SELECT price,id from books")
    List<selectPrice> price();

    @Update("update books set sales = #{sales},stock=#{stock} where id = #{id}")
    public void updateBookSales(Books books);

    @Select("SELECT COUNT(*) FROM books WHERE tags LIKE #{tags}")
    public Integer selectBooksNumber(String tags);

    @Select("select * from books ORDER BY ${condition} DESC LIMIT #{pageStart},15")
    public List<Books> selectBooksOrderBy(selectBooksOrderBy selectBooksOrderBy);

    @Insert("INSERT INTO books (title,author,tags,imagePath,publisher,summary,price,isbn,putaway,stock) VALUES\n" +
            "(#{title},#{author},#{tags},#{imagepath},#{publisher},#{summary},#{price},#{isbn},#{putaway},#{stock})")
    public void addBook(addBook addBook);
    @Select("select * from books")
    public List<Books> selectAllBooks();

    @Update("update books set imagePath = #{imagepath} where id = #{id}")
    public void updateImagePath(Books books);

    @Update("update books set title = #{title},author=#{author},tags={tags},publisher=#{publisher},summary=#{summary},price=#{price},isbn=#{isbn},stock=#{stock} where id = #{id} ")
    public void updateBook(Books books);
    long countByExample(BooksExample example);

    public List<BooksInCollection> selectBooksInCollect(Integer collectId);

    int deleteByExample(BooksExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Books record);

    int insertSelective(Books record);

    List<Books> selectByExampleWithBLOBs(BooksExample example);

    List<Books> selectByExample(BooksExample example);

    Books selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Books record, @Param("example") BooksExample example);

    int updateByExampleWithBLOBs(@Param("record") Books record, @Param("example") BooksExample example);

    int updateByExample(@Param("record") Books record, @Param("example") BooksExample example);

    int updateByPrimaryKeySelective(Books record);

    int updateByPrimaryKeyWithBLOBs(Books record);

    int updateByPrimaryKey(Books record);
}
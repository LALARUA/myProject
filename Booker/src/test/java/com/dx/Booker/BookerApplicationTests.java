package com.dx.Booker;




import com.dx.Booker.generator.extendPojo.allTags;
import com.dx.Booker.generator.extendPojo.commentAndSupport;
import com.dx.Booker.generator.mapper.*;


import com.dx.Booker.generator.po.Books;
import com.dx.Booker.serviceinterface.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookerApplicationTests {
//@Autowired
//	ccService ccService;
//@Autowired
//	bbService bbService;
//@Autowired
//	UserService userService;
//@Autowired
//	BooksMapper booksMapper;
//@Resource
//	BookService bookService;
//@Autowired
//	CartService cartService;
//@Autowired
//	CartMapper cartMapper;
//@Autowired
//	OrderMapper orderMapper;
//@Autowired
//	OrderdetailMapper orderdetailMapper;
	@Autowired
	private DataSource dataSource;

	@Autowired
	private CommentMapper commentMapper;


    @Test
	public void contextLoads() throws Exception{
		List<commentAndSupport> commentAndSupports = commentMapper.commentsOfBook(171);

		System.out.println("pause");


//		List<Books> books = booksMapper.selectAllBooks();
//		for (Books book:books
//			 ) {
//			String imagepath = book.getImagepath();
//			String newImagepath = "/"+imagepath;
//			book.setImagepath(newImagepath);
//			booksMapper.updateImagePath(book);
//
//		}
//test.test();
//userService.test();
//userService.insertOrder(order);
//		order order = new order();
//		order.setUserId(2);
//userService.insertOrder(order);
//		Date date = new Date();
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date parse = simpleDateFormat.parse(simpleDateFormat.format(date));
//		System.out.println(parse);

//		List<selectPrice> price = booksMapper.price();
//		for (int i= 0;i<=price.size();i++){
//			updatePrice updatePrice = new updatePrice();
//			updatePrice.setId(price.get(i).getId());
//			String s = price.get(i).getPrice();
//			String b="";
//			if (s!=null) {
//
//				char[] chars = s.toCharArray();
//
//				for (char c : chars
//						) {
//					if ((int) c >= 46 && (int) c <= 57) {
//						b += c;
//					}
//				}
//			}
//			else b="0";
//		updatePrice.setPrice(b);
//			booksMapper.updatePrice(updatePrice);


		}

//    	String a="125元";
//		char[] chars = a.toCharArray();
//		String b="";
//		for (char c:chars
//			 ) {if ((int)c>=46&&(int)c<=57){
//			 	b+=c;
//			 	}
//		}
//		System.out.println(b);


//		List list=new ArrayList();
//		list.add(new User());
//		User user=(User)list.get(0);
//		user.setUsername("zx");
//		User user1=(User)list.get(0);
//		System.out.println(user1.getUsername());
//		String a="5";
//		int c=Integer.valueOf(a);
//
//		System.out.println(a.equals("5"));
//		String json = "[{'username':'zx','password':'123'},{'username':123,'password':'26'}]";
//		JSONArray jsonArray = JSONArray.fromObject(json);
//		System.out.println(jsonArray.size());
//		JSONObject jsonObject = (JSONObject)jsonArray.get(1);
//		System.out.println(jsonObject.keys().next().toString());
//		List list= JSONArray.toList(jsonArray,new user(),new JsonConfig());
//		System.out.println();





	}



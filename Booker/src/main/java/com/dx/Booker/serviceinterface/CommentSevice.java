package com.dx.Booker.serviceinterface;

import com.dx.Booker.generator.extendPojo.commentAndSupport;
import com.dx.Booker.generator.extendPojo.supportMessage;
import com.dx.Booker.generator.po.Comment;
import com.dx.Booker.generator.po.Support;
import com.dx.Booker.generator.po.reply;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CommentSevice {
  public int addComment(Comment comment);
  public void addSupport(supportMessage supportMessage);
  public List<commentAndSupport> selectCommentAndSupport(Integer bookId, Integer userId);
  public Comment selectCommentByUserIdAndBookId(Comment comment);
  public void supportReply(Integer userId,Integer replyId);

}

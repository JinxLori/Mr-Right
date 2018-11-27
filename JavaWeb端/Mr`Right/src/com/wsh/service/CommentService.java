package com.wsh.service;

import java.util.List;

import com.wsh.bean.Comment;
import com.wsh.bean.CommentAndPost;
import com.wsh.dao.CommentDao;

public class CommentService {

	CommentDao commentDao = new CommentDao();
	
//	根据post查询下面的评论
	public List<Comment> getCommentByPostId(int post_id,int user_id) {
		return commentDao.getCommentByPostId(post_id,user_id);
	}
	
//	根据comment查询评论
	public List<Comment> getCommentByCommentId(int comment_id,int user_id) {
		return commentDao.getCommentByCommentId(comment_id,user_id);
	}
//	添加评论
	public int addComment(Comment comment) {
		return commentDao.addComment(comment);
	}
//	用户点赞（评论）
	public int addCommentNiceNum(int commentId,int userId) {
		return commentDao.addCommentNiceNum(commentId,userId);
	}
//	用户取消点赞
	public int cancleCommentNiceNum(int commentId,int userId) {
		return commentDao.cancleCommentNiceNum(commentId,userId);
	}
//	得到点赞的人Id（评论）
	public List<String> getCommentNiceUids(int comment_id){
		return commentDao.getCommentNiceUids(comment_id);
	}
	
//	根据用户id查询评论
	public List<CommentAndPost> getCommentByUserId(int userId){
		return commentDao.getCommentByUserId(userId);
	}
}

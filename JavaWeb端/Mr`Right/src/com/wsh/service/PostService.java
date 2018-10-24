package com.wsh.service;

import java.util.List;

import com.wsh.bean.Post;
import com.wsh.dao.PostDao;

public class PostService {
	
	PostDao postDao = new PostDao();

//	根据id查询post
	public List<Post> getPostByUserId(int post_from_id) {
		return postDao.getPostByUserId(post_from_id);
	}
//	添加post
	public int addPost(Post post) {
		return postDao.addPost(post);
	}
//	用户点赞（帖子）
	public int addPostNiceNum(int postId,int userId) {
		return postDao.addPostNiceNum(postId,userId);
	}
//	得到点赞的人Id（帖子）
	public List<String> getPostNiceUids(int comment_id){
		return postDao.getPostNiceUids(comment_id);
	}
//	用户取消点赞(帖子)
	public int canclePostNiceNum(int postId,int userId) {
		return postDao.canclePostNiceNum(postId,userId);
	}
}

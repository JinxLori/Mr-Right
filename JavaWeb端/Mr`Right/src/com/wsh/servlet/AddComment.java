package com.wsh.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wsh.bean.Comment;
import com.wsh.service.CommentService;

/**
 * Servlet implementation class AddComment
 */
@WebServlet("/AddComment")
public class AddComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		String post_id = request.getParameter("post_id");
		String comment_content = request.getParameter("comment_content");
		String comment_level = request.getParameter("comment_level");
		String from_uid = request.getParameter("from_uid");
		
		Comment comment = new Comment();
		comment.setPost_id(Integer.parseInt(post_id));
		comment.setComment_content(comment_content);
		comment.setComment_level(Integer.parseInt(comment_level));
		comment.setFrom_uid(Integer.parseInt(from_uid));
		
		CommentService commentService = new CommentService();
		int num = commentService.addComment(comment);
		
		response.getWriter().write(num+"");
	}

}

package com.wsh.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wsh.bean.Comment;
import com.wsh.service.CommentService;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * Servlet implementation class GetCommentByCommentId
 */
@WebServlet("/GetCommentByCommentId")
public class GetCommentByCommentId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCommentByCommentId() {
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
		
		String comment_id = request.getParameter("comment_id");
		String user_id = request.getParameter("user_id");
	
		CommentService commentService = new CommentService();
		List<Comment> comments = new ArrayList<Comment>();
		comments = commentService.getCommentByCommentId(Integer.parseInt(comment_id),Integer.parseInt(user_id));
		
		JsonConfig jsonConfig = new JsonConfig();
		String commentsToJson = JSONArray.fromObject(comments.toArray(),jsonConfig).toString();
		response.getWriter().write(commentsToJson);
	}

}

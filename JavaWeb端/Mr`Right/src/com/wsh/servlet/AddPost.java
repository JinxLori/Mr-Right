package com.wsh.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wsh.bean.Post;
import com.wsh.service.PostService;

/**
 * Servlet implementation class AddPost
 */
@WebServlet("/AddPost")
public class AddPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPost() {
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
		
		String post_from_id = request.getParameter("post_from_id");
		String post_topic_id = request.getParameter("post_topic_id");
		String post_content_text = request.getParameter("post_content_text");
		/*String post_date = request.getParameter("post_date");
		String post_nice_num = request.getParameter("post_nice_num");*/
		
		Post post = new Post();
		post.setPost_from_id(Integer.parseInt(post_from_id));
		post.setPost_topic_id(Integer.parseInt(post_topic_id));
		post.setPost_content_text(post_content_text);
		
		PostService postService = new PostService();
		int num = postService.addPost(post);
		
		response.getWriter().write(num+"");
		
	}

}

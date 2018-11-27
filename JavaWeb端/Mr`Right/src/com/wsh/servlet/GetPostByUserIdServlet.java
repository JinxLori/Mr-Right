package com.wsh.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wsh.bean.Post;
import com.wsh.service.PostService;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * Servlet implementation class GetPostByUserIdServlet
 */
@WebServlet("/GetPostByUserIdServlet")
public class GetPostByUserIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPostByUserIdServlet() {
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
		
		String user_id= request.getParameter("user_id");
		
		PostService postService = new PostService();
		List<Post> posts = new ArrayList<>();
		posts = postService.getPostByUserId(Integer.parseInt(user_id));
		
		JsonConfig jsonConfig = new JsonConfig();
		String postsToJson = JSONArray.fromObject(posts.toArray(),jsonConfig).toString();
		response.getWriter().write(postsToJson);
		
	}

}

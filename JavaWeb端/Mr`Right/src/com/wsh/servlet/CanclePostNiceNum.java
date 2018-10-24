package com.wsh.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.wsh.service.PostService;

/**
 * Servlet implementation class CanclePostNiceNum
 */
@WebServlet("/CanclePostNiceNum")
public class CanclePostNiceNum extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CanclePostNiceNum() {
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
		String user_id = request.getParameter("user_id");
		
		PostService postService = new PostService();
		int num = postService.canclePostNiceNum(Integer.parseInt(post_id),Integer.parseInt(user_id));
		
		response.getWriter().write(num+"");
	}

}

package com.wsh.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wsh.bean.User;
import com.wsh.service.UserService;

/**
 * Servlet implementation class UpdateUser
 */
@WebServlet("/UpdateUser")
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUser() {
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
		
		
		String userid = request.getParameter("userid");
		String username = request.getParameter("username");
		String headimage = request.getParameter("headimage");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		String sign = request.getParameter("sign");
		
		User user = new User();
		user.setUsername(username);
		user.setUserid(Integer.parseInt(userid));
		user.setHeadimage(headimage);
		user.setBirthday(birthday);
		user.setSex(sex);
		user.setSign(sign);
		
		UserService userService = new UserService();
		int n = userService.updateUser(Integer.parseInt(userid),user);
		response.getWriter().write(n+"");
		
	}

}

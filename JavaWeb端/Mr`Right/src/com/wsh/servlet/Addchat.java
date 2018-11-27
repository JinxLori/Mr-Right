package com.wsh.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wsh.service.ChatService;

/**
 * Servlet implementation class Addchat
 */
@WebServlet("/Addchat")
public class Addchat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Addchat() {
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
		request.setCharacterEncoding("utf-8"); 
		response.setCharacterEncoding("utf-8");
		
		String send_id = request.getParameter("send_id");
		String recive_id = request.getParameter("recive_id");
		String chat_content = request.getParameter("chat_content");
		
		ChatService chatService = new ChatService();
		int n = chatService.addChat(Integer.parseInt(send_id),Integer.parseInt(recive_id),chat_content);
		
		response.getWriter().write(n+"");
	}

}

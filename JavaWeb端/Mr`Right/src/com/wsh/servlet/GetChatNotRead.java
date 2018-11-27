package com.wsh.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wsh.bean.Chat;
import com.wsh.service.ChatService;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * Servlet implementation class GetChatNotRead
 */
@WebServlet("/GetChatNotRead")
public class GetChatNotRead extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetChatNotRead() {
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
		
		String sendId= request.getParameter("sendId");
		String reciveId= request.getParameter("reciveId");
		
		ChatService chatService = new ChatService();
		List<Chat> chats = new ArrayList<>();
		chats = chatService.getChatNotRead(Integer.parseInt(sendId),Integer.parseInt(reciveId));
		
		JsonConfig jsonConfig = new JsonConfig();
		String chatsToJson = JSONArray.fromObject(chats.toArray(),jsonConfig).toString();
		response.getWriter().write(chatsToJson);
	}

}

package com.wsh.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wsh.service.ChatService;

/**
 * Servlet implementation class UpdateChatIsRead
 */
@WebServlet("/UpdateChatIsRead")
public class UpdateChatIsRead extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateChatIsRead() {
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
		
		String chat_id = request.getParameter("chat_id");
		String chat_recive = request.getParameter("chat_recive");
		
		ChatService chatService = new ChatService();
		int n = chatService.updateIsRead(Integer.parseInt(chat_id),Integer.parseInt(chat_recive));
		
		response.getWriter().write(n+"");
	}

}

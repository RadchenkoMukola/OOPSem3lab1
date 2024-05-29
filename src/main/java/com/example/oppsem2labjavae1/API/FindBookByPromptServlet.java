package com.example.oppsem2labjavae1.API;

import com.example.oppsem2labjavae1.Core.DBSetup;
import com.example.oppsem2labjavae1.Core.Models.Book;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "findBookByPrompt", value = "/api/findBookByPrompt")
public class FindBookByPromptServlet extends HttpServlet {
    private Gson gson = new Gson();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String prompt = request.getParameter("prompt");

        List<Book> books = DBSetup.getInstance().getRepository().findBookByPrompt(prompt);

        String json = gson.toJson(books);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }
}
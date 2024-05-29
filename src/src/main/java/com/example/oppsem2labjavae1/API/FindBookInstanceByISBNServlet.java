package com.example.oppsem2labjavae1.API;

import com.example.oppsem2labjavae1.Core.DBSetup;
import com.example.oppsem2labjavae1.Core.Models.BookInstance;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "findBookInstanceByISBNServlet", value = "/api/findBookInstanceByISBN")
public class FindBookInstanceByISBNServlet extends HttpServlet {
    private Gson gson = new Gson();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long isbn = Long.parseLong(request.getParameter("isbn"));

        BookInstance instances = DBSetup.getInstance().getRepository().findBookInstanceByISBN(isbn);

        String json = gson.toJson(instances);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }
}

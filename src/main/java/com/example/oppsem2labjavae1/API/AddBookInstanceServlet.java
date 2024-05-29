package com.example.oppsem2labjavae1.API;

import com.example.oppsem2labjavae1.Core.DBSetup;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "api/addBookInstance", urlPatterns = "/api/addBookInstance")
public class AddBookInstanceServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long isbn = Long.parseLong(request.getParameter("isbn"));
        int id = Integer.parseInt(request.getParameter("id"));
        DBSetup.getInstance().getRepository().addBookInstance(isbn, id);
    }
}

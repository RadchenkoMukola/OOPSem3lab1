package com.example.oppsem2labjavae1.API;

import com.example.oppsem2labjavae1.Core.DBSetup;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "returnBook", value = "/api/returnBook")
public class ReturnBookServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long isbn = Long.parseLong(request.getParameter("isbn"));
        DBSetup.getInstance().getRepository().returnBook(isbn);
    }
}

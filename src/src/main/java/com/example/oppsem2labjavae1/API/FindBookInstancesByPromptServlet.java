package com.example.oppsem2labjavae1.API;

import com.example.oppsem2labjavae1.Core.DBSetup;
import com.example.oppsem2labjavae1.Core.Models.BookInstance;
import com.google.gson.Gson;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "findBookInstancesByPrompt", value = "/api/findBookInstancesByPrompt")
public class FindBookInstancesByPromptServlet extends HttpServlet {
    private Gson gson = new Gson();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String prompt = request.getParameter("prompt");

        List<BookInstance> instances = DBSetup.getInstance().getRepository().findBookInstancesByPrompt(prompt);

        String json = gson.toJson(instances);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }
}
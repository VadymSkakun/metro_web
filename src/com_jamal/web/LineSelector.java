package com_jamal.web;

import com_jamal.core.MetroWeb;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class LineSelector extends HttpServlet {
    static String line;
    private static final long serialVersionUID = 1234567891011121314L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        resp.setContentType("text/html;charset=UTF-8");

        if (req.getParameter("line") != null) {
            line = req.getParameter("line");
        }

        PrintWriter pw = resp.getWriter();
        MetroWeb metro;
        try {
            metro = new MetroWeb(pw);
            metro.printLine(line);
        } catch (IllegalArgumentException | IllegalStateException e1) {
            e1.printStackTrace();
        }
    }
}
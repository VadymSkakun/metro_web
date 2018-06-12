package com_jamal.web;

import com_jamal.core.MetroWeb;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TestMetroServlet extends HttpServlet {
    private static final long serialVersionUID = 1234567891011121314L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");

        PrintWriter pw = resp.getWriter();
        MetroWeb metro;
        try {
            metro = new MetroWeb(pw);
            metro.printWagons();
            metro.printTrains();
            metro.showPasssengersLeft();
        } catch (IllegalArgumentException | IllegalStateException e1) {
            e1.printStackTrace();
        }
    }
}
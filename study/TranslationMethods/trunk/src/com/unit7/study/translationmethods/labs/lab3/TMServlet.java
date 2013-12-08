package com.unit7.study.translationmethods.labs.lab3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.labs.lab3.interfaces.AutomateApp;
import com.unit7.study.translationmethods.labs.lab3.interfaces.State;
import com.unit7.study.translationmethods.labs.lab3.interfaces.impl.AutomateAppImpl;
import com.unit7.study.translationmethods.labs.lab3.interfaces.impl.DataBuilder;

public class TMServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();

        DataBuilder builder = new DataBuilder();

        try {
            String terms = builder.parseTerminals(req);
            String chain = builder.parseChain(req);
            Map<String, State> states = builder.parseStates(req);
            State startState = builder.parseStartState(req, states);
            State finalState = builder.parseFinalState(req, states);

            AutomateApp app = new AutomateAppImpl(terms, chain, startState);

            if (app.execute()) {
                prepareResponse("Ok", resp);
            }
        } catch (InformationException e) {
            prepareResponse(e.getLocalizedMessage(), resp);
        }
    }

    private void prepareResponse(String message, HttpServletResponse resp) {
        try {
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            writer.println("<html><title>Результат работы</title><body align=\"center\"><p>");
            writer.println(message);
            writer.println("</p></body></html>");
            writer.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

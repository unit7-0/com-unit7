package com.unit7.study.translationmethods.labs.lab3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.labs.lab3.interfaces.AutomateApp;
import com.unit7.study.translationmethods.labs.lab3.interfaces.AutomateChecker;
import com.unit7.study.translationmethods.labs.lab3.interfaces.State;
import com.unit7.study.translationmethods.labs.lab3.interfaces.impl.AutomateAppImpl;
import com.unit7.study.translationmethods.labs.lab3.interfaces.impl.AutomateCheckerImpl;
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
        String htmlPage = req.getParameter("html_page");

        int count = 0;
        Enumeration<String> names = req.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if (name.startsWith(AutomateApp.PARAM_STATE_BEG))
                ++count;
        }
        
        try {
            String terms = builder.parseTerminals(req);
            String chain = builder.parseChain(req);
            Map<String, State> states = builder.parseStates(req);
            State startState = builder.parseStartState(req, states);
            State finalState = builder.parseFinalState(req, states);

            AutomateApp app = new AutomateAppImpl(terms, chain, startState, finalState);

            if (app.execute()) {
                prepareResponse("Ok", htmlPage, count, resp);
            }
        } catch (InformationException e) {
            prepareResponse(e.getLocalizedMessage(), htmlPage, count, resp);
        }

    }

    private void prepareResponse(String message, String pageCopy, int paramCount, HttpServletResponse resp) {
        try {
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            writer.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
                    + "<html>"
                    + "<head>"
                    + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                    + "<title>Insert title here</title>"
                    + "<script type=\"text/javascript\" src=\"../scripts/automate.js\"></script>"
                    + "</head>"
                    + "<body>" + "<div id=\"root\">");
            
            int ind = pageCopy.indexOf("\"html_page\" value=\"");
            pageCopy = pageCopy.substring(0, ind + "\"html_page\"".length()) + pageCopy.substring(pageCopy.indexOf("\"", ind + "\"html_page\" value=\"".length()));
            pageCopy = pageCopy.replaceFirst("var i = 0;", "var i = " + paramCount + ";");
            pageCopy = pageCopy.replaceFirst("addState\\(i, 'input_data'\\);", "//");
            
            int index = pageCopy.indexOf("<div id=\"log\">");
            index = pageCopy.indexOf("</div>", index);
            pageCopy = pageCopy.substring(0, index) + "<span>" + message + "</span><br>" + pageCopy.substring(index);
            
            writer.println(pageCopy);
            writer.println("</body></html>");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private DataBuilder builder = new DataBuilder();
}

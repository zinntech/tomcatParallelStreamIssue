package io.zinntech;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/test")
public final class MyServlet extends HttpServlet
{
    private static List<Integer> someNumbers = generateNonsense();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        StringBuilder answerBuilder = new StringBuilder();

        // This should be okay.
        someNumbers.stream().forEach( number ->
        {
            answerBuilder.append(".stream() Looked up ")
                    .append(lookupEnvironmentValue())
                    .append("<br/>");
        });

         // This is most likely bad
        someNumbers.parallelStream().forEach( number ->
        {
            synchronized (MyServlet.class)
            {
                answerBuilder.append(".parallelStream() Looked up ")
                        .append(lookupEnvironmentValue())
                        .append("<br/>");
            }
        });

        response.setContentType("text/html");
        response.getWriter().write(answerBuilder.toString());
    }

    private String lookupEnvironmentValue()
    {
        try
        {
            Context context = new InitialContext();
            return (String) context.lookup("java:comp/env/testName");
        }
        catch(NamingException e)
        {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private static List<Integer> generateNonsense()
    {
        List<Integer> nonsense = new ArrayList<>();
        for(int n=0; n<1000; n++)
            nonsense.add(n);

        return nonsense;
    }
}

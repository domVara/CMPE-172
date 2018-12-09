
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.concurrent.ExecutionException;

/*Mohsen Entire file*/
public class ServletHelper
{

    public static void arrayToResponse(Enumeration<?> enumeration, HttpServletResponse response)
    {
        try
        {
            ArrayList<String> arrayList = new ArrayList<String>();
            while (enumeration.hasMoreElements())
            {
                arrayList.add(enumeration.nextElement().toString());
            }
            arrayToResponse(arrayList, response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void arrayToResponse(String[] array, HttpServletResponse response)
    {
        if (array == null || array.length == 0)
        {
            return;
        }

        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(array));

        arrayToResponse(arrayList, response);
    }


    public static void arrayToResponse(ArrayList<String> arrayList, HttpServletResponse response)
    {
        try
        {
            for (int i = 0; i < arrayList.size(); i++)
            {
                response.getWriter().println(String.format("<br>%s<br>", arrayList.get(i)));
            }
        }
        catch (EOFException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void stringToResponse(String string, HttpServletResponse response)
    {
        try
        {
            response.getWriter().println(String.format("<br>%s<br>", string));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String retrieveStringFromResponse(HttpResponse response)
    {
        try
        {
            return EntityUtils.toString(response.getEntity());
        }
        catch (IOException e)
        {
            return e.getMessage();
        }
    }

    public static void addBackButton(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            String url = request.getContextPath();
            String backHtml = "<input type=\"button\" onclick=\"location.href=\'PLACEHOLDER\';\" value=\"HOME\" />";
            backHtml = backHtml.replace("PLACEHOLDER", url);
            response.getWriter().println(backHtml);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}

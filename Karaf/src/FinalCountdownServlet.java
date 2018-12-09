
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileInputStream;

@WebServlet("/FinalCountdownServlet1")
public class FinalCountdownServlet extends HttpServlet
{

/* Mohsen */
    private static final long UID = 1L;
    private static final String DMURL = "https://api.twitter.com/1.1/direct_messages/events/new.json";
    private static final String USERURL = "https://api.twitter.com/1.1/users/show.json?screen_name=";
    private static final String KEY = "SNVBoXaprhOqbYBGLqURFoluI";
    private static final String SECRET = "LMg90IlsINIqkDhvvL5QB7cswk4ORSd2ZSKUFqMksZJdYhmrFx";
    private static final String TOKEN = "1045543061000876033-NPJnTNqmvdoiBd2ptNuuntktg7ChUg";
    private static final String TOKENSECRET = "xE9JT6q6jSTBJvF70RYJnCkm5JTOLcJIeqJEU3PIPORqH";
    private static final String FOLLOWURL = "https://api.twitter.com/1.1/friendships/create.json?screen_name=";
    private static final String USERIDURL = "https://api.twitter.com/1.1/users/lookup.json?screen_name=";
    private static final String UNFOLLOWURL = "https://api.twitter.com/1.1/friendships/destroy.json?screen_name=";
    private static final String FOLLOWERS = "https://api.twitter.com/1.1/followers/list.json?cursor=-1&screen_name=";


/*Mohsen */
    public FinalCountdownServlet()
    {
        super();
    }

    public int sendPM(String username, String message, HttpServletResponse response)
    {
        String userID = checkUserID(username, response);
        int statusCode = 0;

        if (username.isEmpty() || userID.isEmpty())
        {
            ServletHelper.stringToResponse("Sending message encountered a failure", response);
            return statusCode;
        }

        String query = String.format("{ \"event\": { \"type\": \"message_create\", \"message_create\": { \"target\": { \"recipient_id\": \"%s\" }, \"message_data\": { \"text\": \"%s\" } } } }",
                userID, message);

        OAuthConsumer authConsumer = new CommonsHttpOAuthConsumer(KEY, SECRET);
        authConsumer.setTokenWithSecret(TOKEN, TOKENSECRET);

        try
        {
            HttpPost post = new HttpPost(DMURL);
            post.addHeader("Content-Type", "application/json");
            post.addHeader("event", query);
            StringEntity entity = new StringEntity(query);
            post.setEntity(entity);
            authConsumer.sign(post);

            HttpClient client = new DefaultHttpClient();
            HttpResponse response2 = client.execute(post);
            if (response2.getStatusLine().getStatusCode() == 200)
            {
                ServletHelper.stringToResponse("Message was delivered successfully", response);
            }
            else
            {
                ServletHelper.stringToResponse("Sending message encountered a failure", response);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (OAuthCommunicationException e)
        {
            e.printStackTrace();
        }
        catch (OAuthExpectationFailedException e)
        {
            e.printStackTrace();
        }
        catch (OAuthMessageSignerException e)
        {
            e.printStackTrace();
        }

        return statusCode;
    }

/*Mohsen */
    public int fetchUsers(String username, HttpServletResponse response)
    {
        int statusCode = 0;

        try
        {
            OAuthConsumer authConsumer = new CommonsHttpOAuthConsumer(KEY, SECRET);
            authConsumer.setTokenWithSecret(TOKEN, TOKENSECRET);


            HttpGet get = new HttpGet(USERURL + username);

            HttpClient httpClient = new DefaultHttpClient();

            authConsumer.sign(get);
            HttpResponse httpResponse = httpClient.execute(get);

            statusCode = httpResponse.getStatusLine().getStatusCode();
            String json = EntityUtils.toString(httpResponse.getEntity());

            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(json);
            ArrayList<String> arrayList = new ArrayList<String>();

            if (object.containsKey("screen_name"))
            {
                arrayList.add("UserName: " + object.get("screen_name").toString());
            }

            if (object.containsKey("followers_count"))
            {
                arrayList.add("Followers: " + object.get("followers_count").toString());
            }

            if (object.containsKey("friends_count"))
            {
                arrayList.add("Friends: " + object.get("friends_count").toString());
            }

            if (object.containsKey("status"))
            {
                JSONObject object2 = (JSONObject) object.get("status");
                if (object2.containsKey("text"))
                {
                    arrayList.add("status: " + object2.get("text").toString());
                }
            }

            ServletHelper.arrayToResponse(arrayList, response);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (OAuthCommunicationException e)
        {
            e.printStackTrace();
        }
        catch (OAuthExpectationFailedException e)
        {
            e.printStackTrace();
        }
        catch (OAuthMessageSignerException e)
        {
            e.printStackTrace();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return statusCode;
    }

    /*Yonas */
    public int getFollowerList(String username, HttpServletResponse response) {
        int statusCode = 0;

        try
        {
            OAuthConsumer authConsumer = new CommonsHttpOAuthConsumer(KEY, SECRET);
            authConsumer.setTokenWithSecret(TOKEN, TOKENSECRET);


            HttpGet get = new HttpGet(FOLLOWERS + username);

            HttpClient httpClient = new DefaultHttpClient();

            authConsumer.sign(get);
            HttpResponse httpResponse = httpClient.execute(get);

            statusCode = httpResponse.getStatusLine().getStatusCode();
            String json = EntityUtils.toString(httpResponse.getEntity());

            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(json);
            ArrayList<String> arrayList = new ArrayList<String>();

            JSONArray users = (JSONArray) object.get("users");
            System.out.println(users);
            for (int i = 0; i < users.size(); i++) {
                JSONObject userObject = (JSONObject) users.get(i);
                arrayList.add(userObject.get("created_at").toString());

            }


            ServletHelper.arrayToResponse(arrayList, response);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (OAuthCommunicationException e)
        {
            e.printStackTrace();
        }
        catch (OAuthExpectationFailedException e)
        {
            e.printStackTrace();
        }
        catch (OAuthMessageSignerException e)
        {
            e.printStackTrace();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return statusCode;
    }

    /*Yonas */
    public int followUser(String username, HttpServletResponse response)
    {
        int statusCode = 0;
        try
        {
            OAuthConsumer authConsumer = new CommonsHttpOAuthConsumer(KEY, SECRET);
            authConsumer.setTokenWithSecret(TOKEN, TOKENSECRET);

            String modifiedURL = FOLLOWURL + username + "&follow=true";

            HttpPost post = new HttpPost(modifiedURL);
            HttpClient httpClient = new DefaultHttpClient();
            authConsumer.sign(post);

            HttpResponse httpResponse = httpClient.execute(post);

            statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == 200)
            {
                response.getWriter().println(username + " is now followed");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (OAuthCommunicationException e)
        {
            e.printStackTrace();
        }
        catch (OAuthExpectationFailedException e)
        {
            e.printStackTrace();
        }
        catch (OAuthMessageSignerException e)
        {
            e.printStackTrace();
        }

        return statusCode;
    }

    /*Yonas*/
    public int unfollowUser(String username, HttpServletResponse response)
    {
        int statuscode = 0;
        try
        {
            OAuthConsumer authConsumer = new CommonsHttpOAuthConsumer(KEY, SECRET);
            authConsumer.setTokenWithSecret(TOKEN, TOKENSECRET);

            String modifiedQuery = UNFOLLOWURL + username;

            HttpPost post = new HttpPost(modifiedQuery);
            HttpClient client = new DefaultHttpClient();
            authConsumer.sign(post);

            HttpResponse httpResponse = client.execute(post);

            statuscode = httpResponse.getStatusLine().getStatusCode();

            if (statuscode == 200)
            {
                response.getWriter().println("Unfollowed " + username + " successfully");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (OAuthCommunicationException e)
        {
            e.printStackTrace();
        }
        catch (OAuthExpectationFailedException e)
        {
            e.printStackTrace();
        }
        catch (OAuthMessageSignerException e)
        {
            e.printStackTrace();
        }

        return statuscode;
    }

    /*Yonas*/
    private String checkUserID(String username, HttpServletResponse response)
    {
        String modifiedUrl = USERIDURL + username;
        try
        {
            OAuthConsumer authConsumer = new CommonsHttpOAuthConsumer(KEY, SECRET);
            authConsumer.setTokenWithSecret(TOKEN, TOKENSECRET);
            HttpGet get = new HttpGet(modifiedUrl);
            authConsumer.sign(get);

            HttpClient client = new DefaultHttpClient();
            HttpResponse httpResponse = client.execute(get);

            String json = EntityUtils.toString(httpResponse.getEntity());
            JSONParser parser = new JSONParser();
            JSONArray array = (JSONArray) parser.parse(json);


            for (int i = 0; i < array.size(); i++)
            {
                JSONObject object = (JSONObject) array.get(i);
                if (object.containsKey("id_str"))
                {
                    return object.get("id_str").toString();
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (OAuthCommunicationException e)
        {
            e.printStackTrace();
        }
        catch (OAuthExpectationFailedException e)
        {
            e.printStackTrace();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        catch (OAuthMessageSignerException e)
        {
            e.printStackTrace();
        }

        return "";
    }

    /* Dominic  */
    public String checkUserID(String username)
    {
        String modifiedUrl = USERIDURL + username;
        try
        {
            OAuthConsumer authConsumer = new CommonsHttpOAuthConsumer(KEY, SECRET);
            authConsumer.setTokenWithSecret(TOKEN, TOKENSECRET);
            HttpGet get = new HttpGet(modifiedUrl);
            authConsumer.sign(get);

            HttpClient client = new DefaultHttpClient();
            HttpResponse httpResponse = client.execute(get);

            String json = EntityUtils.toString(httpResponse.getEntity());
            JSONParser parser = new JSONParser();
            JSONArray array = (JSONArray) parser.parse(json);


            for (int i = 0; i < array.size(); i++)
            {
                JSONObject object = (JSONObject) array.get(i);
                if (object.containsKey("id_str"))
                {
                    return object.get("id_str").toString();
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (OAuthCommunicationException e)
        {
            e.printStackTrace();
        }
        catch (OAuthExpectationFailedException e)
        {
            e.printStackTrace();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        catch (OAuthMessageSignerException e)
        {
            e.printStackTrace();
        }

        return "";
    }

    /*Mohsen */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1> Team Final Countdown</h1>");
        ServletHelper.addBackButton(request, response);
        String button = request.getParameter("getRadio");
        String username1 = request.getParameter("getUsername");


        if (button.equals("Fetch Users"))
        {
            String username = request.getParameter("getUsername");
            int code = fetchUsers(username, response);
            Assert.assertEquals(code, 200);
        }
        else if(button.equals("Followers"))
        {
            String username = request.getParameter("getUsername");
            int code = getFollowerList(username, response);
            Assert.assertEquals(code, 200);

        }

        response.getWriter().println("<h1>Done!</h1>");



    }
    /*Mohsen */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1> Team Final Countdown</h1>");
        String button = request.getParameter("radios");
        String username1 = request.getParameter("Username");


        System.out.println("one");
        System.out.println(button);
        System.out.println("two");
        System.out.println(username1);
        System.out.println("three");


        if (button.equals("Follow User"))
        {
            String username = request.getParameter("Username");
            System.out.println(username);
            int code = followUser(username, response);
            Assert.assertEquals(code, 200);

        }
        else if (button.equals("Unfollow User"))
        {
            String username = request.getParameter("Username");
            int code = unfollowUser(username, response);
            Assert.assertEquals(code, 200);

        }
        else if (button.equals("Send Private Message"))
        {

            String username = request.getParameter("username");
            String message = request.getParameter("message");
            int code = sendPM(username, message, response);
            System.out.println(code);
            Assert.assertEquals(code, 200);

        }

        response.getWriter().println("<h1>Done!<h1>");
    }


}

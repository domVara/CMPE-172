<%
    response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
    response.setHeader("Pragma","no-cache"); //HTTP 1.0
    response.setDateHeader ("Expires", 0);
%>
<body>
<h2>Hello World!</h2>

<form action="/Karaf_war_exploded/FinalCountdownServlet1" method="post" >

    <INPUT TYPE="radio" NAME="radios" VALUE="Follow User">
    Follow
    <BR>
    <INPUT TYPE="radio" NAME="radios" VALUE="Unfollow User">
    Unfollow
    <BR>
    <input type="text" name="Username" width="30" />


    <INPUT TYPE="submit" VALUE="Submit">
</form>

<form action="/Karaf_war_exploded/FinalCountdownServlet1" method="get" >

    <INPUT TYPE="radio" NAME="getRadio" VALUE="Fetch Users">
    Fetch Users
    <BR>
    <INPUT TYPE="radio" NAME="getRadio" VALUE="Followers">
    Followers Account Creation
    <BR>
    <input type="text" name="getUsername" width="30" />
    <INPUT TYPE="submit" VALUE="Submit">



</form>



</body>
</html>

package other;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class AbstractController
{
    protected static Connection con;
    protected static Statement stmt;
    protected static ResultSet rs;
    protected String url;
    protected String user;
    protected String password;

    public AbstractController(String url, String user, String password)
    {
        this.url = url;
        this.user = user;
        this.password = password;
    }
    public String getUrl()
    {
        return url;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }
    public String getUser()
    {
        return user;
    }
    public void setUser(String user)
    {
        this.user = user;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
}

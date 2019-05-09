public class Customer
{
    private boolean loginStatus;

    public Customer()
    {
        loginStatus = false;
    }

    public boolean getLoginStatus()
    {
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus)
    {
        this.loginStatus = loginStatus;
    }
}

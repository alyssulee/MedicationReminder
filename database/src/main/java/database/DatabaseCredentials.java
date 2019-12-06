package database;

public interface DatabaseCredentials
{
    String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
    String Database_URL = "jdbc:mysql://localhost:3306/medreminder";
    String DB_Username = "newuser"; // Set to "root" or "newuser" depending on your root account username
    String DB_Password = "password";
    String DB_ServerName = "myDBHost.example.org";
}

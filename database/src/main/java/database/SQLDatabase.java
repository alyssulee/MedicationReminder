package database;

import java.sql.*;

public abstract class SQLDatabase implements DatabaseCredentials
{
    protected Connection connection;
    protected ResultSet resultSet;
    protected Statement statement;

    public SQLDatabase()
    {
        connect();
    }

    private void connect()
    {
        try
        {
            Class.forName(JDBC_Driver);
            connection = DriverManager.getConnection(Database_URL, DB_Username, DB_Password);
            statement = connection.createStatement();
            System.out.println("Connected to database");

        } catch (SQLException | ClassNotFoundException e)
        {
            System.out.println("Could not connect to database");
            e.printStackTrace();
        }
    }



}

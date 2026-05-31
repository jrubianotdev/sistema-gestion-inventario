import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL="jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USERNAME = "system";
    private static final String PASSWORD = "Ulibre";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

}

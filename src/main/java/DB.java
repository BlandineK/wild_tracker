import org.sql2o.*;

public class DB {
    public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:4567/wildlife_tracker", "blandine", "blanca");
}

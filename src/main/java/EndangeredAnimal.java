import org.sql2o.*;

//import java.sql.Connection;
import java.util.List;

public class EndangeredAnimal extends Animal {
    private String health;
    private String age;

    public boolean endangered;
    public String name;
    public int id;

    public static final String HEALTHY = "Healthy";
    public static final String ILL = "Ill";
    public static final String OKAY = "Okay";

    public static final String NEWBORN = "Newborn";
    public static final String YOUNG = "Young";
    public static final String ADULT = "Adult";

    public EndangeredAnimal(String name, String health, String age) {
        this.name = name;
        this.health = health;
        this.age = age;
        endangered = true;
    }

    public String getHealth() {

        return health;
    }

    public String getAge() {

        return age;
    }

    public void save() {
        try (Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO animals (name, health, age, endangered) VALUES (:name, :health, :age, :endangered)";
            con.createQuery(sql)
                    .addParameter("health", this.health)
                    .addParameter("age", this.age)
                    .addParameter("id", this.id)
                    .addParameter("endangered", this.endangered)
                    .executeUpdate();
        }
    }

    public static List<EndangeredAnimal> all() {
        String sql = "SELECT * FROM animals WHERE endangered = 'true'";
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).throwOnMappingFailure(false)
                    .executeAndFetch(EndangeredAnimal.class);
        }
    }

    public static EndangeredAnimal find(int id) {
        try (Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals where id=:id";
            EndangeredAnimal animal = con.createQuery(sql)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(EndangeredAnimal.class);
           return animal;
        }
    }

    public static EndangeredAnimal findByName(String name) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals where name=:name";
            EndangeredAnimal animal = con.createQuery(sql)
                    .addParameter("name", name)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(EndangeredAnimal.class);
            return animal;
        }
      }
    }

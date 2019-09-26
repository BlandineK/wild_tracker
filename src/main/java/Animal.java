//import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;
public class Animal {
    public int id;
    public String name;
    public  boolean endangered;
    public String Location;

    public int getId(){

        return id;
    }

    public String getName(){

        return name;
    }

    public boolean  isEndangered(){

        return endangered;
    }
    @Override
    public boolean equals(Object otherAnimal){
        if (!(otherAnimal instanceof Animal)) {
            return false;
        } else {
            Animal newAnimal = (Animal) otherAnimal;
            return this.id == newAnimal.id && this.name.equals(newAnimal.name);
        }
    }

    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO animals (name, endangered) VALUES (:name, :endangered)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("endangered", this.endangered)
                    .executeUpdate()
                    .getKey();
        }
    }


    public List<Sighting> getSightings(){
        try(Connection con = DB.sql2o.open()){
            String sql = "SELECT sightings.* FROM sightings JOIN animals_sightings ON (sightings.id = animals_sightings.sighting_id) WHERE animals_sightings.animal_id = :id";
            return con.createQuery(sql).addParameter("id", this.id).executeAndFetch(Sighting.class);
        }
    }
}




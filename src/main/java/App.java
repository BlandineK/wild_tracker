import spark.ModelAndView;
import spark.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
public class App {
    public static <RegularAnimal> void main(String[] args) {
        staticFileLocation("/public");
        String layout = "templates/layout.hbs";
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        setPort(port);

      get("/", (request, response) ->{
          Map<String, Object> model = new HashMap<String, Object>();
          model.put("animals", Animal.all());
          model.put("sightings", Sighting.all());
//          model.put("regular_animals", RegularAnimal.all());
          model.put("endangeredAnimals", EndangeredAnimal.all());
          model.put("template","template/index.hbs");
          return new ModelAndView(model, layout);
      }, new HandlebarsTemplateEngine());

      get("/animals/", (request, response) -> {
          Map<String, Object> model = new HashMap <String, Object>();
          model.put("animals", Animal.all());
          model.put("endangered_animals", EndangeredAnimal.all());
//          model.put("regular_animals", RegularAnimal.all());
          model.put("template", "template/animals.hbs");
          return new ModelAndView(model, layout);
      }, new HandlebarsTemplateEngine());

      get("animals/new-endangered", (request, response) -> {
          Map<String, Object> model = new HashMap<String, Object>();
          model.put("HEALTHY", EndangeredAnimal.Healthy);
          model.put("OKAY", EndangeredAnimal.Okay);
          model.put("ILL", EndangeredAnimal.Ill);
          model.put("Age_Young", EndangeredAnimal.Age_Young);
          model.put("Age_newborn", EndangeredAnimal.Age_newborn);
          model.put("Age_Adult", EndangeredAnimal.Age_Adult);
          model.put("template", "templates/animal-form.hbs");
          return new ModelAndView(model, layout);
      }, new HandlebarsTemplateEngine());

      post("/animals/new", (request, response) -> {
          Map<String, Object> model = new HashMap<String, Object>();
          String Name = request.queryParams("name");
          boolean endangered = request.queryParamsValues("endangered")!=null;

          if(endangered){
              String health = request.queryParams("health");
              String age = request.queryParams("age");
              EndangeredAnimal endangeredAnimal = new EndangeredAnimal(name, health, age);
              endangeredAnimal.save();
          } else{
              RegularAnimal regularAnimal = new RegularAnimal(name);
              regularAnimal.save();
          }
          response.redirect("/animals");
          return null;
      } , new HandlebarsTemplateEngine());

        get("/animals/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            RegularAnimal animal = RegularAnimal.find(Integer.parseInt(request.params("id")));
            model.put("animal", animal);
            model.put("rangerName", request.session().attribute("rangerName"));
            model.put("template", "templates/animal.hbs");
            return new ModelAndView(model, layout);
        }, new HandlebarsTemplateEngine());

        get("/sightings", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("rangerName", request.session().attribute("rangerName"));
            model.put("sightings", Sighting.allByDate());
            model.put("template", "templates/sightings.hbs");
            return new ModelAndView(model, layout);
        }, new HandlebarsTemplateEngine());


        get("/sightings/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("rangerName", request.session().attribute("rangerName"));
            model.put("template", "templates/sighting-form.hbs");
            return new ModelAndView(model, layout);
        }, new HandlebarsTemplateEngine());

        post("/sightings/new", (request, response) -> {
            String rangerName = request.session().attribute("rangerName");
            String location = request.queryParams("location");
            Sighting sighting = new Sighting(location, rangerName);
            sighting.save();
            response.redirect("/sightings");
            return null;
        }, new HandlebarsTemplateEngine());
    }
}


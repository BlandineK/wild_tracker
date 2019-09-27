import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
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

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("rangerName", request.session().attribute("rangerName"));
            model.put("animals", Animal.all());
            model.put("sightings", Sighting.all());
            model.put("regular_animals", RegularAnimal.all());
            model.put("endangeredAnimals", EndangeredAnimal.all());
            model.put("template", "template/index.hbs");
            return new ModelAndView(model, layout);
        }), new HandlebarsTemplateEngine();

        get("/animals", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("rangerName", request.session().attribute("rangerName"));
            model.put("animals", Animal.all());
            model.put("endangered_animals", EndangeredAnimal.all());
            model.put("template", "template/animals.hbs");
            return new ModelAndView(model, layout);
        }), new HandlebarsTemplateEngine();

        get("animals/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("HEALTHY", EndangeredAnimal.HEALTHY);
            model.put("OKAY", EndangeredAnimal.OKAY);
            model.put("ILL", EndangeredAnimal.ILL);
            model.put("YOUNG", EndangeredAnimal.YOUNG);
            model.put("NEWBORN", EndangeredAnimal.NEWBORN);
            model.put("ADULT", EndangeredAnimal.ADULT);
            model.put("rangerName", request.session().attribute("rangerName"));
            model.put("template", "templates/animal-form.hbs");
            return new ModelAndView(model, layout);
        }), new HandlebarsTemplateEngine();

        post("/animals/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String name = request.queryParams("name");
            boolean endangered = request.queryParamsValues("endangered") != null;

            if (endangered) {
                String health = request.queryParams("health");
                String age = request.queryParams("age");
                EndangeredAnimal endangeredAnimal = new EndangeredAnimal(name, health, age);
                endangeredAnimal.save();
            } else {

            }
            response.redirect("/animals");
            return null;
        }),

                get("/animals/:id", (request, response) -> {
                    Map<String, Object> model = new HashMap<String, Object>();
                    model.put("animal", animal);
                    model.put("rangerName", request.session().attribute("rangerName"));
                    model.put("template", "templates/animals.hbs");
                    return new ModelAndView(model, layout);
                }), new HandlebarsTemplateEngine();

        get("/sightings", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("rangerName", request.session().attribute("rangerName"));
            model.put("sightings", Sighting.allByDate());
            model.put("template", "templates/sightings.hbs");
            return new ModelAndView(model, layout);
        }), new HandlebarsTemplateEngine();


        get("/sightings/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("rangerName", request.session().attribute("rangerName"));
            model.put("template", "templates/sighting-form.hbs");
            return new ModelAndView(model, layout);
        }), new HandlebarsTemplateEngine();

        post("/sightings/new", (request, response) -> {
//            Map<String, Object> model = new HashMap<String, Object>();
//            String rangerName = request.session().attribute("rangerName");
//            String location = request.queryParams("location");
//            Sighting sighting = new Sighting(location, rangerName);
//            sighting.save();
//            response.redirect("/sightings");
//            return null;
//        }), new HandlebarsTemplateEngine();
//    }
}


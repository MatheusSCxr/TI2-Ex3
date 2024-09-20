package app;

import static spark.Spark.*;
import service.CarroService;


public class Principal {
	
	private static CarroService carroService = new CarroService();
	
    public static void main(String[] args) {
        port(8081); //porta do Spark
        
        staticFiles.location("/public");
        
        post("/carro/insert", (request, response) -> carroService.insert(request, response));

        get("/carro/:id", (request, response) -> carroService.get(request, response));
        
        get("/carro/list/:orderby", (request, response) -> carroService.getAll(request, response));

        get("/carro/update/:id", (request, response) -> carroService.getToUpdate(request, response));
        
        post("/carro/update/:id", (request, response) -> carroService.update(request, response));
           
        get("/carro/delete/:id", (request, response) -> carroService.delete(request, response));

             
    }
}
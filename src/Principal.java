import com.google.gson.Gson;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.Scanner;


public class Principal {
    public static void main(String[] args) throws IOException, InterruptedException {


        Busqueda busqueda = new Busqueda();

        Scanner lectura = new Scanner(System.in);
        System.out.println("""
                ********************************************
                Escriba las siglas de la Divisa de origen:
                USD = Dolar estadounidense
                EUR = Euro
                COP = Peso Colombiano
                MXN = Peso Mexicano
                *********************************************
                """);
        busqueda.setDivisaOrigen(lectura.nextLine());

        System.out.println("""
                ********************************************
                Escriba las siglas de la Divisa de destino:
                USD = Dolar estadounidense
                EUR = Euro
                COP = Peso Colombiano
                MXN = Peso Mexicano
                *********************************************
                """);
        busqueda.setDivisaDestino(lectura.nextLine());

        System.out.println("""
                ********************************************
                Escriba la cantidad  que desea convertir:
                *********************************************
                """);
        busqueda.setCantidad(lectura.nextInt());

        System.out.println("su moneda origen es " + busqueda.getDivisaOrigen() + " y va a convertir " + busqueda.getCantidad() + " a " + busqueda.getDivisaDestino());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v6.exchangerate-api.com/v6/b02a088d88862bdb1c42edab/pair/"+busqueda.getDivisaOrigen()+"/"+busqueda.getDivisaDestino()+"/"+busqueda.getCantidad()))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();
        //System.out.println(json);

       Gson gson = new Gson();
       //busqueda = gson.fromJson(json, Busqueda.class);
       BusquedaExchange miBusquedaExchange = gson.fromJson(json, BusquedaExchange.class);
       System.out.println("El resultado de convertir "+busqueda.getCantidad()+" "+busqueda.getDivisaOrigen()+ " a "+busqueda.getDivisaDestino()+ " es :"  + miBusquedaExchange.conversion_result() +" "+ busqueda.getDivisaDestino());


    }
}


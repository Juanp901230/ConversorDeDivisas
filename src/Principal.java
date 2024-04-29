import com.google.gson.Gson;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.Scanner;


public class Principal {
    public static void main(String[] args) throws IOException, InterruptedException {


        Busqueda nuevaBusqueda = new Busqueda();

        Scanner lectura = new Scanner(System.in);

        System.out.print("""
                ********************************************
                Escriba las siglas de la Divisa de origen:
                USD = Dolar estadounidense
                EUR = Euro
                COP = Peso Colombiano
                MXN = Peso Mexicano
                
                *********************************************
                """);
        nuevaBusqueda.setDivisaOrigen(lectura.next());
       // switch (nuevaBusqueda.getDivisaOrigen()){}   queda pendiente para probar con casos switch

            System.out.print("""
                    ********************************************
                    Escriba las siglas de la Divisa de destino:
                    USD = Dolar estadounidense
                    EUR = Euro
                    COP = Peso Colombiano
                    MXN = Peso Mexicano
                    *********************************************
                    """);

        nuevaBusqueda.setDivisaDestino(lectura.next());
        if (nuevaBusqueda.getDivisaDestino() == null){
            System.out.println("Lo sentimos, no se reconoció la divisa de Destino. por favor intente con uno de la lista.");
        }

        System.out.print("""
                ********************************************
                Escriba la cantidad  que desea convertir:
                *********************************************
                """);
        nuevaBusqueda.setCantidad(lectura.nextInt());


        System.out.print("su moneda origen es " + nuevaBusqueda.getDivisaOrigen() + " y va a convertir " + nuevaBusqueda.getCantidad() + " " + nuevaBusqueda.getDivisaOrigen() + " a " + nuevaBusqueda.getDivisaDestino());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v6.exchangerate-api.com/v6/b02a088d88862bdb1c42edab/pair/" + nuevaBusqueda.getDivisaOrigen() + "/" + nuevaBusqueda.getDivisaDestino() + "/" + nuevaBusqueda.getCantidad()))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();
        /* System.out.println(json);  esta linea imprime el json obtenido del API  */


        Gson gson = new Gson();
        BusquedaExchange miBusquedaExchange = gson.fromJson(json, BusquedaExchange.class);
        System.out.println(nuevaBusqueda.getDivisaDestino());


        System.out.print("El resultado de convertir " + nuevaBusqueda.getCantidad() + " " + nuevaBusqueda.getDivisaOrigen() + " a " + nuevaBusqueda.getDivisaDestino() + " es :" + miBusquedaExchange.conversion_result() + " " + nuevaBusqueda.getDivisaDestino());


    }
}



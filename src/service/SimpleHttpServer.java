/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.sun.net.httpserver.*;
import facade.SpectacleFacade;


import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleHttpServer {

    public SimpleHttpServer() {

    }
    
    static HttpServer server;
    //protected static MemberRepository rep = new DBMemberRepository();
    public static void start() {
        try {
            server = HttpServer.create();
            
            server.bind(new InetSocketAddress(8765), 0);

            HttpContext context = server.createContext("/get", new EchoHandler());
            //context.setAuthenticator(new Auth());

            
            server.setExecutor(null);
            server.start();
            System.out.println("Server Started in other thread!");
        } catch (IOException ex) {
            Logger.getLogger(SimpleHttpServer.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Server not started");
        }
    }

    public static void stop() {
        server.stop(0);        
    }

    static class EchoHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            StringBuilder builder = new StringBuilder();
            builder.append(SpectacleFacade.getAllEquipmentInJson());
            
            String answer2 = builder.toString();
            String answer = new String(answer2.getBytes(), "UTF-8");
            
            
            byte[] bytes = answer.toString().getBytes();
            exchange.sendResponseHeaders(200, bytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }
}

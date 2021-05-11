package videoServer.RequestApi;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import videoServer.db.SQL_handler;
import videoServer.streamApi.streamData;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;


public class ServerHttpHandler implements HttpHandler {

    private HttpExchange exchange;
    private final SQL_handler dbAPI = new SQL_handler();
    private Thread executor;


    public SQL_handler getDbAPI() {
        return dbAPI;
    }

    private void ErrorResponseSender(String ErrorMessage, int eCode)
    {
        try {
            exchange.sendResponseHeaders(eCode, ErrorMessage.getBytes().length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputStream os = exchange.getResponseBody();
        try {
            os.write(ErrorMessage.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void responseSender(String response) {
        try {
            exchange.sendResponseHeaders(200, response.getBytes().length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputStream os = exchange.getResponseBody();
        try {
            os.write(response.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void ProcessPostRequest(String endpoint) {
        //add new POST method functionality here

        if("video".equals(endpoint))
        {
            InputStream body = exchange.getRequestBody();
            JSONParser jsonParser = new JSONParser();
            JSONObject bodyJson=null;
            try {
                bodyJson = (JSONObject)jsonParser.parse(
                        new InputStreamReader(body, "UTF-8"));
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }

            if(bodyJson != null) {
                int videoId;
                if (bodyJson.get("videoId") instanceof Long)
                {
                    videoId = ((Long) bodyJson.get("videoId")).intValue();
                    String db_response = dbAPI.selectedVideo(videoId);
                    if (db_response.equals("")){
                        String message = "The videoId: "+videoId+" doesn't exist";
                        ErrorResponseSender(message, 404);
                }
                    else{

                        System.out.println("this is the video that will be streamed: " +db_response);

                        if (executor!=null && executor.isAlive())
                        {
                            executor.interrupt();
                        }
                        streamData stream = new streamData(db_response);
                        executor = stream.getRtpStreamThread();
                        System.out.println("started video streaming....");
                        executor.setDaemon(true);
                        executor.start();

                        responseSender("Stream started....");
                        System.out.println("started streaming....");
                    }
                }
                else{
                    ErrorResponseSender("wrong videoID format in body", 400);
                }
            }
            else{
                ErrorResponseSender("Body doesn't exist, wrong request", 400);
            }

        }
        else{
            ErrorResponseSender("Wrong endpoint", 404);
        }
    }

    private void createResponseToGetRequest(String endpoint) {
        //add here new cases to else if for endpoint handling

        if ("test".equals(endpoint)) {
            String responseMessage = "Hi there!, This is a test.";
            responseSender(responseMessage);

        } else if ("listvideos".equals(endpoint)) {
            String responseMessage = "List of videos\n";
            String db_query = dbAPI.listAllVideos();
            responseSender(responseMessage+db_query);
        }
        //create extra endpoints here
        else{
            String ErrorM = "No corresponding Endpoint";
            ErrorResponseSender(ErrorM,404);

        }

    }
    private String getEndpoint(URI requestURI)
    {
        String pathURI = requestURI.getPath();
        String [] elements = pathURI.split("/");
        return elements[elements.length-1];
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        System.out.println("Got new http request....");
        System.out.println("Exchange Information: URI: "+exchange.getRequestURI().toString()+
                "\nMethod: "+exchange.getRequestMethod());
        this.exchange = exchange;
        URI requestURI = exchange.getRequestURI();

        String requestMethod = exchange.getRequestMethod();
        if (requestMethod.equals("GET"))
        {
            String endpoint = getEndpoint(requestURI);
            createResponseToGetRequest(endpoint);
        }
        else if(requestMethod.equals("POST"))
        {
            String endpoint = getEndpoint(requestURI);
            ProcessPostRequest(endpoint);

        }
        else
        {
            String errorM = "No endpoint with method: "+requestMethod;
            ErrorResponseSender(errorM, 404);
        }
    }
}
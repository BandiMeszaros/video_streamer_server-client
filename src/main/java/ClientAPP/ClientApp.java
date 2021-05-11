package ClientAPP;

import okhttp3.*;

import java.io.IOException;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        Scanner inputData = new Scanner(System.in);
        System.out.println("Hi there! This is my videoStreamer");
        boolean loop = true;
        while (loop) {
            System.out.println("Waiting for commands..");
            String command = inputData.nextLine();
            if (command.equals("listvideos"))
            {
                String response = null;
                try {
                    response = list_videos();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(response);
            }
            else if (command.equals("exit"))
            {
                loop = false;
                System.out.println("Exiting");
            }
            else if (command.equals("video"))
            {
                System.out.println("Which video, please provide videoId: ");
                Integer videoId = inputData.nextInt();
                inputData.nextLine();
                String response = null;
                try {
                    response = video(videoId);
                    Thread.currentThread().sleep(500);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(response);
                clientUIThread UIThread = new clientUIThread();
                UIThread.start();
            }

            else if (command.equals("test"))
            {
                String response = null;
                try {
                    response = test();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(response);
            }
            else{
                System.out.println("No corresponding command....");
                System.out.printf("use onne of these:\nlistvideos\nvideo\nexit\ntest\n");
            }
        }

    }

    private static String test() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:8500/test")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.body().string();

    }

    private static String list_videos() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:8500/listvideos")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.body().string();
    }

    private static String video(Integer videoId) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"videoId\" : "+videoId+"}");
        Request request = new Request.Builder()
                .url("http://localhost:8500/video")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.body().string();
    }


}

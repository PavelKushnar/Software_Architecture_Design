/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 *
 * @author Pavel_User
 */
public class WebInfoService {

    public WebInfo getWebInfo(String name) throws IOException {

        String url = "https://www.kinopoisk.ru/s/type/spectacle/find/" + name + "/m_act[year]/";
        String answer = "";
        answer = getUrl(url);

        if (answer.equals("")) {
            return null;
        }
        String kinpoiskId = parseId(answer);
        //System.out.println(kinpoiskId);
        //String kinpoiskId = "301";
        if (kinpoiskId != "error") {
            String url2 = "http://getmovie.cc/api/videos.json?kinopoisk_id="
                    + kinpoiskId + "&api_token=04ba3087d351da72df53dd5682d2c2d4&r=json";
            String answer2 = "";
            answer2 = new String(getUrl2(url2).getBytes(), "UTF-8");
            if (answer2.equals("")) {
                return null;
            }
            System.out.println(answer2);

            //System.out.println(answer);
            WebInfo wi = new WebInfo();
            ArrayList<String> resu = parseWebInfo(answer2);
            wi.setActors(resu.get(0));
            wi.setActors(resu.get(1));
            wi.setTitle_ru(resu.get(2));
            wi.setTitle_en(resu.get(3));
            wi.setYear(resu.get(4));
            wi.setCountry(resu.get(5));
            wi.setDescription(resu.get(6));
            wi.setTranslator(resu.get(7));
            wi.setScreenwriter(resu.get(8));
            wi.setDirector(resu.get(9));
            wi.setProducer(resu.get(10));
            wi.setOperator(resu.get(11));
            wi.setDesign(resu.get(12));
            wi.setEditor(resu.get(13));
            wi.setGenre(resu.get(14));
            wi.setComposer(resu.get(15));
            wi.setTime(resu.get(16));
            wi.setType(resu.get(17));
            wi.setCamrip(resu.get(18));
            wi.setKinopoisk_id(resu.get(19));
            wi.setKp_rating(resu.get(20));
            wi.setImdb_rating(resu.get(21));
            wi.setEmbed_url(resu.get(22));
            wi.setPoster_big(resu.get(23));

            return wi;
        }
        WebInfo wi = new WebInfo();
        wi.setPoster_big("/resources/404.png");
        return wi;
    }

    private ArrayList<String> parseWebInfo(String jsonLine) throws UnsupportedEncodingException {
        ArrayList<String> resu = new ArrayList<>();
        String[] s = {"actors", "actors", "title_ru", "title_en",
            "year", "country", "description", "translator", "screenwriter", "director", "producer", "operator",
            "design", "editor", "genre", "composer", "time", "type", "camrip", "kinopoisk_id", "kp_rating", "imdb_rating", "embed_url", "poster_big"};

        JsonElement jelement = new JsonParser().parse(jsonLine);
        JsonObject jobject = jelement.getAsJsonObject();
        
        
        
        /*if(jobject.getAsString().equals("false"))
            System.out.println(jobject.getAsString() + " good");
        */
        
        if (!jobject.get("response").isJsonObject()) {
            for (String ss : s) {
                //System.out.println("dsfsdfsdfsdfsdfsdf");
                if (ss.equals("poster_big")) {
                    resu.add("/resources/404.png");
                } else {
                    resu.add("...");
                }

            }
        } else {
            jobject = jobject.get("response").getAsJsonObject();
        
            JsonArray jarray = jobject.getAsJsonArray("items");
            jobject = jarray.get(0).getAsJsonObject();
            //jobject = jobject.get("temp").getAsJsonObject();
            /*
        for (int i = 0; i < 24; i++) {
            String ss = s[i];
        }*/
            for (String ss : s) {
                if (!jobject.get(ss).isJsonNull()) {
                    String res = jobject.get(ss).getAsString();
                    resu.add(res);
                } else {
                    resu.add(" ");
                }
            }/*
        String res = jobject.get("actors").getAsString();
        resu.add(res);
        res = jobject.get("actors").getAsString();
        resu.add(res);
        res = jobject.get("title_ru").getAsString();
        resu.add(res);
        res = jobject.get("title_en").getAsString();
        resu.add(res);
        res = jobject.get("year").getAsString();
        resu.add(res);
        res = jobject.get("country").getAsString();
        resu.add(res);
        res = jobject.get("description").getAsString();
        resu.add(res);
        
        if(jobject.get("translator").getAsString()!=null)
        res = jobject.get("translator").getAsString();
        resu.add(res);
        
        
        res = jobject.get("screenwriter").getAsString();
        resu.add(res);
        res = jobject.get("director").getAsString();
        resu.add(res);
        res = jobject.get("producer").getAsString();
        resu.add(res);
        res = jobject.get("operator").getAsString();
        resu.add(res);
        res = jobject.get("design").getAsString();
        resu.add(res);
        res = jobject.get("editor").getAsString();
        resu.add(res);
        res = jobject.get("genre").getAsString();
        resu.add(res);
        res = jobject.get("composer").getAsString();
        resu.add(res);
        res = jobject.get("time").getAsString();
        resu.add(res);
        res = jobject.get("type").getAsString();
        resu.add(res);
        res = jobject.get("camrip").getAsString();
        resu.add(res);
        res = jobject.get("kinopoisk_id").getAsString();
        resu.add(res);
        res = jobject.get("kp_rating").getAsString();
        resu.add(res);
        res = jobject.get("imdb_rating").getAsString();
        resu.add(res);
        res = jobject.get("embed_url").getAsString();
        resu.add(res);
        res = jobject.get("poster_big").getAsString();
        resu.add(res);*/
 /*Charset cset = Charset.forName("Cp1251");
		ByteBuffer buf = cset.encode(res);
		byte[] b = buf.array();
		String str = new String(b);*/
            //String rescod = new String(res.getBytes(),"UTF-8");
        }
        return resu;
    }

    private String parseId(String str) {

        int i = str.indexOf("data-id=\"");
        if (i != -1) {
            int k = str.indexOf("\"", i + 9);
            String dRes = str.substring(i + 9, k);
            return dRes;
        }
        return "error";
    }

    private static String getUrl(String url) throws MalformedURLException, IOException {

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;

        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            //System.out.println(inputLine);
        }
        in.close();
        //System.out.println(response);

        return response.toString();
    }

    private static String getUrl2(String url) throws MalformedURLException, IOException {

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        connection.addRequestProperty("Accept-Language", "ru;q=0.5");
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;

        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            //System.out.println(inputLine);
        }
        in.close();
        //System.out.println(response);
        return response.toString();
    }
}

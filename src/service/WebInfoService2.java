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
public class WebInfoService2 {
    
    public WebInfo2 getWebInfo(String name) throws IOException {
        name = new String(name.getBytes(), "MacCyrillic");
        String new_str = name.replace (' ', '+');
        String url = "http://www.imdb.com/find?ref_=nv_sr_fn&q=" + new_str + "&s=all";
        String answer = "";
        answer = getUrl(url);
        
        if (answer.equals("")) {
            return null;
        }
        String kinpoiskId = parseId(answer);
        System.out.println(kinpoiskId);
        //System.out.println(kinpoiskId);
        //String kinpoiskId = "301";

        if (kinpoiskId != "error") {
            String url2 = "https://api.themoviedb.org/3/find/"
                    + kinpoiskId + "?api_key=8eb1cee718316d2a46177a235db68e6f&language=en-US&external_source=imdb_id";
            String answer2 = "";
            answer2 = new String(getUrl2(url2).getBytes(), "UTF-8");
            if (answer2.equals("")) {
                return null;
            }
            System.out.println(answer2);

            //System.out.println(answer);
            String tmdbid = parseIdtmdb(answer2);
            System.out.println(tmdbid);
            if(tmdbid!="bad"){
            url2 = "https://api.themoviedb.org/3/movie/"
                    + tmdbid + "?api_key=8eb1cee718316d2a46177a235db68e6f";
            answer2 = "";
            answer2 = new String(getUrl2(url2).getBytes(), "UTF-8");
            if (answer2.equals("")) {
                return null;
            }
            System.out.println(answer2);

            //System.out.println(answer);
            WebInfo2 wi = new WebInfo2();
            ArrayList<String> resu = parseWebInfo(answer2);
            System.out.println(resu.get(0));
            
            wi.setBudget(resu.get(0));
            wi.setGenres(resu.get(1));
            wi.setOriginal_language(resu.get(2));
            wi.setOriginal_title(resu.get(3));
            wi.setOverview(resu.get(4));
            wi.setPopularity(resu.get(5));
            wi.setPoster_path(resu.get(6));
            wi.setProduction_companies(resu.get(7));
            wi.setProduction_countries(resu.get(8));
            wi.setRelease_date(resu.get(9));
            wi.setRevenue(resu.get(10));
            wi.setTagline(resu.get(11));
            wi.setVote_average(resu.get(12));
            
            return wi;
            }
        }
        WebInfo2 wi = new WebInfo2();
        wi.setPoster_path("/resources/404.png");
        return wi;
    }
    
    private ArrayList<String> parseWebInfo(String jsonLine) throws UnsupportedEncodingException {
        ArrayList<String> resu = new ArrayList<>();
        String[] s = {"budget", "genres", "original_language", "original_title", "overview",
            "popularity", "poster_path", "production_companies", "production_countries", "release_date",
            "revenue", "tagline", "vote_average"};
        
        JsonElement jelement = new JsonParser().parse(jsonLine);
        JsonObject jobject = jelement.getAsJsonObject();
        
        for (String ss : s) {
            System.out.println(ss);
            if (!jobject.get(ss).isJsonNull()) {
                
                if (ss == "genres" || ss == "production_companies" || ss == "production_countries") {
                    JsonArray jarray = jobject.getAsJsonArray(ss);
                    if (jarray.size() != 0) {
                        String res = "";
                        for (int i = 0; i < jarray.size(); i++) {
                            JsonObject jobject2 = jarray.get(i).getAsJsonObject();
                            if (!jobject2.get("name").isJsonNull()) {
                                res = res + ", " + jobject2.get("name").getAsString();
                            } else {
                            }
                        }
                        System.out.println(res);
                        resu.add(res);
                    } else {
                        resu.add("...");
                    }
                }else{
                String res = jobject.get(ss).getAsString();
                if(ss=="poster_path")
                    res = "http://image.tmdb.org/t/p/original/"+res;
                resu.add(res);
                System.out.println(res);
                }
            } else {
                resu.add(" ");
            }
        }
        
        return resu;
    }
    
    private String parseId(String str) {
        
        int i = str.indexOf("<a href=\"/title/");
        if (i != -1) {
            int k = str.indexOf("/", i + 16);
            String dRes = str.substring(i + 16, k);
            return dRes;
        }
        return "error";
    }
    
    private String parseIdtmdb(String jsonLine) {
        
        JsonElement jelement = new JsonParser().parse(jsonLine);
        JsonObject jobject = jelement.getAsJsonObject();
        JsonArray jarray = jobject.getAsJsonArray("movie_results");
        //jobject = jarray.get(0).getAsJsonObject();

        if (jarray.size() != 0) {
            jobject = jarray.get(0).getAsJsonObject();
            if (!jobject.get("id").isJsonNull()) {
                String res = jobject.get("id").getAsString();
                return res;
            } else {
                return "bad";
            }
        }
        return "bad";
        
    }
    
    private static String getUrl(String url) throws MalformedURLException, IOException {
        System.out.println(url);
        url = new String(url.getBytes(), "MacCyrillic");
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        connection.addRequestProperty("Accept-Language", "ru;q=0.5");
        connection.setRequestMethod("GET");
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
        System.out.println(response.toString());
        return response.toString();
    }
    
    private static String getUrl2(String url) throws MalformedURLException, IOException {
        
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        connection.addRequestProperty("Accept-Language", "en;q=0.5");
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

package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Search {
    private static StringBuilder sb;

    private static String getString(String input, String data) // API에서 필요한 문자 자르기.
    {
        String[] dataSplit = data.split("{" + input + "}");
        String[] dataSplit2 = dataSplit[1].split("\"" + input + "\"");
        return dataSplit2[0];
    }

    public List<Movie> getMovieList(String clientId, String clientSecret, String movieTitle) {
        int display = 100;
        List<Movie> _movieList = new ArrayList<Movie>();

        try {
            String text = URLEncoder.encode(movieTitle, "utf-8");
            String apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + text + "&display=" + display; //+ "&";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }

            br.close();
            con.disconnect();
            String data = sb.toString();
            String[] array;
            array = data.split("\"");

            String title = "";
            String link = "";
            int pubDate = 0;
            String director = "";
            String actor = "";
            float userRating = (float) 0;
            Bitmap image;

            url = new URL("http://www.xpressengine.com/files/attach/images/122/749/385/021/10a17c02234f8027f0b2def8df5f1415.png");
            URLConnection conn = url.openConnection();
            conn.connect();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            image = BitmapFactory.decodeStream(bis);
            bis.close();

            for (int i = 0; i < array.length; i++) {
                if (array[i].equals("title"))
                    title = array[i + 2];
                if (array[i].equals("link"))
                    link = array[i + 2];
                if (array[i].equals("image")) {
                    url = new URL(array[i + 2]);
                    conn = url.openConnection();
                    conn.connect();
                    bis = new BufferedInputStream(conn.getInputStream());
                    image = BitmapFactory.decodeStream(bis);
                    bis.close();
                }
                if (array[i].equals("pubDate"))
                    pubDate = Integer.parseInt(array[i + 2]);
                if (array[i].equals("director"))
                    director = array[i + 2];
                if (array[i].equals("actor"))
                    actor = array[i + 2];
                if (array[i].equals("userRating")) {
                    userRating = Float.parseFloat(array[i + 2]);
                    _movieList.add(new Movie(title, link, image, pubDate, director, actor, userRating));
                }
            }

        } catch (Exception e) {

        }

        return _movieList;
    }
}



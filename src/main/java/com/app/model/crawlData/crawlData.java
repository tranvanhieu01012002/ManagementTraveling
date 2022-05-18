package com.app.model.crawlData;

import com.app.model.Travel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class crawlData {
    private  static HttpURLConnection connection;

    public List<Travel> callApi() {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        List<Travel> list = new ArrayList<>();
        try {
            URL url = new URL("https://61cfb80065c32600170c7fa8.mockapi.io/test");
            connection = (HttpURLConnection) url.openConnection();
            //request setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // status of connect
            int status = connection.getResponseCode();
            if (status != 200) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
//            System.out.println(responseContent);
//            for(int i = 0; i<=re)
            list = stringParser(responseContent.toString());
        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            //final connect we need to disconnect to save data
            connection.disconnect();
        }
        return list;
    }
    public  static List<Travel> stringParser(String res){
        List<Travel> list = new ArrayList<>();
        JSONArray array = new JSONArray(res);
        for(int i = 0; i <array.length();i++){
            JSONObject item = array.getJSONObject(i);
            String name = item.getString("name");
            String time = item.getString("time");
            String start = item.getString("start_end");
            String img = item.getString("img");
            String price = item.getString("price");
            listTravel( convertToTravel(name,time,start,img,price),list);
        }
        return  list;
    }
    public static Travel convertToTravel(String name, String time, String start,String img ,String price){
           Travel travel = new Travel();
           travel.setImg(img);
           travel.setName(name);
           travel.setTime(time);
           travel.setStart_end(start);
           travel.setPrice(price);
        return travel;
    }
    public static List<Travel> listTravel(Travel travel, List<Travel> list){
        list.add(travel);
        return list;
    }

}

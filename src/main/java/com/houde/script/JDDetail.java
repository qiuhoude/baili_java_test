package com.houde.script;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JDDetail {


    public static void main(String[] args) throws Exception {
        Document document = Jsoup.connect("https://earthquake.usgs.gov/earthquakes/eventpage/usp000fbdn/focal-mechanism").get();
        System.out.println(document.toString());


    }
}
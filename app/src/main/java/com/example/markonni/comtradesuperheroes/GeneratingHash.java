package com.example.markonni.comtradesuperheroes;

import android.support.annotation.NonNull;

import com.example.markonni.comtradesuperheroes.superhero.Superhero;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneratingHash {

    private static final String PUBLIC_KEY = "3e3ee21dbd93dc9a9031bdb12698ec13";
    private static final String PRIVATE_KEY = "53720052d82d6550731525fd7300edc2a7f018ed";
    private static final String DEFAULT_API_VERSION = "v1";


    private static String url = "http://gateway.marvel.com/" + DEFAULT_API_VERSION + "/public/";

    public String getCharactersUrl() {
        return url + "characters" + createHash();
    }

    public String getComicsUrl(int superheroId) {
        return url + "characters/" + superheroId +"/comics" + createHash();
    }

    public String getSeriesUrl(int superheroId) {
        return url + "characters/" + superheroId +"/series" + createHash();
    }

    public String getStoriesUrl(int superheroId) {
        return url + "characters/" + superheroId +"/stories" + createHash();
    }


    public String createHash() {

        try {
            String timeStamp = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").format(new Date());

            String whatToDigest = timeStamp + PRIVATE_KEY + PUBLIC_KEY;

            String original = whatToDigest;
            MessageDigest md = null;

            md = MessageDigest.getInstance("MD5");

            md.update(original.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }

            return "?ts=" + timeStamp + "&apikey=" + PUBLIC_KEY + "&hash=" + sb;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;

    }

}

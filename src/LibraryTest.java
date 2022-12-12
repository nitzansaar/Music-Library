package src;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class LibraryTest {


    @Test
    void removeDefDuplicates() {
        ArrayList<Song> songs = new ArrayList<>();
        Song s1 = new Song();
        s1.setTitle("Hello");
        s1.setAlbum(new Album("Artist 1"));
        s1.setArtist(new Artist("Album 1"));
        Song s2 = new Song();
        s2.setTitle("Hello");
        s2.setAlbum(new Album("Artist 2"));
        s2.setArtist(new Artist("Album 2"));
        Song s3 = new Song();
        s3.setTitle("Hello");
        s3.setAlbum(new Album("Arti 1"));
        s3.setArtist(new Artist("Album 1"));
        Song s4 = new Song("Hello");
        s4.setTitle("Hello");
        s4.setAlbum(new Album("Artist 1"));
        s4.setArtist(new Artist("Album 1"));
        songs.add(s1);
        songs.add(s2);
        songs.add(s3);
        songs.add(s4);
        for(int i = 1; i < songs.size(); i++){
            for(int j = 0; j < i; j++){
                if(songs.get(i).equals(songs.get(j))){
                    System.out.print("These are definitely duplicates\n" +
                            songs.get(i).details() +  "\n"  + songs.get(j).details());

                }
            }
        }
    }

    @Test
    void removePossibleDuplicates() {
        ArrayList<Song> songs = new ArrayList<>();
        Song s1 = new Song();
        s1.setTitle("Duplicate");
        s1.setAlbum(new Album("Artist 1"));
        s1.setArtist(new Artist("Album 1"));
        Song s2 = new Song();
        s2.setTitle("Hello");
        s2.setAlbum(new Album("Artist 2"));
        s2.setArtist(new Artist("Album 2"));
        Song s3 = new Song();
        s3.setTitle("Duplicate");
        s3.setAlbum(new Album("Artist 1"));
        s3.setArtist(new Artist("Album 1"));
        Song s4 = new Song("Hello");
        s4.setTitle("Hello");
        s4.setAlbum(new Album("Artist 1"));
        s4.setArtist(new Artist("Album 1"));
        songs.add(s1);
        songs.add(s2);
        songs.add(s3);
        songs.add(s4);
        for(int i = 1; i < songs.size(); i++){
            for(int j = 0; j < i; j++){
                if(songs.get(i).possiblyEquals(songs.get(j))){
                    System.out.print("These are possibly duplicates\n" +
                            songs.get(i).details() +  "\n"  + songs.get(j).details());

                }
            }
        }

    }
}
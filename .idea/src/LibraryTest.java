import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    @Test
    void parseXML() {

       try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("music-library.xml"));

            Element root = doc.getDocumentElement();
            NodeList songList = root.getElementsByTagName("song");
            Node currentNode, subNode;

            Song currentSong;

            for (int i = 0; i < songList.getLength(); i++) {
                currentNode = songList.item(i);

                NodeList children = currentNode.getChildNodes();
                currentSong = new Song();
                for (int j = 0; j < children.getLength(); j++) {
                    subNode = children.item(j);
                    String tempTitle = null;
                    String tempArt = null;
                    String tempAlb = null;
                    if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                        if (subNode.getNodeName().equals("title")) {
                            tempTitle = Library.getContent(subNode).stripTrailing().stripLeading();
                            currentSong.setTitle(tempTitle);
                            System.out.println(currentSong.title);
                        }if(subNode.getNodeName().equals("artist")){
                            tempArt = Library.getContent(subNode).stripTrailing().stripLeading();
                            Artist artist = new Artist(tempArt);
                            currentSong.setArtist(artist);
                            System.out.println(currentSong.artist);
                        }if(subNode.getNodeName().equals("album")){
                            tempAlb = Library.getContent(subNode).stripTrailing().stripLeading();
                            Album album = new Album(tempAlb);
                            currentSong.setAlbum(album);
                            System.out.println(currentSong.album);
                        }
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("Parsing error:" + e);
        }
    }

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

    @Test
    void parseJSON() {
        String string;
        try {
            Scanner sc = new Scanner(new File("music-library.json"));
            sc.useDelimiter("\\Z");
            string = sc.next();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(string);
            JSONObject jsonObject = (JSONObject)obj;
            JSONArray songArray = (JSONArray)jsonObject.get("songs");
            for (Object song : songArray) {
                Song s = new Song();
                JSONObject jsong = (JSONObject)song;
                s.setTitle((String) jsong.get("title"));
                String artistName = jsong.get("artist").toString();
                Artist artist = new Artist(artistName.substring(9, artistName.length()-13));
                s.setArtist(artist);
                String albumName = jsong.get("album").toString();
                Album album = new Album(albumName.substring(9, albumName.length()-13));
                s.setAlbum(album);
                System.out.println(s.details());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + e);
        } catch (ParseException e1) {
            System.out.println("Parser error");
        }
    }

    @Test
    void playlistToXMLFile() {

    }
}
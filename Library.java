import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Library {

    protected static Library library = new Library();
    private static ArrayList<Song> songs;
    private static ArrayList<Album> albums;
    private static ArrayList<Artist> artists;


    public Library() {
        songs = new ArrayList<Song>();
        albums = new ArrayList<Album>();
        artists = new ArrayList<Artist>();
    }
    public boolean findSong(Song s) {
        return songs.contains(s);
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void addSong(Song s) {
        songs.add(s);
    }
    public void removeSong(Song s){
        songs.remove(s);
    }
    public void addAlbum(Album a) {
        albums.add(a);
    }
    public void addArtist(Artist a) {
        artists.add(a);
    }

    /*
    Allows user to remove songs that are "possibly duplicates"
    - meaning they have the same name and either artist or album is the same
    - or they have the same artist and album, and the names are the same if converted to lower case and punctuation is ignored
     */
    public static void removePossibleDuplicates(){
        Scanner sc = new Scanner(System.in);
        for(int i = 1; i < songs.size(); i++){
            for(int j = 0; j < i; j++){
                if(songs.get(i).possiblyEquals(songs.get(j))){
                    System.out.print("These are possibly duplicates\n" +
                            songs.get(i).details() +  "\n"  + songs.get(j).details() + "\nRemove duplicate?(Y/N): ");
                    String response = sc.next();
                    if(response.equalsIgnoreCase("Y")){
                        library.removeSong(songs.get(i));
                        System.out.println("Done");
                        i--;
                    }

                }
            }
        }
    }
    /*
    Allows user to remove songs that are "definitely duplicates"
    - means that the songs have the same title, artist, and album
     */
    public static void removeDefDuplicates(){
        Scanner sc = new Scanner(System.in);
        for(int i = 1; i < songs.size(); i++){
            for(int j = 0; j < i; j++){
                if(songs.get(i).equals(songs.get(j))){
                    System.out.print("These are definitely duplicates\n" +
                            songs.get(i).details() +  "\n"  + songs.get(j).details() + "\nRemove duplicate?(Y/N): ");
                    String response = sc.next();
                    if(response.equalsIgnoreCase("Y")){
                        library.removeSong(songs.get(i));
                        System.out.println("Done");
                        i--;
                    }

                }
            }
        }

    }
    public static String getContent(Node n) {
        StringBuilder sb = new StringBuilder();
        Node child = n.getFirstChild();
        sb.append(child.getNodeValue());
        return sb.toString();
    }
    /*
Parses a JSON file, creates a song object using the information in the file, and adds the song to the library
 */
    public static void parseJSON(String filename){
        System.out.println("Parsing JSON file...");
        String string;
        try {
            Scanner sc = new Scanner(new File(filename));
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
                library.addSong(s);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + e);
        } catch (ParseException e1) {
            System.out.println("Parser error");
        }
    }
    /*
    Parses an XML file, creates a song object using the information in the file, and adds the song to the library
     */
    public static void parseXML(String filename){
        System.out.println("Parsing XML file...");
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(filename));

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
                            tempTitle = getContent(subNode).stripTrailing().stripLeading();
                            currentSong.setTitle(tempTitle);
                        }if(subNode.getNodeName().equals("artist")){
                            tempArt = getContent(subNode).stripTrailing().stripLeading();
                            Artist artist = new Artist(tempArt);
                            currentSong.setArtist(artist);
                            library.addArtist(artist);
                        }if(subNode.getNodeName().equals("album")){
                            tempAlb = getContent(subNode).stripTrailing().stripLeading();
                            Album album = new Album(tempAlb);
                            currentSong.setAlbum(album);
                            library.addAlbum(album);
                            library.addSong(currentSong);
                        }
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("Parsing error:" + e);
        }
    }
    public static void displaySongsFromSQL(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:music.db");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from songs");

            while(resultSet.next()){
                System.out.println(resultSet.getString("name") +" "+ resultSet.getString("album")+" "
                        + resultSet.getString("artist"));
            }
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    /*
    Prints out all the songs in the library, including the arist and album names
     */
    public static void displaySongs(){
        if(songs.size() > 0) {
            System.out.println("Songs in your library:\n---------------------------");
            for (int i = 0; i < songs.size(); i++) {
                System.out.println(songs.get(i).details());
            }
            System.out.println("---------------------------");
        }else{
            System.out.println("Your library is empty, add some songs to use functionality");
        }
    }
    /*
Writes the entire library out as an XML file
*/
    public static void libraryToXMLFile(String filename) {
        try {
            System.out.println("Writing to XML file...");
            FileWriter fileWriter = new FileWriter(filename);
            String result = "<library>\n<songs>\n";
            for (int i = 0; i < songs.size(); i++) {
                Song temp = songs.get(i);
                result += temp.toXML() + "\n";
            }
            fileWriter.write(result);
            fileWriter.write("</songs>\n</library>");
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /*
    Writes the entire library out as a JSON file
 */
    public static void libraryToJSONFile(String filename) {
        try {
            System.out.println("Writing to JSON file...");
            FileWriter fileWriter = new FileWriter(filename);
            String result = "{\n\"songs\" : [\n";
            for (int i = 0; i < songs.size(); i++) {
                Song temp = songs.get(i);
                result += temp.toJSON() + "\n";
                if(i != songs.size()-1){
                    result += ",";// the last element does not need a comma at the end
                }
            }
            fileWriter.write(result);
            fileWriter.write("]\n}");
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main (String[] args){
        library.parseXML("music-library.xml");
        //library.parseJSON("music-library.json");
        removeDefDuplicates();
        removePossibleDuplicates();
        displaySongs();
        libraryToXMLFile("test.xml");
        libraryToJSONFile("test.json");

    }



}

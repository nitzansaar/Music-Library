import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);
    static Library library = new Library();

    /**
     * Reads in songs from the SQL file into new song objects and adds them to the library
     * Allows for easy playlist creation
     */
    public static void addSongsToLibFromSQL(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:music.db");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from songs");
            String songName;
            String albumName;
            String artistName;
            String genre;

            while(resultSet.next()){
                songName = resultSet.getString("name");
                albumName = resultSet.getString("album");
                artistName = resultSet.getString("artist");
                genre = resultSet.getString("genre");
                Song temp = new Song(songName, albumName, artistName, genre);
                if(!library.findSong(temp)) {//if the song isn't in the library, it gets added
                    library.addSong(temp);
                }
            }
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    /**
     * Deletes existing song table and then creates a new one
     */
    public static void resetSongTable(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:music.db");

            Statement statement = connection.createStatement();

            statement.executeUpdate("drop table if exists songs");
            statement.executeUpdate("create table songs (id integer, name string, artist string, album string, genre string)");
            System.out.println("Song table reset");
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    /**
     * Deletes existing artist table and then creates a new one
     */
    public static void resetArtistTable(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:music.db");

            Statement statement = connection.createStatement();

            statement.executeUpdate("drop table if exists artists");
            statement.executeUpdate("create table artists (id integer, name string)");
            System.out.println("Artist table reset");
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    /**
     * Deletes existing album table and then creates a new one
     */
    public static void resetAlbumTable(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:music.db");

            Statement statement = connection.createStatement();

            statement.executeUpdate("drop table if exists albums");
            statement.executeUpdate("create table albums (id integer, name string, artist string, nSongs integer)");
            System.out.println("Album table reset");
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    /**
     * Prompts user to enter the song name of which to remove from the SQL database
     */
    public static void removeSongFromDatabase(){
        String songToRemove;
        System.out.println("Enter the song to remove: ");
        songToRemove = input.nextLine();
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:music.db");

            Statement statement = connection.createStatement();

            statement.executeUpdate("delete from songs where name = \"" + songToRemove + "\"");

            System.out.println(songToRemove + " removed");
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Prompts the user to enter a song name, artist name, album name, and genre
     * Creates a song object using the user provided information and adds the song to the library and SQL database
     */
    public static void addSongToLibraryAndDataBase(){
        String s;
        do {
            String songName;
            String artistName;
            String albumName;
            String genreType;
            System.out.print("Enter song name: ");
            songName = input.nextLine();
            System.out.print("Enter artist name ");
            artistName = input.nextLine();
            Artist artist = new Artist(artistName);
            System.out.print("Enter album name: ");
            albumName = input.nextLine();
            Album album = new Album(albumName, artistName, 0);
            System.out.print("Enter genre: ");
            genreType = input.nextLine();
            Song song = new Song(songName, albumName, artistName, genreType);
            artist.toSQL();
            album.toSQL();
            song.toSQL();
            library.addSong(song);
            System.out.println(songName + " added");
            System.out.println("Add another song?(Y/N): ");
            s = input.next();
            input.nextLine();
        }while(s.equalsIgnoreCase("y"));
    }
    /**
     * Prompts the user to enter an artist name
     * Creates an artist object using the user provided information and adds it to the SQL database
     */
    public static void addArtistToDataBase(){
        String s;
        do {
            String artistName;
            System.out.print("Enter artist name: ");
            artistName = input.nextLine();
            Artist artist = new Artist(artistName);
            artist.toSQL();
            library.addArtist(artist);
            System.out.println(artistName + " added");
            System.out.println("Add another artist?(Y/N): ");
            s = input.next();
            input.nextLine();
        }while(s.equalsIgnoreCase("y"));
    }
    /**
     * Prompts the user to enter an album name, artist name, and number of songs
     * Creates an album object using the user provided information and adds it to the SQL database
     */
    public static void addAlbumToDataBase(){
        String s;
        do {
            String albumName;
            String artistName;
            int nSongs;
            System.out.print("Enter album name: ");
            albumName = input.nextLine();
            System.out.print("Enter artist name ");
            artistName = input.nextLine();
            System.out.println("Enter number of songs: ");
            nSongs = input.nextInt();
            Album album = new Album(albumName, artistName, nSongs);
            album.toSQL();
            library.addAlbum(album);
            System.out.println(albumName + " added");
            System.out.println("Add another album?(Y/N): ");
            s = input.next();
            input.nextLine();
        }while(s.equalsIgnoreCase("y"));
    }
    /**
     * Asks user to enter a playlist name and prompts the user to add songs to the playlist
     * Loops until the user wishes to stop adding songs
     */
    public static void createPlaylist(){
        String playlistName;
        System.out.print("Enter playlist name: ");
        playlistName = input.nextLine();
        Playlist playlist = new Playlist(playlistName);
        addSongsToLibFromSQL();
        Library.displaySongs();
        do {
            System.out.print("Enter song ID to add to playlist: ");
            playlist.addSong(findSongUsingID(input.nextInt()));
            System.out.print("add another song?(Y/N): ");
        }while(input.next().equalsIgnoreCase("y"));
        System.out.println("You created playlist \" " + playlistName + " \"");
        playlist.printSongList();
    }
    /**
     * Prompts user to enter a playlist name and playlist genre
     * Creates playlist of given genre
     * Prompts user to enter the name of an XML file and then prints the newly created playlist to that file
     */
    public static void generateGenrePlaylist(){
        addSongsToLibFromSQL();
        String playlistName;
        String genre;
        String filename;
        System.out.print("Enter playlist name: ");
        playlistName = input.nextLine();
        System.out.print("Enter playlist genre: ");
        genre = input.nextLine();
        Playlist playlist = new Playlist(playlistName);
        Library.makePlaylistByGenre(playlist, genre);

        System.out.println("You created playlist \" " + playlistName + " \"");
        playlist.printSongList();
        System.out.println("Enter XML filename: ");
        filename = input.next();
        playlist.playlistToXMLFile(filename);
    }
    /**
     * Prompts user to enter a playlist name and an artist name
     * Creates playlist of given artist
     * Prompts user to enter the name of an XML file and then prints the newly created playlist to that file
     */
    public static void generateArtistPlaylist(){
        addSongsToLibFromSQL();
        String playlistName;
        String artistName;
        String filename;
        System.out.print("Enter playlist name: ");
        playlistName = input.nextLine();
        System.out.print("Enter artist: ");
        artistName = input.nextLine();
        Playlist playlist = new Playlist(playlistName);
        Library.makePlaylistByArtist(playlist, artistName);

        System.out.println("You created playlist \" " + playlistName + " \"");
        playlist.printSongList();
        System.out.println("Enter XML filename: ");
        filename = input.next();
        playlist.playlistToXMLFile(filename);
    }

    /**
     * Locates and returns a song based on its Entity ID
     * Prints error message if there is no song in library with specified ID
     */
    public static Song findSongUsingID(int ID){
        for(int i = 0; i < library.getSongs().size(); i++){
            Song temp = library.getSongs().get(i);
            if(temp.entityID == ID){
                return library.getSongs().get(i);
            }
        }
        System.out.println("Error: song isn't in library");
        return null;
    }

    /**
     * Takes input as an artist name and then searches the MusicBrainz database for that artists ID
     * Using the ID, searches MusicBrainz for all songs by that artist
     * Prints out all songs so that the user can decide to add to the library or not
     */
    public static void printArtistDetailsFromMusicBrainz(String name){
        String initialURL = "https://musicbrainz.org/ws/2/artist?query=" + name + "&fmt=xml";

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URLConnection u = new URL(initialURL).openConnection();

            u.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (nesaar@dons.usfca.edu");

            Document doc = db.parse(u.getInputStream());

            NodeList artists = doc.getElementsByTagName("artist-list");

            Node Node = artists.item(0).getFirstChild();
            Node IDNode = Node.getAttributes().getNamedItem("id");
            String id = IDNode.getNodeValue();

            //String lookupURL = "https://musicbrainz.org/ws/2/recording?artist=" + id; this url also works but not as well
            String lookupURL = "https://musicbrainz.org/ws/2/release/?artist=" + id + "&inc=recordings&fmt=xml";
            URLConnection u2 = new URL(lookupURL).openConnection();
            u2.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (nesaar@dons.usfca.edu");

            db = dbf.newDocumentBuilder();
            doc = db.parse(u2.getInputStream());

            NodeList aliases = doc.getElementsByTagName("title");
            for (int i = 0; i < aliases.getLength(); i++) {
                String songName = aliases.item(i).getFirstChild().getNodeValue();
                System.out.println("Song: " + songName + " Artist: " + name);
            }

        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
        }
    }

    /**
     * Prompts user to enter an artist name and then calls a method which prints out all songs by that artist
     */
    public static void partiallySpecify(){
        String artistName;
        System.out.print("Enter artist name: ");
        artistName = input.next();
        printArtistDetailsFromMusicBrainz(artistName);


    }
    /**
     * Displays menu with text based commands
     */
    public static void displayMenu(){
        System.out.println("----------------------------------" +
                "\nOptions: " +
                "\n(1)Add song to database" +
                "\n(2)Add artist to database" +
                "\n(3)Add album to database" +
                "\n(4)Create your own playlist" +
                "\n(5)Generate playlist based on genre and print to XML file" +
                "\n(6)Generate playlist based on artist and print to XML file" +
                "\n(7)Remove song from database" +
                "\n(8)Display songs table in database" +
                "\n(9)Display artists table in database" +
                "\n(10)Display albums table in database" +
                "\n(11)Partially specify artist and show all songs" +
                "\n(12)Reset tables in database and library" +
                "\n(13)Exit");
    }
    /**
     * Takes an integer as input and calls appropriate method
     */
    public static void interpet(int num){
        if(num == 1){
            addSongToLibraryAndDataBase();
        }else if(num == 2){
            addArtistToDataBase();
        }else if(num == 3){
            addAlbumToDataBase();
        }else if(num == 4){
            createPlaylist();
        }else if(num == 5){
            generateGenrePlaylist();
        }else if(num == 6){
            generateArtistPlaylist();
        }else if(num == 7){
            removeSongFromDatabase();
        }else if(num == 8){
            Library.displaySongsFromSQL();
        }else if(num == 9){
            Library.displayArtistsFromSQL();
        }else if(num == 10){
            Library.displayAlbumsFromSQL();
        }else if(num == 11){
            partiallySpecify();
        }else if(num == 12){
            resetSongTable();
            resetAlbumTable();
            resetArtistTable();
            Library.empty();
        }
    }

    public static void main(String[] args){
        int answer;
        do{
            displayMenu();
            answer = input.nextInt();
            input.nextLine();
            interpet(answer);
        }while(answer != 13);

    }
}
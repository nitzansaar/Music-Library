import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);
    static Library library = new Library();

    /*
    Deletes existing song table and then creates a new one
     */
    public static void resetSongTable(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:music.db");

            Statement statement = connection.createStatement();

            statement.executeUpdate("drop table if exists songs");
            statement.executeUpdate("create table songs (id integer, name string, artist string, album string)");
            System.out.println("Song table reset");
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    /*
Deletes existing artist table and then creates a new one
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
    /*
Deletes existing album table and then creates a new one
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


    /*
    Prompts user to enter the ID of the song they wish to remove from the library
    Loops until the user chooses to exit
     */
    public static void removeSongFromLibrary(){
        do {
            Library.displaySongs();
            System.out.print("Enter ID of the song to remove: ");
            library.removeSong(findSongUsingID(input.nextInt()));
            input.nextLine();
            System.out.println("Remove another song?(Y/N): ");
        }while(input.next().equalsIgnoreCase("y"));
    }
    public static void removeSongFromDatabase(){
        String songToRemove;
        System.out.println("Enter the song to remove: ");
        songToRemove = input.nextLine();
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:music.db");

            Statement statement = connection.createStatement();

            statement.executeUpdate("delete from songs where name = " + songToRemove);

            System.out.println(songToRemove + " removed");
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /*
    Prompts the user to enter a song name, artist name, album name, and genre
    Creates a song object using the user provided information and adds the song to the library
     */
    public static void addSongToLibrary(){
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
            System.out.print("Enter album name: ");
            albumName = input.nextLine();
            System.out.print("Enter genre: ");
            genreType = input.nextLine();
            Song song = new Song(songName, albumName, artistName, genreType);
            song.toSQL();
            library.addSong(song);
            System.out.println("Add another song?(Y/N): ");
            s = input.next();
            input.nextLine();
        }while(s.equalsIgnoreCase("y"));
    }
    /*
    Asks user to enter a playlist name and prompts the user to add songs to the playlist
    Loops until the user wishes to stop adding songs
     */
    public static void createPlaylist(){
        String playlistName;
        System.out.print("Enter playlist name: ");
        playlistName = input.nextLine();
        Playlist playlist = new Playlist(playlistName);
        if(library.getSongs().size() == 0){
            System.out.print("You can't add songs to your playlist because your library is empty\n");
            return;
        }
        Library.displaySongs();
        do {
            System.out.print("Enter song ID to add to playlist: ");
            playlist.addSong(findSongUsingID(input.nextInt()));
            System.out.print("add another song?(Y/N): ");
        }while(input.next().equalsIgnoreCase("y"));
        System.out.println("You created playlist \" " + playlistName + " \"");
        playlist.printSongList();
    }
    /*
    Locates and returns a song based on its Entity ID
    Prints error message if there is no song in library with specified ID
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
    public static void displayMenu(){
        System.out.println("Options: " +
                "\n(1)Add song to database" +
                "\n(2)Create playlist" +
                "\n(3)Remove song from library" +
                "\n(4)Display songs in library" +
                "\n(5)Reset databases" +
                "\n(6)Exit");
    }
    public static void interpet(int num){
        if(num == 1){
            addSongToLibrary();
        }else if(num == 2){
            createPlaylist();
        }else if(num == 3){
            //removeSongFromLibrary();
            removeSongFromDatabase();
        }else if(num == 4){
            Library.displaySongsFromSQL();
        }else if(num == 5){
            resetSongTable();
            resetAlbumTable();
            resetArtistTable();
        }
    }

    public static void main(String[] args){
        int answer;
        do{
            displayMenu();
            answer = input.nextInt();
            input.nextLine();
            interpet(answer);
        }while(answer != 6);

    }
}
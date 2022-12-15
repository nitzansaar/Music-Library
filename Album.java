import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Album extends Entity {
    protected ArrayList<Song> songs;
    protected Artist artist;
    protected int nSongs;

    public Album() {
        this.name = "";
        this.nSongs = 0;
    }

    public Album(String name) {
        super(name);
        this.nSongs = 0;
    }
    public Album(String name, String artistName, int songs){
        super(name);
        artist = new Artist(artistName);
        nSongs = songs;
    }

    public String getName() {
        System.out.println("this is an album" + super.getName());
        return name;
    }

    public boolean equals(Album otherAlbum) {
        if ((this.artist.equals(otherAlbum.getArtist())) &&
                (this.name.equals(otherAlbum.getName()))) {
            return true;
        } else {
            return false;
        }
    }



    protected ArrayList<Song> getSongs() {
        return songs;
    }

    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
    public void toSQL(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:music.db");

            Statement statement = connection.createStatement();

            String s = "insert into albums (id, name, artist, nSongs) values (" + this.entityID + ", \"" + this.name + "\", \"" + this.artist.name + "\"," +
                    this.nSongs + ");";

            statement.executeUpdate(s);
            System.out.println("Inserted to SQL");
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }
}
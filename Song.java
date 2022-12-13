import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Song extends Entity {
    protected Album album;
    protected Artist artist;
    protected String genre;
    protected boolean liked;
    protected String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }
    public Song(String name, String albumName, String artistName, String genreType){
        super(name);
        album = new Album(albumName);
        artist = new Artist(artistName);
        genre = genreType;
    }

    public Song(String name) {
        super(name);
        album = new Album();
        artist = new Artist();
        genre = "";

    }
    public Song() {
        this.name = "";
    }

    public String getGenre(){
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }


    public void setArtist(Artist a){
        this.artist = a;
    }
    public boolean equals(Song other){
        return this.title.equals(other.title) && this.artist.name.equals(other.artist.name)
                && this.album.name.equals(other.album.name);
    }
    public boolean possiblyEquals(Song other){
        return (this.title.equals(other.title) && (this.artist.name.equals(other.artist.name) || this.album.name.equals(other.album.name))
                ||
                (this.artist.name.equals(other.artist.name) && (this.album.name.equals(other.album.name)
                        && this.title.replaceAll("\\p{Punct}", "").equalsIgnoreCase
                        (other.title.replaceAll("\\p{Punct}", "")))));
    }
    public String details(){
        return "(Song: " + this.getName() + " Artist: " +
                this.artist + ", Album: " + this.album + ") ID: " + this.entityID;
    }
    public String toString(){
        return "Song: " + this.title;
    }
    public String toHTML(){
        return "<b> " + this.name + " </b><i> " + this.entityID + "</i>";
    }
    public String toXML(){
        return "<song id = \"" + this.entityID + "\">\n<title>\n" + this.title + "\n</title>"
                + "\n<artist id=\"" + this.artist.entityID + "\">\n" + this.artist.name + "\n</artist>"
                + "\n<album id=\"" + this.album.entityID + "\">\n" + this.album.name + "\n</album>\n</song>";
    }
    public String toJSON(){
        return "{ \n\"id\": \"" + this.entityID + "\",\n" +
                "\"title\":\"" + this.title +"\",\n\"artist\": {\n" + "\"id\": \"" + this.artist.entityID + "\"," +
                "\n\"name\": \"" + this.artist.name + "\"\n},\n\"album\": {\n" +"\"id\": \"" + this.album.entityID + "\",\n" +
                "\"name\": \"" + this.album.name + "\"\n}\n}";
    }
    public void setLiked(boolean liked){
        this.liked = liked;
    }
    public boolean isLiked(){
        return liked;
    }
    public void toSQL(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:music.db");

            Statement statement = connection.createStatement();

            String s = "insert into songs (id, name, album, artist) values (" + this.entityID + ", \"" + this.name + "\", " + album.entityID + ", "
                    + artist.entityID  + ");";
            statement.executeUpdate("drop table if exists songs");
            statement.executeUpdate("create table songs (id integer, name string, artist string, album string)");
            statement.executeUpdate(s);
            System.out.println("Inserted to SQL");
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

}
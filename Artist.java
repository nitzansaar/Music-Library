import java.util.ArrayList;

public class Artist extends Entity {

    protected ArrayList<Song> songs;
    protected ArrayList<Album> albums;

    public Artist(){
        this.name = "";
    }
    public Artist(String name) {
        super(name);
    }

    protected ArrayList<Song> getSongs() {
        return songs;
    }

    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    protected ArrayList<Album> getAlbums() {
        return albums;
    }

    protected void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public void addSong(Song s) {
        songs.add(s);
    }
    public String toSQL() {
        return "insert into albums (id, name, nAlbums, nSongs) values (" + this.entityID + ", " + this.name + ", " + this.albums.size() + ", "
                + this.songs.size() + ");";
    }

}
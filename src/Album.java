package src;

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
    public String toSQL() {
        return "insert into albums (id, name, artist, nSongs) values (" + this.entityID + ", " + this.name + ", " + this.artist + ", "
                + this.nSongs+ ");";
    }
}

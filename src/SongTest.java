package src;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SongTest {

    @Test
    void testEquals() {
        Song s1 = new Song();
        s1.setTitle("Pure cocaine");
        s1.setAlbum(new Album("Lil baby"));
        s1.setArtist(new Artist("Album 1"));
        Song s2 = new Song();
        s2.setTitle("Mr Right Now");
        s2.setAlbum(new Album("21 Savage"));
        s2.setArtist(new Artist("Savage Mode 2"));
        Song s3 = new Song();
        s3.setTitle("Major Distribution");
        s3.setAlbum(new Album("Drake"));
        s3.setArtist(new Artist("Album 1"));
        Song s4 = new Song("Hello");
        s4.setTitle("Savage Mode");
        s4.setAlbum(new Album("21 Savage"));
        s4.setArtist(new Artist("Savage Mode"));
        Song s5 = new Song();
        s5.setTitle("Pure cocaine");
        s5.setAlbum(new Album("Lil baby"));
        s5.setArtist(new Artist("Album 1"));
        System.out.println(s1.equals(s2));
    }
}
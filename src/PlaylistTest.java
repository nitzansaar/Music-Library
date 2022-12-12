package src;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistTest {


    @Test
    void merge() {
        Playlist p1 = new Playlist("Swag");
        Song s1 = new Song();
        s1.setTitle("Pure cocaine");
        s1.setAlbum(new Album("Lil baby"));
        s1.setArtist(new Artist("Album 1"));
        Song s2 = new Song();
        s2.setTitle("Mr Right Now");
        s2.setAlbum(new Album("21 Savage"));
        s2.setArtist(new Artist("Savage Mode 2"));
        Song s3 = new Song();
        s3.setTitle("Digital dash");
        s3.setAlbum(new Album("Drake"));
        s3.setArtist(new Artist("Album 1"));
        Song s4 = new Song("Hello");
        s4.setTitle("Savage Mode");
        s4.setAlbum(new Album("21 Savage"));
        s4.setArtist(new Artist("Savage Mode"));
        p1.addSong(s1);
        p1.addSong(s2);
        p1.addSong(s3);
        p1.addSong(s4);
        Playlist p2 = new Playlist("");
        Song s5 = new Song();
        s5.setTitle("Heyy");
        s5.setAlbum(new Album("Lil baby"));
        s5.setArtist(new Artist("Album 1"));
        Song s6 = new Song();
        s6.setTitle("Mr Right Now");
        s6.setAlbum(new Album("21 Savage"));
        s6.setArtist(new Artist("Savage Mode 2"));
        Song s7 = new Song();
        s7.setTitle("Major Distributions");
        s7.setAlbum(new Album("Drake"));
        s7.setArtist(new Artist("Album 1"));
        Song s8 = new Song("Hello");
        s8.setTitle("Savage Mode");
        s8.setAlbum(new Album("21 Savage"));
        s8.setArtist(new Artist("Savage Mode"));
        Song s9 = new Song("Hello");
        s9.setTitle("Ocean drive");
        s9.setAlbum(new Album("21 Savage"));
        s9.setArtist(new Artist("Savage Mode"));
        p2.addSong(s5);
        p2.addSong(s6);
        p2.addSong(s7);
        p2.addSong(s8);
        p2.addSong(s9);
        Playlist merged = Playlist.merge(p1,p2);
        System.out.println("Before: " + p1.getSonglist() + " " +  p2.getSonglist());
        System.out.println("Merged playlist: " + merged.getSonglist());

    }

    @Test
    void removeSong() {
        Playlist p1 = new Playlist("Swag");
        Song s1 = new Song();
        s1.setTitle("Pure cocaine");
        s1.setAlbum(new Album("Lil baby"));
        s1.setArtist(new Artist("Album 1"));
        Song s2 = new Song();
        s2.setTitle("Mr Right Now");
        s2.setAlbum(new Album("21 Savage"));
        s2.setArtist(new Artist("Savage Mode 2"));
        Song s3 = new Song();
        s3.setTitle("Digital dash");
        s3.setAlbum(new Album("Drake"));
        s3.setArtist(new Artist("Album 1"));
        Song s4 = new Song("Hello");
        s4.setTitle("Savage Mode");
        s4.setAlbum(new Album("21 Savage"));
        s4.setArtist(new Artist("Savage Mode"));
        p1.addSong(s1);
        p1.addSong(s2);
        p1.addSong(s3);
        p1.addSong(s4);
        p1.removeSong(s1);
        assertFalse(p1.getSonglist().contains(s1));

    }

    @Test
    void addSong() {
        Playlist p1 = new Playlist("Swag");
        Song s1 = new Song();
        s1.setTitle("Pure cocaine");
        s1.setAlbum(new Album("Lil baby"));
        s1.setArtist(new Artist("Album 1"));
        p1.addSong(s1);
        assertTrue(p1.getSonglist().contains(s1));

    }

    @Test
    void putLikedSongsInFront() {
        Playlist p1 = new Playlist("Swag");
        Song s1 = new Song();
        s1.setTitle("Pure cocaine");
        s1.setAlbum(new Album("Lil baby"));
        s1.setArtist(new Artist("Album 1"));
        Song s2 = new Song();
        s2.setTitle("Liked Song");
        s2.setAlbum(new Album("21 Savage"));
        s2.setArtist(new Artist("Savage Mode 2"));
        Song s3 = new Song();
        s3.setTitle("Digital dash");
        s3.setAlbum(new Album("Drake"));
        s3.setArtist(new Artist("Album 1"));
        Song s4 = new Song();
        s4.setTitle("Liked Song");
        s4.setAlbum(new Album("21 Savage"));
        s4.setArtist(new Artist("Savage Mode"));
        s2.setLiked(true);
        s4.setLiked(true);
        p1.addSong(s1);
        p1.addSong(s2);
        p1.addSong(s3);
        p1.addSong(s4);
        System.out.println("Before sorting:\n" + p1.getSonglist());
        p1.putLikedSongsInFront();
        System.out.println("After sorting:\n" + p1.getSonglist());
        assertTrue(p1.getSonglist().get(0).isLiked());

    }

    @Test
    void shuffle() {
        Playlist p1 = new Playlist("Swag");
        Song s1 = new Song();
        s1.setTitle("Pure cocaine");
        s1.setAlbum(new Album("Lil baby"));
        s1.setArtist(new Artist("Album 1"));
        Song s2 = new Song();
        s2.setTitle("Mr Right Now");
        s2.setAlbum(new Album("21 Savage"));
        s2.setArtist(new Artist("Savage Mode 2"));
        Song s3 = new Song();
        s3.setTitle("Digital dash");
        s3.setAlbum(new Album("Drake"));
        s3.setArtist(new Artist("Album 1"));
        Song s4 = new Song("Hello");
        s4.setTitle("Savage Mode");
        s4.setAlbum(new Album("21 Savage"));
        s4.setArtist(new Artist("Savage Mode"));
        p1.addSong(s1);
        p1.addSong(s2);
        p1.addSong(s3);
        p1.addSong(s4);
        System.out.println("Before:\n" + p1.getSonglist());
        p1.shuffle();
        System.out.println("After:\n" + p1.getSonglist());

    }

    @Test
    void makePlaylistByGenre() {
        Playlist p1 = new Playlist("Swag");
        Song s1 = new Song();
        s1.setTitle("Pure cocaine");
        s1.setAlbum(new Album("Lil baby"));
        s1.setArtist(new Artist("Album 1"));
        Song s2 = new Song();
        s2.setTitle("Mr Right Now");
        s2.setAlbum(new Album("21 Savage"));
        s2.setArtist(new Artist("Savage Mode 2"));
        Song s3 = new Song();
        s3.setTitle("Digital dash");
        s3.setAlbum(new Album("Drake"));
        s3.setArtist(new Artist("Album 1"));
        Song s4 = new Song("Hello");
        s4.setTitle("Savage Mode");
        s4.setAlbum(new Album("21 Savage"));
        s4.setArtist(new Artist("Savage Mode"));
        Song s5 = new Song("Hello");
        s5.setTitle("Rich flex");
        s5.setAlbum(new Album("Drake"));
        s5.setArtist(new Artist("Her Loss"));
        s1.setGenre("Rap");
        s2.setGenre("Rap");
        s3.setGenre("Rap");
        s4.setGenre("Rap");
        s5.setGenre("Not rap");
        p1.addSong(s1);
        p1.addSong(s2);
        p1.addSong(s3);
        p1.addSong(s4);
        System.out.println(p1.getSonglist());
        Playlist playlist = p1.makePlaylistByGenre("rap");
        System.out.println(playlist.getSonglist());
        assertFalse(p1.getSonglist().contains(s5));
    }
    @Test
    void playlistToXMLFile() {
        Playlist p1 = new Playlist("Swag");
        Song s1 = new Song();
        s1.setTitle("Pure cocaine");
        s1.setAlbum(new Album("Lil baby"));
        s1.setArtist(new Artist("Album 1"));
        Song s2 = new Song();
        s2.setTitle("Mr Right Now");
        s2.setAlbum(new Album("21 Savage"));
        s2.setArtist(new Artist("Savage Mode 2"));
        Song s3 = new Song();
        s3.setTitle("Digital dash");
        s3.setAlbum(new Album("Drake"));
        s3.setArtist(new Artist("Album 1"));
        Song s4 = new Song("Hello");
        s4.setTitle("Savage Mode");
        s4.setAlbum(new Album("21 Savage"));
        s4.setArtist(new Artist("Savage Mode"));
        p1.addSong(s1);
        p1.addSong(s2);
        p1.addSong(s3);
        p1.addSong(s4);
        p1.playlistToXMLFile("test.xml");
    }

    @Test
    void playlistToJSONFile() {
        Playlist p1 = new Playlist("");
        Song s1 = new Song();
        s1.setTitle("Pure cocaine");
        s1.setAlbum(new Album("Lil baby"));
        s1.setArtist(new Artist("Album 1"));
        Song s2 = new Song();
        s2.setTitle("Mr Right Now");
        s2.setAlbum(new Album("21 Savage"));
        s2.setArtist(new Artist("Savage Mode 2"));
        Song s3 = new Song();
        s3.setTitle("Digital dash");
        s3.setAlbum(new Album("Drake"));
        s3.setArtist(new Artist("Album 1"));
        Song s4 = new Song("Hello");
        s4.setTitle("Savage Mode");
        s4.setAlbum(new Album("21 Savage"));
        s4.setArtist(new Artist("Savage Mode"));
        p1.addSong(s1);
        p1.addSong(s2);
        p1.addSong(s3);
        p1.addSong(s4);
        p1.playlistToJSONFile("test.json");
    }
}
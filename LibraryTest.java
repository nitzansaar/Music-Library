import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    Library library;

    @BeforeEach
    void setup(){
        library = new Library();
        Song s1 = new Song("1", "1", "Lil baby", "rap");
        Song s2 = new Song("1", "1", "1", "rap");
        Song s3 = new Song("1", "1", "1", "rap");
        Song s4 = new Song("1", "1", "1", "hip hop");
        library.addSong(s1);
        library.addSong(s2);
        library.addSong(s3);
        library.addSong(s4);
    }

    @Test
    void empty() {
        Library library = new Library();
        Library.empty();
        assertTrue(library.getSongs().isEmpty());
    }

    @Test
    void makePlaylistByGenre() {
        Playlist p = new Playlist("rap playlist");
        Library.makePlaylistByGenre(p,"rap");
        assertTrue(p.getSonglist().size() == 3);
    }

    @Test
    void makePlaylistByArtist() {
        Playlist p = new Playlist("Lil baby playlist");
        Library.makePlaylistByArtist(p, "Lil baby");
        assertTrue(p.getSonglist().size() == 1);
    }
}
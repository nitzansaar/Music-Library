import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    Library library;

    @BeforeEach
    void setup(){
        library = new Library();
        Song s1 = new Song("Good song", "1", "Lil baby", "rap");
        Song s2 = new Song("1", "1", "1", "rap");
        Song s3 = new Song("1", "1", "1", "rap");
        Song s4 = new Song("1", "1", "1", "hip hop");
        library.addSong(s1);
        library.addSong(s2);
        library.addSong(s3);
        library.addSong(s4);
    }

    @Test
    void addSongsToLibFromSQL() {
        Library library = new Library();
        Main.addSongsToLibFromSQL();
        for (int i = 0; i<library.getSongs().size(); i++){
            System.out.println(library.getSongs().get(i).name);
        }

    }

    @Test
    void findSongUsingID() {
        for(int i = 0; i < library.getSongs().size(); i++){
            Song temp = library.getSongs().get(i);
            if(temp.entityID == 1){
                assertTrue(temp.name.equals("Good song"));
            }
        }
    }
}
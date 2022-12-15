import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Playlist extends Entity{
    protected ArrayList<Song> songlist;
    protected String genre;
    public Playlist(ArrayList<Song> songs){;
        songlist = songs;
    }
    public void printSongList(){
        for(int i = 0; i < songlist.size(); i++){
            Song temp = songlist.get(i);
            if(temp != null) {
                System.out.println(temp.details());
            }
        }
    }
    public ArrayList<Song> getSonglist() {
        return songlist;
    }
    public Playlist(String name){
        super(name);
        songlist = new ArrayList<Song>();
    }
    public void addSong(Song s){
        songlist.add(s);
    }
    public void removeSong(Song s){
        if(songlist.contains(s)){
            songlist.remove(s);
        }else{
            System.out.printf("%s is not in the playlist\n", s.toString());
        }
    }
    /**
     * Merges two playlists into one playlist containing all songs of both playlist without any duplicate songs
     */
    public static Playlist merge(Playlist p1, Playlist p2){
        ArrayList<Song> songArrayList = new ArrayList<>(p1.songlist);
        for(int i = 0; i < p2.songlist.size(); i++) {
            Song temp = p2.songlist.get(i);
            int j = 0;
            boolean exit = false;
            while (exit == false && j <= i) {
                if(temp.equals(songArrayList.get(j))){// if there is a duplicate song,
                    exit = true;                      // the loop ends and the song does not get added
                }
                j++;
            }
            if(exit == false){// if the loop gets to here, it means there is no duplicate and the song is to be added
                songArrayList.add(temp);
            }
        }
        return new Playlist(songArrayList);
    }

    /**
     * Sorts a list of songs by putting the liked songs in front
     */
    public void putLikedSongsInFront(){
        ArrayList<Song> likedSongs = new ArrayList<>();
        ArrayList<Song> notLikedSongs = new ArrayList<>();
        for (int i = 0; i < this.songlist.size(); i++){
            Song temp = this.songlist.get(i);
            if(temp.isLiked()){// if the song is liked, gets added to list of liked songs
                likedSongs.add(temp);
            }else{// if song is not liked, gets added to list of unliked songs
                notLikedSongs.add(temp);
            }
        }
        likedSongs.addAll(notLikedSongs);// combine the two lists with the liked songs in front
        this.songlist = likedSongs;
    }
    /**
     * Randomly shuffles a playlist
     */
    public void shuffle(){
        Collections.shuffle(this.songlist);
    }
    /**
     * Writes a playlist out as an XML file
     */
    public void playlistToXMLFile(String filename) {
        try {
            System.out.println("Writing to XML file...");
            FileWriter fileWriter = new FileWriter(filename);
            String result = "<playlist>\n";
            for (int i = 0; i < this.songlist.size(); i++) {
                Song temp = this.songlist.get(i);
                result += temp.toXML() + "\n";
            }
            fileWriter.write(result);
            fileWriter.write("</playlist>");
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Writes a playlist out as a JSON file
     */
    public void playlistToJSONFile(String filename) {
        try {
            System.out.println("Writing to JSON file...");
            FileWriter fileWriter = new FileWriter(filename);
            String result = "{\n\"playlist\" : [\n";
            for (int i = 0; i < this.songlist.size(); i++) {
                Song temp = this.songlist.get(i);
                result += temp.toJSON();
                if(i != this.songlist.size()-1){
                    result += ",\n";// the last element does not need a comma at the end
                }
            }
            fileWriter.write(result);
            fileWriter.write("]\n}");
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
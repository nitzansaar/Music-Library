package src;

import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);
    static Library library = new Library();

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

    /*
    Prompts the user to enter a song name, artist name, album name, and genre
    Creates a song object using the user provided information and adds the song to the library
     */
    public static void addSongToLibrary(){
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
        song.toSQL();// need to store the song in a SQL database somehow
        library.addSong(song);
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
                "\n(1)Add song to libray" +
                "\n(2)Create playlist" +
                "\n(3)Remove song from library" +
                "\n(4)Display songs in library" +
                "\n(5)Exit");
    }
    public static void interpet(int num){
        if(num == 1){
            addSongToLibrary();
        }else if(num == 2){
            createPlaylist();
        }else if(num == 3){
            removeSongFromLibrary();
        }else if(num == 4){
            Library.displaySongs();
        }
    }

    public static void main(String[] args){
        int answer;
        do{
            displayMenu();
            answer = input.nextInt();
            input.nextLine();
            interpet(answer);
        }while(answer != 5);

    }
}
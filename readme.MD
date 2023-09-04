# Music Library Management System

This is a simple music library management system implemented in Java. The system allows you to manage songs, artists, albums, and playlists using a SQL database.

## Classes

### Main.java

The `Main` class serves as the central entry point of the application. It provides a user-friendly command-line interface that enables users to interact with the music library and perform various tasks.

- `addSongsToLibFromSQL()`: Reads songs from an SQL file and adds them to the library, facilitating playlist creation.
- `resetSongTable()`, `resetArtistTable()`, `resetAlbumTable()`: Delete and recreate tables for songs, artists, and albums in the SQL database.
- `removeSongFromDatabase()`: Allows users to remove a song from the SQL database.
- `addSongToLibraryAndDataBase()`: Enables users to add new songs to the library and the SQL database.
- `addArtistToDataBase()`, `addAlbumToDataBase()`: Similar to adding songs, these methods allow users to add artists and albums to the database.
- `createPlaylist()`, `generateGenrePlaylist()`, `generateArtistPlaylist()`: Enable playlist creation based on user preferences and export playlists to XML files.
- `partiallySpecify()`: Allows users to search for artists' songs using MusicBrainz.
- `displayMenu()`, `interpret()`: Control the display of menu options and user interaction.

### Song.java

The `Song` class represents an individual song entity. It encapsulates various attributes:

- Title: The title of the song.
- Album: The album the song belongs to.
- Artist: The artist who performed the song.
- Genre: The genre of the song.
- Liked Status: Whether the song is marked as liked by the user.

The class provides methods to convert song data to XML and JSON formats, interact with the SQL database for storage and retrieval, and manage liked status.

### Artist.java

The `Artist` class models an artist entity. It encapsulates the artist's name and provides methods for interaction with the SQL database, including storing and retrieving artist information.

### Album.java

The `Album` class represents an album entity. It stores information about the album's name, the artist who created it, and the number of songs in the album. Similar to other classes, it includes methods to interact with the SQL database to store and retrieve album data.

### Playlist.java

The `Playlist` class models a playlist containing a list of songs. It offers functionalities such as:

- Adding and removing songs from the playlist.
- Shuffling the playlist to change song order.
- Exporting the playlist to XML or JSON formats.
- Merging playlists to combine songs while avoiding duplicates.
- Rearranging the playlist to put liked songs at the front.

## What I Learned

- Designing and implementing object-oriented classes to represent entities and their relationships.
- Utilizing a SQL database for storing and retrieving data, including CRUD operations.
- Creating command-line interfaces for user interaction and application control.
- Handling XML and JSON data formats for exporting and importing data.
- Applying various programming concepts such as inheritance, encapsulation, and polymorphism.

## How to Use

1. Compile the Java files using a Java compiler (e.g., `javac Main.java`).
2. Run the application using the Java Virtual Machine (e.g., `java Main`).
3. Follow the prompts in the command-line interface to perform various tasks with the music library.


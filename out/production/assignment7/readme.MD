Homework #7. Putting it all together.

Due: December 16, 11:59pm.

This homework will be graded using specifications grading. There are a sequence of tasks to complete. Grades will be assigned as follows:

    To receive a C, you should complete tasks 1-3
    To receive a B, complete all the C-level tasks, plus task 4.
    To receive an A, complete the B-level tasks, plus tasks 5 and 6.


In this assignment, you'll integrate some of the pieces and components from our earlier work to develop a complete program for managing our music. Please note that, for this assignment, we'll
be focusing on the management portion of the task. Two pieces that we won't integrate are:
1. A tool for playing music files. The java.io.* package provides support for this, but it can be a little tricky to deal with all the different formats and codecs.
2. A GUI for managing music. We haven't dealt much with developing graphical user interfaces in this class; the old-school way to do this would be as a standalone application using Swing, but most modern approaches instead do this inside a web browser using Javascript. 

These are both great extensions, and well within your skillset, and could make for a really fun project over break to extend your expertise.

What we will do here is integrate some of the work from previous labs and homeworks to build a tool that can 
manage a music collection and load information from third-party repositories. You probably have quite a bit of this 
code developed already, so this homework will be more about polishing and integrating than new development. I've described what to accomplish in terms of specifications: that is, what your code should do.
You can make your own design decisions about how to implement this. I encourage you to be creative, add features that you think are interesting, and have fun.

The goal is to develop a program with a text-based UI that can do the following:

- Display the user's songs, artists, and albums.
- Prompt the user for a new song, artist, or album and then fill in any missing data.
- Generate XML files that contain a playlist based on a particular criterion, such as genre, mood, or artist.

Specific tasks to implement include:

1. You should implement a UI that can take basic text-based commands. I will let you decide on the format for this.
2. Your songs, artists, and albums should all be stored in a SQLite database, with separate tables for each datatype.
3. You should be able to generate XML files containing a playlist. (you probably have this from the past lab.)
4. Your program should allow a user to partially specify a song, artist, or album, and then use a third-party tool to fill in missing details. 
You may use either:
  - TheAudioDB. They have a REST-based API that returns JSON. You can find more information here: https://www.theaudiodb.com/api_guide.php
  - MusicBrainz. They also have a REST-based API; it can return either XML or JSON. You can find more info on this here: https://musicbrainz.org/doc/MusicBrainz_API
  
You might also want to use these services to fill in other data, such as description, genre, running time, etc.

You should also include the following:
5. Please use Javadoc to generate documentation for all of the classes in your homework. This should include a short readme explaining how to run your code.
6. Please use maven to create and organize your project.  When you're completed, you should be able to use maven to:
  - automatically run unit tests
  - regenerate documentation
  - compile a JAR.


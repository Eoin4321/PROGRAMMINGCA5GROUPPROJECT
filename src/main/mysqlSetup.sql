DROP TABLE IF EXISTS GameInformation;

CREATE TABLE GameInformation (
                                 GameID INT NOT NULL AUTO_INCREMENT,
                                 Game_name VARCHAR(20) NOT NULL,
                                 Game_console VARCHAR(20) NOT NULL,
                                 Game_publisher VARCHAR(20) NOT NULL,
                                 Game_developer VARCHAR(20) NOT NULL,
                                 Game_franchise VARCHAR(20) NOT NULL,
                                 Multiplayer Boolean NOT NUll,
                                 Player_amount INT NOT NULL,
                                 Review_Score FLOAT NOT NULL,
                                 Image_ID VARCHAR(20) NOT NULL,
                                 PRIMARY KEY (GameID)
);

INSERT INTO gameinformation (Game_name, Game_console,Game_publisher,Game_developer,Game_franchise,Multiplayer,Player_amount,Review_Score,Image_ID)
VALUES('Overwatch 2','PC','Activision Blizzard','Blizzard','Overwatch',TRUE,10,100,'OVERWATCH.jpg'),
      ('Overwatch 2','PS5','Activision Blizzard','Blizzard','Overwatch',TRUE,10,100,'OVERWATCH.jpg'),
      ('Overwatch 2','XBOX','Activision Blizzard','Blizzard','Overwatch',TRUE,10,100,'OVERWATCH.jpg'),
      ('My Hero Ones Justice','PC','By King','Bandai Namco','My Hero Academia',TRUE,2,99,'MyHeroOnesJustice.jpg'),
      ('My Hero Ones Justice','XBOX','By King','Bandai Namco','My Hero Academia',TRUE,2,99,'MyHeroOnesJustice.jpg'),
      ('One Piece Pirate Warriors 4','PS4','Bandai Namco','Bandai Namco','ONE PIECE',TRUE,2,97.5,'OnePiecePirateWarriors.jpg'),
      ('One Piece World Seeker','PS4','Bandai Namco','Bandai Namco','ONE PIECE',TRUE,2,10.5,'OnePieceWorldSeeker.jpg'),
      ('Spider-Man Miles Morales','PS4','Insomniac','Playstation','MARVEL',FALSE,2,99.5,'SpiderManMilesMorales.jpg'),
      ('Drifting with Maxwell Cat: The Game','Nintendo','Unknown','Unknown','Memes',TRUE,5,100,'Maxwell.jpg'),
      ('My Hero Ones Justice 2','PC','By King','Bandai Namco','My Hero Academia',TRUE,2,92.5,'MyHeroOnesJustice2.jpg');


--AUTHOR Eoin Hamill wrote the code.
--COAUTHOR DOVYDAS helped with coming up with idea and parameters.
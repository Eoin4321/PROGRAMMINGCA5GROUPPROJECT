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

INSERT INTO gameinformation (Game_name, Game_console,Game_publisher,Game_developer,Game_franchise,Multiplayer,Player_amount,Review_Score)
VALUES('Overwatch 2','PC','Activision Blizzard','Blizzard','Overwatch',TRUE,10,100,'OVERWATCH.jpg'),
      ('Overwatch','PC','Activision Blizzard','Blizzard','Overwatch',TRUE,12,97,'OVERWATCH.jpg'),
      ('Marvel Snap','Phone','Second Dinner','Second Dinner','Marvel',TRUE,2,98,'OVERWATCH.jpg'),
      ('My Hero Ones Justice','PC','By King','Bandai Namco','My Hero Academia',TRUE,2,99,'OVERWATCH.jpg'),
      ('My Hero Ones Justice 2','PC','By King','Bandai Namco','My Hero Academia',TRUE,2,95.5,'OVERWATCH.jpg');

--AUTHOR Eoin Hamill wrote the code.
--COAUTHOR DOVYDAS helped with coming up with idea and parameters.
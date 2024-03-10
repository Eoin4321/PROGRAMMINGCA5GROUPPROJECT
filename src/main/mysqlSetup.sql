DROP TABLE IF EXISTS GameInformation;

CREATE TABLE GameInformation (
                                 GameID INT NOT NULL AUTO_INCREMENT,
                                 Game_name VARCHAR(20) NOT NULL,
                                 Game_console VARCHAR(20) NOT NULL,
                                 Game_publisher VARCHAR(20) NOT NULL,
                                 Game_developer VARCHAR(20) NOT NULL,
                                 Game_franchise VARCHAR(20) NOT NULL,
                                 Game_releasedate DATE NOT NULL,
                                 Multiplayer Boolean NOT NUll,
                                 Player_amount INT NOT NULL,
                                 Review_Score INT NOT NULL,

                                 PRIMARY KEY (GameID)
);

INSERT INTO gameinformation (Game_name, Game_console,Game_publisher,Game_developer,Game_franchise,Game_releasedate,Multiplayer,Player_amount,Review_Score)
VALUES('Overwatch 2','PC','Activision Blizzard','Blizzard','Overwatch','2022-9-23',TRUE,10,100),
      ('Overwatch','PC','Activision Blizzard','Blizzard','Overwatch','2016-5-24',TRUE,12,97),
      ('Marvel Snap','Phone','Second Dinner','Second Dinner','Marvel','2022-10-18',TRUE,2,98),
      ('My Hero Ones Justice','PC','By King','Bandai Namco','My Hero Academia','2018-9-23',TRUE,2,99),
      ('My Hero Ones Justice','PC','By King','Bandai Namco','My Hero Academia','2020-3-12',TRUE,2,95);

--AUTHOR Eoin Hamill
--COAUTHOR DOVYDAS
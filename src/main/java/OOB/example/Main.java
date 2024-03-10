//AUTHOR EOIN HAMILL

package OOB.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import OOB.DAOs.DAO;

import OOB.DTOs.Game_Information;

public class Main{
    public static void main(String[] args) throws SQLException {
        //Creating keyboard
        Scanner keyboard = new Scanner(System.in);
        //Creating Variables
        boolean menu = true;
        int choice;
        //Connecting to database. Will return unable to connect to database if cant connect
        DAO dao =DAO.getInstance();
        System.out.println("Welcome to the Video Game Database!!!");
        while(menu==true)
        {
            System.out.println("Please Select An Option" +
                    "\n1.Get all Entities" +
                    "\n2.Find and Display (a single) Entity by Key" +
                    "\n3.Delete an Entity by key" +
                    "\n4.Insert an Entity" +
                    "\n5.Update an existing Entity by ID" +
                    "\n6.Get list of entities matching a filter (based on DTO object)" +
                    "\n7.Convert List of Entities to a JSON String" +
                    "\n8.Convert a single Entity by Key as a JSON String");
            choice= keyboard.nextInt();
            //THis will run function 1
            //Author of Function 1 Eoin Hamill
            //Helped with by Dovydas
            if(choice==1)
            {

                System.out.println(dao.getAllGames());
            }
            if(choice==2)
            {

            }
            if(choice==3) {

            }
            if(choice==4)
            {

            }
        }
    }
}

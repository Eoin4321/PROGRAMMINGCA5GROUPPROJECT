//AUTHOR EOIN HAMILL

package OOB.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import OOB.DAOs.DAO;

import OOB.DTOs.Game_Information;

public class Main{
    public static void main(String[] args) {
        //Creating keyboard
        Scanner keyboard = new Scanner(System.in);
        //Connecting to database. Will return unable to connect to database if cant connect
        DAO dao =DAO.getInstance();
    }
}

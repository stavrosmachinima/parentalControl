package com.parental.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.*;

@Controller
public class parentController {

    @GetMapping("/")
    public String sendForm(Model model){
        System.out.println("Going to Home Page");
        User user=new User();
        model.addAttribute("user",user);
        return "parentsInControl.html";
    }

    @PostMapping("/")
    public String processForm(@ModelAttribute("user") User user){
        System.out.println(user.toString());
        createTableIfNotExists();
        registerUser(user);
        return "parentsInControl.html";
    }

    private static void registerUser(User user){
        try(Connection connection=connect()){
            connection.setAutoCommit(false);
            String checkUser="SELECT * FROM USER WHERE USERNAME=?";
            try (PreparedStatement pst=connection.prepareStatement(checkUser)){
                pst.setString(1,user.getUsername());
                try(ResultSet rs=pst.executeQuery()){
                    if (!rs.isBeforeFirst())
                        System.out.println("No data");
                    else
                    {
                        System.out.println("User is already there");
                        user.setCheckIfUserAlreadyExists(true);
                        return;
                    }
                }
            }
            String query="INSERT INTO USER (username,firstname,lastname,email,password)" +
                    " values(?,?,?,?,?)";
            try (PreparedStatement pst=connection.prepareStatement(query)){
                pst.setString(1,user.getUsername());
                pst.setString(2,user.getFirstname());
                pst.setString(3,user.getLastname());
                pst.setString(4,user.getEmail());
                pst.setString(5,user.getPassword());
                pst.execute();
            }
            connection.commit();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private static void createTableIfNotExists(){
        try (Connection connection=connect()){
            String createTableSQL="CREATE TABLE IF NOT EXISTS USER(USERNAME NOT NULL PRIMARY KEY," +
                    "FIRSTNAME TEXT NOT NULL," +
                    " LASTNAME TEXT NOT NULL," +
                    " EMAIL TEXT NOT NULL," +
                    " PASSWORD TEXT NOT NULL)";
            try (PreparedStatement pst=connection.prepareStatement(createTableSQL)){
                pst.execute();
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private static Connection connect(){
        String url="jdbc:sqlite:src/main/resources/DB/parentalUser.db";
        Connection connection=null;
        try{
            System.out.println("Tries to connect");
            connection= DriverManager.getConnection(url);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return connection;
    }
}

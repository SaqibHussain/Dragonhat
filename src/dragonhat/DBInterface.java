/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dragonhat;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stephane
 */
public class DBInterface {
    public static int userID;
    
    private static final String url = "jdbc:mysql://localhost/";
    
    //remote working server
    //private static final String url = "jdbc:mysql://176.31.94.199/";
    
    private static final String dbname = "redhat";
    private static final String dblogin = "dragon";
    private static final String dbpassword = "dragonhat";
    
    private static Connection con;
    
    public DBInterface()
    {
        userID = -1;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url + dbname, dblogin, dbpassword);
        } catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void InitUser(String login, String password) throws Exception
    {
        int ID = -1;
        //Class.forName("com.mysql.jdbc.Driver");
        
        //try (Connection con = DriverManager.getConnection(url + dbname, dblogin, dbpassword)) {
            
            String query = "SELECT id_user FROM user WHERE login = ? AND password = ?";
            
            PreparedStatement stm = con.prepareStatement(query);
            
            stm.setString(1, login);
            stm.setString(2, password);

            ResultSet res = stm.executeQuery();
            while (res.next()){
                ID = res.getInt(1);
               
            }
        //}
        if(ID == -1)
        {
            throw new Exception("LOGIN_USER_ERROR_UNKNOWN_USER");
        }
        else
        {
            userID = ID;

        }
    }
    
    public static int CreateWorkflow(String workflowName, boolean autorecover) throws Exception
    {
        int result = -1;
        if(userID == -1)
        {
            throw new Exception("CREATE_WF_ERROR_UNKNOWN_USER");
        }
        else
        {
            //try (Connection con = DriverManager.getConnection(url + dbname, dblogin, dbpassword)) {
            
                String query = "INSERT INTO workflow (id_user, name_simulation, date_simulation, autorecover) VALUES (?, ?, now(), ?)";
            
            PreparedStatement stm = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            stm.setInt(1, userID);
            stm.setString(2, workflowName);
            if (autorecover){
                stm.setInt(3, 1);
            } else {
                stm.setInt(3, 0);
            }
            
            stm.executeUpdate();
            
            ResultSet rs = stm.getGeneratedKeys();
            rs.next();
            result = rs.getInt(1);
            
            if(result == -1)
            {
                throw new Exception("CREATE_WF_ERROR_INSERT");
            }
            else
            {
                return result;
            }
            
            //}
            
        }

    }
    
    
    public static List<HashMap<String,Object>> Read(int workflowID) throws Exception
    {
        if(userID == -1)
        {
            throw new Exception("READ_ERROR_UNKNOWN_USER");
        }
        else
        {
          
          
          //try (Connection con = DriverManager.getConnection(url + dbname, dblogin, dbpassword)) {
              
              String query = "SELECT * FROM result, workflow  WHERE id_workflow = id_simulation AND id_workflow = ? AND id_user = ?";
            
            PreparedStatement stm = con.prepareStatement(query);
            
            stm.setInt(1, workflowID);
            stm.setInt(2, userID);
           

            ResultSet rs = stm.executeQuery();

            //return res;
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
            
            while (rs.next()){
                HashMap<String,Object> row = new HashMap<String,Object>(columns);
                for(int i=1; i<=columns; i++)
                {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(row);
            }
            
            //return stm.executeQuery();
            return list;
          //}  
        }
        
    }
  
    
    
    public static List<HashMap<String,Object>> ReadLastGoodResults(int workflowID) throws Exception
    {
        if(userID == -1)
        {
            throw new Exception("READ_ERROR_UNKNOWN_USER");
        }
        else
        {
              
              String query = "(SELECT * FROM result, workflow  WHERE id_workflow = id_simulation AND id_workflow = ? AND id_user = ? AND status = ? ORDER BY id DESC LIMIT 30) ORDER BY id ASC";
            
            PreparedStatement stm = con.prepareStatement(query);
            
            stm.setInt(1, workflowID);
            stm.setInt(2, userID);
            stm.setString(3, Flag.GREEN.toString());

            ResultSet rs = stm.executeQuery();
            
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
            
            while (rs.next()){
                HashMap<String,Object> row = new HashMap<String,Object>(columns);
                for(int i=1; i<=columns; i++)
                {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(row);
            }
            
            return list;
         
        }
        

    }
    
    
    public static List<HashMap<String,Object>> ReadResultID(int workflowID, int resultID) throws Exception
    {
        if(userID == -1)
        {
            throw new Exception("READ_ERROR_UNKNOWN_USER");
        }
        else
        {
            
            String query = "SELECT * FROM result, workflow  WHERE id_workflow = id_simulation AND id_workflow = ? AND id_user = ? AND id = ? LIMIT 1";
            
            PreparedStatement stm = con.prepareStatement(query);
            
            stm.setInt(1, workflowID);
            stm.setInt(2, userID);
            stm.setInt(3, resultID);

            ResultSet rs = stm.executeQuery();
            
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
            
            while (rs.next()){
                HashMap<String,Object> row = new HashMap<String,Object>(columns);
                for(int i=1; i<=columns; i++)
                {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(row);
            }
            
            return list;
         
        }
        

    }
    
    
    public static List<HashMap<String,Object>> ReadResults(int workflowID) throws Exception
    {
        if(userID == -1)
        {
            throw new Exception("READ_ERROR_UNKNOWN_USER");
        }
        else
        {
          
          
          //try (Connection con = DriverManager.getConnection(url + dbname, dblogin, dbpassword)) {
              
              String query = "SELECT * FROM result, workflow  WHERE id_workflow = id_simulation AND id_workflow = ? AND id_user = ? AND status = ? ORDER BY liftdrag DESC LIMIT 0,10";
            
            PreparedStatement stm = con.prepareStatement(query);
            
            stm.setInt(1, workflowID);
            stm.setInt(2, userID);
            stm.setString(3, Flag.GREEN.toString());

            ResultSet rs = stm.executeQuery();

            //return res;
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
            
            while (rs.next()){
                HashMap<String,Object> row = new HashMap<String,Object>(columns);
                for(int i=1; i<=columns; i++)
                {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(row);
            }
            
            //return stm.executeQuery();
            return list;
          //}  
        }
        

    }
    
    public static List<HashMap<String,Object>> ReadWorkflows() throws Exception
    {
        if(userID == -1)
        {
            throw new Exception("READ_ERROR_UNKNOWN_USER");
        }
        else
        {
            String query = "SELECT * FROM workflow where id_user = ?";
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, userID);
            
             ResultSet rs = stm.executeQuery();
             
             ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
            
            while (rs.next()){
                HashMap<String,Object> row = new HashMap<String,Object>(columns);
                for(int i=1; i<=columns; i++)
                {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(row);
            }
            
            //return stm.executeQuery();
            return list;
        }
    }
    
    public static void Add(int workflowID, Parameter param, Result lift, Result drag) throws Exception
    {
        if(userID == -1)
        {
            throw new Exception("ADD_ERROR_UNKNOWN_USER");
        }
        else
        {
            //try (Connection con = DriverManager.getConnection(url + dbname, dblogin, dbpassword)) {
                            
            String checker = "SELECT * FROM workflow where id_user = ? AND id_simulation = ?";
            PreparedStatement checkstm = con.prepareStatement(checker);
            
            checkstm.setInt(1, userID);
            checkstm.setInt(2, workflowID);
            
             ResultSet checkres = checkstm.executeQuery();
             
             boolean exist = checkres.next();
             
             if(exist)
             {
                 String query = "INSERT INTO result (id_workflow, Cx, Cz, Sx, Sz, lift, drag, liftdrag, status, time, min_cx, max_cx, min_cz, max_cz, min_sx, max_sx, min_sz, max_sz) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?, ?, ?, ?, ?, ?, ?)";
                 PreparedStatement stm = con.prepareStatement(query);
                 
                 stm.setInt(1, workflowID);
                 
                 stm.setDouble(2, param.Cx);
                 stm.setDouble(3, param.Cz);
                 stm.setDouble(4, param.Sx);
                 stm.setDouble(5, param.Sz);
                 
                 stm.setDouble(6, lift.value);
                 stm.setDouble(7, drag.value);
                 
                 stm.setDouble(8, lift.value/drag.value);
                 
                 Flag f = Flag.GREEN;
                 
                 if(lift.f == Flag.AMBER || drag.f == Flag.AMBER)
                 {
                     f = Flag.AMBER;
                 }
                 
                 if(lift.f == Flag.RED || drag.f == Flag.RED)
                 {
                     f = Flag.RED;
                 }
                 
                 stm.setString(9, f.name());
                 
                 stm.setDouble(10, param.min_Cx);
                 stm.setDouble(11, param.max_Cx);
                 
                 stm.setDouble(12, param.min_Cz);
                 stm.setDouble(13, param.max_Cz);
                 
                 stm.setDouble(14, param.min_Sx);
                 stm.setDouble(15, param.max_Sx);
                 
                 stm.setDouble(16, param.min_Sz);
                 stm.setDouble(17, param.max_Sz);
                 
                 stm.executeUpdate();
             }
             else
             {
                 throw new Exception("ADD_ERROR_USER_WF_MISMATCH");
             }
             
        }
    }
    
     public static List<HashMap<String,Object>> LoadGraphResults(int workflowID) throws Exception
    {
        if(userID == -1)
        {
            throw new Exception("READ_ERROR_UNKNOWN_USER");
        }
        else
        {
              
              String query = "(SELECT * FROM result, workflow  WHERE id_workflow = id_simulation AND id_workflow = ? AND id_user = ? AND status = ? ORDER BY id DESC) ORDER BY id ASC";
            
            PreparedStatement stm = con.prepareStatement(query);
            
            stm.setInt(1, workflowID);
            stm.setInt(2, userID);
            stm.setString(3, Flag.GREEN.toString());

            ResultSet rs = stm.executeQuery();
            
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
            
            while (rs.next()){
                HashMap<String,Object> row = new HashMap<String,Object>(columns);
                for(int i=1; i<=columns; i++)
                {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(row);
            }
            
            return list;
         
        }
        

    }
}

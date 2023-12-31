
package database;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.User;

/**
 *
 * @author boi09
 */
public class SQLHandler {
    ConnectSQL cn = new ConnectSQL();
    Connection conn;
    public SQLHandler(){
        conn=cn.getConnection();
    }
    //Xác minh tài khoản
    public User verifyUser(User user) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT *\n"
                    + "FROM \"user\"\n"
                    + "WHERE username = ?\n"
                    + "AND pass = ?");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        (rs.getInt(9) != 0), //is playing
                        (rs.getInt(10) != 0));
                        //getRank(rs.getInt(1)));    
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    //Thay đổi thông tin
    public User chagnePro(User user) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(  "Update \"user\"\n"
                        + "set \"user\".nickname = ?, \"user\".avatar = ?\n"
                        + "where \"user\".ID = ?");
            preparedStatement.setString(1, user.getNickname());
            preparedStatement.setInt(2, Integer.parseInt(user.getAvatar()));
            preparedStatement.setInt(3, user.getID());
            preparedStatement.executeUpdate();           
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    //Thay đổi password
    public User chagnePass(User user) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(  "UPdate \"user\" Set \"user\".pass = ? where \"user\".ID= ?");
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setInt(2, user.getID());
            preparedStatement.executeUpdate();           
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    //
    // Kiểm tra ban
    public boolean checkIsBanned(User user){
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM banned_user WHERE ID_User = ?");
            preparedStatement.setInt(1, user.getID()); // set vào dấu ?
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true; // nếu có ban
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false; // nếu không bị ban
    }
    public boolean checkIsFriend(int ID1, int ID2) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT Friend.ID_User1\n"
                    + "FROM friend\n"
                    + "WHERE (ID_User1 = ? AND ID_User2 = ?)\n"
                    + "OR (ID_User1 = ? AND ID_User2 = ?)");
            preparedStatement.setInt(1, ID1);
            preparedStatement.setInt(2, ID2);
            preparedStatement.setInt(3, ID2);
            preparedStatement.setInt(4, ID1);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    // Thêm tài khoản
    public void addUser(User user) {
        try {
            conn=cn.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO \"user\"(username, pass, nickname, avatar)\n"
                    + "VALUES(?,?,?,?)");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getNickname());
            preparedStatement.setString(4, user.getAvatar());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    } 
    //Kiểm tra trùng đăng nhập hoặc trùng tài khoản đăng kí
    public boolean checkDuplicated(String username){
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM \"user\" WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }  
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    //Kiểm tra mật khẩu có đúng hay không
    public boolean checkDupPass(int ID ,String password){
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("Select \"user\".pass from \"user\" where \"user\".ID = ? and \"user\".pass= ?"  );
            preparedStatement.setInt(1, ID);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }  
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    public void makeFriend(int ID1, int ID2) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO friend(ID_User1,ID_User2)\n"
                    + "VALUES(?,?)");
            preparedStatement.setInt(1, ID1);
            preparedStatement.setInt(2, ID2);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    //Thay đổi trạng thái thành online
    public void updateToOnline(int ID) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE \"user\"\n"
                    + "SET IsOnline = 1\n"
                    + "WHERE ID = ?");
            preparedStatement.setInt(1, ID);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    //Thay đổi trạng thái thành offline
    public void updateToOffline(int ID) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE \"user\"\n"
                    + "SET IsOnline = 0\n"
                    + "WHERE ID = ?");
            preparedStatement.setInt(1, ID);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    //Thay đổi trạng thái thành đang chơi
    public void updateToPlaying(int ID){
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE \"user\"\n"
                    + "SET IsPlaying = 1\n"
                    + "WHERE ID = ?");
            preparedStatement.setInt(1, ID);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    //Thay đổi trạng thái thành không đang chơi    
    public void updateToNotPlaying(int ID){
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE \"user\"\n"
                    + "SET IsPlaying = 0\n"
                    + "WHERE ID = ?");
            preparedStatement.setInt(1, ID);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public int getRank(int ID) {
        int rank = 1;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT \"user\".ID\n" +
            "FROM \"user\"\n" +
            "ORDER BY (\"user\".NumberOfGame+\"user\".numberOfDraw*5+\"user\".NumberOfWin*10) DESC");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                if(rs.getInt(1)==ID)
                    return rank;
                rank++;
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }
    public List<User> getUserStaticRank() {
        List<User> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT *\n" +
                        "FROM \"user\"\n" +
                        "ORDER BY(\"user\".NumberOfGame+\"user\".numberOfDraw*5+\"user\".NumberOfWin*10) DESC");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        (rs.getInt(9) != 0),
                        (rs.getInt(10) != 0),
                        getRank(rs.getInt(1))));
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }
     public List<User> getListFriend(int ID) {
        List<User> ListFriend = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT \"User\".ID, \"User\".NickName, \"User\".IsOnline, \"User\".IsPlaying\n" 
                    + "FROM \"User\"\n" 
                    + "WHERE \"User\".ID IN ( SELECT ID_User1  FROM friend WHERE ID_User2 = ?) OR \"User\".ID IN( SELECT ID_User2 FROM friend WHERE ID_User1 = ?)");
            preparedStatement.setInt(1, ID);
            preparedStatement.setInt(2, ID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ListFriend.add(new User(rs.getInt(1),
                        rs.getString(2),
                        (rs.getInt(3)==1),
                        (rs.getInt(4))==1));
            }
            ListFriend.sort(new Comparator<User>(){
                @Override
                public int compare(User o1, User o2) {
                    if(o1.getIsOnline()&&!o2.getIsOnline())
                        return -1;
                    if(o1.getIsPlaying()&&!o2.getIsOnline())
                        return -1;
                    if(!o1.getIsPlaying()&&o1.getIsOnline()&&o2.getIsPlaying()&&o2.getIsOnline())
                        return -1;
                    return 0;
                }
                
            });
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ListFriend;
    }

    public void updateBannedStatus(User user,boolean ban){
        try {
            PreparedStatement preparedStatement1 = conn.prepareStatement("INSERT INTO banned_user (ID_User) VALUES (?)");
            PreparedStatement preparedStatement2 = conn.prepareStatement("DELETE FROM banned_user WHERE ID_User=?");
            if(ban){
                preparedStatement1.setInt(1, user.getID());
                preparedStatement1.executeUpdate();
            } else{
                preparedStatement2.setInt(1, user.getID());
                preparedStatement2.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
          public int getNumberOfWin(int ID) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT \"user\".NumberOfWin\n"
                    + "FROM \"user\"\n"
                    + "WHERE \"user\".ID = ?");
            preparedStatement.setInt(1, ID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }
    public int getNumberOfDraw(int ID) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT \"user\".NumberOfDraw\n"
                    + "FROM \"user\"\n"
                    + "WHERE \"user\".ID = ?");
            preparedStatement.setInt(1, ID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
     public void addDrawGame(int ID){
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE \"user\"\n"
                    + "SET \"user\".NumberOfDraw = ?\n"
                    + "WHERE \"user\".ID = ?");
            preparedStatement.setInt(1, new SQLHandler().getNumberOfDraw(ID)+1);
            preparedStatement.setInt(2, ID);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void addWinGame(int ID){
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE \"user\"\n"
                    + "SET \"user\".NumberOfWin = ?\n"
                    + "WHERE \"user\".ID = ?");
            preparedStatement.setInt(1, new SQLHandler().getNumberOfWin(ID)+1);
            preparedStatement.setInt(2, ID);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }   
    public int getNumberOfGame(int ID) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT \"user\".NumberOfGame\n" 
                    + "FROM \"user\"\n" 
                    + "WHERE \"user\".ID = ?");
            preparedStatement.setInt(1, ID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }
    public void addGame(int ID) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE \"user\"\n" 
                    + "SET \"user\".NumberOfGame = ?\n" 
                    + "WHERE \"user\".ID = ?");
            preparedStatement.setInt(1, new SQLHandler().getNumberOfGame(ID) + 1);
            preparedStatement.setInt(2, ID);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void decreaseGame(int ID){
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE \"user\"\n" 
                    + "SET \"user\".NumberOfGame = ?\n" 
                    + "WHERE \"user\".ID = ?");
            preparedStatement.setInt(1, new SQLHandler().getNumberOfGame(ID) - 1);
            preparedStatement.setInt(2, ID);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
        public String GetUsername(int ID) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("select username \n" 
                    + "from \"user\"\n" 
                    + "where ID=?");
            preparedStatement.setInt(1, ID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
        public String getNickNameByID(int ID) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT \"user\".NickName\n"
                    + "FROM \"user\"\n"
                    + "WHERE \"user\".ID=?");
            preparedStatement.setInt(1, ID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}

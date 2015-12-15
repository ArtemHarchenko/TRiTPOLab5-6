
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TODOlistDAO {
    private final Connection con;

    public TODOlistDAO(Connection con) {
        this.con = con;
    }

    private TODOlist getTaskFromRS(ResultSet rs) {
        TODOlist result = new TODOlist();
        try {
            result.setId(rs.getInt("id"));
            result.setDate(rs.getDate("Дата").toString());
            result.setTime(rs.getTime("Время").toString().substring(0, 5));
            result.setPlace(rs.getString("Место"));
            result.setDefinition(rs.getString("Описание"));
            result.setImportance(rs.getString("Важность"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getID(TODOlist task) {
        int result = 0;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM tasktable WHERE дата=? AND время=?");
            ps.setString(1, task.getDate());
            ps.setString(2, task.getTime());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt("id");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<TODOlist> getTaskList(String date) {
        ArrayList<TODOlist> result = new ArrayList<TODOlist>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM tasktable WHERE дата=?");
            ps.setString(1, date);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(getTaskFromRS(rs));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void update(TODOlist task) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE tasktable SET дата=?, время=?, место=?, описание=?, важность=?  WHERE id=?");

            ps.setInt(6, task.getId());
            ps.setString(1, task.getDate());
            ps.setString(2, task.getTime());
            ps.setString(3, task.getPlace());
            ps.setString(4, task.getDefinition());
            ps.setString(5, task.getImportance());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(TODOlist task) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO tasktable (дата, время, место, описание, важность, id) VALUES (?,?,?,?,?,?)");

            ps.setInt(6, task.getId());
            ps.setString(1, task.getDate());
            ps.setString(2, task.getTime());
            ps.setString(3, task.getPlace());
            ps.setString(4, task.getDefinition());
            ps.setString(5, task.getImportance());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(TODOlist task) {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM tasktable WHERE дата=? AND время=?");
            ps.setString(1, task.getDate());
            ps.setString(2, task.getTime());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
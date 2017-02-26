package buildit;


import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author c0538434
 */
@ApplicationScoped
@Named
public class Posts {
    private List<Post> posts = new ArrayList<>();
    
    public Posts() {
        
    }
    
    private void getPostsFromDb() {
        try {
            Connection conn = DBUtils.getConnection();
            
            posts = new ArrayList<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM posts");
            
            while (rs.next()) {
                Post p = new Post(rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getString("title"),
                rs.getDate("created_time"),
                rs.getString("contents"));
                posts.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Posts.class.getName()).log(Level.SEVERE, null, ex);
            posts = new ArrayList<>();
        }
    }
    
    public List<Post> getPosts() {
        return posts;
    }
    private Post currentPost = new Post();
    public Post getCurrentPost() {
        return currentPost;
    }
    
    public String viewPost(Post post) {
        currentPost = post;
        return "viewPost";
    }
}

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
    private Post currentPost = new Post();
    
    public Posts() {
        getPostsFromDb();
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
    
    public Post getCurrentPost() {
        return currentPost;
    }
    
    public String viewPost(Post post) {
        currentPost = post;
        return "viewPost";
    }
    
    public String editPost(Post post) {
        currentPost = post;
        return "editPost";
    }
    
    public String goNewPost() {
        currentPost = new Post();
        return "newPost";
    }
    
    public String newPost(int user_id, String title, String contents) {
        currentPost = new Post();
        int id = getNextPostId();
        try {
            Connection conn = DBUtils.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO posts VALUES (" + id +
                    ", " + user_id +
                    ", '" + title + 
                    "', NOW(), '" + contents + "')");
            getPostsFromDb();
            currentPost = getPostById(id);
            return "viewPost";
        } catch (SQLException ex) {
            Logger.getLogger(Posts.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }
    
    
    
    public String savePost(String title, String contents) {
        int id = currentPost.getId();
        try {
            Connection conn = DBUtils.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE posts SET title = '" + title + "', contents = '" + contents + "' WHERE id = " + currentPost.getId());
            getPostsFromDb();
            currentPost = getPostById(id);
            return "viewPost";
        } catch (SQLException ex) {
            Logger.getLogger(Posts.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }
    
    public String deletePost(Post thePost) {
        try {
            Connection conn = DBUtils.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM posts WHERE id = " + thePost.getId());
            getPostsFromDb();
            return "index";
        } catch (SQLException ex) {
            Logger.getLogger(Posts.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }
    
    private int getNextPostId() {
        int latestPost = 0;
        for (Post p : posts) {
            if (latestPost < p.getId())
                latestPost = p.getId();
        }
        return latestPost + 1;
    }
    
    private Post getPostById(int id) {
        for (Post p : posts) {
            if (p.getId() == id)
                return p;
        }
        return null;
    }
}

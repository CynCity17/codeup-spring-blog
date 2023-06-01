package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Category;
import com.codeup.codeupspringblog.models.Comment;
import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.CategoryRepository;
import com.codeup.codeupspringblog.repositories.CommentRepository;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class PostController {

    private PostRepository postsDao;
    private UserRepository usersDao;
    private CommentRepository commentsDao;
    private CategoryRepository categoriesDao;

    public PostController(PostRepository postsDao, UserRepository usersDao, CommentRepository commentsDao, CategoryRepository categoriesDao) {
        this.postsDao = postsDao;
        this.usersDao = usersDao;
        this.commentsDao = commentsDao;
        this.categoriesDao = categoriesDao;
    }

    public Set<Category> makeCategorySet(String categoriesCsl){
        Set<Category> categoryObjects = new HashSet<>();
        if (categoriesCsl.equals("")){
            return categoryObjects;
        }
        for (String category : categoriesCsl.split(",")){
            Category categoryObject = new Category(category.trim());
            categoryObjects.add(categoryObject);
        }
        return categoryObjects;
    }

    @GetMapping("/posts")
    public String allPosts(Model model) {
        List<Post> posts = postsDao.findAll();
        model.addAttribute("posts", posts);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String individualPost(@PathVariable long id, Model model){
        Post post = postsDao.findById(id);
        model.addAttribute("post", post);
        model.addAttribute("email", post.getUser().getEmail());
        return "posts/show";
    }

    @GetMapping("/posts-form")
    public String createForm() {
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String submitForm(@RequestParam(name="title") String title, @RequestParam(name="body") String body, @RequestParam(name="categories") String categories) {
        User user = usersDao.findUserById(1L);
        Post post = new Post(title, body, user);
        Set<Category> categorySet = makeCategorySet(categories);
        if (categorySet.size() > 0){
            List<Category> categoriesToAdd = new ArrayList<>();
            for (Category category : categorySet){
                Category categoryFromDb = categoriesDao.findCategoryByName(category.getName());
                if (categoryFromDb == null){
                    categoriesToAdd.add(category);
                } else {
                    categoriesToAdd.add(categoryFromDb);
                }
            }
            categorySet.clear();
            categorySet.addAll(categoriesToAdd);
            post.setCategories(categorySet);
        }
        postsDao.save(post);
        return "redirect:/posts";
    }

    @PostMapping("/posts/comment")
    public String submitComment(@RequestParam(name="content") String content, @RequestParam(name="postId") long postId){
        Post post = postsDao.findById(postId);
        User user = usersDao.findUserById(1L);
        Comment comment = new Comment(content, post, user);
        commentsDao.save(comment);
        return "redirect:/posts";
    }
}

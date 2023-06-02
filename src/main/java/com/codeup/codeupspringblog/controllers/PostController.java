package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Category;
import com.codeup.codeupspringblog.models.Comment;
import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.CategoryRepository;
import com.codeup.codeupspringblog.repositories.CommentRepository;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import com.codeup.codeupspringblog.services.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.*;

@Controller
public class PostController {

    private PostRepository postsDao;
    private UserRepository usersDao;
    private CommentRepository commentsDao;
    private CategoryRepository categoriesDao;

    private final EmailService emailService;

    public PostController(PostRepository postsDao, UserRepository usersDao, CommentRepository commentsDao, CategoryRepository categoriesDao, EmailService emailService) {
        this.postsDao = postsDao;
        this.usersDao = usersDao;
        this.commentsDao = commentsDao;
        this.categoriesDao = categoriesDao;
        this.emailService = emailService;
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
        Collections.reverse(posts);
        model.addAttribute("posts", posts);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String individualPost(@PathVariable long id, Model model) {
        Post post = postsDao.findById(id);
        model.addAttribute("post", post);

        User user = post.getUser();
        if (user != null) {
            model.addAttribute("email", user.getEmail());
        } else {
            model.addAttribute("email", "No user");
        }

        return "posts/show";
    }

    @GetMapping("/posts-form")
    public String createForm(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @GetMapping("/posts/{id}/edit")
    public String editForm(@PathVariable long id, Model model){
        Post post = postsDao.findById(id);
        model.addAttribute("post", post);
        return "posts/create";
    }

    @PostMapping("/posts/{id}/edit")
    public String submitEditForm(@PathVariable long id, @ModelAttribute Post post){
        User user = usersDao.findUserById(1l);
        post.setUser(user);
        post.setId(id);
        postsDao.save(post);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/create")
    public String submitForm(@ModelAttribute Post post, @RequestParam("categories") String categories) {
        User user = usersDao.findUserById(1L);
        post.setUser(user);
        Set<Category> categorySet = makeCategorySet(categories);
        if (!categorySet.isEmpty()) {
            List<Category> categoriesToAdd = new ArrayList<>();
            for (Category category : categorySet) {
                Category categoryFromDb = categoriesDao.findCategoryByName(category.getName());
                if (categoryFromDb == null) {
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

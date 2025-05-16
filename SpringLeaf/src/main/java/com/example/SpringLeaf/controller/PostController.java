package com.example.SpringLeaf.controller;

import com.example.SpringLeaf.dto.PostDto;
import com.example.SpringLeaf.entity.Post;
import com.example.SpringLeaf.entity.User;
import com.example.SpringLeaf.repository.PostRepository;
import com.example.SpringLeaf.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Controller
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/posts/new")
    public String showPostForm(Model model) {
        model.addAttribute("postDto", new PostDto());
        model.addAttribute("users", userRepository.findAll()); // for user dropdown
        return "post-form";
    }


    @GetMapping("/posts")
    public String showPosts(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "posts";
    }

    @PostMapping("/posts")
    public String savePost(@ModelAttribute("postDto") PostDto postDto) {
        User user = userRepository.findById(postDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setUser(user);

        postRepository.save(post);
        return "redirect:/posts";
    }
}

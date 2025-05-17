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

/*
🧠 BIG PICTURE: What This Controller Does

✅ Handles three routes:
   - GET  /posts/new   → show a form to create a new post
   - POST /posts       → save a new post submitted via form
   - GET  /posts       → display all existing posts in a table

❓ How PostDto connects to Post:
There’s no automatic mapping. You manually transfer fields from PostDto (used in the form)
to Post (the actual database entity). This keeps your database layer safe and clean.
*/

@Controller
public class PostController {

    // 🧩 Inject dependencies: Repositories for Post and User
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // ✅ Constructor-based dependency injection
    public PostController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 🧾 GET /posts/new — Show form to create a new post
    @GetMapping("/posts/new")
    public String showPostForm(Model model) {
        model.addAttribute("postDto", new PostDto()); // Empty form object for Thymeleaf binding
        model.addAttribute("users", userRepository.findAll()); // Fill dropdown with users
        return "post-form"; // Returns the name of the template (post-form.html)
    }

    // 📄 GET /posts — Display all posts
    @GetMapping("/posts")
    public String showPosts(Model model) {
        model.addAttribute("posts", postRepository.findAll()); // Load all posts from DB
        return "posts"; // Returns the posts.html view to display the list
    }

    // 💾 POST /posts — Handle form submission and save post
    @PostMapping("/posts")
    public String savePost(@ModelAttribute("postDto") PostDto postDto) {
        // Step 1: Look up the user by the ID selected in the form
        User user = userRepository.findById(postDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Step 2: Create a new Post entity and copy data from PostDto
        Post post = new Post();
        post.setContent(postDto.getContent()); // Form content
        post.setUser(user);                    // User selected in dropdown

        // Step 3: Save to database
        postRepository.save(post);

        // Step 4: Redirect to the list view (Post/Redirect/Get pattern)
        return "redirect:/posts";
    }
}



/*
🔁 Summary
Step                            What happens

new PostDto()	                Creates an empty object for the form
th:object="${postDto}"	        Tells Thymeleaf what object the form binds to
th:field="*{...}"	            Binds each field to a property of PostDto
On submit	                    Spring populates a PostDto from the form and passes it to the controller
 */
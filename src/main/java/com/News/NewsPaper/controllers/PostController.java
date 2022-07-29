package com.News.NewsPaper.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.News.NewsPaper.Model.Comment;
import com.News.NewsPaper.Model.Post;
import com.News.NewsPaper.repository.CommentRepository;
import com.News.NewsPaper.repository.PostRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	PostRepository postRepository;

	@Autowired
	CommentRepository commentRepository;

	@GetMapping("posts")
	//return this.http.get<Post[]>(`${baseUrl}?owner=${owner}`);
	public ResponseEntity<List<Post>> getAllPosts(@RequestParam(required = false) String owner) {
		try {
			List<Post> posts = new ArrayList<Post>();

			if (owner == null)
				//add each post find in the list posts
				postRepository.findAll().forEach(posts::add);
			else
				postRepository.findByOwnerContaining(owner).forEach(posts::add);

			if (posts.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(posts, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/posts/{id}")
	public ResponseEntity<Post> getPostById(@PathVariable("id") long id) {
		Optional<Post> postData = postRepository.findById(id);

		if (postData.isPresent()) {
			//get() to get elements from list optional<Post>
			return new ResponseEntity<>(postData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/posts")
	public ResponseEntity<Post> createPost(@RequestBody Post post) {
		try {
			Post _post = postRepository.save(new Post(post.getTitle(), post.getOwnerId(), post.getOwner(),
					post.getCategory(), post.getContent()));
			return new ResponseEntity<>(_post, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/posts/{id}")
	public ResponseEntity<Post> updatePost(@PathVariable("id") long id, @RequestBody Post post) {
		Optional<Post> postData = postRepository.findById(id);

		if (postData.isPresent()) {
			Post _post = postData.get();
			_post.setTitle(post.getTitle());
			// _post.setOwner(post.getOwner());
			_post.setCategory(post.getCategory());
			_post.setContent(post.getContent());
			return new ResponseEntity<>(postRepository.save(_post), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/posts/{id}")
	public ResponseEntity<HttpStatus> deletePost(@PathVariable("id") long id) {
		try {
			postRepository.deleteById(id);
			for (Comment com : commentRepository.findByPostid(id)) {
				commentRepository.delete(com);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/posts")
	public ResponseEntity<HttpStatus> deleteAllPosts() {
		try {
			postRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
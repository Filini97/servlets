package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
  private final ConcurrentHashMap<Long, Post> posts;
  private final AtomicLong idCounter = new AtomicLong(0);

  public PostRepository() {
    this.posts = new ConcurrentHashMap<>();
  }

  public List<Post> all() {
    return new ArrayList<>(posts.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    if (post.getId() != 0) {
      if (posts.containsKey(post.getId())) {
        posts.put(post.getId(), post);
        return post;
      } else {
        throw new NotFoundException();
      }
    } else {
      long newId = idCounter.incrementAndGet();
      post.setId(newId);
      posts.put(newId, post);
      return post;
    }
  }

  public void removeById(long id) {
    if (posts.containsKey(id)) {
      posts.remove(id);
    }
    else {
      throw new NotFoundException("wrong id");
    }
  }
}

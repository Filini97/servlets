package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;
import ru.netology.exception.NotFoundException;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
  private PostController controller;
  private static final String GET = "GET";
  private static final String POST = "POST";
  private static final String DELETE = "DELETE";
  private static final String API_POSTS = "/api/posts";
  private static final String PATH = "ru.netology";
  private static final int NOT_FOUND_STATUS = HttpServletResponse.SC_NOT_FOUND;
  private static final int INTERNAL_SERVER_ERROR_STATUS = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;


  @Override
  public void init() {
    final var context = new AnnotationConfigApplicationContext(PATH);
      controller = context.getBean(PostController.class);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();
      // primitive routing
      if (method.equals(GET) && path.equals(API_POSTS)) {
        controller.all(resp);
        return;
      }
      if (method.equals(GET) && path.matches(API_POSTS + "/\\d+")) {
        // easy way
        final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
        controller.getById(id, resp);
        return;
      }
      if (method.equals(POST) && path.equals(API_POSTS)) {
        controller.save(req.getReader(), resp);
      }

      if (method.equals(DELETE) && path.matches(API_POSTS + "/\\d+")) {
        // easy way
        final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
        controller.removeById(id, resp);
      }
    } catch (NotFoundException e) {
      e.getMessage();
      resp.setStatus(NOT_FOUND_STATUS);
    } catch (IOException ioException) {
      ioException.getMessage();
      resp.setStatus(INTERNAL_SERVER_ERROR_STATUS);
    }
  }
}


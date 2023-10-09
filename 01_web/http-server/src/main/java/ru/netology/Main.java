package ru.netology;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
  public static final int PORT = 8090;
  public static final int Threads = 64;
  public static void main(String[] args) {
    String filename="classic.html";
    Path pathToFile = Paths.get(filename);
    System.out.println(pathToFile.toAbsolutePath());
    Server server = new Server(PORT, Threads);
    server.start();
  }
}



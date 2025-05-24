package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Task;
import com.example.demo.repo.TaskRepository;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private TaskRepository repo;

  @GetMapping
  public List<Task> getAll() {
    return repo.findAll();
  }

  @PostMapping
  public Task create(@RequestBody Task task) {
    task.setCreatedAt(LocalDateTime.now());
    task.setStatus("pending");
    return repo.save(task);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task updatedTask) {
    Task task = repo.findById(id).orElseThrow();
    task.setTitle(updatedTask.getTitle());
    task.setStatus(updatedTask.getStatus());
    return ResponseEntity.ok(repo.save(task));
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    repo.deleteById(id);
  }
}

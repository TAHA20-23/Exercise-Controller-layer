package com.example.exercisecontroller.Controller;

import com.example.exercisecontroller.Model.Task;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final List<Task> tasks = new ArrayList<>();
    private int idCounter = 1;

    // add new task
    @PostMapping("/add")
    public Task addTask(@RequestBody Task task) {
        task.setId(idCounter++);
        tasks.add(task);
        return task;
    }

    // Bring all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;
    }

    // Update tasks
    @PutMapping("/update/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        for (Task task : tasks) {
            if (task.getId()==id) {
                task.setTitle(updatedTask.getTitle());
                task.setDescription(updatedTask.getDescription());
                task.setStatus(updatedTask.getStatus());
                return task;
            }
        }
        return null;
    }

    // Delete task
    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        tasks.removeIf(task -> task.getId()==id);
        return "Task with ID " + id + " deleted.";
    }

    // Change task status
    @PatchMapping("/status/{id}")
    public Task changeTaskStatus(@PathVariable Long id, @RequestParam String status) {
        for (Task task : tasks) {
            if (task.getId()==id) {
                task.setStatus(status);  // Change Status
                return task;
            }
        }
        return null;  // if task could not find
    }

    //Search for a task by title
    @GetMapping("/search")
    public List<Task> searchTaskByTitle(@RequestParam String title) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getTitle().equalsIgnoreCase(title)) {
                result.add(task);
            }
        }
        return result;
    }
}
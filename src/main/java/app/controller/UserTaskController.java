package app.controller;

import app.exception.NotFound;
import app.model.User;
import app.model.UserTask;
import app.repository.UserRepository;
import app.repository.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user/{user_id}/task")
public class UserTaskController {

    @Autowired
    private UserTaskRepository userTaskRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public Iterable<UserTask> listUserTasks(
            @PathVariable(value = "user_id") int userId) {
        return userTaskRepository.findByUserId(userId);
    }

    @GetMapping("/{task_id}")
    public UserTask readUserTask(
            @PathVariable(value = "user_id") int userId,
            @PathVariable(value = "task_id") int taskId) {
        return userTaskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new NotFound());
    }

    @PostMapping("")
    public UserTask createUserTask(
            @PathVariable(value = "user_id") int userId,
            @Valid @RequestBody UserTask userTask) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFound());
        userTask.setUser(user);
        this.userTaskRepository.save(userTask);

        return userTask;
    }

    @PutMapping("/{task_id}")
    public ResponseEntity updateUserTask(
            @PathVariable(value = "user_id") int userId,
            @PathVariable(value = "task_id") int taskId,
            @Valid @RequestBody UserTask userTask) {
        this.userTaskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(()->new NotFound());
        userTask.setId(taskId);
        this.userTaskRepository.save(userTask);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{task_id}")
    public ResponseEntity deleteUserTask(
            @PathVariable(value = "user_id") int userId,
            @PathVariable(value = "task_id") int taskId) {
        this.userTaskRepository.deleteByIdAndUserId(userId, taskId);

        return ResponseEntity.noContent().build();
    }
}
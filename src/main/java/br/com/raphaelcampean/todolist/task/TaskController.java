package br.com.raphaelcampean.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.raphaelcampean.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping
    public ResponseEntity<TaskModel> create(@RequestBody TaskModel task, HttpServletRequest request) {
        var userId = request.getAttribute("userId"); 
        task.setUserId((UUID) userId);

        var currentDateTime = LocalDateTime.now();

        if (currentDateTime.isAfter(task.getStartAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(task);
        }

        if (task.getStartAt().isAfter(task.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(task);
        }

        this.taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping
    public List<TaskModel> tasks(HttpServletRequest request) {
        var userId = request.getAttribute("userId");
        var tasks = this.taskRepository.findByUserId((UUID) userId);
        return tasks;
    }

    @PutMapping("/{id}")
    public Optional<TaskModel> update(@RequestBody TaskModel task, HttpServletRequest request, @PathVariable UUID id) {
        var userId = request.getAttribute("userId");
        task.setUserId((UUID) userId);

        var task1 = this.taskRepository.findById(id);

        Utils.copyNonNullProperties(task, task1);
        return task1;
    }

    @PatchMapping("/{id}")
    public ResponseEntity updatePartial(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
        var taskFound = this.taskRepository.findById(id).orElse(null);

        if (taskFound == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada");
        }

        var userId = request.getAttribute("userId");
        if (!taskFound.getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autorizado");
        }

        Utils.copyNonNullProperties(taskModel, taskFound);

        var taskUpdated = this.taskRepository.save(taskFound);

        return ResponseEntity.ok().body(taskUpdated);
    }
}

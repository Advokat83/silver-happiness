package main;

import main.model.Task;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
public class TaskController {

    @Autowired
    private final TaskRepository taskRepository;
    private LocalDateTime currentDateTime = LocalDateTime.now();

    public TaskController(TaskRepository taskRepository) {

        this.taskRepository = taskRepository;
    }

    @PostMapping(value = "/tasks/")
    @ResponseStatus(HttpStatus.CREATED)
    public int add (Task taskResp) {

        Task task = new Task();
        task.setTitle(taskResp.getTitle());
        task.setDescription(taskResp.getDescription());
        taskResp.setDone(false);
        taskResp.setCreationTime(currentDateTime);
        Task request = taskRepository.save(taskResp);
        System.out.println("Добавлена задача: " + "\n" + request.getTitle() +
                " - " + request.getDescription() + " от " + request.getCreationTime());
        return request.getId();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Object> getTask (@PathVariable int id) {

        Optional<Task> optionalTask = taskRepository.findById(id);

        if (!optionalTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return new ResponseEntity<>(optionalTask.get(), HttpStatus.OK);
    }

    @GetMapping("/tasks/")
    public List<Task> lists () {

        Iterable<Task> taskIterable = taskRepository.findAll();
        ArrayList<Task> taskList = new ArrayList<>();

        for (Task task : taskIterable) {
            taskList.add(task);
        }
        return taskList;
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Object> deleteTaskById(@PathVariable int id) {

        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            taskRepository.delete(task);
            System.out.println("Задача с id: " + task.getId() + " успешно удалена");
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/tasks/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity <Object> patchTask (@PathVariable int id, @RequestBody Task updateTask) {

        Task task = taskRepository.findById(id).orElse(null);

        if (task != null) {

            task.setDone(updateTask.getIsDone());
            task.setTitle(updateTask.getTitle());
            task.setDescription(updateTask.getDescription());

            taskRepository.save(task);

            System.out.println("Задача с id: " + task.getId() + " по переданным параметрам успешно изменена");
            return ResponseEntity.ok("Задача по переданным параметрам успешно изменена");
        } else {

            return ResponseEntity.notFound().build();
        }
    }
}

package app;

import app.enumeration.UserTaskStatus;
import app.model.UserTask;
import app.repository.UserTaskRepository;
import app.task.ProcessUserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class ScheduledTasks {
    @Autowired
    private UserTaskRepository userTaskRepository;

    @Autowired
    private ProcessUserTask processUserTask;

    @Scheduled(fixedDelay = 5000, initialDelay = 10000)
    public void reportCurrentTime() {
        List<UserTask> userTaskList = userTaskRepository.findByProcessAtLessThanAndStatus(
                new Timestamp(System.currentTimeMillis()),
                UserTaskStatus.PENDING
        );

        for (UserTask userTask: userTaskList) {
            processUserTask.lock(userTask);
            processUserTask.execute(userTask);
        }
    }
}
package app.task;

import app.enumeration.UserTaskStatus;
import app.model.UserTask;
import app.repository.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ProcessUserTask {
    @Autowired
    private UserTaskRepository userTaskRepository;

    public void lock(UserTask userTask) {
        userTask.setStatus(UserTaskStatus.PROCESSING);
        userTaskRepository.save(userTask);
    }

    @Async
    public void execute(UserTask userTask) {
        userTask.touchProcessStartedAt();
        userTask.setStatus(UserTaskStatus.PROCESSING);
        try {
            System.out.printf("Starting work on user task id %d with name \"%s\"\n",
                    userTask.getId(), userTask.getName());
            Thread.sleep(1000);
            userTask.setStatus(UserTaskStatus.DONE);
            System.out.printf("Completed user task %d on thread \"%s\"\n",
                    userTask.getId(), Thread.currentThread().getName());
        }
        catch (Exception e) {
            userTask.setStatus(UserTaskStatus.FAILED);
            System.out.printf("User task %d failed on thread \"%s\" with message \"%s\"\n",
                    userTask.getId(), Thread.currentThread().getName(), e.getMessage());
        }
        finally {
            userTask.touchProcessCompletedAt();
            userTaskRepository.save(userTask);
        }
    }
}

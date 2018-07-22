package app.repository;

import app.enumeration.UserTaskStatus;
import app.model.UserTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserTaskRepository extends CrudRepository<UserTask, Integer> {
    List<UserTask> findByUserId(int userId);
    List<UserTask> findByProcessAtLessThanAndStatus(Timestamp timestamp, UserTaskStatus userTaskStatus);
    Optional<UserTask> findByIdAndUserId(int id, int userId);

    @Transactional
    void deleteByIdAndUserId(int id, int userId);
}

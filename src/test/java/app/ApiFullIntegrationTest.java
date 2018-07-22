package app;

import app.model.User;
import app.model.UserTask;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiFullIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void crud() throws Exception {
        // Create a user
        String firstName = "Test First Name";
        String lastName = "Test Last Name";
        String username = "Test.First.Last.Username";
        int userId = 1;

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);

        this.mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username").value(username))
                    .andExpect(jsonPath("$.first_name").value(firstName))
                    .andExpect(jsonPath("$.last_name").value(lastName))
                    .andExpect(jsonPath("$.id").value(userId))
                ;

        // Update a user
        firstName = firstName + " Modified";
        user.setFirstName(firstName);
        this.mockMvc.perform(put("/api/user/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                    .andExpect(status().isNoContent())
                ;

        // Read a user
        this.mockMvc.perform(get("/api/user/" + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username").value(username))
                    .andExpect(jsonPath("$.first_name").value(firstName))
                    .andExpect(jsonPath("$.last_name").value(lastName))
                    .andExpect(jsonPath("$.id").value(userId))
                ;

        // List users
        this.mockMvc.perform(get("/api/user")
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].username").value(username))
                    .andExpect(jsonPath("$[0].first_name").value(firstName))
                    .andExpect(jsonPath("$[0].last_name").value(lastName))
                    .andExpect(jsonPath("$[0].id").value(userId))
                ;

        // Create a task
        Timestamp taskProcessAt = new Timestamp(System.currentTimeMillis());
        String taskName = "Test Task Name";
        String taskDescription = "Test Task Description";
        int taskId = 1;

        UserTask userTask = new UserTask();
        userTask.setName(taskName);
        userTask.setDescription(taskDescription);
        userTask.setProcessAt(taskProcessAt);

        this.mockMvc.perform(post("/api/user/" + userId + "/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userTask)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value(taskName))
                    .andExpect(jsonPath("$.description").value(taskDescription))
                ;

        // Update a task
        taskName = taskName + " Modified";
        userTask.setName(taskName);
        this.mockMvc.perform(put("/api/user/" + userId + "/task/" + taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userTask)))
                    .andExpect(status().isNoContent())
                ;

        // Read a task
        this.mockMvc.perform(get("/api/user/" + userId + "/task/" + taskId)
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value(taskName))
                    .andExpect(jsonPath("$.description").value(taskDescription))
                    .andExpect(jsonPath("$.id").value(taskId))
                ;

        // List users
        this.mockMvc.perform(get("/api/user/" + userId + "/task")
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].name").value(taskName))
                    .andExpect(jsonPath("$[0].description").value(taskDescription))
                    .andExpect(jsonPath("$[0].id").value(taskId))
                ;

        // Delete a task
        this.mockMvc.perform(delete("/api/user/" + userId + "/task/" + taskId))
                    .andExpect(status().isNoContent())
                ;

        // Assert task removed by loading again
        // Read a task
        this.mockMvc.perform(get("/api/user/" + userId + "/task/" + taskId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
        ;
    }

}

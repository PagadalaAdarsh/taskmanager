# Task Manager - Full Stack Application

This is a simple full stack **Task Manager** application built using:
- **Backend:** Java Spring Boot with MySQL
- **Frontend:** React.js (Vite)
- **Database:** MySQL
- **Object-Relational Mapping:** Spring Data JPA
- **API Communication:** Axios

---

## 🔧 Project Setup Instructions

### ✅ Backend (Spring Boot)

**Technologies Used:**
- Java
- Spring Boot
- Spring Data JPA
- MySQL

**Backend Code Structure:**

> `Task.java` (Entity)
```java
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String status;
    private LocalDateTime createdAt;
    // Getters and Setters
}
```

> `TaskRepository.java`
```java
public interface TaskRepository extends JpaRepository<Task, Long> {}
```

> `TaskController.java`
```java
@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*") // For CORS support
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

    @PutMapping("/<built-in function id>")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task updatedTask) {
        Task task = repo.findById(id).orElseThrow();
        task.setTitle(updatedTask.getTitle());
        task.setStatus(updatedTask.getStatus());
        return ResponseEntity.ok(repo.save(task));
    }

    @DeleteMapping("/<built-in function id>")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
```

> `application.properties`
```properties
spring.application.name=taskmanager
spring.datasource.url=jdbc:mysql://localhost:3306/taskdb
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

---

### ✅ Frontend (React.js with Vite)

**Install Dependencies:**
```bash
npm install
```

**Start the Server:**
```bash
npm run dev
```

> `App.jsx`
```jsx
import React, { useEffect, useState } from "react";
import axios from "axios";

function App() {
  const [tasks, setTasks] = useState([]);
  const [title, setTitle] = useState("");

  useEffect(() => {
    axios.get("http://localhost:8080/tasks")
      .then(res => setTasks(res.data));
  }, []);

  const addTask = () => {
    axios.post("http://localhost:8080/tasks", { title })
      .then(res => setTasks([...tasks, res.data]));
  };

  const deleteTask = id => {
    axios.delete(`http://localhost:8080/tasks/${id}`)
      .then(() => setTasks(tasks.filter(t => t.id !== id)));
  };

  return (
    <div className="p-4">
      <h1>Task Manager</h1>
      <input value={title} onChange={e => setTitle(e.target.value)} />
      <button onClick={addTask}>Add</button>
      <ul>
        {tasks.map(t => (
          <li key={t.id}>
            {t.title} - {t.status}
            <button onClick={() => deleteTask(t.id)}>X</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;
```

---

## 📁 Folder Structure

```
taskmanager/
├── backend/
│   ├── src/main/java/com/example/demo/
│   │   ├── controller/TaskController.java
│   │   ├── entity/Task.java
│   │   └── repo/TaskRepository.java
│   └── resources/application.properties
├── frontend/
│   └── src/App.jsx
```

---

## ✅ Testing and Deployment

- **Backend Tested using Postman**
- **MySQL Database created successfully (`taskdb`)**
- **React Frontend tested with live backend**
- **Git Push Error Resolved using:** `git pull origin main --rebase`

---


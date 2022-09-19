import App from "./components/App"
import TaskForm from "./components/TaskForm"
import TaskList from "./components/TaskList"
import TaskRepository from "./repositories/TaskRepository"
import "./public/index.html"
import "./public/main.css"

const taskRepository = new TaskRepository()
const taskList = new TaskList(taskRepository)
const taskForm = new TaskForm(taskList, taskRepository)
const app = new App(taskList, taskForm)

app.init()

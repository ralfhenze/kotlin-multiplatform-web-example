import {tasklist} from "shared"
import EnUsLocale = tasklist.i18n.EnUsLocale
import TaskValidator = tasklist.domain.TaskValidator
import App from "./components/App"
import TaskForm from "./components/TaskForm"
import TaskList from "./components/TaskList"
import TaskRepository from "./repositories/TaskRepository"
import "./public/index.html"
import "./public/main.css"

const locale = new EnUsLocale()
const validator = new TaskValidator()
const taskRepository = new TaskRepository()
const taskList = new TaskList(taskRepository, locale)
const taskForm = new TaskForm(taskList, taskRepository, validator, locale)
const app = new App(taskList, taskForm)

app.init()

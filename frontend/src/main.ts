import {tasklist} from "shared"
import EnUsLocalization = tasklist.i18n.EnUsLocalization
import TaskValidator = tasklist.domain.TaskValidator
import App from "./components/App"
import TaskForm from "./components/TaskForm"
import TaskList from "./components/TaskList"
import TaskRepository from "./repositories/TaskRepository"
import "./public/asap-regular.woff2"
import "./public/asap-bold.woff2"
import "./public/main.css"
import "./public/index.html"

const localization = new EnUsLocalization()
const validator = new TaskValidator()
const taskRepository = new TaskRepository()
const taskList = new TaskList(taskRepository, localization)
const taskForm = new TaskForm(taskList, taskRepository, validator, localization)
const app = new App(taskList, taskForm)

app.init()

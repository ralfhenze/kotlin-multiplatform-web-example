import TaskList from "./TaskList"
import TaskForm from "./TaskForm"

export default class App {

    constructor(
        private taskList: TaskList,
        private taskForm: TaskForm,
    ) {}

    init() {
        document.getElementById("app").innerHTML = this.getHtml()

        this.taskList.init()
        this.taskForm.init()
    }

    getHtml(): string {
        return `
            <div>
                <h1>My Tasks</h1>
                ` + this.taskList.getHtml() + `
                ` + this.taskForm.getHtml() + `
                </ul>
            </div>`
    }
}

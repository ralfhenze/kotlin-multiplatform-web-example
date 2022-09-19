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
                <h1>Task List</h1>
                <p>
                    This is a very simple web application to demonstrate code sharing between
                    backend and frontend with Kotlin Multiplatform.
                </p>
                ` + this.taskList.getHtml() + `
                ` + this.taskForm.getHtml() + `
            </div>`
    }
}

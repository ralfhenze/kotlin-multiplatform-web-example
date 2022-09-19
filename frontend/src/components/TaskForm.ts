import Task from "../types/Task"
import TaskList from "./TaskList"
import TaskRepository from "../repositories/TaskRepository"

export default class TaskForm {

    constructor(
        private taskList: TaskList,
        private taskRepo: TaskRepository,
    ) {}

    init() {
        const self = this

        document
            .getElementById("create-task-form")
            .addEventListener("submit", event => {
                this.onSubmit(event, self)
            })
    }

    reset() {
        (document.getElementById("state") as HTMLFormElement).value = "TODO";
        (document.getElementById("description") as HTMLFormElement).value = "";
    }

    onSubmit(event: Event, self: TaskForm) {
        event.preventDefault()

        const state = (document.getElementById("state") as HTMLFormElement).value
        const description = (document.getElementById("description") as HTMLFormElement).value

        self.taskRepo
            .createTask(new Task(null, state, description))
            .then(_ => {
                self.taskList.refresh()
                self.reset()
            })
    }

    getHtml(): string {
        return `
            <h2>Create a new task</h2>
            <form id="create-task-form">
                <div>
                    <label>State</label>
                    <select id="state" name="state">
                        <option value="TODO">TODO</option>
                        <option value="IN_PROGRESS">IN PROGRESS</option>
                        <option value="DONE">DONE</option>
                    </select>
                </div>

                <div>
                    <label>Description</label>
                    <textarea id="description" name="description"></textarea>
                </div>

                <div>
                    <input type="submit" value="Create">
                </div>
            </form>`
    }
}

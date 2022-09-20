import {tasklist} from "shared"
import EnUsLocale = tasklist.i18n.EnUsLocale
import TaskState = tasklist.domain.TaskState
import TaskList from "./TaskList"
import TaskRepository from "../repositories/TaskRepository"

export default class TaskForm {

    constructor(
        private taskList: TaskList,
        private taskRepo: TaskRepository,
        private locale: EnUsLocale,
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
        (document.getElementById("state") as HTMLFormElement).value = TaskState.TODO.name;
        (document.getElementById("description") as HTMLFormElement).value = "";
    }

    onSubmit(event: Event, self: TaskForm) {
        event.preventDefault()

        const state = (document.getElementById("state") as HTMLFormElement).value
        const description = (document.getElementById("description") as HTMLFormElement).value

        self.taskRepo
            .createTask({id: null, state: state, description: description})
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
                    <select id="state" name="state">` +
                        TaskState.values()
                            .map(it => `<option value="` + it.name + `">` + this.locale.getTaskStateLabel(it) + `</option>`)
                            .join("\n") + `
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

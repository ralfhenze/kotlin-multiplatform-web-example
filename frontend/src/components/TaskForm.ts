import {tasklist} from "shared"
import Localization = tasklist.i18n.Localization
import TaskState = tasklist.domain.TaskState
import TaskValidator = tasklist.domain.TaskValidator
import TaskList from "./TaskList"
import TaskRepository from "../repositories/TaskRepository"

export default class TaskForm {

    constructor(
        private taskList: TaskList,
        private taskRepo: TaskRepository,
        private validator: TaskValidator,
        private l10n: Localization,
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

        const createTaskForm = document.getElementById("create-task-form")
        const descriptionInput = (document.getElementById("description") as HTMLFormElement)

        createTaskForm.className = ""
        descriptionInput.className = ""
        document.getElementById("error-message")?.remove()

        const state = (document.getElementById("state") as HTMLFormElement).value
        const description = descriptionInput.value

        const errorMessage = self.validator.getDescriptionErrorMessage(description)

        if (errorMessage) {
            descriptionInput.className = "error"
            descriptionInput.insertAdjacentHTML(
                "afterend",
                "<p id=\"error-message\" class=\"error-message\">" + errorMessage + "</p>"
            )
            createTaskForm.className = "has-error"
            return
        }

        self.taskRepo
            .createTask({id: null, state: state, description: description})
            .then(_ => {
                self.taskList.refresh()
                self.reset()
            })
    }

    getHtml(): string {
        return `
            <ul id="task-form">
                <li>
                    <form id="create-task-form">
                        <input type="text" id="description" name="description" />
                        <select id="state" name="state">` +
                            TaskState.values()
                                .map(state =>
                                    `<option value="` + state.name + `">`
                                    + this.l10n.getTaskStateLabel(state)
                                    + `</option>`
                                )
                                .join("\n") + `
                        </select>
                        <input type="submit" id="submit-button" value="Add" />
                    </form>
                </li>
            </ul>`
    }
}

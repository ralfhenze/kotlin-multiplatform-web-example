import {tasklist} from "shared"
import Localization = tasklist.i18n.Localization
import TaskSchema = tasklist.web.TaskSchema
import TaskRepository from "../repositories/TaskRepository"

export default class TaskList {

    constructor(
        private taskRepo: TaskRepository,
        private l10n: Localization,
    ) {}

    init() {
        this.loadAllTasks()
    }

    refresh() {
        this.loadAllTasks()
    }

    loadAllTasks() {
        this.taskRepo
            .getAllTasks()
            .then(tasks => {
                document.getElementById("task-list").innerHTML = this.getTasksHtml(tasks)

                const deleteButtons = document.getElementsByClassName("delete")

                Array.from(deleteButtons).forEach(deleteButton => {
                    const id = parseInt(deleteButton.getAttribute("data-id"))
                    deleteButton.addEventListener("click", _ => {
                        this.onDeleteClick(id)
                    })
                })
            })
    }

    onDeleteClick(taskId: number) {
        this.taskRepo
            .deleteTask(taskId)
            .then(_ => {
                this.loadAllTasks()
            })
    }

    getHtml(): string {
        return `
            <h2>My tasks</h2>
            <ul id="task-list">
            ` + this.getTasksHtml([]) + `
            </ul>`
    }

    getTasksHtml(tasks: TaskSchema[]): string {
        let html = ""

        for (const task of tasks) {
            const state = this.l10n.getTaskStateLabel(task.state)

            html += `
                <li>
                    <button class="delete" data-id="` + task.id + `">Delete</button>
                    <div class="state">` + state + `</div>
                    <div>` + task.description + `</div>
                    <div class="clear"></div>
                </li>`
        }

        return html
    }
}

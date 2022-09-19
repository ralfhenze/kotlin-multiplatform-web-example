import TaskRepository from "../repositories/TaskRepository"
import Task from "../types/Task"

export default class TaskList {

    constructor(
        private taskRepo: TaskRepository,
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
                    deleteButton.addEventListener("click", event => {
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

    getTasksHtml(tasks: Task[]): string {
        let html = ""

        for (const task of tasks) {
            html += `
                <li>
                    <button class="delete" data-id="` + task.id + `">Delete</button>
                    <div class="state">` + task.state + `</div>
                    <div>` + task.description + `</div>
                    <div class="clear"></div>
                </li>`
        }

        return html
    }
}

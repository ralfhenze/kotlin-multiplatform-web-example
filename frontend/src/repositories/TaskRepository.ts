import axios from "axios";
import type Task from "@/types/Task";

export default class TaskRepository {

    async createTask(task: Task) {
        await axios
            .post("http://localhost:7070/api/task", task)
            .then(response => {
                console.log(response)
            })
    }

    async getAllTasks(): Promise<Task[]> {
        return await axios
            .get<Task[]>("http://localhost:7070/api/task")
            .then(response => {
                return response.data
            })
    }
}

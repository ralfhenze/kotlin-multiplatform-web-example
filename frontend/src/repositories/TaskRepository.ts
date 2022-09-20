import axios from "axios"
import {tasklist} from "shared"
import TaskSchema = tasklist.web.TaskSchema
import TaskState = tasklist.domain.TaskState;

export default class TaskRepository {

    async createTask(task: TaskSchema) {
        await axios
            .post("http://localhost:7070/api/task", task)
            .then(response => {
                console.log(response)
            })
    }

    async getAllTasks(): Promise<TaskSchema[]> {
        return await axios
            .get<TaskSchema[]>("http://localhost:7070/api/task")
            .then(response => {
                console.log(response)

                // If we'd just return response.data as it is, the objects wouldn't contain a real
                // instance of the TaskState enum value, but just a string. This would lead to
                // errors later, when we want to call methods of that enum, which are not defined
                // on a string.
                //
                // On the JVM-backend the JSON parsing library Jackson automatically converts
                // incoming string properties to their corresponding enum instances. But here we
                // have to do it manually:

                return (response.data as TaskSchema[]).map(task => {
                    return {
                        id: task.id,
                        state: TaskState.valueOf(task.state as unknown as string),
                        description: task.description,
                    }
                })

                // A little less hacky, but not typesafe alternative would be:
                //
                // return (response.data as any[]).map(task => {
                //     return {
                //         id: task["id"],
                //         state: TaskState.valueOf(task["state"]),
                //         description: task["description"],
                //     }
                // })
                //
                // Even though it doesn't have the hacky type conversions for the state property,
                // it won't throw a compilation error, if any of the properties get renamed,
                // because the property access is made with strings.
            })
    }

    async deleteTask(taskId: number): Promise<boolean> {
        return await axios
            .delete("http://localhost:7070/api/task/" + taskId)
            .then(response => {
                console.log(response)
                return true
            })
    }
}

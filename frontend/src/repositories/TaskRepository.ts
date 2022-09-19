import axios from "axios";

export default class TaskRepository {

    async createTask(state: string, description: string) {
        await axios.post("http://localhost:7070/api/task", {
            state: state,
            description: description,
        }).then(response => {
            console.log(response)
        })
    }
}

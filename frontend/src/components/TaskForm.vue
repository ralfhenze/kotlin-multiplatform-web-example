<script lang="ts">
import { defineComponent } from "vue";
import TaskRepository from "@/repositories/TaskRepository";
import Task from "@/types/Task";

let taskRepo = new TaskRepository()

export default defineComponent({
    data() {
        return {
            state: "",
            description: "",
        }
    },
    methods: {
        onCreateTaskFormSubmit(event: Event) {

            taskRepo.createTask(new Task(null, this.state, this.description))

            event.preventDefault()
        }
    }
})
</script>

<template>
    <h2>Create a new task</h2>
    <form @submit="onCreateTaskFormSubmit">
        <div>
            <label>State</label>
            <select name="state" v-model="state">
                <option value="TODO">TODO</option>
                <option value="IN_PROGRESS">IN PROGRESS</option>
                <option value="DONE">DONE</option>
            </select>
        </div>

        <div>
            <label>Description</label>
            <textarea name="description" v-model="description"></textarea>
        </div>

        <div>
            <input type="submit" value="Create">
        </div>
    </form>
</template>

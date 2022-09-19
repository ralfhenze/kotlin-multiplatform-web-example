<script lang="ts">
import { defineComponent } from "vue";
import TaskRepository from "@/repositories/TaskRepository";
import type Task from "@/types/Task";

let taskRepo = new TaskRepository()

export default defineComponent({
    data() {
        return {
            tasks: [] as Task[],
        }
    },
    mounted() {
        this.loadAllTasks()
    },
    methods: {

        onDeleteClick(taskId: number) {
            taskRepo
                .deleteTask(taskId)
                .then(_ => {
                    this.loadAllTasks()
                })
        },

        loadAllTasks() {
            taskRepo.getAllTasks().then(tasks => {
                this.tasks = tasks
            })
        },
    }
})
</script>

<template>
    <h2>My tasks</h2>
    <ul>
        <li v-for="task in tasks">
            <button class="delete" @click="onDeleteClick(task.id)">Delete</button>
            <div class="state">{{task.state}}</div>
            <div>{{task.description}}</div>
            <div class="clear"></div>
        </li>
    </ul>
</template>

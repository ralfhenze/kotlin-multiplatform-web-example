<script setup lang="ts">
import TaskForm from "@/components/TaskForm.vue";
import TaskList from "@/components/TaskList.vue";
</script>

<template>
    <h1>Task List</h1>
    <p>
        This is a very simple web application to demonstrate code sharing between backend
        and frontend with Kotlin Multiplatform.
    </p>
    <TaskList />
    <TaskForm />
</template>

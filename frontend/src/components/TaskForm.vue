<script setup lang="ts">
</script>

<template>
    <form>
        <div>
            <label>State</label>
            <select>
                <option>TODO</option>
                <option>IN PROGRESS</option>
                <option>DONE</option>
            </select>
        </div>

        <div>
            <label>Description</label>
            <textarea></textarea>
        </div>

        <div>
            <input type="submit" value="Create">
        </div>
    </form>
</template>

<template>
    <div id="tab" v-if="isSelected">
        <slot></slot>
    </div>
</template>

<script>
export default {
    props: {
        isSelected: {
            type: Boolean,
        },
    },
};
</script>

<style scoped>
    #tab{
        margin-top: 30px;
    }
</style>

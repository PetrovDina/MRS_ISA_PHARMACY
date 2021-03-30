<template>
    <button class="btn" @click="onClick()" :style="{background : color}">{{text}}</button>
</template>

<script>
export default {
    name: 'Button',
    props: {
        text: String,
        color: String,
    },
    methods:{
        onClick : function(){
            this.$emit('action-performed')
        }
    }
}
</script>

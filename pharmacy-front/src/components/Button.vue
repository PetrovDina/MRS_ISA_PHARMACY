<template>
    <button class="btn" @click="onClick()" :style="{background : bgd_color}">{{text}}</button>
</template>

<script>
export default {
    name: 'Button',
    props: {
        text: String,
        bgd_color: String,
    },
    methods:{
        onClick : function(){
            this.$emit('action-performed')
        }
    }
}
</script>

<template>
    <div class="d-flex justify-content-center px-3">
        <div class="search" id="searchDiv">
            <input
                id="searchBar"
                type="text"
                class="search-input"
                :placeholder="placeHolder"
                v-model="text"
                @input="onClick"
            />
            <a href="#" class="search-icon" id="iconHolder">
                <i class="fa fa-search fa-2x" id="blackIcon" @click="onClick"></i>
            </a>
        </div>
    </div>
</template>

<script>
export default {
    name: "SearchBar",
    data() {
        return {
            text: "",
        }
    },
    props: {
        placeHolder: String
    },
    methods: {
        onClick: function () {
            this.$emit("search-performed", this.text);
        },
    },
};
</script>
<style scoped>
#searchBar {
    width: 90%;
    padding: 10px;
    margin-right: 5px;
    border: 0.5px solid rgba(128, 128, 128, 0.473);
}

#searchDiv {
    width: 100%;
    margin-top: 20px;
}

#blackIcon {
    color: rgba(0, 0, 0, 0.747);
}

#iconHolder{
    height: 100%;
}
</style>

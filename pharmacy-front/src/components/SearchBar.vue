<template>
    <div class="search" id="searchDiv">
        <div class="searchBar" id="searchInputAndButton">
            <input
                id="searchBar"
                type="text"
                class="search-input"
                :placeholder="placeHolder"
                v-model="text"
                @input="onClick"
            />
            <!-- <a href="#" class="search-icon" id="iconHolder">
                <i
                    class="fa fa-search fa-2x"
                    id="blackIcon"
                    @click="onClick"
                ></i>
            </a> -->
        </div>

        <div id="sort-and-filter">
            <div id="sort">
                <p class="sort-label">sort by</p>

                <select class="sort-dropdown" name="optionsSelect" id="optionsSelect" @change="sortSelected">
                    <option
                        class="option"
                        v-for="option in options"
                        :key="option"
                        :value="option"
                        
                    >
                        {{ option }}
                    </option>
                </select>
            </div>

            <!-- ovde ce ici filter nekad -->
        </div>
    </div>
</template>

<script>
export default {
    name: "SearchBar",

    data() {
        return {
            text: "",
        };
    },
    props: {
        placeHolder: String,
        options: Array,
    },
    methods: {
        onClick: function () {
            this.$emit("search-performed", this.text);
        },

        sortSelected(event){
            //console.log(event.target.value);
            let choice = event.target.value;
            this.$emit("sort-performed", choice);

        }
    },
};
</script>
<style scoped>
#searchBar {
    padding: 10px;
    width: 100%;
    border: 0.5px solid rgba(128, 128, 128, 0.473);
}

#searchDiv {
    margin: 50px;
}


.sort-dropdown {
    padding: 10px;
    margin: 10px 0 10px 0;
    border: 0.5px solid rgba(128, 128, 128, 0.473);
    display: inline-block;

    /* float: right; */

}

#sort{
    width:100%;

}

.sort-label{
    padding: 10px;
    margin: 10px 0 10px 0;
    display: inline-block;
    /* float: right; */

}

#sort-and-filter{
    float:right;
    margin: 5px 0 30px 0;
}


</style>

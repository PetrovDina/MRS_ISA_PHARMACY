<template>
    <div class="search" id="searchDiv">

        <!-- search bar i search button -->
        <div id="searchInputAndButton">
            <input
                id="searchBar"
                type="text"
                class="search-input inlinepls"
                :placeholder="placeHolder"
                v-model="text"
            />
            <a href="#" class="search-icon inlinepls" id="iconHolder" @click="onClick">
                <i class="fa fa-search fa-2x" id="blackIcon"></i>
                
            </a>
            
        </div>

        <!-- filter bar  -->
        <div id="filterDiv">
            <PharmaciesFilterBar ref="filterBar" v-if="isSearchOf('pharmacies')"></PharmaciesFilterBar>
            <!-- ovde ce ici filter bar za lekove -->
        </div>

        <!-- sort bar -->
        <div id="sort">
            <p class="sort-label">sort by</p>
            <select class="sort-dropdown" @change="sortSelected">
                <option v-for="option in options" :key="option" :value="option">{{option}}</option>
            </select>
        </div>



    </div>
</template>

<script>
import PharmaciesFilterBar from "../components/PharmaciesFilterBar";

export default {
    name: "SearchBar",

    components:{
        PharmaciesFilterBar,

    },

    data() {
        return {
            text: "",
        };
    },
    props: {
        placeHolder: String,
        options: Array,
        searchType: String,
    },
    methods: {
        onClick: function () {
            this.$emit("search-performed", this.text, this.$refs.filterBar.$data.city, this.$refs.filterBar.$data.minRating, this.$refs.filterBar.$data.maxRating);
        },

        sortSelected(event){
            //console.log(event.target.value);
            let choice = event.target.value;
            this.$emit("sort-performed", choice);

        },

        isSearchOf(criterium){
            return this.searchType === criterium;
        }
    },
};
</script>
<style scoped>


#searchDiv {
    margin: 40px 40px 10px 40px;
    text-align: left;

}

#searchBar {
    padding: 10px;
    width: 97%;
    border: 0.5px solid rgba(128, 128, 128, 0.473);
    text-align: left;
}


.sort-dropdown {
    padding: 10px;
    border: 0.5px solid rgba(128, 128, 128, 0.473);
    display: inline-block;

}

#sort{
    margin-top: 40px;
    width:100%;
    text-align:right;

}

.sort-label{
    /* margin: 10px 0 10px 0; */
    display: inline-block;
    /* float: right; */

}

#sort-and-filter{
    /* float:right; */
    text-align: right;
    /* margin: 5px 0 30px 0; */
}

.inlinepls{
    display:inline;
}

#filterDiv{
    margin-top: 20px;
    text-align: left;
}

.rating-input{
    width: 30px;
}

input{
    border: 0.5px solid rgba(128, 128, 128, 0.473);

}

.filter-element{
    margin-right: 30px;
}

#blackIcon {
    color: rgba(0, 0, 0, 0.747);

}

#iconHolder{
    width: 10%;
    height:100%;
}


</style>

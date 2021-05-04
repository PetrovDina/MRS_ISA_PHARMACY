<template> 
    <div id="filterDiv">
        <span class=" filter-element">
            <p class="inlinepls">city</p>
            <input class="inlinepls" placeholder="ex. Novi Sad" v-model="city" />
        </span>

        <span class=" filter-element">
            <p class="inlinepls">min rating</p>
            <input class="rating-input inlinepls" v-model="minRating" type="number" min='0' max='5'/>
        </span>

        <span class=" filter-element">
            <p class="inlinepls">max rating</p>
            <input class="rating-input inlinepls" v-model="maxRating" type="number" min='0' max='5'/>
        </span>

        <a href="#" class="search-icon inlinepls" id="iconHolder" @click="clearFilters">
            <i class="fa fa-refresh" title="clear filters" id="blackIcon"></i>

        </a>
    </div>
        
</template>

<script>
export default {
    name: "PharmaciesFilterBar",

    data() {
        return {
            city : "",
            minRating : 0,
            maxRating : 5
        };
    },
    props: {
    },
    methods: {
        clearFilters(){
            this.city = "";
            this.minRating = 0;
            this.maxRating = 5;
        }
    },
};
</script>
<style scoped>

.inlinepls{
    display:inline;
}

#filterDiv{
    margin-top:20px;
    text-align: left;
}

.rating-input{
    width: 50px;
}

input{
    border: 0.5px solid rgba(128, 128, 128, 0.473);
    padding: 5px;

}

.filter-element{
    margin-right: 30px;
}


#blackIcon {
    color: rgba(0, 0, 0, 0.747);

}

</style>

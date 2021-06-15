<template> 
    <div id="filterDiv">
        <span class=" filter-element">
            <p class="inlinepls">Status</p>
            <input class="inlinepls" placeholder="ex. NEW" v-model="status" />
        </span>
        <button class="search-icon inlinepls" @click="clearFilters"><i class="fa fa-refresh" title="clear filters" id="blackIcon"></i></button>
    </div>
        
</template>

<script>
export default {
    name: "PharmaciesFilterBar",

    data() {
        return {
            status : "",
        };
    },
    props: {
    },
    methods: {
        clearFilters(){
            this.status = "";
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

input{
    border: 0.5px solid rgba(128, 128, 128, 0.473);
    border-radius: 5px;

    padding: 5px;

}

.filter-element{
    margin-right: 30px;
}

#blackIcon {
    color: rgba(0, 0, 0, 0.747);

}

</style>

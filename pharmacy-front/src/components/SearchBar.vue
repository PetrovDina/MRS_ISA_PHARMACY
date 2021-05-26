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
            <button class="search-icon inlinepls" @click="onClick"><i class="fa fa-search fa-2x" id="blackIcon"></i></button>
        </div>

        <!-- filter bar  -->
        <div id="filterDiv">
            <PharmaciesFilterBar ref="filterBar" v-if="isSearchOf('pharmacies')" ></PharmaciesFilterBar>
            <MedicationFilterBar ref="filterBar" v-if="isSearchOf('medications')" ></MedicationFilterBar>
            <OrdersFilterBar ref="filterBar" v-if="isSearchOf('orders')" ></OrdersFilterBar>
            <DermatologistsFilterBar ref="filterBar" v-if="isSearchOf('dermatologists')" ></DermatologistsFilterBar>
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
import MedicationFilterBar from "../components/MedicationFilterBar";
import OrdersFilterBar from "../components/OrdersFilterBar";
import DermatologistsFilterBar from "../components/DermatologistsFilterBar";

export default {
    name: "SearchBar",

    components:{
        PharmaciesFilterBar,
        MedicationFilterBar,
        OrdersFilterBar,
        DermatologistsFilterBar
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
            if(this.isSearchOf('pharmacies'))
                this.$emit("search-performed", this.text, this.$refs.filterBar.$data.city, this.$refs.filterBar.$data.minRating, this.$refs.filterBar.$data.maxRating);
            else if(this.isSearchOf('medications'))
            {
                this.$emit("search-performed", 
                        this.text, 
                        this.$refs.filterBar.$data.form, 
                        this.$refs.filterBar.$data.prescriptionReq);
            }
            else if(this.isSearchOf('orders'))
            {
                this.$emit("search-performed", this.text, this.$refs.filterBar.$data.status)
            }
            else if(this.isSearchOf('dermatologists'))
                this.$emit("search-performed", this.text, this.$refs.filterBar.$data.minRating, this.$refs.filterBar.$data.maxRating);
            
                
        },

        sortSelected(event){
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
    padding: 5px;
    border: 0.5px solid rgba(128, 128, 128, 0.473);
    border-radius: 5px;

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
    border-radius: 5px;


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

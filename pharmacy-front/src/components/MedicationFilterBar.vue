<template> 
    <div id="filterDiv">

        <span class=" filter-element">
            <p class="inlinepls">form</p>
            <select class="req-dropdown" @change="formSelected">
                <option v-for="option in formOptions" :key="option" :value="option">{{option}}</option>
            </select>
        </span>

        <span class=" filter-element">
            <p class="inlinepls">prescription required</p>
            <select class="req-dropdown" @change="reqSelected">
                <option v-for="option in prescriptionReqOptions" :key="option" :value="option">{{option}}</option>
            </select>
        </span>

        <!-- <a href="#" class="search-icon inlinepls" id="iconHolder" @click="clearFilters">
            <i class="fa fa-refresh" title="clear filters" id="blackIcon"></i>
        </a> -->
    </div>
        
</template>

<script>
export default {
    name: "PharmaciesFilterBar",

    data() {
        return {
            form : "",
            prescriptionReq : null,

            prescriptionReqOptions: [
                "-",
                "required",
                "not required",
            ],

            formOptions: [
                '-',
                'POWDER',
                'CAPSULE',
                'PILL',
                'OINTMENT',
                'PASTE',
                'GEL',
                'SOLUTION',
                'SYRUP'
            ]
        };
    },
    props: {
    },
    methods: {
        clearFilters(){
            this.form = "";
            this.prescriptionReq = null;
        },

        reqSelected() {
            let choice = event.target.value;
            if(choice != '-')
                this.prescriptionReq = (choice == 'required') ? true : false;
            else
                this.prescriptionReq = null;
        },

        formSelected() {
            let choice = event.target.value;
            this.form = (choice == '-') ? '' : choice;
        }
    },
};
</script>
<style scoped>

.req-dropdown {
    padding: 5px;
    border: 0.5px solid rgba(128, 128, 128, 0.473);
    border-radius: 5px;

    display: inline-block;

}

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

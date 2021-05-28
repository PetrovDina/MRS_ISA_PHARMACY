<template>
    <div id="prescriptions"> 
         <p style="font-size:20px; margin-top:50px" v-if="prescriptions.length == 0">You don't have any new ePrescriptions.</p>

        <div id="sort-and-filter" v-if="prescriptions.length != 0">
            <div id="sort">
                <p class="sort-label">sort by</p>

                <select
                    class="sort-dropdown"
                    name="optionsSelect"
                    id="optionsSelect"
                    @change="sortSelected"
                >
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
        </div>
        <div id="prescriptionsCards">
            <div :key="prescription.id" v-for="prescription in prescriptions">
                <div class="card mx-auto">
                    <div class="card-body">
                        <div class="container">
                            <div class="row">
                                <div class="col-sm">
                                    <div class="c1">
                                        <p class="card-text">
                                            Date issued: <b>{{convertDate(prescription.prescribedDate)}}</b>
                                        </p>
                                    </div>
                                    <div class="col-sm">
                                        <div class="c2"></div>
                                    </div>
                                    <prescription-items-table :items="prescription.prescriptionItems"></prescription-items-table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { client } from "@/client/axiosClient";
import moment from "moment";
import PrescriptionItemsTable from '../components/PrescriptionItemsTable.vue';

export default {
    name: "PatientsNewPrescriptionsView",

    components:{
        PrescriptionItemsTable
    },

    data() {
        return {
        
            prescriptions: [],
            options: ["-", "date older first", "date recent first"]
        };
    },

    methods: {
        convertDate(seconds) {
            return moment(seconds).format("MMMM Do yyyy.");
        },

        sortSelected(event){

            let sortCriterium = event.target.value;
            let self = this;
            if (sortCriterium === "-") 
                return;

            
           else if (sortCriterium === "date recent first") {
                this.prescriptions = this.prescriptions.sort(function (a, b) {
                    let dateA = new Date(a.prescribedDate);
                    let dateB = new Date(b.prescribedDate);
                    return dateB - dateA;
                });

            } else if (sortCriterium === "date older first") {
                this.prescriptions = this.prescriptions.sort(function (a, b) {
                    let dateA = new Date(a.prescribedDate);
                    let dateB = new Date(b.prescribedDate);
                    return dateA - dateB;
                });

            }
        },
    },

    mounted() {
        //get patient's prescriptions
        client({
            url: "therapy/newPrescriptionsByPatient",
            params: { patientUsername: localStorage.getItem("USERNAME") },
            method: "GET",
        }).then((response) => (this.prescriptions = response.data));
    },
};
</script>

<style scoped>
.prescriptions-title {
    font-size: 5vh;
    margin-top: 40px;
}
.card {
    width: 80%;
    margin-top: 40px;
    border: 1px solid rgba(63, 63, 63, 0.5);
}

.c1 {
    text-align: left;
}

.c2 {
    text-align: right;
}
.container {
    display: inline-block;
}
.card-title {
    font-weight: 700;
    font-size: 30px;
}

#prescriptionCards {
    margin-top: 80px;
}

/* sort */
.sort-dropdown {
    padding: 10px;
    border: 0.5px solid rgba(128, 128, 128, 0.473);
    display: inline-block;
}

#sort {

        float: right;

}

.sort-label {
    padding: 10px;
    display: inline-block;
}

#sort-and-filter {
    width: 90%;
}
</style>
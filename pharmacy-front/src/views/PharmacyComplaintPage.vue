<template>
    <div>
        <v-card class="ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div >
                    <h2>Write complaint for pharamcy</h2>
                </div>
                <v-spacer></v-spacer>
            </v-card-title>
        </v-card>

        <PharmaciesForComplaintTable
            :pharmacies="pharmacies"
            @pharmacySelected="selectPharmacy"
        >
        </PharmaciesForComplaintTable>

        <div v-if="pharmacies.length != 0">
            <form onsubmit="return false;">     	
                <h4 class="text-center " style="margin-bottom: 40px;"></h4>
                <div class="form-group" style="margin: 30px 100px 30px 100px;">
                    <label for="username" >Complaint:</label><br>
                    <textarea style="border:solid 1px black;" rows="10" name="complaintText" id="complaintText" class="form-control" v-model="complaint.text" required=""
                    oninvalid="this.setCustomValidity('Enter complaint.')"  oninput="setCustomValidity('')"> </textarea>
                </div>
                <div class="form-group">
                    <button style="background: rgba(15, 95, 72, 0.95)" class="btn btn-info btn-md" value="Registration" @click="sendComplaint(complaint)">Send complaint</button>
                </div>
            </form>
        </div>

    </div>
    
</template>

<script>

import PharmaciesForComplaintTable from "../components/PharmaciesForComplaintTable.vue";
import { client } from "@/client/axiosClient";

export default {

    name: 'PharmacyComplaintPage',

    components : {
        PharmaciesForComplaintTable
    },

    data() {
        return {
            pharmacies: [],

            complaint: {
                text: ""
            },

            selectedPharmacy: null,
            
        }
    },

    methods: {

        sendComplaint: function(complaint)
        {
            if(complaint.text === "") return;
            if(!this.selectedPharmacy)
            {
                alert("Please select pharmacy.");
                return;
            } 
            let complaintDTO =
            {
                patientUsername: localStorage.user,
                content: complaint.text,
                pharmacyId: this.selectedPharmacy.id
            };

            client({
					url: "complaintPharmacy/create",
					method: "POST",
                    data: complaintDTO
				}).then((response) => {
                    this.$toasted.show("Complaint successfully sent to pharmacy " + this.selectedPharmacy.name, {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                    this.$router
                        .push({ name: "Home" })
                        .catch((err) => {
                            if (err.name != "NavigationDuplicated") {
                                console.error(err);
                            }
                        });
        	});
        },

        selectPharmacy: function(pharmacy)
        {
            this.selectedPharmacy = pharmacy
        },

    },

    mounted() {
            client({
					url: "pharmacy/" + localStorage.user + "/complaints",
					method: "GET",
				}).then((response) => {
				this.pharmacies = response.data;
        	});
        }
};
</script>
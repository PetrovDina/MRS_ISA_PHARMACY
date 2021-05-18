<template>
    <div>
        <v-card class="ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div >
                    <h2>All my complaints for pharmacies</h2>
                </div>
                <v-spacer></v-spacer>
            </v-card-title>
        </v-card>

        <TabNav
            :tabs="['Not responsed', 'Responsed']"
            :selected="selected"
            @selected="setSelected"
        >
            <Tab :isSelected="selected === 'Not responsed'">

                <ComplaintPharmacyUserViewTable
                    :complaints="complaints">
                </ComplaintPharmacyUserViewTable>

            </Tab>

            <Tab :isSelected="selected === 'Responsed'">

                <ComplaintPharmacyUserViewTable
                    :complaints="responsedComplaints"
                    :showAdmin="true">
                </ComplaintPharmacyUserViewTable>

            </Tab>
        </TabNav>

    </div>
    
</template>

<script>

import ComplaintPharmacyUserViewTable from "../components/ComplaintPharmacyUserViewTable.vue";
import { client } from "@/client/axiosClient";
import TabNav from "../components/TabNav.vue";
import Tab from "../components/Tab.vue";

export default {

    name: 'ComplaintPharmacyUserPage',

    components : {
        ComplaintPharmacyUserViewTable, TabNav, Tab
    },

    data() {
        return {
            complaints: [],
            responsedComplaints: [],
            selected: "Not responsed"
        }
    },

    methods: {

        setSelected(tab) {
            this.selected = tab;
        },

    },

    mounted() {
            client({
					url: "complaintPharmacy/NotResponsed/" + localStorage.user,
					method: "GET",
				}).then((response) => {
				this.complaints = response.data;
        	});

            client({
					url: "complaintPharmacy/Responsed/" + localStorage.user,
					method: "GET",
				}).then((response) => {
				this.responsedComplaints = response.data;
        	});
        }
};
</script>
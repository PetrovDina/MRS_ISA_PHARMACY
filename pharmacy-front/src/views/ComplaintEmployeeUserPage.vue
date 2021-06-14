<template>
    <div>
        <v-card class="ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div >
                    <h2>All my complaints for employees</h2>
                </div>
                <v-spacer></v-spacer>
            </v-card-title>
        </v-card>

        <TabNav
            :tabs="['Not responded', 'Responded']"
            :selected="selected"
            @selected="setSelected"
        >
            <Tab :isSelected="selected === 'Not responded'">

                <ComplaintEmployeeUserViewTable
                    :complaints="complaints">
                </ComplaintEmployeeUserViewTable>

            </Tab>

            <Tab :isSelected="selected === 'Responded'">

                <ComplaintEmployeeUserViewTable
                    :complaints="responsedComplaints">
                </ComplaintEmployeeUserViewTable>

            </Tab>
        </TabNav>

    </div>
    
</template>

<script>

import ComplaintEmployeeUserViewTable from "../components/ComplaintEmployeeUserViewTable.vue";
import { client } from "@/client/axiosClient";
import TabNav from "../components/TabNav.vue";
import Tab from "../components/Tab.vue";

export default {

    name: 'ComplaintPharmacyUserPage',

    components : {
        ComplaintEmployeeUserViewTable, TabNav, Tab
    },

    data() {
        return {
            complaints: [],
            responsedComplaints: [],
            selected: "Not responded"
        }
    },

    methods: {

        setSelected(tab) {
            this.selected = tab;
        },

    },

    mounted() {
            client({
					url: "complaintEmployee/NotResponsed/" + localStorage.user,
					method: "GET",
				}).then((response) => {
				this.complaints = response.data;
        	});

            client({
					url: "complaintEmployee/Responsed/" + localStorage.user,
					method: "GET",
				}).then((response) => {
				this.responsedComplaints = response.data;
        	});
        }
};
</script>
<template>
    <div>
        <v-card class="ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div >
                    <h2>All complaints for employees</h2>
                </div>
                <v-spacer></v-spacer>
            </v-card-title>
        </v-card>

        <TabNav
            :tabs="['To response', 'Responsed']"
            :selected="selected"
            @selected="setSelected"
        >
            <Tab :isSelected="selected === 'To response'">

                <AdminEmployeeComplaintTable
                    :complaints="complaints"
                    @send-response-table="sendResponse"
                >
                </AdminEmployeeComplaintTable>

            </Tab>

            <Tab :isSelected="selected === 'Responsed'">

                <AdminEmployeeComplaintTable
                    :complaints="responsedComplaints"
                    :disableResponse="true"
                >
                </AdminEmployeeComplaintTable>

            </Tab>
        </TabNav>

    </div>
    
</template>

<script>

import AdminEmployeeComplaintTable from "../components/AdminEmployeeComplaintTable.vue";
import { client } from "@/client/axiosClient";
import TabNav from "../components/TabNav.vue";
import Tab from "../components/Tab.vue";

export default {

    name: 'AdminEmployeeComplaintPage',

    components : {
        AdminEmployeeComplaintTable, TabNav, Tab
    },

    data() {
        return {
            complaints: [],
            responsedComplaints: [],
            selected: "To response"
        }
    },

    methods: {

        setSelected(tab) {
            this.selected = tab;
        },

        sendResponse: function(complaint, response)
        {

            let complaintDTO = 
            {
                id: complaint.id,
                patientUsername: complaint.patientUsername,
                content: complaint.content,
                response: response,
                systemAdminUsername: localStorage.user,
                employeeUsername: complaint.employeeUsername
            }

            client
            ({

                url : "complaintEmployee/update",
                method : "PUT",
                data : complaintDTO

            }).then((response) => {

                this.$toasted.show("Response successfully sent.", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                this.removeFromTable(complaintDTO.id, complaintDTO.response);

            }).catch((response) => {
                this.$toasted.show("Error ocured. Please try again.", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 5000,
                    });

                client({
                        url: "complaintEmployee/all/forResponse",
                        method: "GET",
                    }).then((response) => {
                    this.complaints = response.data;
                });
            })
        },

        removeFromTable: function(complaintId, response)
        {
             for(let i = 0; i < this.complaints.length; i++)
                {
                    if(this.complaints[i].id == complaintId)
                    {
                        this.complaints[i].response = response;
                        this.responsedComplaints.push(this.complaints[i]);
                        this.complaints.splice(i, 1)
                        break;
                    }
                }
        }

    },

    mounted() {
            client({
					url: "complaintEmployee/all/forResponse",
					method: "GET",
				}).then((response) => {
				this.complaints = response.data;
        	});

            client({
					url: "complaintEmployee/all/" + localStorage.user,
					method: "GET",
				}).then((response) => {
				this.responsedComplaints = response.data;
        	});
        }
};
</script>
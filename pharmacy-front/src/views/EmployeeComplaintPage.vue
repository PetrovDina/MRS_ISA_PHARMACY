<template>
    <div>
        <v-card class="ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div >
                    <h2>Write complaint for employee</h2>
                </div>
                <v-spacer></v-spacer>
            </v-card-title>
        </v-card>

        <EmployeeTable
            :employees="employees"
            @employeeSelected="selectEmployee"
        >
        </EmployeeTable>

        <div v-if="employees.length != 0">
            <form onsubmit="return false;">     	
                <h4 class="text-center " style="margin-bottom: 40px;"></h4>
                <div class="form-group" style="margin: 30px 100px 30px 100px;">
                    <label for="username" >Complaint:</label><br>
                    <textarea style="border:solid 1px rgba(32, 102, 75, 0.295);" rows="10" name="complaintText" id="complaintText" class="form-control" v-model="complaint.text" required=""
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

import EmployeeTable from "../components/EmployeeTable.vue";
import { client } from "@/client/axiosClient";

export default {

    name: 'EmployeeComplaintPage',

    components : {
        EmployeeTable
    },

    data() {
        return {
            employees: [],

            complaint: {
                text: ""
            },

            selectedEmployee: null,
        }
    },

    methods: {

        sendComplaint: function(complaint)
        {
            if(complaint.text === "") return;
            if(!this.selectedEmployee)
            {
                this.$toasted.show("Please select employee", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                return;
            } 
            alert(this.selectedEmployee.username)
            let complaintDTO =
            {
                patientUsername: localStorage.user,
                content: complaint.text,
                employeeUsername: this.selectedEmployee.username
            };

            client({
					url: "complaintEmployee/create",
					method: "POST",
                    data: complaintDTO
				}).then((response) => {
                    let message = "Complaint successfully sent for " + this.selectedEmployee.firstName + " " 
                    + this.selectedEmployee.lastName;
                    this.$toasted.show(message, {
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

        selectEmployee: function(employee)
        {
            this.selectedEmployee = employee
        },

    },

    mounted() {
            client({
					url: "employee/" + localStorage.user + "/complaints",
					method: "GET",
				}).then((response) => {
				this.employees = response.data;
        	});
        }
};
</script>
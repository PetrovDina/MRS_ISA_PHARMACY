<template>
    <div id="complaintsViewDiv">
        <table class="table table-hover" >
            <thead>
                <tr>
                    <th scope="col">Username</th>
                    <th scope="col">Email</th>
                    <th scope="col">First Name</th>
                    <th scope="col">Last Name</th>
                    <th scope="col">Employee Username</th>
                    <th scope="col">Employee work as</th>
                    <th scope="col">See complaint</th>
                </tr>
            </thead>
            <tbody>
                <tr :key="complaint.id" v-for="complaint in complaints">
                    <!-- <td><button @click="deleteRecord(med.id, med.name)"><i class="fa fa-trash fa-lg"></i></button></td> -->
                    <td>{{complaint.patientUsername}}</td>
                    <td>{{complaint.patientEmail}}</td>
                    <td>{{complaint.patientFirstName}}</td>
                    <td>{{complaint.patientLastName}}</td>
                    <td>{{complaint.employeeUsername}}</td>
                    <td>{{complaint.employeeWorkAs}}</td>
                    <td><button @click="showComplaint(complaint)"><i class="fa fa-ellipsis-h fa-2x"></i></button></td>
                </tr>
            </tbody>
        </table>

        <AdminComplaintResponseModal
            @modal-closed = "mw_show_complaint = false"
            @send-response-modal = "sendResponse"
            :modal_show = "mw_show_complaint"
            :complaint = "complaintToShow"
            :disableResponse = "disableResponse">
        </AdminComplaintResponseModal>

    </div>
</template>

<script>

import AdminComplaintResponseModal from './AdminComplaintResponseModal.vue';

export default {
    name: "AdminEmployeeComplaintTable",
    components: { AdminComplaintResponseModal },
    props: {
        complaints: {
            type : Array,
            default() {
                return [];
            }
        },

        disableResponse: {
            type: Boolean,
            default() {
                return false;
            }
        }
    },
    data() {
        return {
            mw_show_complaint : false,
            complaintToShow: null
        };
    },
    methods: {

        showComplaint: function(complaint)
        {
            this.complaintToShow = complaint
            this.mw_show_complaint = true;
        },

        sendResponse: function(response)
        {
            this.complaintToShow
            this.$emit("send-response-table", this.complaintToShow, response);
        }

    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

#complaintsViewDiv{
    margin: 30px 10px 10px 10px;;
}
thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}
</style>
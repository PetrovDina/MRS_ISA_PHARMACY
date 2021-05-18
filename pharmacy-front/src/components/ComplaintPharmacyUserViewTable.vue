<template>
    <div id="complaints">
        <label v-if="complaints.length == 0"><span style="font-size: 20px;">There are no complaints for pharmacies.</span></label>
        <div v-if="complaints.length != 0">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Address</th>
                        <th scope="col">Rating</th>
                        <th v-if="showAdmin == true" scope="col">Admin who responded</th>
                        <th scope="col">Details</th>
                    </tr>
                </thead>

                <tbody>
                    <tr
                        :key="complaint.id"
                        v-for="complaint in complaints"
                    >
                        <td>{{ complaint.pharmacy.name }}</td>
                        <td>
                            {{ complaint.pharmacy.location.street }}  {{ complaint.pharmacy.location.streetNum }},
                            {{ complaint.pharmacy.location.zipcode }} {{ complaint.pharmacy.location.city }}
                        </td>
                        <td>{{ complaint.pharmacy.rating }}/5</td>
                        <td v-if="showAdmin == true">{{ complaint.systemAdminUsername }}</td>
                        <td><button @click="showComplaint(complaint)"><i class="fa fa-ellipsis-h fa-2x"></i></button></td>
                    </tr>
                </tbody>
            </table>

            <AdminComplaintResponseModal
                @modal-closed = "mw_show_complaint = false"
                :modal_show = "mw_show_complaint"
                :complaint = "complaintToShow"
                :disableResponse = "true">
            </AdminComplaintResponseModal>

        </div>
    </div>
</template>

<script>

import AdminComplaintResponseModal from './AdminComplaintResponseModal.vue';

export default {

    name: "ComplaintPharmacyUserViewTable",

    components: { AdminComplaintResponseModal },
    
    props: {
        complaints: {
            type: Array,
            required: false,
        },

        showAdmin: {
            type: Boolean,
            required: false,
            default(){
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

    },
};
</script>

<style scoped>
#complaints {
    margin: 30px 100px 30px 100px;
}

thead {
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295);
}

.selected {
    background-color: rgba(155, 82, 151, 0.527);
}
</style>

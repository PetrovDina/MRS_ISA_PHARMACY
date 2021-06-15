<template>
    <div id="allergiesDiv">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Form</th>
                    <th scope="col"></th>
                </tr>
            </thead>

            <tbody>
                <tr :key="alg.id" v-for="alg in allergies">
                    <td>{{ alg.name }}</td>
                    <td>{{ alg.form }}</td>
                    <td>
                        <i
                            class="fa fa-2x fa-trash"
                            @click="removeAllergy(alg)"
                        ></i>
                    </td>
                </tr>
            </tbody>
        </table>
        <div style="display: flex; justify-content: flex-start">
            <Button
                class="btn"
                text="Add allergy"
                bgd_color="rgba(155, 82, 151, 0.527)"
                data-toggle="modal"
                data-target=".bd-example-modal-lg"
            ></Button>
        </div>

        <!-- Modal -->
        <div
            class="modal fade bd-example-modal-lg"
            id="cancelModal"
            tabindex="-1"
            role="dialog"
            aria-labelledby="exampleModalCenterTitle"
            aria-hidden="true"
        >
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">
                            Add allergy
                        </h5>
                        <button
                            type="button"
                            class="close"
                            data-dismiss="modal"
                            aria-label="Close"
                        >
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th scope="col">Name</th>
                                    <th scope="col">Form</th>
                                    <th scope="col"></th>
                                </tr>
                            </thead>

                            <tbody>
                                <tr :key="med.id" v-for="med in medications">
                                    <td>{{ med.name }}</td>
                                    <td>{{ med.form }}</td>
                                    <td>
                                        <i
                                            class="fa fa-2x fa-plus-circle"
                                            @click="addAllergy(med)"
                                        ></i>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button
                            type="button"
                            class="btn btn-secondary"
                            data-dismiss="modal"
                        >
                            Close
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { client } from "@/client/axiosClient";
import Button from "./Button";

import $ from "jquery";

export default {
    name: "AllergiesAddEdit",

    components: { Button },

    data() {
        return {
            allergies: [],
            medications: [],
        };
    },

    methods: {
        removeAllergy(al) {
            for (let i = 0; i < this.allergies.length; i++) {
                if (this.allergies[i].id == al.id) {
                    this.medications.push(this.allergies[i]);
                    this.allergies.splice(i, 1);
                }
            }

            //saving to database
            client({
                url: "patient/removeAllergy",
                params: { patientUsername: localStorage.getItem("USERNAME"), allergyId: al.id },
                method: "GET",
            }).then();
        },

        addAllergy(al) {
            for (let i = 0; i < this.medications.length; i++) {
                if (this.medications[i].id == al.id) {
                    this.allergies.push(this.medications[i]);
                    this.medications.splice(i, 1);
                }
            }

            //saving to database
            client({
                url: "patient/addAllergy",
                params: { patientUsername: localStorage.getItem("USERNAME"), allergyId: al.id },
                method: "GET",
            }).then();
        },
    },

    mounted() {
        client({
            url: "patient/getPatientsAllergies",
            params: { patientUsername: localStorage.getItem("USERNAME") },
            method: "GET",
        }).then((response) => (this.allergies = response.data));

        client({
            url: "patient/allowedMedications",
            params: { patientUsername: localStorage.getItem("USERNAME") },
            method: "GET",
        }).then((response) => (this.medications = response.data));
    },
};
</script>

<style scoped>
thead {
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295);
}
</style>
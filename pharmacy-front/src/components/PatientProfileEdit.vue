<template>
    <div>
        <div class="p-3 py-5" v-if="user">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="text-right">Personal information</h4>
            </div>
            <div class="row mt-2 text-left">
                <div class="col-md-6">
                    <label class="labels">Name</label
                    ><input
                        type="text"
                        class="form-control"
                        placeholder="first name"
                        v-model="tempData.firstName"
                    />
                </div>
                <div class="col-md-6">
                    <label class="labels">Surname</label
                    ><input
                        type="text"
                        class="form-control"
                        value=""
                        placeholder="surname"
                        v-model="tempData.lastName"
                    />
                </div>
                <div class="col-md-12">
                    <label class="labels">Email</label
                    ><input
                        type="text"
                        class="form-control"
                        placeholder="email"
                        value=""
                        disabled="true"
                        v-model="tempData.email"
                    />
                </div>

            </div>
            <br>
            <div class="row mt-3 text-left">
                <div class="col-md-6">
                    <label class="labels">Street</label
                    ><input
                        type="text"
                        class="form-control"
                        placeholder="street"
                        value=""
                        v-model="tempData.location.street"
                    />
                </div>
                <div class="col-md-6">
                    <label class="labels">Street number</label
                    ><input
                        type="text"
                        class="form-control"
                        placeholder="street number"
                        value=""
                        v-model="tempData.location.streetNum"
                    />
                </div>
                <div class="col-md-6">
                    <label class="labels">Zip code</label
                    ><input
                        type="text"
                        class="form-control"
                        placeholder="zip code"
                        value=""
                        v-model="tempData.location.zipcode"
                    />
                </div>
                <div class="col-md-6">
                    <label class="labels">City</label
                    ><input
                        type="text"
                        class="form-control"
                        placeholder="city"
                        value=""
                        v-model="tempData.location.city"
                    />
                </div>
            </div>
            <div class="row mt-3"></div>
            <div class="mt-5 text-center">
                <br />
                <Button
                    class="btn"
                    text="Save changes"
                    bgd_color="rgba(155, 82, 151, 0.527)"
                    @action-performed="saveChanges()"
                ></Button>
            </div>
        </div>

        <!-- Modal for success -->
        <div
            class="modal fade"
            id="exampleModal"
            tabindex="-1"
            role="dialog"
            aria-labelledby="exampleModalLabel"
            aria-hidden="true"
        >
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <p style="text-align: justify">
                            Successfully saved changes.
                        </p>
                    </div>
                    <div class="modal-footer">
                        <button
                            type="button"
                            class="btn btn-secondary"
                            data-dismiss="modal"
                        >
                            Go back
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
    name: "PatientProfileEdit",

    components: { Button },

    data() {
        return {
            user: null,
            tempData: null,
        };
    },

    props : {

        adminSupplier : {
            type : Boolean,
            default() {
                return false;
            }
        },

    },

    methods: {
        saveChanges() {
            if (
                !this.tempData.firstName ||
                !this.tempData.lastName ||
                !this.tempData.username ||
                !this.tempData.location.street ||
                !this.tempData.location.streetNum ||
                !this.tempData.location.zipcode ||
                !this.tempData.location.city
            ) {
                let toast = this.$toasted.show("Please input all fields!", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
                return;
            }

            if(this.adminSupplier) {    // Veljko dodao
                client({
                    url: "/auth",
                    method: "PUT",
                    data: this.tempData,
                })
                    .then((response) => {
                        //modal window todo
                        $("#exampleModal").modal("show");

                    })
                    .catch((response) => alert(response));
            }
            else {
                client({
                    url: "/patient",
                    method: "PUT",
                    data: this.tempData,
                })
                    .then((response) => {
                        //modal window todo
                        $("#exampleModal").modal("show");

                    })
                    .catch((response) => alert(response));
            }
            
        },
    },

    mounted() {

        if(this.adminSupplier) {    // Veljko dodao
            client({
                url: "auth/" + localStorage.getItem("USERNAME"),
                method: "GET",
            }).then((response) => {
                this.user = response.data;
                this.tempData = JSON.parse(JSON.stringify(response.data));
            });
        }
        else {
            client({
                url: "patient/" + localStorage.getItem("USERNAME"),
                method: "GET",
            }).then((response) => {
                this.user = response.data;
                this.tempData = JSON.parse(JSON.stringify(response.data));
            });
        }
        
    },
};
</script>

<style scoped>
</style>
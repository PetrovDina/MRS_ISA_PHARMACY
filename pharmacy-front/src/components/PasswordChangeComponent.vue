<template>
    <div>
        <div class="p-3 py-5">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="text-right">Password</h4>
            </div>
            <div class="row mt-2 text-left">
                <div class="col-md-12">
                    <label class="labels">Current password</label
                    ><input
                        type="password"
                        class="form-control"
                        value=""
                        v-model="oldPassword"
                    />
                </div>

                <div class="col-md-6">
                    <label class="labels">New password</label
                    ><input
                        type="password"
                        class="form-control"
                        value=""
                        v-model="newPassword1"
                    />
                </div>
                <div class="col-md-6">
                    <label class="labels">Confirm new password</label
                    ><input
                        type="password"
                        class="form-control"
                        value=""
                        v-model="newPassword2"
                    />
                </div>
            </div>
            <br />

            <div class="row mt-3"></div>
            <div class="mt-5 text-center">
                <br />
                <Button
                    class="btn"
                    text="Save changes"
                    bgd_color="rgba(155, 82, 151, 0.527)"
                    @action-performed="changePassword()"
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
                            Successfully changed password.
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
    name: "PasswordChangeComponent",

    components: { Button },

    data() {
        return {
            user: null,
            oldPassword: "",
            newPassword1: "",
            newPassword2: "",
        };
    },

    methods: {
        changePassword() {
            console.log(
                this.oldPassword + this.newPassword1 + this.newPassword2
            );
            if (!this.oldPassword || !this.newPassword1 || !this.newPassword2) {
                let toast = this.$toasted.show("Please input all fields!", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
                return;
            }

            if (this.newPassword1 !== this.newPassword2) {
                let toast = this.$toasted.show("Passwords mismatch!", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
                return;
            }

            //change password
            client({
                url: "auth/tryChangePassword",
                params: {username: localStorage.getItem("USERNAME"), oldPassword: this.oldPassword, newPassword: this.newPassword1},
                method: "GET",
            }).then((response) => {
                let passwordChanaged = response.data;
                if (passwordChanaged){
                    $("#exampleModal").modal("show");
                }
                else{
                    let toast = this.$toasted.show("Wrong password!", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
                return;
                }

            });
        },
    },

    mounted() {
        // client({
        //     url: "patient/" + localStorage.getItem("USERNAME"),
        //     method: "GET",
        // }).then((response) => {
        //     this.user = response.data;
        //     this.tempData = JSON.parse(JSON.stringify(response.data));
        // });
    },
};
</script>

<style scoped>
</style>
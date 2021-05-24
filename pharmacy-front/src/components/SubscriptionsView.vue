<template>
    <div>
        <div id="appointmentCards">
            <p v-if="subscriptions.length == 0">No subscriptions!</p>
            <div :key="subscription.id" v-for="subscription in subscriptions">
                <div class="card mx-auto">
                    <div class="card-body">
                        <div class="container">
                            <div class="row">
                                <div class="col-sm">
                                    <div class="c1">
                                        <p class="card-text">
                                            Pharmacy:
                                            <b>
                                                {{ subscription.name }}
                                            </b>
                                        </p>

                                        <p class="card-text">
                                            Pharamacy rating:
                                            <star-rating
                                                active-color="rgba(155, 82, 151, 0.527)"
                                                :inline="true"
                                                :star-size="20"
                                                :read-only="true"
                                                :show-rating="false"
                                                :rating="
                                                    subscription.rating
                                                "
                                                :increment="0.1"
                                            ></star-rating>
                                        </p>
                                        <p class="card-text"></p>
                                    </div>
                                </div>
                                <div class="col-sm">
                                    <div class="c2">
                                        <p class="card-text">
                                            Address:
                                            <b
                                                >{{
                                                    subscription.location.street
                                                }}
                                                {{
                                                    subscription.location
                                                        .streetNum
                                                }}
                                            </b>
                                        </p>
                                        <p class="card-text">
                                            City:
                                            <b>
                                                {{
                                                    subscription.location
                                                        .zipcode
                                                }},
                                                {{
                                                    subscription.location.city
                                                }}</b
                                            >
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <Button
                            text="Unsubscribe"
                            class="book-button"
                            @action-performed="cancelSubscription(subscription)"
                        >
                        </Button>
                    </div>
                </div>
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
                            Successfully unsubscribed. You won't be receiving
                            e-mails about upcoming sales.
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
import StarRating from "vue-star-rating";

import $ from "jquery";

export default {
    name: "SubscriptionsView",

    components: { Button, StarRating },

    data() {
        return {
            subscriptions: [],
        };
    },

    methods: {
        cancelSubscription(subscription) {
            client({
                url: "patient/removeSubscription",
                params: {
                    patientUsername: localStorage.getItem("USERNAME"),
                    subscriptionId: subscription.id,
                },
                method: "GET",
            }).then((response) => {
                client({
                    url: "patient/getSubscriptions",
                    params: {
                        patientUsername: localStorage.getItem("USERNAME"),
                    },
                    method: "GET",
                }).then((response) => {
                    this.subscriptions = response.data; //refreshing data
                });
            });

            $("#exampleModal").modal("show");
        },
    },

    mounted() {
        client({
            url: "patient/getSubscriptions",
            params: { patientUsername: localStorage.getItem("USERNAME") },
            method: "GET",
        }).then((response) => {
            this.subscriptions = response.data;
        });
    },
};
</script>

<style scoped>
h3 {
    margin-bottom: 20px;
}

.card {
    width: 70%;
    margin-top: 40px;
    border: 1px solid rgba(63, 63, 63, 0.3);
}
.c1 {
    text-align: left;
}

.c2 {
    text-align: right;
}
</style>
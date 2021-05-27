<template>
    <div>
        <v-card class="ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div>
                    <h1>{{ pharmacy.name }}</h1>
                    <h1 id="mapPointer" @click="setUpMap()">
                        {{ pharmacy.location.street }}
                        {{ pharmacy.location.streetNum }},
                        {{ pharmacy.location.city }}
                    </h1>
                    <star-rating
                        data-toggle="tooltip"
                        data-placement="top"
                        :title="pharmacy.rating"
                        active-color="rgba(155, 82, 151, 0.527)"
                        :star-size="50"
                        :read-only="true"
                        :show-rating="false"
                        :rating="pharmacy.rating"
                        :increment="0.1"
                    ></star-rating>
                    <Button
                        v-if="isPatient() == true"
                        @action-performed="rateClicked()"
                        text="Rate pharmacy"
                        style="margin-top: 10px"
                    >
                    </Button>
                </div>

                <v-spacer> </v-spacer>
            </v-card-title>
        </v-card>
        <div v-if="isPatient() == true">
            <Button
                @action-performed="subscribedToggle"
                id="sub-btn"
                class="btn"
                :text="!subscribed ? 'Subscribe' : 'Unsubscribe'"
                :bgd_color="
                    !subscribed
                        ? 'rgba(15, 95, 72, 0.95)'
                        : 'rgba(155, 82, 151, 0.527)'
                "
            >
            </Button>
        </div>
        <MapContainer
            :coordinates="[
                pharmacy.location.longitude,
                pharmacy.location.latitude,
            ]"
        >
        </MapContainer>

        <!-- Modal Can Rate -->
        <div
            class="modal fade bd-example-modal-lg .modal-lg"
            id="rateModal"
            tabindex="-1"
            role="dialog"
            aria-labelledby="exampleModalCenterTitle"
            aria-hidden="true"
        >
            <div
                class="modal-dialog modal-dialog-centered .modal-lg"
                role="document"
            >
                <div class="modal-content justify-content-center">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">
                            How would you rate {{ pharmacy.name }}?
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
                    <div
                        class="modal-body w-100 text-center justify-content-center"
                    >
                        <star-rating
                            style="margin: 0 auto"
                            v-model="patientsRating"
                            active-color="rgba(155, 82, 151, 0.527)"
                        ></star-rating>
                    </div>
                    <div class="modal-footer">
                        <button
                            type="button"
                            class="btn btn-secondary"
                            data-dismiss="modal"
                            @click="saveRating()"
                        >
                            Save rating
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal Can't Rate -->
        <div
            class="modal fade bd-example-modal-lg .modal-lg"
            id="cantRateModal"
            tabindex="-1"
            role="dialog"
            aria-labelledby="exampleModalCenterTitle"
            aria-hidden="true"
        >
            <div
                class="modal-dialog modal-dialog-centered .modal-lg"
                role="document"
            >
                <div class="modal-content justify-content-center">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">
                            Rating unavailable
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
                    <div
                        class="modal-body w-100 justify-content-center"
                    >
                        <p style="text-align:justify">Unfortunately, you can't rate {{ pharmacy.name }}.<br> In
                        order to rate a pharmacy, you have to complete at least
                        one <b>reservation</b>, <b>appointment</b>, or <b>ePrescription</b> there.</p>
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
import Button from "../components/Button";
import { client } from "@/client/axiosClient";
import StarRating from "vue-star-rating";
import MapContainer from "../components/MapContainer";
import $ from "jquery";

export default {
    name: "PharmacyViewPatient",
    components: { Button, StarRating, MapContainer },

    props: {
        pharmacyIdProp: {
            type: Number,
            required: false,
            default() {
                return -1;
            },
        },
    },

    data() {
        return {
            pharmacyId: -1,
            subscribed: false,
            pharmacy: {
                location: {}, // morao da dodam zbog rendera
            },
            patientsRating: 0,
            canRate: true,
        };
    },

    methods: {
        subscribedToggle: function () {
            if (this.subscribed) {
                client({
                    url: "patient/removeSubscription",
                    params: {
                        patientUsername: localStorage.getItem("USERNAME"),
                        subscriptionId: this.pharmacyId,
                    },
                    method: "GET",
                }).then((response) => {
                    this.subscribed = false;
                });
            } else {
                client({
                    url: "patient/addSubscription",
                    params: {
                        patientUsername: localStorage.getItem("USERNAME"),
                        subscriptionId: this.pharmacyId,
                    },
                    method: "GET",
                }).then((response) => {
                    this.subscribed = true;
                });
            }
        },

        isPatient: function () {
            return localStorage.getItem("USER_TYPE") == "PATIENT";
        },

        rateClicked() {
            //getting patients rating if it already exists
            client({
                url: "pharmacy/getRating",
                params: {
                    patientUsername: localStorage.getItem("USERNAME"),
                    pharmacyId: this.pharmacyId,
                },
                method: "GET",
            }).then((response) => {
                this.patientsRating = response.data;
                    client({
                        url: "pharmacy/checkCanRate",
                        params: {
                            patientUsername: localStorage.getItem("USERNAME"),
                            pharmacyId: this.pharmacyId,
                        },
                        method: "GET",
                    }).then((response) => {
                        this.canRate = response.data;
                        if (this.canRate) {
                            $("#rateModal").modal("show");
                        } else {
                            $("#cantRateModal").modal("show");
                        }
                    });
                
            });

        },

        saveRating() {
            client({
                url: "pharmacy/ratePharmacy",
                params: {
                    patientUsername: localStorage.getItem("USERNAME"),
                    pharmacyId: this.pharmacyId,
                    ratedValue: this.patientsRating,
                },
                method: "GET",
            }).then((response) => {
                //refreshing
                client({
                    url: "pharmacy/" + this.pharmacyId,
                    method: "GET",
                }).then((response) => {
                    this.pharmacy = response.data;
                });
            });
        },
    },

    mounted() {
        // Ove silne zavrzlame su zbog refresh-a i zato sto kada se menja props izlazi error
        this.pharmacyId = this.pharmacyIdProp;

        if (this.pharmacyId == -1)
            this.pharmacyId = this.$store.getters.getLastPharmacyPatientView;
        else
            this.$store.commit(
                "changeLastPharmacyPatientView",
                this.pharmacyId
            );

        if (this.isPatient()) {
            // Samo ako je pacijent proveri jel pretplacen
            client({
                url: "patient/isSubscribed",
                params: {
                    patientUsername: localStorage.getItem("USERNAME"),
                    pharmacyId: this.pharmacyId,
                },
                method: "GET",
            }).then((response) => {
                this.subscribed = response.data;
            });

            //ovde dobavim kako je pacijent rejtovao apoteku ako je
        }
        client({
            url: "pharmacy/" + this.pharmacyId,
            method: "GET",
        }).then((response) => {
            this.pharmacy = response.data;
        });
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#pharmacy {
    align-self: flex-start;
}

#sub-btn {
    float: right;
    margin: 10px;
    color: white;
}

#mapPointer {
    cursor: pointer;
}
.vue-star-rating {
    align-items: center;
    justify-content: center;
}
</style>
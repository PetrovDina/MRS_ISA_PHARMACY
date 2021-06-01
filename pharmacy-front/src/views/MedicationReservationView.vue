<template>
    <div v-if="medication != null" id="reservationDiv">
        <p class="titl" v-if="results.length == 0">
            Unfortunately, {{ medication.name }} is out of stock!
            <br><br>
            <button
                    class="cancelButton btn btn-secondary"
                    @click="goBack()"
                >
                    Go back
            </button>
        </p>

        <div v-if="results.length != 0">
            <p class="titl">
                Pharmacies that carry {{ medication.name }}
            </p>
            <p class="titl2">Please select one</p>

            <PharmaciesWithPriceComponent
                :results="results"
                @pharmacySelected="chosenPharmacySelected"
            ></PharmaciesWithPriceComponent>
            <br />
            <div>
                <span>
                    <input
                        v-model="chosenQuantity"
                        ref="quantityInput"
                        type="number"
                        class="form-control"
                        id="quantityInput"
                        placeholder="Enter quantity"
                        min="0"
                        :max="[chosenRow ? chosenRow.availableQuantity : 100]"
                    />
                </span>

                <span>
                    <label for="dueDate">Pick up due date</label>
                    <input
                        type="date"
                        id="dueDate"
                        name="dueDate"
                        :min="today"
                        class="form-control"
                        v-model="chosenDueDate"
                    />
                </span>
                <br /><br /><br />
                <button
                    class="cancelButton btn btn-secondary"
                    @click="goBack()"
                >
                    Cancel
                </button>
                <button
                    class="finishButton btn btn-primary"
                    @click="finishReservation()"
                >
                    Complete reservation
                </button>
            </div>

            <!-- Modal for filling all fields -->
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
                        <div class="modal-body">Please fill all fields!</div>
                        <div class="modal-footer">
                            <button
                                type="button"
                                class="btn btn-secondary"
                                data-dismiss="modal"
                            >
                                Ok
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal for valid quantity -->
            <div
                class="modal fade"
                id="exampleModal2"
                tabindex="-1"
                role="dialog"
                aria-labelledby="exampleModalLabel"
                aria-hidden="true"
            >
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-body">
                            Please input valid quantity!
                        </div>
                        <div class="modal-footer">
                            <button
                                type="button"
                                class="btn btn-secondary"
                                data-dismiss="modal"
                            >
                                Ok
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal for success -->
            <div
                class="modal fade"
                id="exampleModal3"
                tabindex="-1"
                role="dialog"
                aria-labelledby="exampleModalLabel"
                aria-hidden="true"
            >
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">Successfully reserved!</div>
                        <div class="modal-body">
                            <p style="text-align: justify">
                                You can see all your reserved medications on
                                your profile.
                            </p>
                        </div>
                        <div class="modal-footer">
                            <button
                                type="button"
                                class="btn btn-secondary"
                                data-dismiss="modal"
                                @click="goBack()"
                            >
                                Go back
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import PharmaciesWithPriceComponent from "../components/PharmaciesWithPriceComponent.vue";
import moment from "moment";
import { client } from "@/client/axiosClient";
import $ from "jquery";

export default {
    name: "MedicationReservationView",

    components: { PharmaciesWithPriceComponent },

    props: {
        medication: {
            type: Object,
            required: false,
            default: null,
        },
    },

    data() {
        return {
            mode: "selection",
            pharmaciesWithMedication: {},
            results: [],

            chosenRow: null,
            chosenQuantity: null,
            chosenDueDate: null,

            today: moment().format("YYYY-MM-DD"),
        };
    },

    methods: {
        chosenPharmacySelected(row) {
            this.$refs.quantityInput.disabled = row.availableQuantity <= 0;
            this.chosenQuantity = null;
            this.chosenRow = row;
        },

        finishReservation() {
            if (
                !this.chosenRow ||
                !this.chosenQuantity ||
                !this.chosenDueDate
            ) {
                $("#exampleModal").modal("show");
                return;
            }

            if (
                this.chosenQuantity == 0 ||
                this.chosenQuantity > this.chosenRow.availableQuantity
            ) {
                $("#exampleModal2").modal("show");
                return;
            }

            let p = {
                id: 2, //ovo je samo bitno, ostalo nije jer ce na osnovu id-a na beku da se vadi iz baze
                username: "pera",
                password: "pera",
                email: "pera@gmail.com",
                firstName: "pera",
                lastName: "peric",
                penaltyPoints: 0,
            };

            client({
                url: "reservation/create",
                method: "POST",
                data: {
                    patient: p,
                    pharmacy: this.chosenRow.pharmacy,
                    medication: this.medication,
                    quantity: this.chosenQuantity,
                    dueDate: this.chosenDueDate,
                    medicationPrice: this.chosenRow.currentPrice,
                },
            }).then();

            $("#exampleModal3").modal("show");

            //this.$router.push({ name: "Home" });
        },

        goBack() {
            this.$router.push({ name: "Home" });
        },
    },

    mounted() {
        //check if medication passed as prop
        //we need this in case someone presses the back or forward button
        if (!this.medication) {
            this.$router.push({ name: "Home" });
            return;
        }

        //check if user is barred
        client({
            url: "patient/" + localStorage.getItem("USERNAME"),
            method: "GET",
        }).then((response) => {
            if (response.data.penaltyPoints >= 3) {
                this.$router.push({ name: "PenaledScreen" });
                return;
            }
        });

        client({
            url: "pharmacyStorageItem/allByMedicationAndQuantity",
            params: { medicationId: this.medication.id },
            method: "GET",
        }).then((response) => (this.results = response.data));
    },
};
</script>

<style scoped>
input {
    width: 25%;
    display: inline;
}

span {
    margin: 10px;
    padding: 20px;
}

.form-control:focus {
    border-color: rgba(155, 82, 151, 0.527);
    box-shadow: 0 0 0 0.1rem rgba(155, 82, 151, 0.527);
}

.finishButton {
    background-color: rgba(15, 95, 72, 0.95);
    border-color: rgba(15, 95, 72, 0.95);
}

#reservationDiv {
    margin: 50px 60px 30px 60px;
}

.titl2 {
    font-size: 20px;
}

.titl {
    font-size: 24px;
}
</style>

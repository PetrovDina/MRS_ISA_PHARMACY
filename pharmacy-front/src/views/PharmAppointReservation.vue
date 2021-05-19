<template>
    <div id="reservationDiv">
        <p class="appointments-title">Book pharmacist appointment</p>

        <div class="card card-body">
            <div class="container">
                <div class="row">
                    <div class="col-sm">
                        <div class="c1">
                            <div class="md-form mx-5 my-5">
                                <label for="date">Choose date</label>
                                <input
                                    type="date"
                                    id="date"
                                    name="date"
                                    :min="today"
                                    class="form-control"
                                    v-model="chosenDate"
                                />
                            </div>
                        </div>
                    </div>

                    <div class="col-sm">
                        <div class="c2">
                            <div class="md-form mx-5 my-5">
                                <label class="label" for="start_time"
                                    >Choose time</label
                                >
                                <input
                                    type="time"
                                    id="start_time"
                                    class="form-control"
                                    v-model="chosenTime"
                                />
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <Button
                text="Find appointment"
                bgd_color="rgba(15, 95, 72, 0.95)"
                style="
                    color: white;
                    width: 200px;
                    text-align: center;
                    margin: auto;
                "
                @action-performed="findClicked()"
            >
            </Button>
        </div>

        <div id="pharmaciesDiv" v-if="resultsAvailable == true" class="card">
            <p class="text">Pharmacies with available appointments</p>
            <!-- sort bar -->
            <div id="sort">
                <p class="sort-label">sort by</p>
                <select class="sort-dropdown" @change="pharmacySortSelected"> 
                    <option
                        v-for="option in optionsPharmacies"
                        :key="option"
                        :value="option"
                    >
                        {{ option }}
                    </option>
                </select>
            </div>
            <PharmaciesConsultationTable
                :pharmacies="pharmacies"
                @pharmacySelected="pharmacySelected"
            ></PharmaciesConsultationTable>
        </div>

        <div id="pharmacistsDiv" v-if="chosenPharmacy.id" class="card">
            <p class="text">Available pharmacists</p>
            <!-- sort bar -->
            <div id="sort">
                <p class="sort-label">sort by</p>
                <select class="sort-dropdown" @change="pharmacistSortSelected">
                    <option
                        v-for="option in optionsPharmacists"
                        :key="option"
                        :value="option"
                    >
                        {{ option }}
                    </option>
                </select>
            </div>
            <PharmacistsSelectionTable
                :pharmacists="pharmacists"
                @pharmacistSelected="pharmacistSelected"
            ></PharmacistsSelectionTable>
        </div>

        <Button
            v-if="chosenPharmacist.id"
            text="Book now"
            bgd_color="rgba(15, 95, 72, 0.95)"
            style="color: white; width: 200px; text-align: center; margin: auto"
            @action-performed="bookClicked()"
            class="book-button"
        >
        </Button>

        <!-- Modal -->
        <div
            class="modal fade"
            id="bookModal"
            tabindex="-1"
            role="dialog"
            aria-labelledby="exampleModalCenterTitle"
            aria-hidden="true"
        >
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">
                            Successfully booked!
                        </h5>
                    </div>
                    <div class="modal-body">
                        You can see all your scheduled appointments on your page
                    </div>
                    <div class="modal-footer">
                        <button
                            type="button"
                            class="btn btn-secondary"
                            data-dismiss="modal"
                            @click="goHome()"
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
import moment from "moment";
import Button from "@/components/Button";
import PharmaciesConsultationTable from "@/components/PharmaciesConsultationTable";
import PharmacistsSelectionTable from "@/components/PharmacistsSelectionTable";

import $ from "jquery";

export default {
    name: "PharmAppointReservation",

    components: {
        Button,
        PharmaciesConsultationTable,
        PharmacistsSelectionTable,
    },

    data() {
        return {
            chosenDate: null,
            chosenTime: null,
            today: moment().format("YYYY-MM-DD"),
            optionsPharmacies: ["-", "price low first", "price high first", "rating"],
            optionsPharmacists: ["-", "rating"],

            employments: [],
            pharmacies: [],
            pharmacists: [],
            resultsAvailable: false,
            chosenPharmacy: {},
            chosenPharmacist: {},

            pharmacySortResults: [],
            pharmacistSortResults: []
        };
    },

    methods: {
        findClicked() {
            //resetting all before search
            this.resultsAvailable = false;
            this.chosenPharmacy = {};
            this.chosenPharmacist = {};
            this.pharmacies = [];
            this.pharmacists = [];

            let date = this.chosenDate;
            let start_time_value = this.chosenTime;
            // let start_time_value = document.getElementById("start_time").value;

            if (!date || !start_time_value) {
                let toast = this.$toasted.show("Please input all fields!", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
                return;
            }

            //checking which pharmacies have pharmacists available at chosen time and date

            //TODO DODAJ PROVERU DA L' POSTOJE APPOINTMENTI TADA!!!!!
            client({
                url: "employments/pharmacistEmploymentsByTime",
                method: "GET",
                params: {
                    startDate: this.chosenDate,
                    startTime: start_time_value,
                },
            }).then((response) => {
                if (response.data.length == 0) {
                    this.resultsAvailable = false;
                    let toast = this.$toasted.show(
                        "No available pharmacists at that time!",
                        {
                            theme: "toasted-primary",
                            position: "top-center",
                            duration: 2000,
                        }
                    );
                    return;
                }
                this.employments = response.data;
                this.resultsAvailable = true;
                for (let empl of this.employments) {
                    if (!this.pharmacyAlreadyInList(empl.pharmacy.id)){
                        this.pharmacies.push(empl.pharmacy);
                    }
                    
                }
            });
        },
        pharmacyAlreadyInList(pharmacyId){
            for (let pharm of this.pharmacies){
                if (pharm.id == pharmacyId){
                    return true;
                }
            }
            return false;
        },

        pharmacySelected(ph) {
            this.chosenPharmacy = ph;
            this.pharmacists = [];
            for (let empl of this.employments) {
                if (empl.pharmacy === this.chosenPharmacy) {
                    this.pharmacists.push(empl.employee);
                }
            }
        },

        pharmacistSelected(ph) {
            this.chosenPharmacist = ph;
        },

        bookClicked() {
            //ovde sad saljes novi appointment na back!
            client({
                url: "appointments/savePharmacistAppointment",
                method: "POST",
                data: {
                    employee: {
                        id: this.chosenPharmacist.id,
                    },
                    timePeriod: {
                        startDate: this.chosenDate,
                        startTime: this.chosenTime,
                        endDate: this.chosenDate,
                        endTime: this.chosenTime,
                    },

                    pharmacy: {
                        id: this.chosenPharmacy.id,
                    },

                    patient: {
                        username: localStorage.getItem("USERNAME"),
                    },
                },
            })
                .then((response) => {
                    $("#bookModal").modal("show");
                })
                .catch((response) => alert("Sorry... "));
        },

        goHome() {
            this.$router.push({ name: "Home" });
        },

        pharmacySortSelected(event){
            let sortCriterium = event.target.value;
            let self = this;
            if (sortCriterium === "-") {
                this.pharmacySortResults = this.pharmacies
                return;

            }


            else if (sortCriterium === "price high first") {
                this.pharmacySortResults = this.pharmacies.sort(function (
                    a,
                    b
                ) {
                    return b.price - a.price; //TODO zameni pravim cenama
                });
            }

            else if (sortCriterium === "price low first") {
                this.pharmacySortResults = this.pharmacies.sort(function (
                    a,
                    b
                ) {
                    return a.price - b.price; //TODO zameni pravim cenama
                });
            }

            else if (sortCriterium === "rating") {
                this.pharmacySortResults = this.pharmacies.sort(function (
                    a,
                    b
                ) {
                    return b.rating - a.rating; 
                });
            }
        },

        pharmacistSortSelected(event){
            let sortCriterium = event.target.value;
            let self = this;
            if (sortCriterium === "-") {
                this.pharmacistSortResults = this.pharmacists
                return;

            }

            else if (sortCriterium === "rating") {
                this.pharmacistSortResults = this.pharmacists.sort(function (
                    a,
                    b
                ) {
                    return b.rating - a.rating; 
                });
            }
        }
    },

    mounted() {
        //check if user is barred
        client({
            url: "patient/" + localStorage.getItem("USERNAME"),
            method: "GET",
        }).then((response) => {
            console.log(response.data.penaltyPoints >= 3);
            if (response.data.penaltyPoints >= 3) {
                this.$router.push({ name: "PenaledScreen" });
                return;
            }
        });
    },
};
</script>


<style scoped>
#reservationDiv {
    margin: 0 50px 0 50px;
}
.appointments-title {
    font-size: 5vh;
    margin-top: 40px;
}

.c1 {
    text-align: left;
}

.c2 {
    text-align: right;
}

.form-control:focus,
Button:focus {
    border-color: rgba(155, 82, 151, 0.527);
    box-shadow: 0 0 0 0.1rem rgba(155, 82, 151, 0.527);
}

#pharmaciesDiv,
#pharmacistsDiv {
    margin: 70px 70px 50px 70px;
    padding: 20px;
}

.text {
    font-size: 30px;
}



.sort-dropdown {
    padding: 5px;
    border: 0.5px solid rgba(128, 128, 128, 0.473);
    border-radius: 5px;

    display: inline-block;

}

#sort{
    width:100%;
    text-align:right;

}

.sort-label{
    /* margin: 10px 0 10px 0; */
    display: inline-block;
    /* float: right; */

}

.book-button{
    margin: 50px;
}
</style>


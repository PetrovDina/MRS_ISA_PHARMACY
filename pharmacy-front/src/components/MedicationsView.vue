<template>
    <div id="medicationsViewDiv">
        <!-- <table class="table table-hover">
            <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Manufacturer</th>
                    <th scope="col">Form</th>
                    <th scope="col">Rating</th>
                    <th scope="col">Prescription</th>

                    <th scope="col"></th>
                </tr>
            </thead>

            <tbody>
                <tr
                    :key="med.id"
                    v-for="med in medications"
                    @dblclick="dblClickedOnRow(med.id)"
                >
                    <td>{{ med.name }}</td>
                    <td>{{ med.manufacturer }}</td>

                    <td>{{ med.form }}</td>
                    <td>
                        <star-rating
                            active-color="rgba(155, 82, 151, 0.527)"
                            :inline="true"
                            :star-size="20"
                            :read-only="true"
                            :show-rating="false"
                            :rating="med.rating"
                            :increment="0.1"
                        ></star-rating>
                    </td>

                    <td>
                        {{ med.prescriptionReq ? "required" : "not required" }}
                    </td>
                    <td>

                        <i
                            v-if="checkRole() && !med.prescriptionReq"
                            @click="reserveMedication(med)"
                            class="fa fa-shopping-cart"
                            aria-hidden="true"
                        ></i>
                    </td>
                </tr>
            </tbody>
            
        </table> -->
        <!-- <ModalWindowMedicationDetail
            @modal-closed="closeModalWindow"
            :modal_show="modalWindowShowed"
            :medicationId="selectedMedicationId"
        >
        </ModalWindowMedicationDetail> -->

        <!-- CARDS -->
        <div class="row">
            <div class="col-sm-3" :key="med.id"
                    v-for="med in medications">
                <div class="card" >
                    <div id="pictureDiv">
                    <img class="card-img-top" src="@/assets/medicine2.png" alt="Card image cap">
                    </div>
                    <div class="card-body">
                        <h4 class="card-title">{{med.name}}</h4>
                        <star-rating

                            active-color="rgba(155, 82, 151, 0.527)"
                            :star-size="28"
                            :read-only="true"
                            :show-rating="false"
                            :rating="med.rating"
                            :increment="0.1"
                            style="margin-bottom: 20px;"
                        ></star-rating>

                        <p class="card-text">
                            Manufacturer: <b>{{med.manufacturer}}</b>
                        </p>
                        <p class="card-text">
                            Form: <b>{{med.form}}</b>
                        </p>
                        <p class="card-text">
                            Prescription: <b>{{ med.prescriptionReq ? "required" : "not required" }}</b>
                        </p>

                        <div style="display:flex; justify-content: center;
">
                        <Button  @action-performed="moreDetails(med.id)" bgd_color="rgba(87, 87, 87, 0.247)" text="Details"></Button>
                        <Button v-if="checkRole() && !med.prescriptionReq" bgd_color="rgba(32, 102, 75, 0.295)"
                            @action-performed="reserveMedication(med)" text="Buy"></Button>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import ModalWindowMedicationDetail from "../components/ModalWindowMedicationDetail";
import StarRating from "vue-star-rating";
import Button from "@/components/Button";

export default {
    name: "MedicationsView",

    components: { ModalWindowMedicationDetail, StarRating, Button },

    props: ["medications"],

    data() {
        return {
            selectedMedicationId: -1,
            modalWindowShowed: false,
        };
    },

    mounted() {},

    methods: {
        reserveMedication(med) {
            this.$router.push({
                name: "MedicationReservationView",
                params: {
                    medication: med,
                },
            });
        },

        moreDetails: function (id) {
            this.selectedMedicationId = id;
            //this.modalWindowShowed = true;
            this.$router.push({
                name: "MedicationDetailsView",
                params: {
                    medicationId: this.selectedMedicationId,
                },
            });
        },

        closeModalWindow: function () {
            this.modalWindowShowed = false;
        },

        checkRole() {
            return this.$store.getters.getLoggedUserRole == "PATIENT";
        },
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#medicationsViewDiv {
    margin: 10px 40px 30px 40px;
}

thead {
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(87, 87, 87, 0.247);
}

.reserveButton {
    background-color: rgba(155, 82, 151, 0.527);
    padding: 5px;
    border-radius: 5px;
}

table {
    cursor: pointer;
}

i {
    font-size: 30px;
}

/* cards */
.card-img-top{
    max-width: 250px; 
    max-height: 250px;

}

#pictureDiv {
    align-content: center;
}

.card{
    margin-top:20px;
    text-align:center;
}

Button{
    margin:10px;
}
</style>


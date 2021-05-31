<template>
    <div v-if="user && loyaltyProgram">
        <div class="p-3">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h2>Your account category: {{ user.category }}</h2>
                <h2>Your loyalty points: {{ user.loyaltyPoints }}</h2>
                <br />
            </div>
            <div>

                <table class="table table-hover"  style="margin: 30px 0 30px 0;">
                    <thead>
                        <tr>
                            <th scope="col">Category</th>
                            <th scope="col">Minimum points</th>
                            <th scope="col">Discount</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><b>Regular</b></td>
                            <td>0</td>
                            <td>0%</td>
                        </tr>
                        <tr>
                            <td><b>Silver</b></td>
                            <td>{{ loyaltyProgram.maxPointsRegular + 1 }}</td>
                            <td>{{ loyaltyProgram.silverDis }}%</td>
                        </tr>
                        <tr>
                            <td><b>Gold</b></td>
                            <td>{{ loyaltyProgram.maxPointsSilver + 1 }}</td>
                            <td>{{ loyaltyProgram.goldDis }}%</td>
                        </tr>
                    </tbody>
                </table>

                <p style="text-align: justify">
                    Users earn points through our services and advance their
                    account by:
                </p>
                <ul style="text-align: left">
                    <li>Reserving medications</li>
                    <li>Scheduling dermatologist examinations</li>
                    <li>Scheduling pharmacist consultations</li>
                </ul>
            </div>
        </div>
    </div>
</template>


<script>
import { client } from "@/client/axiosClient";
import Button from "./Button";

import $ from "jquery";

export default {
    name: "LoyaltySystemComponent",

    components: { Button },

    data() {
        return {
            user: null,
            loyaltyProgram: null,
        };
    },

    methods: {},

    mounted() {
        //geting patient
        client({
            url: "patient/" + localStorage.getItem("USERNAME"),
            method: "GET",
        }).then((response) => {
            this.user = response.data;
        });

        //getting loyalty program rules
        client({
            url: "loyalty/getLoyalty",
            method: "GET",
        }).then((response) => {
            this.loyaltyProgram = response.data;
        });
    },
};
</script>

<style scoped>
h2 {
    margin-bottom: 20px;
    color: rgba(129, 68, 126, 0.856);
    font-weight: 700;
}

thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}
</style>
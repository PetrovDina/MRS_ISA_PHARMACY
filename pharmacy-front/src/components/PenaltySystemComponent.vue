<template>
    <div v-if="user">
        <div class="p-3 ">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h2>Your pentalty points: {{user.penaltyPoints}}</h2>
                <br>
            </div>
            <div>
                <p style="text-align:justify">Users are automatically given <b>one (1) penalty point</b> for:</p>
                <ul style="text-align:left">
                    <li>Neither cancelling a reservation nor picking it up by its due date</li>
                    <li>Not showing up for a scheduled dermatologist examination</li>
                    <li>Not showing up for a scheduled pharmacist consultation</li>

                </ul>

                <p style="text-align:justify">Users with <b> three (3) </b> penalty points are barred from further reserving medications, getting EPrescriptions, and scheduling dermatologist and pharmacist appointments. </p>
                <p style="text-align:justify">Penalty points are automatically reset on the beginning of every month.</p>

            </div>
            </div>
    </div>
</template>


<script>
import { client } from "@/client/axiosClient";
import Button from "./Button";

import $ from "jquery";

export default {
    name: "PenaltySystemComponent",

    components: { Button },

    data() {
        return {
            user: null,

        };
    },

    methods: {
       
        
    },

    mounted() {
        client({
            url: "patient/" + localStorage.getItem("USERNAME"),
            method: "GET",
        }).then((response) => {
            this.user = response.data;
        });
    },
};
</script>

<style scoped>

h2{
    margin-bottom: 20px;
    color: rgba(129, 68, 126, 0.856);
    font-weight: 700;

}
</style>
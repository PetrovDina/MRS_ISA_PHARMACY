<template>
    <div id="pahrmas">
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">First name</th>
                    <th scope="col">Last name</th>
                    <th scope="col">Gender</th>
                    <th scope="col">Rating</th>
                </tr>
            </thead>

            <tbody>
                <tr
                    :key="phar.id"
                    v-for="phar in pharmacists"
                    @click="clickedOnRow(phar)"
                    v-bind:class="{
                        selected: selected_pharmacist.id === phar.id,
                    }"
                >
                    <td>{{ phar.id }}</td>
                    <td>{{ phar.firstName }}</td>
                    <td>{{ phar.lastName }}</td>
                    <td>
                        <i
                            v-bind:class="
                                phar.gender != 'MALE'
                                    ? 'fa fa-venus'
                                    : 'fa fa-mars'
                            "
                        ></i>
                    </td>
                    <td>
                        <star-rating
                            active-color="rgba(155, 82, 151, 0.527)"
                            :inline="true"
                            :star-size="20"
                            :read-only="true"
                            :show-rating="false"
                            :rating="phar.rating"
                            :increment="0.1"
                        ></star-rating>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import Button from "./Button.vue";
import StarRating from "vue-star-rating";

export default {
    name: "PharmacistsSelectionTable",
    components: { Button, StarRating },
    props: {
        pharmacists: {
            type: Array,
            default() {
                return [];
            },
        },
        pharmacyId: {
            type: Number,
            default() {
                return -1;
            },
        },
    },
    data() {
        return {
            selected_pharmacist: {},
        };
    },
    methods: {
        clickedOnRow: function (phar) {
            this.selected_pharmacist = phar;
            this.$emit("pharmacistSelected", this.selected_pharmacist);
        },
    },
};
</script>

<style scoped>

thead {
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295);
}

tr.selected {
    background-color: rgba(155, 82, 151, 0.527);
}
</style>

<template>
    <div id="pharmacies">
        <label v-if="pharmacies.length == 0"><span style="font-size: 20px;">There are no pharmacies that you can write complaint on.</span></label>
        <div v-if="pharmacies.length != 0">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Address</th>
                        <th scope="col">Rating</th>
                    </tr>
                </thead>

                <tbody>
                    <tr
                        :key="pharmacy.id"
                        v-for="pharmacy in pharmacies"
                        @click="choosePharmacy(pharmacy)"
                        :class="[
                                selected === pharmacy
                                ? 'selected'
                                : '',
                        ]"
                    >
                        <td>{{ pharmacy.name }}</td>
                        <td>
                            {{ pharmacy.location.street }}  {{ pharmacy.location.streetNum }},
                            {{ pharmacy.location.zipcode }} {{ pharmacy.location.city }}
                        </td>
                        <td>{{ pharmacy.rating }}/5</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
export default {

    name: "PharmaciesForComplaintTable",
    
    props: {
        pharmacies: {
            type: Array,
            required: false,
        },
    },

    data() {
        return {
            selected: {},
        };
    },

    methods: {

        choosePharmacy(pharmacy)
        {
            this.selected = pharmacy;
            this.$emit("pharmacySelected", this.selected);
        },

    },
};
</script>

<style scoped>
#pharmacies {
    margin: 30px 100px 30px 100px;
}

thead {
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295);
}

.selected {
    background-color: rgba(155, 82, 151, 0.527);
}
</style>

<template>
    <div id="pharmaciesWithSelect">
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Name</th>
                    <th scope="col">Address</th>
                </tr>
            </thead>

            <tbody>
                <tr
                    :key="pharmacy.id"
                    v-for="pharmacy in pharmacies"

                    @click="choosePharmacy(pharmacy)"
                    :class="[ selected === pharmacy ? 'selected' : '', ]"
                >
                    <td>{{ pharmacy.id }}</td>
                    <td>{{ pharmacy.name }}</td>
                    <td>
                        {{ pharmacy.location.street }}  {{ pharmacy.location.streetNum }},
                        {{ pharmacy.location.zipcode }} {{ pharmacy.location.city }}
                    </td>

                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
export default {
    name: "PharmaciesWithSelect",
    props: {
        pharmacies: {
            type: Array,
            required: false,
        },
    },

    data() {
        return {
            selected : {},
        };
    },

    methods: {
        choosePharmacy(pharmacy){
            this.selected = pharmacy;
            this.$emit("pharmacySelected", this.selected.id);
        }
    },
};
</script>

<style scoped>

thead {
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295);
}

.selected {
    background-color: rgba(155, 82, 151, 0.527);
}
</style>

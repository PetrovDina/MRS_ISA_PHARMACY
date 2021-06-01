<template>
    <div id="pharmaciesWithPrice">
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Address</th>
                    <th scope="col">Rating</th>
                    <th scope="col">Quantity available</th>
                    <th scope="col">Price</th>
                </tr>
            </thead>

            <tbody>
                <tr
                    :key="result.pharmacy.id"
                    v-for="result in results"
                    @click="choosePharmacy(result)"
                    :class="[selected === result ? 'selected' : '']"
                >
                    <td>{{ result.pharmacy.name }}</td>
                    <td>
                        {{ result.pharmacy.location.street }}
                        {{ result.pharmacy.location.streetNum }},
                        {{ result.pharmacy.location.zipcode }}
                        {{ result.pharmacy.location.city }}
                    </td>
                    <td>
                        <star-rating
                            active-color="rgba(155, 82, 151, 0.527)"
                            :inline="true"
                            :star-size="20"
                            :read-only="true"
                            :show-rating="false"
                            :rating="result.pharmacy.rating"
                            :increment="0.1"
                        ></star-rating>
                    </td>
                    <td>{{ result.availableQuantity }}</td>
                    <td>{{ result.currentPrice }},00 RSD</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import StarRating from "vue-star-rating";

export default {
    name: "PharmaciesWithPriceComponent",
    props: {
        results: {
            type: Array,
            required: false,
        },
        canSelect: {
            default: true,
            type: Boolean,
        },
    },
    components: { StarRating },

    data() {
        return {
            selected: {},
        };
    },

    methods: {
        choosePharmacy(result) {
            if (this.canSelect) {
                this.selected = result;
                this.$emit("pharmacySelected", this.selected);
            }
        },
    },
};
</script>

<style scoped>
#pharmaciesWithPrice {
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

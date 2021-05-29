<template>
    <div id="pharmaciesWithPrice">
        <h4 v-if="results.length == 0">There are no pharmacies where you can buy medications from qr code</h4>
        <div v-else>
            <h4 style="margin-bottom: 40px;">Here are pharmacies where you can buy all medications from qr code</h4>
            <table class="table" >
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Name</th>
                        <th scope="col">Address</th>
                        <th scope="col">Rating</th>
                        <th scope="col">Price</th>
                        <th scope="col">Buy</th>
                    </tr>
                </thead>

                <tbody>
                    <tr
                        :key="result.pharmacy.id"
                        v-for="result in results"
                    >
                        <td>{{ result.pharmacy.id }}</td>
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
                        <td>{{ result.price }},00 RSD</td>
                        <td><button @click="buyMedications(result.pharmacy.id, result.price)"><i class="fa fa-shopping-cart" aria-hidden="true"></i></button></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
import StarRating from "vue-star-rating";

export default {
    name: "PharmaciesWithPriceQR",
    props: {

        results: {
            type: Array,
            required: false,
        },

    },
    components: { StarRating },

    data() {
        return {

        };
    },

    methods: {

        buyMedications: function(pharmacyId, price)
        {
            this.$emit('buy-medications', pharmacyId, price);
        }

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

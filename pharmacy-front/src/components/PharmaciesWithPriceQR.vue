<template>
    <div id="pharmaciesWithPrice">
        <h4 v-if="results.length == 0">There are no pharmacies where you can buy medications from qr code</h4>
        <div v-else>
            <h4 style="margin-bottom: 40px;">Here are pharmacies where you can buy all medications from qr code</h4>

            <div id="sort" v-if="results.length != 0">
                <p class="sort-label">sort by</p>
                <select class="sort-dropdown" @change="sortPerformed">
                    <option v-for="option in options" :key="option" :value="option">{{option}}</option>
                </select>
            </div>

            <table class="table" >
                <thead>
                    <tr>
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

            options: ['-', 'price', 'rating', 'name', 'city']

        };
    },

    methods: {

        buyMedications: function(pharmacyId, price)
        {
            this.$emit('buy-medications', pharmacyId, price);
        },

        sortPerformed: function(event)
        {
            let sortCriterium = event.target.value;
            this.$emit("sort-performed", sortCriterium);
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

.sort-dropdown {
    padding: 5px;
    border: 0.5px solid rgba(128, 128, 128, 0.473);
    border-radius: 5px;

    display: inline-block;

}

#sort{
    margin-top: 40px;
    width:100%;
    text-align:right;

}

.sort-label{
    /* margin: 10px 0 10px 0; */
    display: inline-block;
    /* float: right; */

}

#sort-and-filter{
    /* float:right; */
    text-align: right;
    /* margin: 5px 0 30px 0; */
}

.inlinepls{
    display:inline;
}

#filterDiv{
    margin-top: 20px;
    text-align: left;
}

.rating-input{
    width: 30px;
}

input{
    border: 0.5px solid rgba(128, 128, 128, 0.473);
    border-radius: 5px;


}

.filter-element{
    margin-right: 30px;
}

#blackIcon {
    color: rgba(0, 0, 0, 0.747);

}

#iconHolder{
    width: 10%;
    height:100%;
}
</style>

<template>
    <div id="pharmaciesViewDiv">
        <!-- <table class="table table-hover">
            <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Address</th>
                    <th scope="col">Rating</th>
                    <th scope="col">See more</th>
                </tr>
            </thead>

            <tbody>
                <tr :key="ph.id" v-for="ph in pharmacies">
                    <td>{{ ph.name }}</td>
                    <td>
                        {{ ph.location.street }} {{ ph.location.streetNum }},
                        {{ ph.location.zipcode }} {{ ph.location.city }}
                    </td>
                    <td>
                        <star-rating
                            active-color="rgba(155, 82, 151, 0.527)"
                            :inline="true"
                            :star-size="20"
                            :read-only="true"
                            :show-rating="false"
                            :rating="ph.rating"
                            :increment="0.1"
                        ></star-rating>
                    </td>
                    <td>
                        <button @click="redirectPharmacyView(ph.id)">
                            <i class="fa fa-ellipsis-h fa-2x"></i>
                        </button>
                    </td>
                </tr>
            </tbody>
        </table> -->

        <!-- CARDS -->
        <div class="row">
            <div class="col-sm-3" :key="ph.id" v-for="ph in pharmacies">
                <div class="card">
                    <div id="pictureDiv">
                        <img
                            class="card-img-top"
                            src="@/assets/pharmacy.png"
                            alt="Card image cap"
                        />
                    </div>
                    <div class="card-body">
                        <h4 class="card-title">{{ ph.name }}</h4>
                        <star-rating
                            active-color="rgba(155, 82, 151, 0.527)"
                            :star-size="28"
                            :read-only="true"
                            :show-rating="false"
                            :rating="ph.rating"
                            :increment="0.1"
                            style="margin-bottom: 20px"
                        ></star-rating>

                        <p class="card-text">
                            Address:
                            <b
                                >{{ ph.location.street }}
                                {{ ph.location.streetNum }}
                            </b>
                        </p>
                        <p class="card-text">
                            City:
                            <b>
                                {{ ph.location.zipcode }}
                                {{ ph.location.city }}</b
                            >
                        </p>

                        <div style="display: flex; justify-content: center">
                            <Button
                                @action-performed="redirectPharmacyView(ph.id)"
                                bgd_color="rgba(87, 87, 87, 0.247)"
                                text="Details"
                            ></Button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Button from "./Button.vue";
import StarRating from "vue-star-rating";

export default {
    components: { Button, StarRating },
    name: "PharmaciesView",

    props: ["pharmacies"],

    data() {
        return {
            selected: {},
        };
    },

    mounted() {},

    methods: {
        clickedId: function (id) {
            this.$router.push({
                name: "PharmacyView",
                params: {
                    pharmacyId: id,
                },
            });
        },

        redirectPharmacyView: function (pharmacyId) {
            this.$router.push({
                name: "PharmacyViewPatient",
                params: {
                    pharmacyIdProp: pharmacyId,
                },
            });
        },
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#pharmaciesViewDiv {
    margin: 10px 40px 30px 40px;
}

thead {
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295);
}

.selected {
    background-color: rgba(155, 82, 151, 0.527);
}

.card-img-top {
    max-width: 220px;
    max-height: 220px;
}

#pictureDiv {
    align-content: center;
    padding:10px;
}

.card {
    margin-top: 20px;
    text-align: center;
}

Button {
    margin: 10px;
}
</style>


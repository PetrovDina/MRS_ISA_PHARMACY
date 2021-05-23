<template>
    <div>
        <v-card class="ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div>
                    <h1>{{pharmacy.name}}</h1>
                    <h1 id="mapPointer" @click="setUpMap()" >{{pharmacy.location.street}} {{pharmacy.location.streetNum}}, {{pharmacy.location.city}}</h1>
                    <star-rating
                        data-toggle="tooltip" 
                        data-placement="top" 
                        :title="pharmacy.rating"
                        active-color="rgba(155, 82, 151, 0.527)"
                        :inline="true"
                        :star-size="50"
                        :read-only="true"
                        :show-rating="false"
                        :rating="pharmacy.rating"
                        :increment="0.1"
                    ></star-rating>
                </div>
                <v-spacer>
                </v-spacer>
            </v-card-title>
        </v-card>
        <div v-if="isPatient() == true">
            <Button 
                @action-performed="subscribedToggle" 
                id="sub-btn"
                class="btn"
                :text="!subscribed ? 'Subscribe' : 'Unsubscribe'"
                :bgd_color="!subscribed ? 'rgba(15, 95, 72, 0.95)' : 'rgba(155, 82, 151, 0.527)'">
            </Button>
        </div>
        <MapContainer
            :coordinates = "[pharmacy.location.longitude, pharmacy.location.latitude]"
        >
        </MapContainer>
    </div>
    
</template>

<script>

import Button from '../components/Button';
import { client } from "@/client/axiosClient";
import StarRating from 'vue-star-rating';
import MapContainer from '../components/MapContainer';

export default {
    name: "PharmacyViewPatient",
    components: { Button, StarRating, MapContainer},

    props: {
        pharmacyIdProp: {
            type: Number,
            required: false,
            default() {
                return -1;
            }
        },

    },

    data() {
        return {
            pharmacyId : -1,
            subscribed : false,
            pharmacy : { 
                location: {} // morao da dodam zbog rendera
            },
        };
    },

    methods: {
        
        subscribedToggle: function() {
            if(this.subscribed)
            {
                client({
                    url: "patient/removeSubscription",
                    params: {
                        patientUsername: localStorage.getItem("USERNAME"),
                        subscriptionId: this.pharmacyId,
                    },
                    method: "GET",
                }).then((response) => {
                    this.subscribed = false;
                });
            }
            else
            {
                client({
                    url: "patient/addSubscription",
                    params: {
                        patientUsername: localStorage.getItem("USERNAME"),
                        subscriptionId: this.pharmacyId,
                    },
                    method: "GET",
                }).then((response) => {
                    this.subscribed = true;
                });
            }
        },

        isPatient: function() {
            return localStorage.getItem('USER_TYPE') == 'PATIENT';
        }

    },

    mounted() {

        // Ove silne zavrzlame su zbog refresh-a i zato sto kada se menja props izlazi error
        this.pharmacyId = this.pharmacyIdProp;

        if(this.pharmacyId == -1)
            this.pharmacyId = this.$store.getters.getLastPharmacyPatientView;
        else
            this.$store.commit('changeLastPharmacyPatientView', this.pharmacyId)

        if(this.isPatient()) {      // Samo ako je pacijent proveri jel pretplacen
            client({
                url: "patient/isSubscribed",
                params: {
                    patientUsername: localStorage.getItem("USERNAME"),
                    pharmacyId: this.pharmacyId,
                },
                method: "GET",
            }).then((response) => {
                this.subscribed = response.data;
            });
        }
        client({
                url: "pharmacy/" + this.pharmacyId,
                method: "GET",
            }).then((response) => {
                this.pharmacy = response.data
            });
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#pharmacy {
    align-self: flex-start;
}

#sub-btn {
    float : right;
    margin : 10px;
    color :white;
}

#mapPointer {
    cursor: pointer;
}
</style>
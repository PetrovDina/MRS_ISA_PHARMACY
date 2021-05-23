<template>
    <div>
        <h1>Apoteka</h1>
        <div v-if="isPatient() == true">
            <Button 
                @action-performed="subscribedToggle" 
                id="sub-btn"
                class="btn"
                :text="!subscribed ? 'Subscribe' : 'Unsubscribe'"
                :bgd_color="!subscribed ? 'rgba(15, 95, 72, 0.95)' : 'rgba(155, 82, 151, 0.527)'">
            </Button>
        </div>
    </div>
    
</template>

<script>

import Button from '../components/Button';
import { client } from "@/client/axiosClient";

export default {
    name: "PharmacyViewPatient",
    components: { Button },

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
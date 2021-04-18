<template>
    <div id="homeDiv">
        <h1>{{ msg }}</h1>
        <TabNav
            :tabs="['Medications', 'Pharmacies']"
            :selected="selected"
            @selected="setSelected"
        >
            <Tab :isSelected="selected === 'Medications'">
                <MedicationsView :medications="medications"></MedicationsView>
            </Tab>

            <Tab :isSelected="selected === 'Pharmacies'">
                <SearchBar
                    :placeHolder="searchPlaceholder"
                    @search-performed="searchPerformed"
                />
                <PharmaciesView
                    :pharmacies="pharmacySearchResults"
                ></PharmaciesView>
            </Tab>
        </TabNav>
    </div>
</template>

<script>
import { client } from "@/client/axiosClient";
import MedicationsView from "../components/MedicationsView";
import PharmaciesView from "../components/PharmaciesView";
import SearchBar from "../components/SearchBar";

import TabNav from "../components/TabNav.vue";
import Tab from "../components/Tab.vue";

export default {
    name: "Home",

    components: {
        MedicationsView,
        PharmaciesView,
        TabNav,
        Tab,
        SearchBar,
    },

    data() {
        return {
            selected: "Medications",
            msg: "Welcome to Team12 pharmacy",
            medications: [],
            pharmacies: [],
            searchPlaceholder: "Search pharmacies...",
            searchQuery: "",
            pharmacySearchResults: [],
            // medications: [
            //     {
            //         id: 1,
            //         name: "Probiotik",
            //         manufacturer: "Ivančic i sinovi",
            //         prescriptionReq: false,
            //         form: "PILL",
            //     },
            //     {
            //         id: 2,
            //         name: "Brufen",
            //         manufacturer: "Bosna lijek",
            //         prescriptionReq: true,
            //         form: "PILL",
            //     },
            //     {
            //         id: 3,
            //         name: "Paracetamol",
            //         manufacturer: "Krka",
            //         prescriptionReq: true,
            //         form: "CAPSULE",
            //     },
            //     {
            //         id: 4,
            //         name: "Panadol",
            //         manufacturer: "Jugoremedija",
            //         prescriptionReq: false,
            //         form: "PASTE",
            //     },
            //     {
            //         id: 5,
            //         name: "Panklav",
            //         manufacturer: "Krka",
            //         prescriptionReq: true,
            //         form: "CAPSULE",
            //     },
            // ],

            // pharmacies: [
            //     {
            //         id: 1,
            //         name: "Apoteka Janković",
            //         address: "Narodnog fronta 5, 21000 Novi Sad",
            //         rating: 3.0,
            //     },

            //     {
            //         id: 2,
            //         name: "Benu",
            //         address: "Bulevar Oslobođenja 25, 21000 Novi Sad",
            //         rating: 4.3,
            //     },

            //     {
            //         id: 3,
            //         name: "Srbotrade",
            //         address: "Tomaša Ježa 2, 11000 Beograd",
            //         rating: 4.8,
            //     },
            // ],
        };
    },

    methods: {
        setSelected(tab) {
            this.selected = tab;
        },

        searchPerformed(text) {
            this.searchQuery = text;
            // client({
            //     url: "pharmacy/searchPharmacies",
            //     params: {query: this.searchQuery},
            //     method: "GET",
            // }).then((response) => (this.pharmacies = response.data));

            let self = this;
            this.pharmacySearchResults = this.pharmacies.filter(function (
                pharmacy
            ) {
                return (
                    pharmacy.name
                        .toLowerCase()
                        .includes(self.searchQuery.toLowerCase()) ||
                    pharmacy.rating
                        .toString()
                        .toLowerCase()
                        .includes(self.searchQuery.toLowerCase()) ||
                    pharmacy.location.city
                        .toLowerCase()
                        .includes(self.searchQuery.toLowerCase()) ||
                    pharmacy.location.zipcode
                        .toLowerCase()
                        .includes(self.searchQuery.toLowerCase()) ||
                    pharmacy.location.street
                        .toLowerCase()
                        .includes(self.searchQuery.toLowerCase()) ||
                    pharmacy.location.streetNum
                        .toString()
                        .toLowerCase()
                        .includes(self.searchQuery.toLowerCase()) ||
                    pharmacy.location.city
                        .toLowerCase()
                        .includes(self.searchQuery.toLowerCase())
                );
            });
        },
    },

    mounted() {
        client({
            url: "med/all",
            method: "GET",
        }).then((response) => (this.medications = response.data));

        client({
            url: "pharmacy/all",
            method: "GET",
        }).then((response) => {
            this.pharmacies = response.data;
            this.pharmacySearchResults = this.pharmacies;
        });
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#homeDiv {
    margin-top: 5vh;
}
</style>

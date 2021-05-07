<template>
    <div id="homeDiv">
        <img
            src="@/assets/logo2.png"
            height="100"
            width="100"
        />
        <h2>{{ msg }}</h2>
        <TabNav
            :tabs="['Medications', 'Pharmacies']"
            :selected="selected"
            @selected="setSelected"
        >
            <Tab :isSelected="selected === 'Medications'">
                <SearchBar
                    :placeHolder="medicationSearchPlaceholder"
                    :options="medicationSortOptions"
                    searchType="medications"
                    @search-performed="medicationSearchPerformed"
                />

                <MedicationsView
                    :medications="medicationSearchResults"
                ></MedicationsView>
            </Tab>

            <Tab :isSelected="selected === 'Pharmacies'">
                <SearchBar
                    :placeHolder="searchPlaceholder"
                    :options="pharmaciesSortOptions"
                    search-type="pharmacies"
                    @search-performed="pharmaciesSearchPerformed"
                    @sort-performed="pharmaciesSortPerformed"
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
            msg: "Team12 pharmacy",
            medications: [],
            pharmacies: [],
            searchPlaceholder: "Search pharmacies...",
            medicationSearchPlaceholder: "Search medication...",
            searchQuery: "",
            pharmacySearchResults: [],
            medicationSearchResults: [],

            pharmaciesSortOptions: ["-", "name", "city", "rating"],
            medicationSortOptions: [
                "-",
                "name",
                "manufacturer",
                "prescription",
                "form",
            ],

            
        };
    },

    methods: {
        setSelected(tab) {
            this.selected = tab;
        },

        pharmaciesSortPerformed(sortCriterium) {
            let self = this;
            if (sortCriterium === "-") 
                return;

            else if (sortCriterium === "rating") {
                this.pharmacySearchResults = this.pharmacySearchResults.sort(function (
                    a,
                    b
                ) {
                    return b.rating - a.rating;
                });
            }

            else if (sortCriterium === "city") {
                this.pharmacySearchResults = this.pharmacySearchResults.sort(function (
                    a,
                    b
                ) {
                    return a.location.city > b.location.city ? 1 : -1;
                });
            }

            else if (sortCriterium === "name") {
                this.pharmacySearchResults = this.pharmacySearchResults.sort(function (
                    a,
                    b
                ) {
                    return a.name > b.name ? 1 : -1;
                });
            }

        },

        pharmaciesSearchPerformed(text, city, minRating, maxRating) {
            this.searchQuery = text;
            let self = this;

            //first we check the text from the search bar
            let temp = this.pharmacies.filter(function (
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

            //now we filter the results based on user input rating and city
            temp = temp.filter(function (
                pharmacy
            ) {
                return (

                    (pharmacy.rating
                        >= minRating && pharmacy.rating <= maxRating) &&
                    pharmacy.location.city
                        .toLowerCase()
                        .includes(city.toLowerCase())

                );
            });

            this.pharmacySearchResults = temp;
        },

        medicationSearchPerformed(text) {
            this.searchQuery = text;
            let self = this;
            this.medicationSearchResults = this.medications.filter(function (
                medication
            ) {
                return (
                    medication.name
                        .toLowerCase()
                        .includes(self.searchQuery.toLowerCase()) ||
                    medication.manufacturer
                        .toLowerCase()
                        .includes(self.searchQuery.toLowerCase()) ||
                    medication.form
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
        }).then((response) => {
            this.medications = response.data;
            this.medicationSearchResults = this.medications;
        });

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

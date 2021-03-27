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
                <p>Ovde ce biti apoteke</p>
            </Tab>

        </TabNav>

    </div>
</template>

<script>
import { client } from "@/client/axiosClient";
import MedicationsView from "../components/MedicationsView";
import TabNav from '../components/TabNav.vue';
import Tab from '../components/Tab.vue';

export default {
    name: "Home",

    components: {
        MedicationsView,
        TabNav,
        Tab
    },

    data() {
        return {
            selected: 'Medications',
            msg: "Welcome to Team12 pharmacy",

            medications: [
                {
                    id: 1,
                    name: "Probiotik",
                    manufacturer: "Ivančic i sinovi",
                    prescriptionReq: false,
                    form: "PILL",
                },
                {
                    id: 2,
                    name: "Brufen",
                    manufacturer: "Bosna lijek",
                    prescriptionReq: true,
                    form: "PILL",
                },
                {
                    id: 3,
                    name: "Paracetamol",
                    manufacturer: "Krka",
                    prescriptionReq: true,
                    form: "CAPSULE",
                },
                {
                    id: 4,
                    name: "Panadol",
                    manufacturer: "Jugoremedija",
                    prescriptionReq: false,
                    form: "PASTE",
                },
                {
                    id: 5,
                    name: "Panklav",
                    manufacturer: "Krka",
                    prescriptionReq: true,
                    form: "CAPSULE",
                },
            ],
        };
    },

    methods: {
        setSelected(tab) {
            console.log("setting to " + tab)
            this.selected = tab;
    }
    },

    mounted() {},
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#homeDiv {
    margin-top: 5vh;
}
</style>

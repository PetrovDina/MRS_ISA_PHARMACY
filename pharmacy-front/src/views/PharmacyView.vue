<template>
    <div>
        <v-card class="ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div >
                    <h1>{{pharmacyName}}</h1>
                    <h1>{{address}}, {{city}} {{zipCode}}</h1>
                </div>
                <v-spacer></v-spacer>
            </v-card-title>
        </v-card>
        <Button v-if="checkRoleTEST('PATIENT')"
            @action-performed="subscribedToggle" 
            id="sub-btn"
            class="btn"
            :text="!subscribed ? 'Subscribe' : 'Subscribed'"
            :bgd_color="!subscribed ? 'rgba(15, 95, 72, 0.95)' : 'grey'">
        </Button>
        <TabNav v-if="checkRoleTEST('PHARMACY_ADMIN')"
            :tabs="['Dermatologists', 'Pharmacists', 'Medications']"
            :selected="selected"
            @selected="setSelected"
        >
            <Tab :isSelected="selected === 'Dermatologists'">
                <DermatologistsTable
                    @hired-dermatologist="addDermatologistToList"
                    :dermatologists = "dermatologistsToSend"
                    :pharmacyId = "pharmacyId">
                </DermatologistsTable>
            </Tab>

            <Tab :isSelected="selected === 'Pharmacists'">
                <PharmacistsTable
                    @registeredPharmacist = "addPharmacistIntoList"
                    :pharmacists = "pharmacistsToSend"
                    :pharmacyId = "pharmacyId">
                </PharmacistsTable>
            </Tab>
            <Tab :isSelected="selected === 'Medications'">
                <MedicationsTable 
                    @update-medication="updateMedicationPrice" 
                    @add-med-into-pharmacy="addMedicationToDB" 
                    @record-deleted="deleteRecordFromDB" 
                    :medications = "medicationToSend">
                </MedicationsTable>
            </Tab>
        </TabNav>
    </div>
    
</template>

<script>
import { client } from "@/client/axiosClient";
import DermatologistsTable from '../components/DermatologistsTable.vue';
import MedicationsTable from '../components/MedicationsTable.vue';
import Button from '../components/Button';
import TabNav from '../components/TabNav';
import Tab from '../components/Tab';
import PharmacistsTable from '../components/PharmacistsTable';

export default {
  components: { Button, DermatologistsTable, MedicationsTable, TabNav, Tab, PharmacistsTable },
    data() {
        return {
            selected: "Dermatologists",
            subscribed : false,
            showDermos: false,
            showMedication: false,
            pharmacy : null,
            pharmacyName: '',
            address: '',
            city: '',
            zipCode: '',
            pharmacyItems : [],
            dermatologistsToSend: [],
            pharmacistsToSend: [],
            medicationToSend: [],
            // lista svih slobodnih termina treba da se doda
            rating: 0.0
        };
    },

    props: {
        pharmacyId: null,
    },

    methods: {
        checkRoleTEST : function(userRole){
            return this.$store.getters.getLoggedUserRole === userRole;
        },
        toggleDermos : function(){
            this.showDermos = !this.showDermos;
        },
        toggleMedication : function(){
            this.showMedication = !this.showMedication;
        },
        setSelected(tab) {
            this.selected = tab;
        },
        subscribedToggle : function(){
            this.subscribed = !this.subscribed;
        },
        deleteRecordFromDB : function(id){
            let medId = 0;
            for(medId in this.medicationToSend){
                if(this.medicationToSend[medId].id === id){
                    this.medicationToSend.splice(medId, 1);
                    break;
                }
            }
            // poziv na back da se obrise pharmacyStorageItem
            client({
                url: "/pharmacyStorageItem/"+id,
                method: "DELETE"
            })
            .catch((response) => (console.log(response)));
        },
        addMedicationToDB : function(med, medicationPrice){
            this.medicationToSend = [...this.medicationToSend, {
                    id: this.medicationToSend.lenght,
                    name: med.name,
                    manufacturer: med.manufacturer,
                    prescriptionReq: med.prescriptionReq,
                    form: med.form,
                    quantity : 0,
                    price : medicationPrice,
                    price_edit : false
                }
            ]
            // poziv na back da se doda novi pharmacyStorageItem u pharmacy
            var dataToSave = {
                quantity : 0,
                itemPrices : [
                    {
                        price: medicationPrice
                    }
                ],
                medication: {
                    id : med.id
                },
                pharmacy : {
                    id : this.pharmacy.id
                }
            }
            client({
                url: "/pharmacyStorageItem",
                method: "POST",
				data: dataToSave
            })
            .catch((response) => (console.log(response)));
        },
        updateMedicationPrice : function(med, priceToUpdate){
            var dataToUpdate = 
            {
                id: med.id,
                itemPrices: [
                    {
                        price: priceToUpdate
                    }
                ]
            }
            // poziv na back da se update-uje cijena pharmacyStorageItem u pharmacy
			client({
                url: "/pharmacyStorageItem",
                method: "PUT",
				data: dataToUpdate
            })
            .catch((response) => (console.log(response)));
        },
        addDermatologistToList: function(dermatologist){
            client({
            url: "employments",
            method: "POST",
            data: {
                employee: {
                    id: dermatologist.id
                },
                workTime: dermatologist.workTime,
                pharmacy: {
                    id: this.pharmacyId
                },
                contractType: "DERMATOLOGIST_CONTRACT",
            }
            })
            .then((response) => {
                console.log("Hiring success!");
                this.dermatologistsToSend = [...this.dermatologistsToSend, dermatologist];
                })
            .catch((response) => console.log("Hiring failed!"));
        },
        addPharmacistIntoList: function(pharmacist){
            this.pharmacistsToSend = [...this.pharmacistsToSend, pharmacist];
        }
    },

    mounted() {
        // Za apoteku ce ovo biti kompletan izgled koji ce na pocetku da povuce podatak iz baze o konkretnoj apoteci
        // /pharmacy/{id} i dobavice sve podatke vezano za apoteku
        // primjera radi dobavljamo apoteku sa ID-em 1
        if (!this.pharmacyId){
            this.$router.push({name:"Home"});
            return;
        }                                    

        client({
            url: "pharmacy/" + this.pharmacyId +"/withEmployments",
            method: "GET",
        }).then((response) => {
            this.pharmacy = response.data
            // treba sada da se razvrstaju dermatolozi od farmaceuta
            let employment = null;
            for(employment of this.pharmacy.employments){
                employment.employee['workTime'] = employment.workTime;
                if(employment.contractType === 'DERMATOLOGIST_CONTRACT')
                    this.dermatologistsToSend = [...this.dermatologistsToSend, employment.employee];
                else
                    this.pharmacistsToSend = [...this.pharmacistsToSend, employment.employee];
            }
            this.pharmacyName = this.pharmacy.name;
            this.address = this.pharmacy.location.street;
            this.city = this.pharmacy.location.city;
            this.zipCode = this.pharmacy.location.zipcode;
        }).catch((response) => alert("pharmacyId je los :("));
            // dobavljamo sve lijekove iz apoteke
        client({
            url : "pharmacyStorageItem/fromPharmacy/" + this.pharmacyId,
            method : "GET",
        }).then((response) => {
                this.pharmacyItems = response.data;
                let pharmacy_item = null;
                for(pharmacy_item of this.pharmacyItems){
                    let current_price = 0;
                    let iter = null;
                    for(iter of pharmacy_item.itemPrices){
                        if(iter.current === true){
                            current_price = iter.price;
                            break;
                        }
                    }
                    let medication = {
                            id: pharmacy_item.id,
                            name: pharmacy_item.medication.name,
                            manufacturer: pharmacy_item.medication.manufacturer,
                            prescriptionReq: pharmacy_item.medication.prescriptionReq,
                            form: pharmacy_item.medication.form,
                            quantity : pharmacy_item.quantity,
                            price : current_price,
                            price_edit : false,
                        };
                    this.medicationToSend = [...this.medicationToSend, medication]
                }
        }).catch((response) => alert("pharmacyId je null"));
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
</style>
<template>
    <div>
        <v-card class="ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div>
                    <h1>{{pharmacy.name}}</h1>
                    <h1 id="mapPointer" @click="setUpMap()" >{{pharmacy.location.street}} {{pharmacy.location.streetNum}}, {{pharmacy.location.city}}</h1>
                </div>
                <v-spacer>
                    <button style="float:right" @click="editPharmacyData()"><i class="fa fa-edit fa-lg"></i></button>
                </v-spacer>
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
            :tabs="['Dermatologists', 'Pharmacists', 'Medications', 'Orders']"
            :selected="selected"
            @selected="setSelected"
        >
            <Tab :isSelected="selected === 'Dermatologists'">
                <DermatologistsTable
                    @hired-dermatologist = "addDermatologistToList"
                    @fired-dermatologist = "removeDermatologistFromList"
                    :dermatologists = "dermatologistsToSend"
                    :pharmacyId = "pharmacyId">
                </DermatologistsTable>
            </Tab>

            <Tab :isSelected="selected === 'Pharmacists'">
                <PharmacistsTable
                    @registeredPharmacist = "addPharmacistIntoList"
                    @fired-pharmacist = "removePharmacistFromList"
                    :pharmacists = "pharmacistsToSend"
                    :pharmacyId = "pharmacyId">
                </PharmacistsTable>
            </Tab>
            <Tab :isSelected="selected === 'Medications'">
                <MedicationsTable 
                    @update-medication="updateMedicationPrice" 
                    @add-med-into-pharmacy="addMedicationToDB" 
                    @record-deleted="deleteRecordFromDB" 
                    :medications = "medicationToSend"
                    :pharmacyId = "pharmacyId">
                </MedicationsTable>
            </Tab>
            <Tab :isSelected="selected === 'Orders'">
                <OrdersTable 
                    @order-updated = "orderUpdated"
                    @order-added = "addNewOrder"
                    @new-medications-added-from-order = "addMedIntoPharStorage"
                    @update-quanities-for-medications = "updateQuantities"
                    :orders = "ordersToSend"
                    :medicationsInPharmacy = "medicationToSend">
                </OrdersTable>
            </Tab>
        </TabNav>
        <ModalWindowEditPharmacyData
        @modal-closed = "modalEditPharmacyData = false"
        @pharamcy-data-changed = "saveEditedPharmacyData"
        :pharmacy = "pharmacy"
        :modal_show = "modalEditPharmacyData"
        >
        </ModalWindowEditPharmacyData>
        <ModalWindowMap
        @modal-closed = "modalMap = false"
        :pharmacy = "pharmacy"
        :modal_show = "modalMap"
        >
        </ModalWindowMap>
    </div>
    
</template>

<script>
import { client } from "@/client/axiosClient";
import DermatologistsTable from '../components/DermatologistsTable.vue';
import MedicationsTable from '../components/MedicationsTable.vue';
import Button from '../components/Button';
import TabNav from '../components/TabNav';
import Tab from '../components/Tab';
import PharmacistsTable from '../components/PharmacistsTable.vue';
import OrdersTable from '../components/OrdersTable';
import ModalWindowEditPharmacyData from '../components/ModalWindowEditPharmacyData.vue'
import ModalWindowMap from "../components/ModalWindowMap.vue"

export default {
    name: "PharmacyView",
    components: { Button, DermatologistsTable, MedicationsTable, TabNav, Tab, PharmacistsTable, OrdersTable, ModalWindowEditPharmacyData, ModalWindowMap},
    data() {
        return {
            selected: "Dermatologists",
            subscribed : false,
            pharmacy : { 
                location: {} // morao da dodam zbog rendera
            },
            pharmacyId: null,
            dermatologistsToSend: [],
            pharmacistsToSend: [],
            medicationToSend: [],
            ordersToSend: [],
            // lista svih slobodnih termina treba da se doda
            rating: 0.0,
            modalEditPharmacyData : false,
            coordinates : [],
            modalMap : false,
        };
    },

    methods: {
        checkRoleTEST : function(userRole){
            return this.$store.getters.getLoggedUserRole === userRole;
        },
        setSelected(tab) {
            this.selected = tab;
        },
        subscribedToggle : function(){
            this.subscribed = !this.subscribed;
        },
        deleteRecordFromDB : function(id){
            for(const medId in this.medicationToSend){
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
            // poziv na back da se doda novi pharmacyStorageItem u pharmacy
            client({
                url: "/pharmacyStorageItem",
                method: "POST",
				data: {
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
            }).then((response) => {
                this.medicationToSend = [...this.medicationToSend, {
                    id: response.data.id,
                    name: med.name,
                    manufacturer: med.manufacturer,
                    prescriptionReq: med.prescriptionReq,
                    form: med.form,
                    quantity : 0,
                    medicationId : med.id,
                    price : medicationPrice,
                    price_edit : false
                }]
            }).catch((response) => (console.log(response)));
        },
        updateMedicationPrice : function(med, priceToUpdate){
            // poziv na back da se update-uje cijena pharmacyStorageItem u pharmacy
			client({
                url: "/pharmacyStorageItem",
                method: "PUT",
                data: {
                    id: med.id,
                    itemPrices: [
                        {
                            price: priceToUpdate
                        }
                    ]
                }
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
                this.$toasted.show("Hiring success", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
                dermatologist['employmentId'] = response.data.id;
                this.dermatologistsToSend = [...this.dermatologistsToSend, dermatologist];
                })
            .catch((response) => 
                this.$toasted.show("Hiring failed! Overlapping working hours.", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                })
            );
        },
        removeDermatologistFromList : function(dermatologist){
            for(const dermIter in this.dermatologistsToSend){
                if(this.dermatologistsToSend[dermIter].id === dermatologist.id){
                    this.dermatologistsToSend.splice(dermIter, 1);
                    return;
                }
            }
        },
        addPharmacistIntoList: function(pharmacist){
            this.pharmacistsToSend = [...this.pharmacistsToSend, pharmacist];
        },
        removePharmacistFromList : function(pharmacist){
            for(const pharIter in this.pharmacistsToSend){
                if(this.pharmacistsToSend[pharIter].id === pharmacist.id){
                    this.pharmacistsToSend.splice(pharIter, 1);
                    return;
                }
            }
        },
        addNewOrder : function(order){
            this.ordersToSend = [...this.ordersToSend, order];
        },
        orderUpdated : function(orderUpdated){
            this.ordersToSend.forEach(order => {
                if(order.id == orderUpdated.id){
                    order.dueDate = orderUpdated.dueDate;
                    order.orderItems = orderUpdated.orderItems;
                }
            });
        },
        addMedIntoPharStorage : function(medications){
            for (let medIndex = 0; medIndex < medications.length; medIndex++) {
                const medication = medications[medIndex];
                client({
                    url: "pharmacyStorageItem",
                    method: "POST",
                    data : {
                        quantity : 0,
                        itemPrices : [
                            {
                                price: medication["price"]
                            }
                        ],
                        medication: {
                            id : medication.id
                        },
                        pharmacy : {
                            id : this.pharmacy.id
                        }
                }
                }).then((response) => {
                    this.medicationToSend = [... this.medicationToSend, {
                        id: response.data.id,
                        name: medication.name,
                        manufacturer: medication.manufacturer,
                        prescriptionReq: medication.prescriptionReq,
                        form: medication.form,
                        quantity : 0,
                        price : medication["price"],
                        medicationId: medication.id,
                        price_edit : false,
                    }]
                });
            }
        },
        updateQuantities : function(order){
            order.orderItems.forEach(orderItem => {
                this.medicationToSend.forEach(med => {
                    if(med.medicationId === orderItem.medication.id)
                        med.quantity += orderItem.quantity;
                });
            });
        },
        editPharmacyData : function(){
            this.modalEditPharmacyData = true;
        },
        isLocationChanged : function(location){
            if(location.city != this.pharmacy.location.city) return true;
            if(location.street != this.pharmacy.location.street) return true;
            if(location.streetNum != this.pharmacy.location.streetNum) return true;
            return false;
        },
        saveWithLocationLongAndLat : function(pharmacy){
            const url =
                "https://nominatim.openstreetmap.org/search/" +
                pharmacy.location.city +
                ", " +
                pharmacy.location.street +
                " " +
                pharmacy.location.streetNum;

            this.axios.get(
                url, {
                params: {
                    format: "json",
                    limit: 1, 
                    "accept-language": "en",
                },
            }).then((response) => {
                if (response.data && response.data.lenght != 0) {
                    pharmacy.location['longitude'] = response.data[0].lon;
                    pharmacy.location['latitude'] = response.data[0].lat;
                    pharmacy.location['zipcode'] = 21000;
                    client({
                        url: "pharmacy",
                        method: "PUT",
                        data: pharmacy
                    }).then((response) => {
                        this.pharmacy.name = response.data.name;
                        this.pharmacy.location = response.data.location;
                        this.pharmacy.appointmentPriceCatalog = response.data.appointmentPriceCatalog;
                    });
                }
            }).catch(() => {
                alert('Could not find coordinates based on given info.', '');
            });
        },
        saveEditedPharmacyData : function(pharmacy){
            if(this.isLocationChanged(pharmacy.location))
                this.saveWithLocationLongAndLat(pharmacy);
            else {
                pharmacy.location['longitude'] = this.pharmacy.location.longitude;
                pharmacy.location['latitude'] = this.pharmacy.location.latitude;
                client({
                    url: "pharmacy",
                    method: "PUT",
                    data: pharmacy
                }).then((response) => {
                    this.pharmacy.name = response.data.name;
                    this.pharmacy.location = response.data.location;
                    this.pharmacy.appointmentPriceCatalog = response.data.appointmentPriceCatalog;
                });
            }
        },
        setUpMap: function() {
             this.modalMap = true;   
        },
    },

    mounted() {
        // Za apoteku ce ovo biti kompletan izgled koji ce na pocetku da povuce podatak iz baze o konkretnoj apoteci
        // /pharmacy/{id} i dobavice sve podatke vezano za apoteku
        // primjera radi dobavljamo apoteku sa ID-em 1
        client({
            url: "pharmacyAdmin/" + localStorage.getItem('USERNAME'),
            method: "GET",
        }).then((response) => {
            this.pharmacyId = response.data.pharmacyId;
            client({
                url: "pharmacy/" + this.pharmacyId +"/withEmployments",
                method: "GET",
            }).then((response) => {
                this.pharmacy = response.data
                // treba sada da se razvrstaju dermatolozi od farmaceuta
                for(const employment of this.pharmacy.employments){
                    employment.employee['workTime'] = employment.workTime;
                    employment.employee['employmentId'] = employment.id;
                    if(employment.contractType === 'DERMATOLOGIST_CONTRACT')
                        this.dermatologistsToSend = [...this.dermatologistsToSend, employment.employee];
                    else
                        this.pharmacistsToSend = [...this.pharmacistsToSend, employment.employee];
                }
            }).catch((response) => alert("pharmacyId je los :("));
                // dobavljamo sve lijekove iz apoteke
            client({
                url : "pharmacyStorageItem/fromPharmacy/" + this.pharmacyId,
                method : "GET",
            }).then((response) => {
                    for(const pharmacy_item of response.data){
                        let current_price = 0;
                        for(const iter of pharmacy_item.itemPrices){
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
                                medicationId: pharmacy_item.medication.id,
                                price_edit : false,
                            };
                        this.medicationToSend = [...this.medicationToSend, medication]
                    }
            }).catch((response) => alert("pharmacyId je null"));

            client({
                url : "order/allFrom/" + this.pharmacyId + "/withOffers",
                method : "GET",
            }).then((response) => {
                this.ordersToSend = response.data;
            }).catch((response) => alert("pharmacyId je null"));
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
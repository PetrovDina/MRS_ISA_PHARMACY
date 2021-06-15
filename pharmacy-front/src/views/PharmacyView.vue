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
                    <button style="float:right" @click="editPharmacyData()"><i class="fa fa-edit fa-lg"></i></button>
                </v-spacer>
            </v-card-title>
        </v-card>
        <TabNav v-if="checkRoleTEST('PHARMACY_ADMIN')"
            :tabs="['Dermatologists', 'Pharmacists', 'Medications', 'Orders']"
            :selected="selected"
            @selected="setSelected"
        >
            <Tab :isSelected="selected === 'Dermatologists'">
                <SearchBar
                    :placeHolder="searchDermatologistsPlaceholder"
                    :options="dermatologistsSortOptions"
                    search-type="dermatologists"
                    @search-performed="dermatologistsSearchPerformed"
                    @sort-performed="dermatologistsSortPerformed"
                />
                <DermatologistsTable
                    @hired-dermatologist = "addDermatologistToList"
                    @fired-dermatologist = "removeDermatologistFromList"
                    :dermatologists = "dermatologistsSearchResults"
                    :pharmacyId = "pharmacyId">
                </DermatologistsTable>
            </Tab>

            <Tab :isSelected="selected === 'Pharmacists'">
                <SearchBar
                    :placeHolder="searchPharmacistsPlaceholder"
                    :options="pharmacistsSortOptions"
                    search-type="pharmacists"
                    @search-performed="pharmacistsSearchPerformed"
                    @sort-performed="pharmacistsSortPerformed"
                />
                <PharmacistsTable
                    @registeredPharmacist = "addPharmacistIntoList"
                    @fired-pharmacist = "removePharmacistFromList"
                    :pharmacists = "pharmacistsSearchResults"
                    :pharmacyId = "pharmacyId">
                </PharmacistsTable>
            </Tab>
            <Tab :isSelected="selected === 'Medications'">
                <MedicationsTable 
                    @update-medication="updateMedicationPrice" 
                    @add-med-into-pharmacy="addMedicationToDB" 
                    @record-deleted="deleteRecordFromDB"
                    @promotion-created = "addPromotion"
                    :medications = "medicationToSend"
                    :pharmacyId = "pharmacyId">
                </MedicationsTable>
            </Tab>
            <Tab :isSelected="selected === 'Orders'">
                <SearchBar
                    :placeHolder="searchOrdersPlaceholder"
                    :options="ordersSortOptions"
                    search-type="orders"
                    @search-performed="ordersSearchPerformed"
                    @sort-performed="ordersSortPerformed"
                />
                <OrdersTable 
                    @order-updated = "orderUpdated"
                    @order-added = "addNewOrder"
                    @new-medications-added-from-order = "addMedIntoPharStorage"
                    @update-quanities-for-medications = "updateQuantities"
                    @order-deleted = "deleteOrder"
                    :orders = "ordersSearchResults"
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
import moment from 'moment';
import StarRating from 'vue-star-rating';
import SearchBar from '../components/SearchBar'

export default {
    name: "PharmacyView",
    components: { 
        Button, 
        DermatologistsTable, 
        MedicationsTable, 
        TabNav, 
        Tab, 
        PharmacistsTable, 
        OrdersTable, 
        ModalWindowEditPharmacyData, 
        ModalWindowMap, 
        StarRating,
        SearchBar
    },
    data() {
        return {
            selected: "Dermatologists",
            pharmacy : { 
                location: {} // morao da dodam zbog rendera
            },
            pharmacyId: null,
            dermatologistsToSend: [],
            pharmacistsToSend: [],
            medicationToSend: [],
            ordersToSend: [],
            // lista svih slobodnih termina treba da se doda
            modalEditPharmacyData : false,
            coordinates : [],
            modalMap : false,

            // search-and-filter orders
            ordersSortOptions : ["--select--", "Duedate", "Admin", "Status"],
            searchOrdersPlaceholder: "Search orders...",
            ordersSearchResults: [],

            // search-and-filter dermatologists
            dermatologistsSortOptions : ["--select--", "Username", "Email", "First name", "Last name", "Gender", "Rating"],
            searchDermatologistsPlaceholder: "Search dermatologists...",
            dermatologistsSearchResults: [],

            // search-and-filter pharmacists
            pharmacistsSortOptions : ["--select--", "Username", "Email", "First name", "Last name", "Gender", "Rating"],
            searchPharmacistsPlaceholder: "Search pharmacists...",
            pharmacistsSearchResults: [],
        };
    },

    methods: {
        checkRoleTEST : function(userRole){
            return this.$store.getters.getLoggedUserRole === userRole;
        },
        setSelected(tab) {
            this.selected = tab;
        },
        deleteRecordFromList : function(data, datumId){
            for(const datumIndex in data){
                if(data[datumIndex].id === datumId){
                    data.splice(datumIndex, 1);
                    break;
                }
            }
            return data;
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
                    price : {
                        priceValue: medicationPrice,
                        hasPromo : false,
                        percentage : 0,
                        price_edit : false
                    },
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
            .then((response) => {
                const pharmacyStorageItem = response.data
                for (const medicationId in this.medicationToSend) {
                    if(pharmacyStorageItem.id === this.medicationToSend[medicationId].id)
                        this.medicationToSend[medicationId].price = this.calculatePrice(pharmacyStorageItem);
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
        deleteOrder : function(order){
            client({
                url: "/order/" + order.id,
                method: "DELETE"
            })
            .then(() => {
                this.ordersToSend = this.deleteRecordFromList(this.ordersToSend, order.id);
                this.ordersSearchResults = this.deleteRecordFromList(this.ordersSearchResults, order.id);
                this.$toasted.show("Order successfuly canceled!", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
            })
        },
        addNewOrder : function(order){
            this.ordersToSend = [...this.ordersToSend, order];
            this.ordersSearchResults = this.ordersToSend;
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
                        price : {
                            priceValue: medication["price"],
                            hasPromo : false,
                            percentage : 0,
                            price_edit : false
                        },
                        medicationId: medication.id,
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
            for (const orderId in this.ordersToSend) {
                const orderToSend = this.ordersToSend[orderId];
                if(orderToSend.id == order.id){
                    this.ordersToSend[orderId].orderStatus = "DONE";
                }
            }
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
        addPromotion : function(promotion){
            for(const promotionItem in promotion.promotionItems){
                promotion.promotionItems[promotionItem]['itemPrices'] = [{
                    price : promotion.promotionItems[promotionItem].price
                }]
            }
            client({
                url: "promotion",
                method: "POST",
                data : promotion
            }).then((response) => {
                for (const promotionItemId in response.data.promotionItems) {
                    const promotionItem = response.data.promotionItems[promotionItemId];
                    for (const medicationId in this.medicationToSend) {
                        if(promotionItem.id === this.medicationToSend[medicationId].id)
                            this.medicationToSend[medicationId].price = this.calculatePrice(promotionItem);
                    }
                }
            });
        },
        calculatePrice : function(pharmacy_item){
            let current_price = {
                priceValue: 0,
                hasPromo : false,
                percentage : 0,
                price_edit : false
            };
            for(const iter of pharmacy_item.itemPrices){
                if(iter.promotion === true && iter.current === true){
                    current_price.priceValue = iter.price;
                    current_price.hasPromo = true;
                    for(const iter2 of pharmacy_item.itemPrices){
                        let promoPriceStartDate = moment().format(iter.timePeriod.startDate, 'YYYY-MM-DD')
                        let oldPriceEndDate = moment().format(iter2.timePeriod.endDate, 'YYYY-MM-DD')
                        let promoPriceStartTime = moment().format(iter.timePeriod.startTime, 'HH:mm:ss');
                        let oldPriceEndTime = moment().format(iter2.timePeriod.endTime, 'HH:mm:ss');
                        if(promoPriceStartDate == oldPriceEndDate && promoPriceStartTime == oldPriceEndTime)
                            current_price.percentage = Math.round((1 - (current_price.priceValue / iter2.price)) * 100);
                    }
                    break;
                }
                else {
                    if(iter.current === true){
                        current_price.priceValue = iter.price;
                        break;
                    }
                }
            }
            return current_price;
        },
        ordersSearchPerformed(text, status) {
            this.searchQuery = text;
            let self = this;

            //first we check the text from the search bar
            let temp = this.ordersToSend.filter(function (order) {
                return (order.dueDate
                    .toLowerCase()
                    .includes(self.searchQuery.toLowerCase()) ||
                        order.pharmacyAdmin
                    .toString()
                    .toLowerCase()
                    .includes(self.searchQuery.toLowerCase()) ||
                        order.orderStatus
                    .toLowerCase()
                    .includes(self.searchQuery.toLowerCase())
                );
            });

            //now we filter the results based on user input rating and city
            temp = temp.filter(function (order) {
                return (order.orderStatus
                    .toLowerCase()
                    .includes(status.toLowerCase())
                );
            });

            if(temp.length === 0)
                this.$toasted.show("There are no mathcing records", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
            else
                this.ordersSearchResults = temp;
        },
        ordersSortPerformed(sortCriterium) {
            if (sortCriterium === this.ordersSortOptions[0]) 
                return;
            else if (sortCriterium === this.ordersSortOptions[1]) {
                this.ordersSearchResults = this.ordersSearchResults.sort(function (a, b) {
                    return moment().format(b.dueDate, "YYYY-MM-DD") < moment().format(a.dueDate, "YYYY-MM-DD") ? 1 : -1;;
                });
            }
            else if (sortCriterium === this.ordersSortOptions[2]) {
                this.ordersSearchResults = this.ordersSearchResults.sort(function (a, b) {
                    return a.pharmacyAdmin > b.pharmacyAdmin ? 1 : -1;
                });
            }
            else if (sortCriterium === this.ordersSortOptions[3]) {
                this.ordersSearchResults = this.ordersSearchResults.sort(function (a, b) {
                    return a.orderStatus > b.orderStatus ? 1 : -1;
                });
            }
        },
        dermatologistsSearchPerformed(text, minRating, maxRating) {
            this.searchQuery = text;
            let self = this;
            //first we check the text from the search bar
            let temp = this.dermatologistsToSend.filter(function (derm) {
                return (derm.username
                    .toLowerCase()
                    .includes(self.searchQuery.toLowerCase()) ||
                        derm.email
                    .toString()
                    .toLowerCase()
                    .includes(self.searchQuery.toLowerCase()) ||
                        derm.firstName
                    .toLowerCase()
                    .includes(self.searchQuery.toLowerCase()) ||
                        derm.lastName
                    .toLowerCase()
                    .includes(self.searchQuery.toLowerCase()) ||
                        derm.gender
                    .toLowerCase()
                    .includes(self.searchQuery.toLowerCase())
                );
            });

            //now we filter the results based on user input rating and city
            temp = temp.filter(function (derm) {
                return ((derm.rating >= minRating && derm.rating <= maxRating));
            });

            if(temp.length === 0)
                this.$toasted.show("There are no mathcing records", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
            else
                this.dermatologistsSearchResults = temp;
        },
        dermatologistsSortPerformed(sortCriterium) {
            if (sortCriterium === this.dermatologistsSortOptions[0]) 
                return;
            else if (sortCriterium === this.dermatologistsSortOptions[1]) {
                this.dermatologistsSearchResults = this.dermatologistsSearchResults.sort(function (a, b) {
                    return a.username > b.username ? 1 : -1;
                });
            }
            else if (sortCriterium === this.dermatologistsSortOptions[2]) {
                this.dermatologistsSearchResults = this.dermatologistsSearchResults.sort(function (a, b) {
                    return a.email > b.email ? 1 : -1;
                });
            }
            else if (sortCriterium === this.dermatologistsSortOptions[3]) {
                this.dermatologistsSearchResults = this.dermatologistsSearchResults.sort(function (a, b) {
                    return a.firstName > b.firstName ? 1 : -1;
                });
            }
            else if (sortCriterium === this.dermatologistsSortOptions[4]) {
                this.dermatologistsSearchResults = this.dermatologistsSearchResults.sort(function (a, b) {
                    return a.lastName > b.lastName ? 1 : -1;
                });
            }
            else if (sortCriterium === this.dermatologistsSortOptions[5]) {
                this.dermatologistsSearchResults = this.dermatologistsSearchResults.sort(function (a, b) {
                    return a.gender > b.gender ? 1 : -1;
                });
            }
            else if (sortCriterium === this.dermatologistsSortOptions[6]) {
                this.dermatologistsSearchResults = this.dermatologistsSearchResults.sort(function (a, b) {
                    return a.rating < b.rating ? 1 : -1;
                });
            }
        },
        pharmacistsSearchPerformed(text, minRating, maxRating) {
            this.searchQuery = text;
            let self = this;
            //first we check the text from the search bar
            let temp = this.pharmacistsToSend.filter(function (phar) {
                return (phar.username
                    .toLowerCase()
                    .includes(self.searchQuery.toLowerCase()) ||
                        phar.email
                    .toString()
                    .toLowerCase()
                    .includes(self.searchQuery.toLowerCase()) ||
                        phar.firstName
                    .toLowerCase()
                    .includes(self.searchQuery.toLowerCase()) ||
                        phar.lastName
                    .toLowerCase()
                    .includes(self.searchQuery.toLowerCase()) ||
                        phar.gender
                    .toLowerCase()
                    .includes(self.searchQuery.toLowerCase())
                );
            });

            //now we filter the results based on user input rating and city
            temp = temp.filter(function (phar) {
                return ((phar.rating >= minRating && phar.rating <= maxRating));
            });

            if(temp.length === 0)
                this.$toasted.show("There are no mathcing records", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
            else
                this.pharmacistsSearchResults = temp;
        },
        pharmacistsSortPerformed(sortCriterium) {
            if (sortCriterium === this.pharmacistsSortOptions[0]) 
                return;
            else if (sortCriterium === this.pharmacistsSortOptions[1]) {
                this.pharmacistsSearchResults = this.pharmacistsSearchResults.sort(function (a, b) {
                    return a.username > b.username ? 1 : -1;
                });
            }
            else if (sortCriterium === this.pharmacistsSortOptions[2]) {
                this.pharmacistsSearchResults = this.pharmacistsSearchResults.sort(function (a, b) {
                    return a.email > b.email ? 1 : -1;
                });
            }
            else if (sortCriterium === this.pharmacistsSortOptions[3]) {
                this.pharmacistsSearchResults = this.pharmacistsSearchResults.sort(function (a, b) {
                    return a.firstName > b.firstName ? 1 : -1;
                });
            }
            else if (sortCriterium === this.pharmacistsSortOptions[4]) {
                this.pharmacistsSearchResults = this.pharmacistsSearchResults.sort(function (a, b) {
                    return a.lastName > b.lastName ? 1 : -1;
                });
            }
            else if (sortCriterium === this.pharmacistsSortOptions[5]) {
                this.pharmacistsSearchResults = this.pharmacistsSearchResults.sort(function (a, b) {
                    return a.gender > b.gender ? 1 : -1;
                });
            }
            else if (sortCriterium === this.pharmacistsSortOptions[6]) {
                this.pharmacistsSearchResults = this.pharmacistsSearchResults.sort(function (a, b) {
                    return a.rating < b.rating ? 1 : -1;
                });
            }
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
                this.dermatologistsSearchResults = this.dermatologistsToSend;
                this.pharmacistsSearchResults = this.pharmacistsToSend;
            }).catch((response) => alert("pharmacyId je los :("));
                // dobavljamo sve lijekove iz apoteke
            client({
                url : "pharmacyStorageItem/fromPharmacy/" + this.pharmacyId,
                method : "GET",
            }).then((response) => {
                let current_price = {};
                for(const pharmacy_item of response.data){
                    current_price = this.calculatePrice(pharmacy_item);
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
                    this.medicationToSend = [...this.medicationToSend, medication];
                }
            }).catch((response) => console.log(response));

            client({
                url : "order/allFrom/" + this.pharmacyId + "/withOffers",
                method : "GET",
            }).then((response) => {
                this.ordersToSend = response.data;
                this.ordersSearchResults = this.ordersToSend
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